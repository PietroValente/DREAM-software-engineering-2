package beans.access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.Transaction;

import entities.Person;

import entities.Code;
import util.HibernateUtil;
import util.VARIABLES;

//bean that manages sign up
@Stateful
public class SignUpBean implements SignUpBeanRemote {

	@PersistenceContext(unitName = "first_unit")
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("first_unit");
	@SuppressWarnings("unused")
	private EntityManager manager = emf.createEntityManager();

	public SignUpBean() {
	}

	// check if a code is valid
	@Override
	public String checkCode(String id) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			Code code = session.get(Code.class, id);
			if (code != null) {
				String type = code.getType();
				session.delete(code);
				transaction.commit();
				session.close();
				return type;
			}
			transaction.commit();
			session.close();
			return null;
		}
	}

	// add a new user
	@Override
	public void addUser(Person user) {
		boolean flag = true;
		long userID = 0;
		while (flag) {
			userID = (long) Math.floor(Math.random() * (9999999999L - 1000000000L + 1) + 1000000000L);
			Transaction transaction = null;
			try (Session session = HibernateUtil.getSessionFactory().openSession()) {
				transaction = session.beginTransaction();
				Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dream",
						VARIABLES.databaseUser, VARIABLES.databasePassword);
				Statement statement = c.createStatement();
				statement = c.createStatement();
				statement.executeUpdate("INSERT into Person values(" + userID + ",'" + user.getFirstname() + "','"
						+ user.getLastname() + "','" + user.getCategory() + "','" + user.getPassword() + "','"
						+ user.getUsername() + "','" + user.getProvince() + "');");
				user.setId(userID);
				c.close();
				transaction.commit();
				session.close();
				flag = false;
			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
		}

	}
}
