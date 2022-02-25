package beans.askForHelp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ListIterator;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;

import entities.Farm;
import entities.Help_Request;
import util.HibernateUtil;
import util.VARIABLES;

//bean that manages help requests
@Stateful
public class AddHelpRequestBean implements AddHelpRequestBeanRemote {

	@PersistenceContext(unitName = "first_unit")
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("first_unit");
	@SuppressWarnings("unused")
	private EntityManager manager = emf.createEntityManager();

	// method to insert a new request in the system
	@SuppressWarnings("unchecked")
	@Override
	public long addRequest(String subject, String description, long farmer) {
		boolean flag = true;
		long ID = 0;
		while (flag) {
			Transaction transaction = null;
			try (Session session = HibernateUtil.getSessionFactory().openSession()) {
				transaction = session.beginTransaction();
				String query = "SELECT f.id, f.address, f.phone, f.owner, f.agronomist, f.water from Farm f";
				Query q = manager.createNativeQuery(query, Farm.class);
				List<Farm> results = q.getResultList();
				Farm f = new Farm();
				for (ListIterator<Farm> iter = results.listIterator(); iter.hasNext();) {
					Farm tmp = iter.next();
					if (tmp.getOwner() == farmer) {
						f = tmp;
					}
				}
				Help_Request request = new Help_Request();
				request.setSubject(subject);
				request.setDescription(description);
				request.setFarm(f.getId());
				request.setAgronomist(f.getAgronomist());
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDateTime now = LocalDateTime.now();
				String t = dtf.format(now);
				LocalDate today = LocalDate.of(Integer.parseInt(t.substring(0, 4)), Integer.parseInt(t.substring(5, 7)),
						Integer.parseInt(t.substring(8, 10)));
				request.setDate(today);
				ID = (long) Math.floor(Math.random() * (9999999999L - 1000000000L + 1) + 1000000000L);
				Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dream",
						VARIABLES.databaseUser, VARIABLES.databasePassword);
				Statement statement = c.createStatement();
				statement = c.createStatement();
				statement.executeUpdate("INSERT into Help_request values(" + ID + ",'" + request.getFarm() + "','"
						+ request.getAgronomist() + "','" + request.getDate() + "','" + request.getSubject() + "','"
						+ request.getDescription() + "');");
				request.setID(ID);
				manager.persist(request);
				flag = false;
				c.close();
				transaction.commit();
				session.close();
			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
		}
		return ID;
	}
	
	// find request
	public Help_Request findRequest(long id) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			Help_Request request = session.get(Help_Request.class, id);
			transaction.commit();
			session.close();
			return request;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
