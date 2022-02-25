package servelets.ranking;

import java.io.IOException;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.Transaction;

import beans.access.LogInBean;
import beans.access.LogInBeanRemote;
import beans.access.SessionBean;
import beans.access.SessionBeanRemote;
import beans.farm.ViewDataBean;
import beans.farm.ViewDataBeanRemote;
import beans.ranking.ManageRankingBean;
import beans.ranking.ManageRankingBeanRemote;
import entities.Farm;
import entities.Ranking;
import util.AddableHttpRequest;
import util.HibernateUtil;

@WebServlet(description = "Manage view ranking requests", urlPatterns = { "/viewranking.do" })
public class ViewRankingServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public ViewRankingServlet() {
		super();
		SessionBean = new SessionBean();
		ManageRankingBean = new ManageRankingBean();
		ViewDataBean = new ViewDataBean();
		LoginBean = new LogInBean();
	}

	@EJB
	private SessionBeanRemote SessionBean;
	private ManageRankingBeanRemote ManageRankingBean;
	private ViewDataBeanRemote ViewDataBean;
	private LogInBeanRemote LoginBean;

	@PersistenceContext(unitName = "first_unit")
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("first_unit");
	@SuppressWarnings("unused")
	private EntityManager manager = emf.createEntityManager();

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(new AddableHttpRequest(request), response);
	}
	
	public void doGet(AddableHttpRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (SessionBean.farmer(request.getCookies()) != 0) {
			Ranking[] ranking = ManageRankingBean.viewRanking();
			String[] owners = new String[ranking.length];
			String[] provinces = new String[ranking.length];
			Transaction transaction = null;
			for (int i = 0; i < ranking.length; i++) {
				Ranking r = ranking[i];
				long f = r.getFarm();
				try (Session session = HibernateUtil.getSessionFactory().openSession()) {
					transaction = session.beginTransaction();
					Farm farm = session.get(Farm.class, f);
					long owner = farm.getOwner();
					owners[i] = ViewDataBean.getPerson(owner);
					provinces[i] = LoginBean.getUser(owner).getProvince();
					transaction.commit();
					session.close();
				} catch (Exception e) {
				}
			}
			request.addParameter("FarmNumber", Integer.toString(ranking.length));
			for (int i = 0; i < ranking.length; i++) {
				request.addParameter("Farm" + i + "ID", Long.toString(ranking[i].getFarm()));
				request.addParameter("Farm" + i + "Place", Integer.toString(ranking[i].getPlace()));
				request.addParameter("Farm" + i + "Score", Integer.toString(ranking[i].getScore()));
				request.addParameter("Farm" + i + "Owner", owners[i]);
				request.addParameter("Farm" + i + "Province", provinces[i]);
			}
			RequestDispatcher view = request.getRequestDispatcher("/core/ranking/view_ranking.jsp");
			view.forward(request, response);
		} else {
			RequestDispatcher view = request.getRequestDispatcher("/index.jsp");
			view.forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
