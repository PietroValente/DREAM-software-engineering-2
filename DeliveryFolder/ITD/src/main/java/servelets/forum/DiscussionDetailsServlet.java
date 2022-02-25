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
import entities.Comment;
import entities.Discussion;
import util.AddableHttpRequest;

@WebServlet(description = "Manage forum request", urlPatterns = { "/discussionDetails.do" })
public class DiscussionDetailsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public DiscussionDetailsServlet() {
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
			Discussion discussion = ForumBean
					.getSpecificDiscussion(Integer.parseInt(request.getParameter("Discussion")));
			request.addParameter("ID", Long.toString(discussion.getId()));
			String[] person = ViewDataBean.getPerson(discussion.getAuthor()).split("\\s+");
			request.addParameter("Name", person[0]);
			request.addParameter("Surname", person[1]);
			request.addParameter("Subject", discussion.getSubject());
			request.addParameter("Description", discussion.getDescription());
			request.addParameter("Time", discussion.getTimestamp().toString().substring(0, 19));
			Comment[] comments = ForumBean.getComments(discussion);
			request.addParameter("CommentsNumber", Integer.toString(comments.length));
			for (int i = 0; i < comments.length; i++) {
				request.addParameter("CID" + i, Long.toString(comments[i].getId()));
				String[] person2 = ViewDataBean.getPerson(comments[i].getAuthor()).split("\\s+");
				request.addParameter("CName" + i, person2[0]);
				request.addParameter("CSurname" + i, person2[1]);
				request.addParameter("CText" + i, comments[i].getText());
				request.addParameter("CTime" + i, comments[i].getTimestamp().toString().substring(0, 19));
			}
			RequestDispatcher view = request.getRequestDispatcher("/core/forum/discussion_details.jsp");
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
			if (request.getParameter("text")==null || request.getParameter("text").compareTo("") == 0) {
				RequestDispatcher view = request.getRequestDispatcher("/forumGet.do");
				view.forward(request, response);
				return;
			} else {
				Comment comment = new Comment();
				comment.setAuthor(user);
				comment.setText(request.getParameter("text"));
				comment.setDiscussion(Integer.parseInt(request.getParameter("DiscussionID")));
				ForumBean.insertComment(comment);
				RequestDispatcher view = request.getRequestDispatcher("/forumGet.do");
				view.forward(request, response);
			}
		} else {
			RequestDispatcher view = request.getRequestDispatcher("/index.jsp");
			view.forward(request, response);
		}
	}
}
