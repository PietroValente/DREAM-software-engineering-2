package servelets.askForHelp;

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
import beans.askForHelp.AddHelpRequestBean;
import beans.askForHelp.AddHelpRequestBeanRemote;

@WebServlet(description = "Manage ask for help requests", urlPatterns = { "/askforhelp.do", "/sendAskRequest.do" })
public class AskForHelpServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@EJB
	private SessionBeanRemote SessionBean;
	private AddHelpRequestBeanRemote AddHelpRequestBean;

	public AskForHelpServlet() {
		SessionBean = new SessionBean();
		AddHelpRequestBean = new AddHelpRequestBean();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (SessionBean.farmer(request.getCookies()) != 0) {
			RequestDispatcher view = request.getRequestDispatcher("/core/askForHelp/ask_for_help.jsp");
			view.forward(request, response);
		} else {
			RequestDispatcher view = request.getRequestDispatcher("index.jsp");
			view.forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (SessionBean.farmer(request.getCookies()) != 0) {
			if(request.getParameter("Subject").compareTo("") == 0 || request.getParameter("Problems").compareTo("")==0) {
				doGet(request,response);
				return;
			}
			String subject = request.getParameter("Subject");
			String problems = request.getParameter("Problems");
			Long farmer = SessionBean.farmer(request.getCookies());
			AddHelpRequestBean.addRequest(subject, problems, farmer);
			response.sendRedirect("home.do?goodInsert");
		} else {
			RequestDispatcher view = request.getRequestDispatcher("index.jsp");
			view.forward(request, response);
		}
	}

}
