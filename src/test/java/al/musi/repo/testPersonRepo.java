package al.musi.repo;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import org.h2.tools.Server;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import al.musi.osoba.Person;

public class PersonRepositoryTest {
	private static final Logger log = LoggerFactory.getLogger(PersonRepositoryTest.class);
	private static PersonRepository repository;
	private static Server server;

	@BeforeClass
	public static void beforeClass() throws SQLException {
		server = Server.createTcpServer().start();
		repository = new PersonRepositoryImpl();
	}

	@Before
	public void before() {
		Person person = new Person();
		person.setFirstName("Adam");
		person.setLastName("Kowalski");
		person.setCreatedDate(LocalDateTime.now());
		person.setDateOfBirth(LocalDate.of(1999, Month.JANUARY, 1));
		repository.create(person);
	}

	@Test
	public void testCreate() {
		Person person = new Person();
		person.setFirstName("Imie");
		person.setLastName("Nazwisko");
		person.setCreatedDate(LocalDateTime.now());
		person.setDateOfBirth(LocalDate.of(1982, Month.APRIL, 4));

		person = repository.create(person);
		log.info("Created person: {}", person);

		assertThat(person.getId(), is(3L));
	}

	@Test
	public void testUpdate() {
		Person person = repository.read(1L);
		person.setModifiedDate(LocalDateTime.now());
		person.setFirstName("updejt");

		person = repository.update(person);
		person = null;

		person = repository.read(1L);
		assertNotNull(person);
		assertThat(person.getFirstName(), is("updejt"));
	}

	@Test
	public void testRead() {
		Person person = repository.read(1L);

		assertNotNull(person);
		assertThat(person.getFirstName(), is("Adam"));
	}

	@Test
	public void testDelete() {
		Person person = repository.read(2L);

		assertNotNull(person);
		repository.delete(person);

		person = repository.read(2L);
		assertNull(person);
	}

	@AfterClass
	public static void afterClass() {
		repository.close();
		server.stop();
	}
}
