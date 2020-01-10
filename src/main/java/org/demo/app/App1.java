package org.demo.app;

import java.util.Optional;

import org.demo.entity.User;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.reflect.ConstructorMapper;
import org.jdbi.v3.core.statement.Update;

public class App1 {

	public static void main(String[] args) {
		
		// JDBI with connexion / H2 in-memory database
		// NB : add ";DB_CLOSE_DELAY=-1" at the end in order to keep database tables between connections 
		// see https://stackoverflow.com/questions/5763747/h2-in-memory-database-table-not-found/5936988 
		Jdbi jdbi = Jdbi.create("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
		
//		// JDBI with datasource 
//		DataSource ds = null ;
//		Jdbi jdbiDS = Jdbi.create(ds);
		
		// Handles represent an active database connection.
		// 1rst connection
		jdbi.useHandle(handle -> {
		    handle.execute("create table contacts (id int primary key, name varchar(100))");
		    handle.execute("insert into contacts (id, name) values (?, ?)", 1, "Alice");
		    handle.execute("insert into contacts (id, name) values (?, ?)", 2, "Bob");
		    handle.execute("insert into contacts (id, name) values (?, ?)", 3, "Bart");
		    
		    String name = handle.createQuery("select name from contacts where id = :id")
                    .bind("id", 1)
                    .mapTo(String.class)
                    .one();
		    System.out.println("name = " + name );

		    name = handle.createQuery("select name from contacts where id = :id")
                    .bind("id", 2)
                    .mapTo(String.class)
                    .one();
		    System.out.println("name = " + name );
		});
		
		// 2nd connection : new handle 
		jdbi.useHandle(handle -> {
		    String name = handle.createQuery("select name from contacts where id = :id")
                    .bind("id", 2)
                    .mapTo(String.class)
                    .one();
		    System.out.println("name = " + name );
		});		

		// CRUD : INSERT
		jdbi.useHandle(handle -> {
			System.out.println("insert with bindBean()");
			User bean = new User(20, "John");
		    Update update = handle.createUpdate("insert into contacts (id, name) values (:id, :name)")
                    .bindBean(bean);
		    
            int r = update.execute();
		    System.out.println("r = " + r );
		});		

		// CRUD : UPDATE
		jdbi.useHandle(handle -> {
			System.out.println("update with bindBean()");
			User bean = new User(20, "John bis");
		    Update update = handle.createUpdate("update contacts set name = :name where id = :id")
                    .bindBean(bean);
		    
            int r = update.execute();
		    System.out.println("r = " + r );
		});		

		jdbi.useHandle(handle -> {
		    String name = handle.createQuery("select name from contacts where id = :id")
                    .bind("id", 20)
                    .mapTo(String.class)
                    .one();
		    System.out.println("name = " + name );
		});		

		jdbi.useHandle(handle -> {
			System.out.println("delete with bindBean()");
			User bean = new User(20, "John bis");
		    Update update = handle.createUpdate("delete contacts where id = :id")
                    .bindBean(bean);
		    
            int r = update.execute();
		    System.out.println("r = " + r );
		});		

		// CRUD : DELETE
		jdbi.useHandle(handle -> {
			System.out.println("delete with bindBean()");
			User bean = new User(20, "John bis");
		    Update update = handle.createUpdate("delete contacts where id = :id")
                    .bindBean(bean);
		    
            int r = update.execute();
		    System.out.println("r = " + r );
		});		

		// CRUD : SELECT
		jdbi.useHandle(handle -> {
			handle.registerRowMapper(ConstructorMapper.factory(User.class));
			Optional<User> bean = handle.createQuery("select id, name from contacts where id = :id")
                    .bind("id", 1)
                    .mapTo(User.class)
                    .findOne();
		    System.out.println("bean = " + bean );
		});		

//		// new handle = new connection 
//		jdbi.useHandle(handle -> {
//			handle.registerRowMapper(ConstructorMapper.factory(User.class));
////			List<User> = 
////					handle.createQuery("select id, name from contacts where id = :id")
////                    .bind("id", 20)
////                    .mapToBean(User.class)
////                    .list() ;
//
//			User bean = handle.createQuery("select id, name from contacts where id = :id")
//                    .bind("id", 20)
//                    .mapToBean(User.class);
//		    //System.out.println("user  = " + bean );
//		});		

	}

}
