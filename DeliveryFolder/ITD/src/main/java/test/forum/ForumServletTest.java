package test.forum;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import beans.access.SessionBean;
import beans.forum.ForumBean;
import entities.Discussion;
import entities.Person;
import servelets.forum.ForumServlet;
import util.AddableHttpRequest;

//before starting the tests make sure 
//1.have silenced all "RequestDispatcher.forward (request, response)" from testing servlet
class ForumServletTest {

	private AddableHttpRequest request;
	private HttpServletResponse response;
	private ForumServlet servlet;
	private SessionBean SessionBean;
	private ForumBean ForumBean;
	private Cookie[] requestCookies;
	private long id;

	@BeforeEach
	public void setUp() {
		request = mock(AddableHttpRequest.class);
		response = mock(HttpServletResponse.class);
		servlet = new ForumServlet();
		SessionBean = new SessionBean();
		ForumBean = new ForumBean();
		id = 1039380987L;

		Person user = new Person();
		user.setId(id);
		long session = SessionBean.getSessionID(user);
		requestCookies = new Cookie[1];
		requestCookies[0] = new Cookie("AccessSession", Long.toString(session));
	}

	// correct forum request
	@Test
	public void testGet1() throws Exception {
		when(request.getCookies()).thenReturn(requestCookies);
		
		servlet.doGet(request, response);
		
		verify(request, times(1)).getRequestDispatcher("/core/forum/forum.jsp");
	}
	
	// miss body request
	@Test
	public void testPost1() throws Exception {
		when(request.getServletPath()).thenReturn("/forum.do");
		when(request.getCookies()).thenReturn(requestCookies);
		when(request.getParameter("title")).thenReturn("");
		
		servlet.doPost(request, response);
		
		verify(request, times(1)).getRequestDispatcher("/core/forum/forum.jsp");
	}
	
	// from post to get
	@Test
	public void testPost2() throws Exception {
		when(request.getServletPath()).thenReturn("/forumGet.do");
		when(request.getCookies()).thenReturn(requestCookies);
		
		servlet.doPost(request, response);
		
		verify(request, times(1)).getRequestDispatcher("/core/forum/forum.jsp");
	}
	
	// good discussion insert
	@Test
	public void testPost3() throws Exception {
		String title = "testTitle";
		String body = "testBody";
		when(request.getServletPath()).thenReturn("/forum.do");
		when(request.getCookies()).thenReturn(requestCookies);
		when(request.getParameter("title")).thenReturn(title);
		when(request.getParameter("body")).thenReturn(body);
		
		servlet.doPost(request, response);
		
		verify(request, times(1)).getRequestDispatcher("/core/forum/forum.jsp");
		
		Discussion[] discussion = ForumBean.getDiscussions();
		boolean flag = false;
		for (int i = 0; i < discussion.length; i++) {
			if(discussion[i].getSubject().compareTo(title) == 0 && discussion[i].getDescription().compareTo(body) == 0) {
				flag = true;
			}
		}
		
		assertEquals(flag,true);
	}
}
