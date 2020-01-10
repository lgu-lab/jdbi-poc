package org.demo.h2;

import java.util.Optional;

import org.demo.entity.User;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.reflect.ConstructorMapper;

public class GetData {

	public static void main(String[] args) {
		
		Jdbi jdbi = Jdbi.create(Const.JDBC_URL);
		
		// Handles represent an active database connection.
		// 1rst connection
		jdbi.useHandle(handle -> {
			Optional<String> s ;
			s = handle.createQuery("select name from contacts where id = :id")
                    .bind("id", 1)
                    .mapTo(String.class)
                    .findOne();
		    System.out.println("name = " + s );

		    s = handle.createQuery("select name from contacts where id = :id")
                    .bind("id", 2)
                    .mapTo(String.class)
                    .findOne();
		    System.out.println("name = " + s );
		});
		
		// 2nd connection : new handle 
		jdbi.useHandle(handle -> {
			Optional<String> name = handle.createQuery("select name from contacts where id = :id")
                    .bind("id", 2)
                    .mapTo(String.class)
                    .findOne();
		    System.out.println("name = " + name );
		});		

		jdbi.useHandle(handle -> {
			Optional<String> name = handle.createQuery("select name from contacts where id = :id")
                    .bind("id", 20)
                    .mapTo(String.class)
                    .findOne();
		    System.out.println("name = " + name );
		});		

		jdbi.useHandle(handle -> {
			handle.registerRowMapper(ConstructorMapper.factory(User.class));
			Optional<User> bean = handle.createQuery("select id, name from contacts where id = :id")
                    .bind("id", 1)
                    .mapTo(User.class)
                    .findOne();
		    System.out.println("bean = " + bean );
		});		

	}

}
