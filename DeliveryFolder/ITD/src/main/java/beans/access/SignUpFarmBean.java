package beans.access;

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

import beans.ranking.ManageRankingBean;
import entities.Farm;
import entities.Land;
import entities.Person;
import entities.Ranking;
import util.HibernateUtil;
import util.VARIABLES;

@Stateful
public class SignUpFarmBean implements SignUpFarmBeanRemote {
	@PersistenceContext(unitName = "first_unit")
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("first_unit");
	@SuppressWarnings("unused")
	private EntityManager manager = emf.createEntityManager();

	public SignUpFarmBean() {
	}

	// add a farm and its lands still empty
	@SuppressWarnings("unchecked")
	@Override
	public void addFarm(Farm farm, String province, String landsnumber) {
		boolean flag = true;
		int lands = Integer.parseInt(landsnumber);
		long agronomist = 0;
		long farmID = 0;
		long landID = 0;
		String category = "agronomist";
		while (flag) {
			farmID = (long) Math.floor(Math.random() * (9999999999L - 1000000000L + 1) + 1000000000L);
			Transaction transaction = null;
			try (Session session = HibernateUtil.getSessionFactory().openSession()) {
				transaction = session.beginTransaction();
				String query = "SELECT p.id, p.firstname, p.lastname, p.category, p.password, p.username, p.province from Person p";
				Query q = manager.createNativeQuery(query, Person.class);
				List<Person> results = q.getResultList();
				for (ListIterator<Person> iter = results.listIterator(); iter.hasNext();) {
					Person p = iter.next();
					if ((p.getCategory().equalsIgnoreCase(category)) && (p.getProvince().equalsIgnoreCase(province))) {
						agronomist = p.getId();
					}
				}
				farm.setAgronomist(agronomist);
				Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dream",
						VARIABLES.databaseUser, VARIABLES.databasePassword);
				Statement statement = c.createStatement();
				statement = c.createStatement();
				statement.executeUpdate(
						"INSERT into Farm values(" + farmID + ",'" + farm.getAddress() + "','" + farm.getPhone() + "','"
								+ farm.getOwner() + "','" + farm.getAgronomist() + "','" + farm.getWater() + "');");
				farm.setId(farmID);
				Ranking r = new Ranking(farmID, 0);
				statement.executeUpdate(
						"INSERT into Ranking values(" + farmID + ",'" + r.getScore() + "','" + r.getPlace() + "');");
				manager.persist(r);
				ManageRankingBean ranking = new ManageRankingBean();
				ranking.updateRanking();
				c.close();
				transaction.commit();
				session.close();
				flag = false;
			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
		}
		flag = true;
		while (flag) {
			landID = (long) Math.floor(Math.random() * (9999999999L - 1000000000L + 1) + 1000000000L);
			Transaction transaction = null;
			try (Session session = HibernateUtil.getSessionFactory().openSession()) {
				transaction = session.beginTransaction();
				Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dream",
						VARIABLES.databaseUser, VARIABLES.databasePassword);
				Statement statement = c.createStatement();
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDateTime now = LocalDateTime.now();
				String t = dtf.format(now);
				LocalDate today = LocalDate.of(Integer.parseInt(t.substring(0, 4)), Integer.parseInt(t.substring(5, 7)),
						Integer.parseInt(t.substring(8, 10)));
				Land newLand = new Land();
				newLand.setDate(today);
				newLand.setFarm(farmID);
				for (int i = 0; i < lands; i++) {
					landID = landID + i;
					statement.executeUpdate("INSERT into Land values(" + landID + ",'" + newLand.getDate() + "', 1 , "
							+ newLand.getFarm() + ");");
				}
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
