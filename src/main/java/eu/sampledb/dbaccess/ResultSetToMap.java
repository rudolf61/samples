/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.sampledb.dbaccess;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 *
 * Helper class for converting ResultSet
 * 
 * @author RUUD
 */
public class ResultSetToMap {

    /**
     * Immutable key for Map.
     */
    public static class ColumnKey {

        private String name;
        private ColumnTypes type;

        public ColumnKey(String name, ColumnTypes type) {
            this.name = name;
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public ColumnTypes getType() {
            return type;
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 79 * hash + Objects.hashCode(this.name);
            hash = 79 * hash + Objects.hashCode(this.type);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final ColumnKey other = (ColumnKey) obj;
            if (!Objects.equals(this.name, other.name)) {
                return false;
            }
            if (this.type != other.type) {
                return false;
            }
            return true;
        }

    }

    /**
     * Given a ResultSet, create a list of objects of type T. It is the
     * responsibility of Function convert to convert all instances.
     *
     * @param <T>
     * @param result
     * @param convert
     * @return
     * @throws SQLException
     */
    public static <T> List<T> convertResultSet(ResultSet result, Function<ResultSet, T> convert) throws SQLException {
        List<T> resultSet = new ArrayList<>();

        while (result.next()) {
            T t = convert.apply(result);
            resultSet.add(t);
        }

        return resultSet;

    }

    /**
     * Convert resultset to a list of Map entries, corresponding to the columns.
     * The key consists of the type and column name.
     *
     * @param result
     * @return
     * @throws SQLException
     */
    public static List<Map<ColumnKey, Object>> convertResultSetToMap(ResultSet result) throws SQLException {
        List<Map<ColumnKey, Object>> resultList = new ArrayList<>();

        ResultSetMetaData metaData = result.getMetaData();
        int colCount = metaData.getColumnCount();
        while (result.next()) {
            Map<ColumnKey, Object> resultSet = new HashMap<>();
            for (int col = 1; col <= colCount; col++) {
                resultSet.put(new ColumnKey(metaData.getColumnName(col), ColumnTypes.deriveSqlType(metaData.getColumnType(col))), result.getObject(col));
            }
            if (resultSet.size() > 0) {
                resultList.add(resultSet);
            }
        }

        return resultList;

    }

}
