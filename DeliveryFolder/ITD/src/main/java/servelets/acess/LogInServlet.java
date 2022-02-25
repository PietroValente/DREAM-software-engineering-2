package servelets.acess;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
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
import util.SetupDatabase;

@WebServlet(description = "Manage log in requests", urlPatterns = { "/loginUser.do" })
public class LogInServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@EJB
	private LogInBeanRemote loginBean;
	private SessionBeanRemote SessionBean;
	private ViewDataBeanRemote ViewDataBean;

	public LogInServlet() {
		SetupDatabase.setup();
		loginBean = new LogInBean();
		SessionBean = new SessionBean();
		ViewDataBean = new ViewDataBean();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(new AddableHttpRequest(request), response);
	}

	public void doPost(AddableHttpRequest request, HttpServletResponse response) throws ServletException, IOException {
		loginBean = new LogInBean();
		SessionBean = new SessionBean();
		ViewDataBean = new ViewDataBean();
		boolean valid = false;
		long id = -1;
		String password = "";
		try {
			id = Long.parseLong(request.getParameter("id"));
			password = request.getParameter("password");
			valid = loginBean.validateUser(id, password);
			long limit = 9999999999L;
			if (id < 1000000000 || id > limit) {
				throw new IllegalArgumentException();
			}
		} catch (Exception e) {
			response.sendRedirect("index.jsp?wrongAccess");
			return;
		}
		if (valid == false) {
			response.sendRedirect("index.jsp?wrongAccess");
		} else {
			Person user = loginBean.getUser(id);
			long sessionID = SessionBean.getSessionID(user);
			request.addParameter("firstname", user.getFirstname());
			request.addParameter("lastname", user.getLastname());
			Cookie AccessSession = new Cookie("AccessSession", Long.toString(sessionID));
			AccessSession.setMaxAge(3600);
			response.addCookie(AccessSession);
			if (user.getCategory().equalsIgnoreCase("Farmer")) {
				String code = Long.toString(id);
				request.addParameter("id", code);
				Farm farm = ViewDataBean.getFarm(user.getId());
				Crop[] crops = ViewDataBean.getOldCrops(farm);
				request.addParameter("CropsNumber", Integer.toString(crops.length));
				for (int i = 0; i < crops.length; i++) {
					request.addParameter("CropDate" + i, crops[i].getDate().toString());
					request.addParameter("CropScore" + i, Integer.toString(crops[i].getScore()));
				}
				RequestDispatcher view1 = request.getRequestDispatcher("/core/access/homepages/farmerHomepage.jsp");
				view1.forward(request, response);
			} else if (user.getCategory().equalsIgnoreCase("Agronomist")) {
				String code = Long.toString(id);
				request.addParameter("id", code);
				RequestDispatcher view1 = request.getRequestDispatcher("/core/access/homepages/agronomistHomepage.jsp");
				view1.forward(request, response);
			} else {
				String code = Long.toString(id);
				request.addParameter("id", code);
				RequestDispatcher view1 = request
						.getRequestDispatcher("/core/access/homepages/policymakerHomepage.jsp");
				view1.forward(request, response);
			}
		}
	}
}
