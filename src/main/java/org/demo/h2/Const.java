package org.demo.h2;

public class Const {

	
	// NB : add ";DB_CLOSE_DELAY=-1" at the end in order to keep database tables between connections 
	// see https://stackoverflow.com/questions/5763747/h2-in-memory-database-table-not-found/5936988 

	
//	public static final String JDBC_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";  // in memory
	
//	public static final String JDBC_URL = "jdbc:h2:./test;DB_CLOSE_DELAY=-1";  // in file
	public static final String JDBC_URL = "jdbc:h2:./test";  // in file
	

}
