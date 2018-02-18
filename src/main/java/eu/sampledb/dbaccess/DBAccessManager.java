/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.sampledb.dbaccess;

import eu.configuration.AppConfig;
import eu.ratemysubject.webhelper.dbaccess.DatabaseContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author RUUD
 */
public class DBAccessManager {
    
    public interface ProcessDatabaseContext {
        void processContext(DatabaseContext context) throws SQLException;
    }

    private final DataSource dataSource;

    public interface ProcessConnection {

        void process(Connection connection) throws SQLException;
    }

    public interface ProcessPreparedStatement {

        void process(PreparedStatement statement) throws SQLException;
    }

    public interface ProcessStatement {

        void process(Statement statement) throws SQLException;
    }


    public DBAccessManager(AppConfig appConfig) {
        this.dataSource = new DataSource(appConfig);
    }

    /**
     * Process connection. This method takes care of handling the connection, so
     * you only need to use it.
     *
     * @param processConnection
     * @throws SQLException
     */
    public void processContext(ProcessDatabaseContext processDatabaseContext) throws SQLException {

        try (Connection connection = dataSource.getConnection()) {
            DatabaseContext context = new DatabaseContext(connection);
            processDatabaseContext.processContext(context);
        }
    }


    public void shutDown() {
        dataSource.shutDown();
    }

}
