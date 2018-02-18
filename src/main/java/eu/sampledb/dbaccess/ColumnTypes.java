/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.ratemysubject.webhelper.dbaccess;

/**
 *
 * @author RUUD
 */
    /*
    -7 	BIT
-6 	TINYINT
-5 	BIGINT
-4 	LONGVARBINARY 
-3 	VARBINARY
-2 	BINARY
-1 	LONGVARCHAR
0 	NULL
1 	CHAR
2 	NUMERIC
3 	DECIMAL
4 	INTEGER
5 	SMALLINT
6 	FLOAT
7 	REAL
8 	DOUBLE
12 	VARCHAR
91 	DATE
92 	TIME
93 	TIMESTAMP
1111  	OTHER
    */
public enum ColumnTypes {
     	BIT(-7),
 	TINYINT(-6),
 	BIGINT(-5),
 	LONGVARBINARY(-4), 
 	VARBINARY(-3),
 	BINARY(-2),
 	LONGVARCHAR(-1),
 	NULL(0),
 	CHAR(1),
 	NUMERIC(2),
 	DECIMAL(3),
 	INTEGER(4),
 	SMALLINT(5),
 	FLOAT(6),
 	REAL(7),
 	DOUBLE(8),
 	VARCHAR(12),
 	DATE(91),
 	TIME(92),
 	TIMESTAMP(93),
  	OTHER(1111);
        
        private int sqlType;

        private ColumnTypes(int sqlType) {
            this.sqlType = sqlType;
        }

        public int getSqlType() {
            return sqlType;
        }

        public static ColumnTypes deriveSqlType(int type) {

            switch (type) {
                case -7: return	BIT;
                case -6: return	TINYINT;
                case -5: return	BIGINT;
                case -4: return	LONGVARBINARY;
                case -3: return	VARBINARY;
                case -2: return	BINARY;
                case -1: return	LONGVARCHAR;
                case 0:  return NULL;
                case 1: return CHAR;
                case 2: return NUMERIC;
                case 3: return DECIMAL;
                case 4: return INTEGER;
                case 5: return SMALLINT;
                case 6: return FLOAT;
                case 7: return REAL;
                case 8: return DOUBLE;
                case 12: return VARCHAR;
                case 91: return DATE;
                case 92: return TIME;
                case 93: return TIMESTAMP;
            }
            
            return OTHER;
        }
        
    }
