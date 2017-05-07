package al.musi.repo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import al.musi.osoba.Person;

public class PersonRepositoryImpl implements PersonRepository {
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("al.musi.jacek.tutorial");
	private EntityManager em;

	public PersonRepositoryImpl() {
		em = emf.createEntityManager();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Person create(Person person) {
		em.getTransaction().begin();
		em.persist(person);
		em.getTransaction().commit();
		return person;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Person read(Long id) {
		em.getTransaction().begin();
		Person person = em.find(Person.class, id);
		em.getTransaction().commit();
		return person;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Person update(Person person) {
		em.getTransaction().begin();
		person = em.merge(person);
		em.getTransaction().commit();
		return person;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(Person person) {
		em.getTransaction().begin();
		em.remove(person);
		em.getTransaction().commit();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void close() {
		emf.close();
	}
}
