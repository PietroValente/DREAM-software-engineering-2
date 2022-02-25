package servelets.forum;

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
import beans.farm.ViewDataBean;
import beans.farm.ViewDataBeanRemote;
import beans.forum.ForumBean;
import beans.forum.ForumBeanRemote;
import entities.Discussion;
import util.AddableHttpRequest;

@WebServlet(description = "Manage forum request", urlPatterns = { "/forum.do", "/forumGet.do" })
public class ForumServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public ForumServlet() {
		super();
		SessionBean = new SessionBean();
		ForumBean = new ForumBean();
		ViewDataBean = new ViewDataBean();
	}

	@EJB
	private SessionBeanRemote SessionBean;
	private ForumBeanRemote ForumBean;
	private ViewDataBeanRemote ViewDataBean;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(new AddableHttpRequest(request), response);
	}

	public void doGet(AddableHttpRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		long user = SessionBean.farmer(request.getCookies());
		if (user != -1) {
			Discussion[] discussion = ForumBean.getDiscussions();
			request.addParameter("DiscussionsNumber", Integer.toString(discussion.length));
			for (int i = 0; i < discussion.length; i++) {
				request.addParameter("ID" + i, Long.toString(discussion[i].getId()));
				String[] person = ViewDataBean.getPerson(discussion[i].getAuthor()).split("\\s+");
				request.addParameter("Name" + i, person[0]);
				request.addParameter("Surname" + i, person[1]);
				request.addParameter("Subject" + i, discussion[i].getSubject());
				request.addParameter("Description" + i, discussion[i].getDescription());
				request.addParameter("Time" + i, discussion[i].getTimestamp().toString().substring(0, 19));
			}
			RequestDispatcher view = request.getRequestDispatcher("/core/forum/forum.jsp");
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
			if (request.getServletPath().equals("/forumGet.do")) {
				doGet(request, response);
				return;
			}
			if ((request.getParameter("title").compareTo("") != 0)
					&& (request.getParameter("body").compareTo("") != 0)) {
				Discussion discussion = new Discussion();
				discussion.setAuthor(user);
				discussion.setSubject(request.getParameter("title"));
				discussion.setDescription(request.getParameter("body"));
				ForumBean.insertDiscussion(discussion);
				doGet(request, response);
			} else {
				doGet(request, response);
			}
		} else {
			RequestDispatcher view = request.getRequestDispatcher("/index.jsp");
			view.forward(request, response);
		}
	}
}
