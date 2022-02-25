package beans.farm;

import java.time.LocalDate;
import java.util.List;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import entities.Crop;
import entities.Farm;
import entities.Land;
import entities.Product;
import util.HibernateUtil;

//bean that manages view of farm data
@Stateful
public class ViewDataBean implements ViewDataBeanRemote {

	@PersistenceContext(unitName = "first_unit")
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("first_unit");
	@SuppressWarnings("unused")
	private EntityManager manager = emf.createEntityManager();

	public ViewDataBean() {
	}

	// returns the farm associated with the user
	@Override
	public Farm getFarm(long user) {
		Farm farm = new Farm();
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			Query q = manager.createNativeQuery("SELECT id from Farm where owner =" + user);
			long id = 0;
			try {
				id = Long.parseLong(q.getSingleResult().toString());
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error, user not linked to a farm");
			}
			transaction = session.beginTransaction();
			farm = session.get(Farm.class, id);
			transaction.commit();
			session.close();
		}
		return farm;
	}

	// returns the name and surname associated with the user
	@Override
	public String getPerson(long user) {
		Transaction transaction = null;
		String name = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			name = manager.createNativeQuery("SELECT firstname from Person where id =" + user).getSingleResult()
					.toString();
			name = name + " " + manager.createNativeQuery("SELECT lastname from Person where id =" + user)
					.getSingleResult().toString();
			transaction.commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error, agronomist not found");
		}
		return name;
	}

	// returns the lands associated with a farm on a certain date
	@SuppressWarnings("unchecked")
	@Override
	public Land[] getLands(Farm farm, LocalDate date) {
		Transaction transaction = null;
		Land[] lands;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			List<java.math.BigInteger> landList = manager
					.createNativeQuery("SELECT id FROM Land where farm=" + farm.getId() + " AND date='" + date + "'")
					.getResultList();
			lands = new Land[landList.size()];
			for (int i = 0; i < landList.size(); i++) {
				Land tmp = new Land(landList.get(i).longValue(), date);
				lands[i] = session.get(Land.class, tmp);
			}
			transaction.commit();
			session.close();
			return lands;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// returns the product object associated to id
	@Override
	public Product getProduct(int id) {
		Product product = new Product();
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			product = session.get(Product.class, id);
			transaction.commit();
			session.close();
		}
		return product;
	}

	// returns the old crops associated with a farm
	@SuppressWarnings("unchecked")
	@Override
	public Crop[] getOldCrops(Farm farm) {
		Transaction transaction = null;
		Crop[] crops;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			List<java.math.BigInteger> cropList = manager
					.createNativeQuery("SELECT id FROM crop where farm=" + farm.getId() + " ORDER BY date DESC")
					.getResultList();
			crops = new Crop[cropList.size()];
			for (int i = 0; i < cropList.size(); i++) {
				crops[i] = session.get(Crop.class, cropList.get(i).longValue());
			}
			transaction.commit();
			session.close();
			return crops;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}