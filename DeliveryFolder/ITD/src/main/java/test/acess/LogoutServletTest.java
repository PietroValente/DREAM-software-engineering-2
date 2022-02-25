package test.acess;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import beans.access.SessionBean;
import entities.Person;
import servelets.acess.LogoutServlet;

import javax.servlet.http.*;

//before starting the tests make sure 
//1.have silenced all "RequestDispatcher.forward (request, response)" from testing servlet
class LogoutServletTest {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private LogoutServlet servlet;
	private SessionBean SessionBean;
	private Cookie[] requestCookies;
	private long id;

	@BeforeEach
	public void setUp() {
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		servlet = new LogoutServlet();
		SessionBean = new SessionBean();
		id = 1039380987L;

		Person user = new Person();
		user.setId(id);
		long session = SessionBean.getSessionID(user);
		requestCookies = new Cookie[1];
		requestCookies[0] = new Cookie("AccessSession", Long.toString(session));
	}

	// log out request completed successfully
	@Test
	public void testGet1() throws Exception {
		when(request.getCookies()).thenReturn(requestCookies);

		servlet.doGet(request, response);

		verify(request, times(1)).getRequestDispatcher("/index.jsp");

		Person user = new Person();
		user.setId(id);
		assertEquals(SessionBean.farmer(requestCookies), -1);
	}
}
