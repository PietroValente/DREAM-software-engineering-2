package beans.farm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.Transaction;

import beans.ranking.ManageRankingBean;
import entities.Crop;
import entities.Farm;
import entities.Land;
import entities.Ranking;
import util.HibernateUtil;
import util.VARIABLES;

//bean that manages update of farm data
@Stateful
public class ModifyDataBean implements ModifyDataBeanRemote {

	@PersistenceContext(unitName = "first_unit")
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("first_unit");
	@SuppressWarnings("unused")
	private EntityManager manager = emf.createEntityManager();

	public ModifyDataBean() {
	}

	// updates the farm that has the corresponding farm.id, returns true if the
	// change is successful
	@Override
	public boolean updateFarm(Farm farm) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.merge(farm);
			transaction.commit();
			session.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// updates the land in that date,
	// returns true if the change is successful
	@Override
	public boolean updateLand(Land land) {
		boolean flag = false;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime now = LocalDateTime.now();
		String t = dtf.format(now);
		LocalDate today = LocalDate.of(Integer.parseInt(t.substring(0, 4)), Integer.parseInt(t.substring(5, 7)),
				Integer.parseInt(t.substring(8, 10)));
		Transaction transaction = null;
		if (land.getEmpty()) {
			try (Session session = HibernateUtil.getSessionFactory().openSession()) {
				transaction = session.beginTransaction();
				Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dream",
						VARIABLES.databaseUser, VARIABLES.databasePassword);
				Statement statement = c.createStatement();
				statement = c.createStatement();
				statement.executeUpdate("UPDATE land " + "SET empty = true, humidity = NULL, host = NULL "
						+ "WHERE id =" + land.getId() + "AND date='" + land.getDate().toString() + "';");
				c.close();
				transaction.commit();
				session.close();
				flag = true;
			} catch (Exception e) {
				return flag;
			}
		} else {
			try (Session session = HibernateUtil.getSessionFactory().openSession()) {
				if ((land.getDate().equals(today))) {
					transaction = session.beginTransaction();
					session.merge(land);
					flag = true;
				}
				transaction.commit();
				session.close();
			} catch (Exception e) {
				return flag;
			}
		}

		return flag;
	}

	// delete the relative land from database
	public boolean deleteLand(Land land) {
		boolean flag = false;
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dream", VARIABLES.databaseUser,
					VARIABLES.databasePassword);
			Statement statement = c.createStatement();
			statement = c.createStatement();
			statement.executeUpdate(
					"DELETE FROM land WHERE (id, date) = (" + land.getId() + ",'" + land.getDate() + "');");
			transaction.commit();
			session.close();
			flag = true;
		} catch (Exception e) {
			return flag;
		}
		return flag;
	}

	// insert the relative land on the database
	public void addLand(int dimension, long farmID) {
		boolean flag = true;
		long ID = 0;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime now = LocalDateTime.now();
		String t = dtf.format(now);
		LocalDate today = LocalDate.of(Integer.parseInt(t.substring(0, 4)), Integer.parseInt(t.substring(5, 7)),
				Integer.parseInt(t.substring(8, 10)));
		while (flag) {
			ID = (long) Math.floor(Math.random() * (9999999999L - 1000000000L + 1) + 1000000000L);
			Transaction transaction = null;
			try (Session session = HibernateUtil.getSessionFactory().openSession()) {
				transaction = session.beginTransaction();
				Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dream",
						VARIABLES.databaseUser, VARIABLES.databasePassword);
				Statement statement = c.createStatement();
				statement.executeUpdate(
						"INSERT into land values(" + ID + ",'" + today + "'," + dimension + "," + farmID + ");");
				c.close();
				transaction.commit();
				session.close();
				flag = false;
			} catch (Exception e) {
			}
		}
	}

	// add the new crop
	public void addCrop(Crop crop, Land land) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime now = LocalDateTime.now();
		String t = dtf.format(now);
		LocalDate today = LocalDate.of(Integer.parseInt(t.substring(0, 4)), Integer.parseInt(t.substring(5, 7)),
				Integer.parseInt(t.substring(8, 10)));
		if ((crop.getDate().equals(today))) {
			Transaction transaction = null;
			try (Session session = HibernateUtil.getSessionFactory().openSession()) {
				transaction = session.beginTransaction();
				Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dream",
						VARIABLES.databaseUser, VARIABLES.databasePassword);
				Statement statement = c.createStatement();
				int score = crop.getQuantity() / land.getDimension();
				statement.executeUpdate("INSERT into crop (date,product,quantity,farm,score) values('" + crop.getDate()
						+ "'," + crop.getProduct() + "," + crop.getQuantity() + "," + crop.getFarm() + "," + score
						+ ");");
				Ranking row = session.get(Ranking.class, land.getFarm());
				score = score + row.getScore();
				row.setScore(score);
				session.merge(row);
				c.close();
				transaction.commit();
				session.close();
			} catch (Exception e) {
			}
			ManageRankingBean manageRankingBean = new ManageRankingBean();
			manageRankingBean.updateRanking();
		}
	}
}