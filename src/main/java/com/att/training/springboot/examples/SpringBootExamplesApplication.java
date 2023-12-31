package com.att.training.springboot.examples;

import com.mongodb.MongoClient;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoOperations;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

@SpringBootApplication
@ConfigurationPropertiesScan
@Slf4j
public class SpringBootExamplesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootExamplesApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner(MongoOperations mongoOps, MongoClient mongoClient) {
		return args -> {
			Person person = new Person("Joe", 34);

			// Insert is used to initially store the object into the database.
			mongoOps.insert(person);
			log.info("Insert: " + person);

			// Find
			person = mongoOps.findById(person.getId(), Person.class);
			log.info("Found: " + person);

			// Update
			mongoOps.updateFirst(query(where("name").is("Joe")), update("age", 35), Person.class);
			person = mongoOps.findOne(query(where("name").is("Joe")), Person.class);
			requireNonNull(person);
			log.info("Updated: " + person);

			// Delete
			mongoOps.remove(person);

			// Check that deletion worked
			List<Person> people = mongoOps.findAll(Person.class);
			log.info("Number of people = " + people.size());

			mongoOps.dropCollection(Person.class);

			// Use the mongoClient directly:
			log.info("Mongo databases:");
			for (String dbName : mongoClient.listDatabaseNames()) {
				log.info(dbName);
			}
		};
	}
}

@ToString
@RequiredArgsConstructor
@Getter
class Person {
	private final String name;
	private final int age;
	private String id;
}