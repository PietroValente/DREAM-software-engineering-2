package beans.access;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;

import entities.Person;
import util.HibernateUtil;

//bean that manages log in
@Stateful
public class LogInBean implements LogInBeanRemote {

	@PersistenceContext(unitName = "first_unit")
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("first_unit");
	@SuppressWarnings("unused")
	private EntityManager manager = emf.createEntityManager();

	public LogInBean() {
	}

	// check id and password entered, true if user exist
	@Override
	public boolean validateUser(long id, String pass) {
		boolean valide = false;
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			Query q = manager.createNativeQuery("SELECT password from Person where id =" + id);
			String password = null;
			try {
				password = q.getSingleResult().toString();
			} catch (Exception e) {
				password = null;
			}
			if (password != null && pass.equals(password)) {
				valide = true;
			}
			transaction.commit();
			session.close();
		}
		return valide;
	}

	// returns the user corresponding to the id, null if the user does not exist
	@Override
	public Person getUser(long id) {
		Person person = new Person();
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			person = session.get(Person.class, id);
			transaction.commit();
			session.close();
		} catch (Exception e) {
			return null;
		}
		return person;
	}
}