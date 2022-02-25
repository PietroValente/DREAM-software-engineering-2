package beans.report;

import java.time.LocalDate;
import java.util.ArrayList;
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
import entities.Report;
import util.HibernateUtil;

//bean that manages view reports requests
@Stateful
public class ViewReportBean implements ViewReportBeanRemote {

	@PersistenceContext(unitName = "first_unit")
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("first_unit");
	@SuppressWarnings("unused")
	private EntityManager manager = emf.createEntityManager();

	// allows to show all the reports related to a farm
	@SuppressWarnings("unchecked")
	@Override
	public Report[] viewAllReports(long farmer) {
		Report[] reports;
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			String query1 = "SELECT f.id, f.address, f.phone, f.owner, f.agronomist, f.water from Farm f";
			Query q1 = manager.createNativeQuery(query1, Farm.class);
			List<Farm> results1 = q1.getResultList();
			Farm f = new Farm();
			for (ListIterator<Farm> iter = results1.listIterator(); iter.hasNext();) {
				Farm farm = iter.next();
				if (farm.getOwner() == farmer) {
					f = farm;
				}
			}
			Long farm = f.getId();
			String query = "SELECT r.id, r.farm, r.agronomist, r.date, r.advice, r.description from Report r order by date DESC";
			Query q = manager.createNativeQuery(query, Report.class);
			List<Report> results = q.getResultList();
			List<Report> report = new ArrayList<Report>();
			int i = 0;
			for (ListIterator<Report> iter = results.listIterator(); iter.hasNext();) {
				Report r = iter.next();
				if (r.getFarm() == farm) {
					report.add(r);
				}
			}
			reports = new Report[report.size()];
			for (ListIterator<Report> iter = report.listIterator(); iter.hasNext();) {
				Report r = iter.next();
				reports[i] = r;
				i++;
			}
			transaction.commit();
			session.close();
			return reports;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// allows to see the reports related to a certain date
	@Override
	public Report[] viewAllReports(long farmer, LocalDate day) {
		Report[] reports;
		Report[] allReports = viewAllReports(farmer);
		ArrayList<Report> report = new ArrayList<Report>();
		int counter = 0;
		for (int i = 0; i < allReports.length; i++) {
			if (allReports[i].getDate().toString().equals(day.toString())) {
				report.add(allReports[i]);
				counter++;
			}
		}
		reports = new Report[counter];
		int j = 0;
		for (ListIterator<Report> iter = report.listIterator(); iter.hasNext();) {
			Report r = iter.next();
			reports[j] = r;
			j++;
		}
		return reports;
	}
}
