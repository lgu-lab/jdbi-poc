package org.demo.h2;

import org.jdbi.v3.core.Jdbi;

public class InitDB {

	public static void main(String[] args) {
		
		Jdbi jdbi = Jdbi.create(Const.JDBC_URL);
		
		// Handles represent an active database connection.
		jdbi.useHandle(handle -> {

			System.out.println("create table ... " );
		    handle.execute("create table contacts (id int primary key, name varchar(100))");
		    
			System.out.println("insert in table ... " );
		    handle.execute("insert into contacts (id, name) values (?, ?)", 1, "Alice");
		    handle.execute("insert into contacts (id, name) values (?, ?)", 2, "Bob");
		    handle.execute("insert into contacts (id, name) values (?, ?)", 3, "Bart");
		    
		    Integer n = handle.createQuery("select count(*) from contacts ")
                    .mapTo(Integer.class)
                    .one();
		    System.out.println("count = " + n );

		});
	}

}
