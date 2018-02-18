/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.sampledb.dbaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.Consumer;

/**
 * Wrapper for connection. Purpose is to keep the responsibilty of cleaning up within this module.
 * 
 * 
 * @author RUUD
 */
public class DatabaseContext {
    
    public interface ProcessStatement {
        void processStatement(Statement statement) throws SQLException;
    }

    public interface ProcessPreparedStatement {
        void processPreparedStatement(PreparedStatement statement) throws SQLException;
        
    }

    public enum ResultSetType {
        TYPE_FORWARD_ONLY(ResultSet.TYPE_FORWARD_ONLY), TYPE_SCROLL_INSENSITIVE(ResultSet.TYPE_SCROLL_INSENSITIVE), TYPE_SCROLL_SENSITIVE(ResultSet.TYPE_SCROLL_SENSITIVE);

        private int cursorType;

        private ResultSetType(int type) {
            this.cursorType = type;
        }

        public int getCursorType() {
            return cursorType;
        }
    };

    public enum ResultSetConcurrency {
        CONCUR_READ_ONLY(ResultSet.CONCUR_READ_ONLY), CONCUR_UPDATABLE(ResultSet.CONCUR_UPDATABLE);
        private int concurrencyType;

        private ResultSetConcurrency(int concurrencyType) {
            this.concurrencyType = concurrencyType;
        }

        public int getConcurrencyType() {
            return concurrencyType;
        }

    };

    public enum ResultSetHoldability {
        HOLD_CURSORS_OVER_COMMIT(ResultSet.HOLD_CURSORS_OVER_COMMIT), CLOSE_CURSORS_AT_COMMIT(ResultSet.CLOSE_CURSORS_AT_COMMIT);
        private int holdability;

        private ResultSetHoldability(int holdability) {
            this.holdability = holdability;
        }

        public int getHoldability() {
            return holdability;
        }

    };

    private Connection connection;

    public DatabaseContext(Connection connection) {
        this.connection = connection;
    }

    public void createStatement(ProcessStatement processStatement) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            processStatement.processStatement(statement);
        }
    }

    public void createStatement(ResultSetType resultSetType, ResultSetConcurrency resultSetConcurrency, ProcessStatement processStatement) throws SQLException {
        try (Statement statement = connection.createStatement(resultSetType.getCursorType(), resultSetConcurrency.getConcurrencyType())) {
            processStatement.processStatement(statement);
        }
    }

    public void createStatement(ResultSetType resultSetType, ResultSetConcurrency resultSetConcurrency, ResultSetHoldability resultSetHoldability, ProcessStatement processStatement) throws SQLException {
        try (Statement statement = connection.createStatement(resultSetType.getCursorType(), resultSetConcurrency.getConcurrencyType(), resultSetHoldability.getHoldability())) {
            processStatement.processStatement(statement);
        }
    }

    public void createPreparedStatement(String sql, boolean returnGeneratedKeys, ProcessPreparedStatement processPreparedStatement) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS)) {
            processPreparedStatement.processPreparedStatement(preparedStatement);
        }
    }

    public void createPreparedStatement(String sql, ResultSetType resultSetType, ResultSetConcurrency resultSetConcurrency, ProcessPreparedStatement processStatement) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(sql, resultSetType.getCursorType(), resultSetConcurrency.getConcurrencyType())) {
            processStatement.processPreparedStatement(statement);
        }
    }

    public void createPreparedStatement(String sql, ResultSetType resultSetType, ResultSetConcurrency resultSetConcurrency, ResultSetHoldability resultSetHoldability, ProcessPreparedStatement processStatement) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(sql, resultSetType.getCursorType(), resultSetConcurrency.getConcurrencyType(), resultSetHoldability.getHoldability())) {
            processStatement.processPreparedStatement(statement);
        }
    }

}
