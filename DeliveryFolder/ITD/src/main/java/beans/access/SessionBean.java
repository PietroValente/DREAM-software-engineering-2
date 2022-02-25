package beans.access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.Cookie;

import org.hibernate.Session;
import org.hibernate.Transaction;

import entities.Person;
import util.HibernateUtil;
import util.VARIABLES;

//bean that manages session control
@Stateful
public class SessionBean implements SessionBeanRemote {

	@PersistenceContext(unitName = "first_unit")
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("first_unit");
	@SuppressWarnings("unused")
	private EntityManager manager = emf.createEntityManager();

	public SessionBean() {
	}

	// checks the validity of the AccessSession cookie and returns the associated
	// user ID,
	// or -1 if the session does not exist
	@Override
	public long farmer(Cookie[] cookies) {
		if (cookies == null) {
			return 0;
		}
		long sessionAccess = session(cookies);
		if (sessionAccess == -1) {
			return -1;
		}
		EntityManager manager = Persistence.createEntityManagerFactory("first_unit").createEntityManager();
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			Query q = manager.createNativeQuery("SELECT farmer from SessionID where id =" + sessionAccess);
			transaction.commit();
			session.close();
			try {
				return Long.parseLong(q.getSingleResult().toString());
			} catch (Exception e) {
				return -1;
			}
		}
	}

	// extracts the AccessSession cookie code, or returns -1 if it doesn't exist
	@Override
	public long session(Cookie[] cookies) {
		if (cookies == null) {
			return -1;
		}
		for (int i = 0; i < cookies.length; i++) {
			if (cookies[i].getName().compareTo("AccessSession") == 0) {
				return Long.parseLong(cookies[i].getValue());
			}
		}
		return -1;
	}

	// generates a new SessionAccess value to be associated with Person p
	@Override
	public long getSessionID(Person p) {
		boolean flag = true;
		long sessionID = 0;
		while (flag) {
			sessionID = (long) Math.floor(Math.random() * (9999999999L - 1000000000L + 1) + 1000000000L);
			Transaction transaction = null;
			try (Session session = HibernateUtil.getSessionFactory().openSession()) {
				transaction = session.beginTransaction();
				Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dream",
						VARIABLES.databaseUser, VARIABLES.databasePassword);
				Statement statement = c.createStatement();
				statement.executeUpdate("INSERT into sessionid values(" + sessionID + "," + p.getId() + ");");
				c.close();
				transaction.commit();
				session.close();
				flag = false;
			} catch (Exception e) {
			}
		}
		return sessionID;
	}

	// delete the SessionAccess value
	@Override
	public void deleteSessionID(Cookie[] cookies) {
		long sessionID = session(cookies);
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dream", VARIABLES.databaseUser,
					VARIABLES.databasePassword);
			Statement statement = c.createStatement();
			statement = c.createStatement();
			statement.executeUpdate("DELETE FROM sessionid WHERE id = " + sessionID + ";");
			transaction.commit();
			session.close();
		} catch (Exception e) {
		}
	}
}