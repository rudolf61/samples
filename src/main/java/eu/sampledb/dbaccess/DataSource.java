/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.sampledb.dbaccess;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import eu.configuration.AppConfig;
import java.sql.Connection;
import java.sql.SQLException;


/**
 *
 * @author RUUD
 */
class DataSource {
 
    private HikariConfig config = new HikariConfig();
    private HikariDataSource ds;
 
 
    DataSource(AppConfig appConfig) {
        config.setJdbcUrl( appConfig.getValue("sql.db.url") );
        config.setUsername( appConfig.getValue("sql.db.userid") );
        config.setPassword( appConfig.getValue("sql.db.password") );
        config.addDataSourceProperty( "cachePrepStmts" , "true" );
        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        ds = new HikariDataSource( config );
    }
 
    Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
    
    void shutDown() {
        ds.close();
    }
}