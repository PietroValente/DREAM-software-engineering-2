package servelets.farm;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import beans.access.SessionBean;
import beans.access.SessionBeanRemote;
import beans.farm.ModifyDataBean;
import beans.farm.ModifyDataBeanRemote;
import beans.farm.ViewDataBean;
import beans.farm.ViewDataBeanRemote;
import entities.Farm;

@WebServlet(description = "Manage land insertion", urlPatterns = { "/addLand.do" })
public class AddLandServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public AddLandServlet() {
		super();
		SessionBean = new SessionBean();
		ViewDataBean = new ViewDataBean();
		ModifyDataBean = new ModifyDataBean();
	}

	@EJB
	private SessionBeanRemote SessionBean;
	private ViewDataBeanRemote ViewDataBean;
	private ModifyDataBeanRemote ModifyDataBean;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long user = SessionBean.farmer(request.getCookies());
		if (user != -1) {
			RequestDispatcher view = request.getRequestDispatcher("/core/farm/add_land.jsp");
			view.forward(request, response);
		} else {
			RequestDispatcher view = request.getRequestDispatcher("/index.jsp");
			view.forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long user = SessionBean.farmer(request.getCookies());
		if (user != -1) {
			Farm farm = ViewDataBean.getFarm(user);
			ModifyDataBean.addLand(Integer.parseInt(request.getParameter("Dimension")), farm.getId());
			RequestDispatcher view = request.getRequestDispatcher("/viewdata.do");
			view.forward(request, response);
		} else {
			RequestDispatcher view = request.getRequestDispatcher("/index.jsp");
			view.forward(request, response);
		}
	}
}
