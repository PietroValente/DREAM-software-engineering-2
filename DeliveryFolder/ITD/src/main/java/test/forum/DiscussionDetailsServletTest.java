package test.forum;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import beans.access.SessionBean;
import beans.forum.ForumBean;
import entities.Comment;
import entities.Discussion;
import entities.Person;
import servelets.forum.DiscussionDetailsServlet;
import util.AddableHttpRequest;

import javax.servlet.http.*;

//before starting the tests make sure 
//1.have silenced all "RequestDispatcher.forward (request, response)" from testing servlet
class DiscussionDetailsServletTest {

	private AddableHttpRequest request;
	private HttpServletResponse response;
	private DiscussionDetailsServlet servlet;
	private SessionBean SessionBean;
	private ForumBean ForumBean;
	private Cookie[] requestCookies;
	private long id;
	
	@BeforeEach
	public void setUp() {
		request = mock(AddableHttpRequest.class);
		response = mock(HttpServletResponse.class);
		servlet = new DiscussionDetailsServlet();
		SessionBean = new SessionBean();
		ForumBean = new ForumBean();
		id = 1039380987L;

		Person user = new Person();
		user.setId(id);
		long session = SessionBean.getSessionID(user);
		requestCookies = new Cookie[1];
		requestCookies[0] = new Cookie("AccessSession", Long.toString(session));
	}
	
	// correct view of a discussion in detail
	@Test
	public void testGet1() throws Exception {
		int discussion = 1;
		
		when(request.getCookies()).thenReturn(requestCookies);
		when(request.getParameter("Discussion")).thenReturn(Integer.toString(discussion));
		
		servlet.doGet(request, response);
		
		verify(request, times(1)).getRequestDispatcher("/core/forum/discussion_details.jsp");
	}
	
	// insert an empty comment
	@Test
	public void testPost1() throws Exception {
		int discussion = 1;
		
		when(request.getCookies()).thenReturn(requestCookies);
		when(request.getParameter("DiscussionID")).thenReturn(Integer.toString(discussion));
		when(request.getParameter("text")).thenReturn("");
		
		servlet.doPost(request, response);
		
		verify(request, times(1)).getRequestDispatcher("/forumGet.do");
	}
	
	// comment insertion successfully
	@Test
	public void testPost2() throws Exception {
		int discussionID = 1;
		
		String text = "Test";
		when(request.getCookies()).thenReturn(requestCookies);
		when(request.getParameter("DiscussionID")).thenReturn(Integer.toString(discussionID));
		when(request.getParameter("text")).thenReturn(text);
		
		servlet.doPost(request, response);
		
		verify(request, times(1)).getRequestDispatcher("/forumGet.do");
		
		Discussion discussion = new Discussion();
		discussion.setId(discussionID);
		Comment[] comments = ForumBean.getComments(discussion);
		boolean flag = false;
		for (int i = 0; i < comments.length; i++) {
			if(comments[i].getText().compareTo(text) == 0) {
				flag = true;
			}
		}
		
		assertEquals(flag,true);
	}

}
