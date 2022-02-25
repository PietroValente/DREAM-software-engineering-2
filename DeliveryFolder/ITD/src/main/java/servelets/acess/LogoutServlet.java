package servelets.acess;

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

@WebServlet(description = "Manage logout request", urlPatterns = { "/logout.do" })
public class LogoutServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@EJB
	private SessionBeanRemote SessionBean;

	public LogoutServlet() {
		super();
		SessionBean = new SessionBean();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long user = SessionBean.farmer(request.getCookies());
		if (user != -1) {
			SessionBean.deleteSessionID(request.getCookies());
			RequestDispatcher view = request.getRequestDispatcher("/index.jsp");
			view.forward(request, response);
		} else {
			RequestDispatcher view = request.getRequestDispatcher("/index.jsp");
			view.forward(request, response);
		}
	}
}
