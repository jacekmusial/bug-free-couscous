package al.musi;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import al.musi.osoba.Person;
import al.musi.repo.PersonRepositoryImpl;

public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		Server server = null;
		PersonRepositoryImpl repository = null;
		try {
			server = Server.createTcpServer().start(); 

			Person person = new Person();
			person.setFirstName("Adam");
			person.setLastName("Kowalski");
			person.setCreatedDate(LocalDateTime.now());
			person.setDateOfBirth(LocalDate.of(1999, Month.JANUARY, 1));

			repository = new PersonRepositoryImpl();
			repository.create(person);

			person = repository.read(1L);

			person.setModifiedDate(LocalDateTime.now());
			person.setFirstName("Janusz");
			repository.update(person);

			person = repository.read(1L);
			repository.delete(person);

			person = repository.read(1L);
		} catch (SQLException e) {
			log.error("Error occurred in initialization: " + e.getMessage());
			e.printStackTrace();
		} finally {
			log.info("Closing Entity Manager Factory");
			if (repository != null)
				repository.close();
			log.info("Entity Manager Factory closed ");
			log.info("Server shutting down");
			if (server != null)
				server.stop();
			log.info("Shutdown complete");
		}
	}

}
