package beans.ranking;

import java.math.BigInteger;
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

import entities.Ranking;
import util.HibernateUtil;

//bean that manages ranking
@Stateful
public class ManageRankingBean implements ManageRankingBeanRemote {

	@PersistenceContext(unitName = "first_unit")
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("first_unit");
	@SuppressWarnings("unused")
	private EntityManager manager = emf.createEntityManager();

	public ManageRankingBean() {

	}

	// keeps the ranking updated
	@SuppressWarnings("unchecked")
	@Override
	public void updateRanking() {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			String query = "SELECT r.farm, r.place, r.score from Ranking r order by score DESC";
			Query q = manager.createNativeQuery(query, Ranking.class);
			List<Ranking> results = q.getResultList();
			int position = 1;
			for (ListIterator<Ranking> iter = results.listIterator(); iter.hasNext();) {
				Ranking r = iter.next();
				r.setPlace(position);
				session.update(r);
				position++;
			}
			transaction.commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// allows to see the ranking
	@SuppressWarnings("unchecked")
	@Override
	public Ranking[] viewRanking() {
		Transaction transaction = null;
		Ranking[] ranking;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			List<BigInteger> rankingList = manager
					.createNativeQuery("SELECT farm FROM ranking ORDER BY place ASC").getResultList();
			ranking = new Ranking[rankingList.size()];
			for (int i = 0; i < rankingList.size(); i++) {
				ranking[i] = session.get(Ranking.class, rankingList.get(i).longValue());
			}
			transaction.commit();
			session.close();
			return ranking;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
