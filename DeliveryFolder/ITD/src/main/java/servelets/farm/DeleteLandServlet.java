package servelets.farm;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import entities.Land;
import util.AddableHttpRequest;

@WebServlet(description = "Manage land elimination", urlPatterns = { "/deleteLand.do" })
public class DeleteLandServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@EJB
	private SessionBeanRemote SessionBean;
	private ViewDataBeanRemote ViewDataBean;
	private ModifyDataBeanRemote ModifyDataBean;
	
	public DeleteLandServlet() {
		super();
		SessionBean = new SessionBean();
		ViewDataBean = new ViewDataBean();
		ModifyDataBean = new ModifyDataBean();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(new AddableHttpRequest(request), response);
	}
	
	public void doGet(AddableHttpRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		long user = SessionBean.farmer(request.getCookies());
		if (user != -1) {
			Farm farm = ViewDataBean.getFarm(user);
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDateTime now = LocalDateTime.now();
			String t = dtf.format(now);
			LocalDate day = LocalDate.of(Integer.parseInt(t.substring(0, 4)), Integer.parseInt(t.substring(5, 7)),
					Integer.parseInt(t.substring(8, 10)));
			Land[] lands = ViewDataBean.getLands(farm, day);
			for (int i = 0; i < lands.length; i++) {
				if (lands[i].getId() == Long.parseLong(request.getParameter("LandID"))) {
					request.addParameter("LandID", Long.toString(lands[i].getId()));
					request.addParameter("LandDimension", Integer.toString(lands[i].getDimension()));
					if (!lands[i].getEmpty()) {
						RequestDispatcher view = request.getRequestDispatcher("/core/farm/view_update.jsp");
						view.forward(request, response);
					}
				}
			}
			RequestDispatcher view = request.getRequestDispatcher("/core/farm/delete_land.jsp");
			view.forward(request, response);
		} else {
			RequestDispatcher view = request.getRequestDispatcher("/index.jsp");
			view.forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		long user = SessionBean.farmer(request.getCookies());
		if (user != -1) {
			Farm farm = ViewDataBean.getFarm(user);
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDateTime now = LocalDateTime.now();
			String t = dtf.format(now);
			LocalDate day = LocalDate.of(Integer.parseInt(t.substring(0, 4)), Integer.parseInt(t.substring(5, 7)),
					Integer.parseInt(t.substring(8, 10)));
			Land[] lands = ViewDataBean.getLands(farm, day);
			for (int i = 0; i < lands.length; i++) {
				if (lands[i].getEmpty() && lands[i].getId() == Long.parseLong(request.getParameter("LandID"))) {
					lands[i].setDate(day);
					ModifyDataBean.deleteLand(lands[i]);
				}
			}
			RequestDispatcher view = request.getRequestDispatcher("/viewdata.do");
			view.forward(request, response);
		} else {
			RequestDispatcher view = request.getRequestDispatcher("/index.jsp");
			view.forward(request, response);
		}
	}
}
