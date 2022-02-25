package test.acess;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import servelets.acess.LogInServlet;
import util.AddableHttpRequest;

import javax.servlet.http.*;

// before starting the tests make sure 
// 1.have silenced all "RequestDispatcher.forward (request, response)" from testing servlet
class LogInServletTest {

	private AddableHttpRequest request;
	private HttpServletResponse response;
	private LogInServlet servlet;

	@BeforeEach
	public void setUp() {
		request = mock(AddableHttpRequest.class);
		response = mock(HttpServletResponse.class);
		servlet = new LogInServlet();
	}

	// wrong credentials
	@Test
	public void testPost1() throws Exception {
		when(request.getParameter("id")).thenReturn("1");
		when(request.getParameter("password")).thenReturn("wrong");

		servlet.doPost(request, response);

		verify(response, times(1)).sendRedirect("index.jsp?wrongAccess");
	}

	// empty credentials
	@Test
	public void testPost2() throws Exception {
		when(request.getParameter("id")).thenReturn("");
		when(request.getParameter("password")).thenReturn("");

		servlet.doPost(request, response);

		verify(response, times(1)).sendRedirect("index.jsp?wrongAccess");
	}

	// correct credentials farmer
	@Test
	public void testPost3() throws Exception {
		when(request.getParameter("id")).thenReturn("1039380987");
		when(request.getParameter("password")).thenReturn("2RZmk8RBp3");

		servlet.doPost(request, response);

		verify(request, times(1)).getRequestDispatcher("/core/access/homepages/farmerHomepage.jsp");
	}

	// correct credentials agronomist
	@Test
	public void testPost4() throws Exception {
		when(request.getParameter("id")).thenReturn("3790580724");
		when(request.getParameter("password")).thenReturn("cMUYq8Hvn8");

		servlet.doPost(request, response);

		verify(request, times(1)).getRequestDispatcher("/core/access/homepages/agronomistHomepage.jsp");
	}

	// correct credentials policy maker
	@Test
	public void testPost5() throws Exception {
		when(request.getParameter("id")).thenReturn("7567064013");
		when(request.getParameter("password")).thenReturn("R1pQV09bNu");

		servlet.doPost(request, response);

		verify(request, times(1)).getRequestDispatcher("/core/access/homepages/policymakerHomepage.jsp");
	}
}
