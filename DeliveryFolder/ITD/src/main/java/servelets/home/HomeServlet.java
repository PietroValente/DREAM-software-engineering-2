package servelets.home;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.access.LogInBean;
import beans.access.LogInBeanRemote;
import beans.access.SessionBean;
import beans.access.SessionBeanRemote;
import beans.farm.ViewDataBean;
import beans.farm.ViewDataBeanRemote;
import entities.Crop;
import entities.Farm;
import entities.Person;
import util.AddableHttpRequest;

@WebServlet(description = "Manage home request", urlPatterns = { "/home.do" })
public class HomeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public HomeServlet() {
		super();
		SessionBean = new SessionBean();
		loginBean = new LogInBean();
		ViewDataBean = new ViewDataBean();
	}

	@EJB
	private SessionBeanRemote SessionBean;
	private LogInBeanRemote loginBean;
	private ViewDataBeanRemote ViewDataBean;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(new AddableHttpRequest(request), response);
	}
	
	public void doGet(AddableHttpRequest request, HttpServletResponse response) throws ServletException, IOException {
		long user = SessionBean.farmer(request.getCookies());
		if (user != -1) {
			Person farmer = loginBean.getUser(user);
			request.addParameter("firstname", farmer.getFirstname());
			request.addParameter("lastname", farmer.getLastname());
			Farm farm = ViewDataBean.getFarm(user);
			Crop[] crops = ViewDataBean.getOldCrops(farm);
			request.addParameter("CropsNumber", Integer.toString(crops.length));
			for (int i = 0; i < crops.length; i++) {
				request.addParameter("CropDate" + i, crops[i].getDate().toString());
				request.addParameter("CropScore" + i, Integer.toString(crops[i].getScore()));
			}
			RequestDispatcher view = request.getRequestDispatcher("/core/access/homepages/farmerHomepage.jsp");
			view.forward(request, response);
		} else {
			RequestDispatcher view = request.getRequestDispatcher("/index.jsp");
			view.forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
