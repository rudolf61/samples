/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.ratemysubject.test;

import eu.configuration.AppConfig;
import eu.sampledb.dbaccess.DBAccessManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.Assert;
import org.junit.Test;

/**
 * (c) 2018, Ruud de Grijs
 *
 * @author RUUD
 */
public class TestDBAccess {

    private static final String CREATE_PRS = "CREATE TABLE PRS(ID INTEGER, NAME VARCHAR(64))";
    private static final String INSERT_PRS = "INSERT INTO PRS(ID, NAME) VALUES(?, ?)";
    private static final String SELECT_PRS = "SELECT ID, NAME FROM PRS WHERE ID = ?";

    @Test
    public void testDatabaseManager() throws SQLException {
        AppConfig appConfig = AppConfig.getInstance();
        DBAccessManager dbam = new DBAccessManager(appConfig);
        try {
            dbam.processContext((context) -> {
                Assert.assertNotNull(context);
                context.createStatement((statement) -> {
                    statement.executeUpdate(CREATE_PRS);
                });
            });
            dbam.processContext((context) -> {
                Assert.assertNotNull(context);
                context.createPreparedStatement(INSERT_PRS, false, (statement) -> {
                    statement.setInt(1, 1000);
                    statement.setString(2, "Jam Bond");
                    int count = statement.executeUpdate();
                    Assert.assertEquals(1, count);
                });
            });
            dbam.processContext((context) -> {
                Assert.assertNotNull(context);
                context.createPreparedStatement(INSERT_PRS, false, (statement) -> {
                    statement.setInt(1, 2000);
                    statement.setString(2, "Jane Bond");
                    int count = statement.executeUpdate();
                    Assert.assertEquals(1, count);
                });
            });
            dbam.processContext((context) -> {
                Assert.assertNotNull(context);
                context.createPreparedStatement(SELECT_PRS, false, (statement) -> {
                    statement.setInt(1, 1000);
                    ResultSet result = statement.executeQuery();
                    Assert.assertTrue(result.next());
                    Assert.assertEquals("Jam Bond", result.getString("NAME"));
                });
            });
        } finally {
            dbam.shutDown();
        }
    }

}
