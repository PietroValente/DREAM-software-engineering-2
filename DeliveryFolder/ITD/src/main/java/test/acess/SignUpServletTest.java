package test.acess;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import servelets.acess.SignUpServlet;
import util.AddableHttpRequest;

import javax.servlet.http.*;

// before starting the tests make sure: 
// 1.have silenced all "RequestDispatcher.forward (request, response)" from testing servlet
// 2.having restarted the database in order to have all the registration codes available
class SignUpServletTest {

	private AddableHttpRequest request;
	private HttpServletResponse response;
	private SignUpServlet servlet;

	@BeforeEach
	public void setUp() {
		request = mock(AddableHttpRequest.class);
		response = mock(HttpServletResponse.class);
		servlet = new SignUpServlet();
	}

	// wrong code
	@Test
	public void testPost1() throws Exception {
		when(request.getServletPath()).thenReturn("/checkCode.do");
		when(request.getParameter("code")).thenReturn("wrong");

		servlet.doPost(request, response);

		verify(response, times(1)).sendRedirect("index.jsp?wrongCode");
	}

	// correct code farmer
	@Test
	public void testPost2() throws Exception {
		when(request.getServletPath()).thenReturn("/checkCode.do");
		when(request.getParameter("code")).thenReturn("!*QT14&qrd");

		servlet.doPost(request, response);

		verify(request, times(1)).getRequestDispatcher("/core/access/signUpFarmer.jsp");
	}

	// correct code agronomist
	@Test
	public void testPost3() throws Exception {
		when(request.getServletPath()).thenReturn("/checkCode.do");
		when(request.getParameter("code")).thenReturn("9dEeRduTn%");

		servlet.doPost(request, response);

		verify(request, times(1)).getRequestDispatcher("/core/access/signUpAgronomist.jsp");
	}

	// correct code policy maker
	@Test
	public void testPost4() throws Exception {
		when(request.getServletPath()).thenReturn("/checkCode.do");
		when(request.getParameter("code")).thenReturn("jEA95$cuh0");

		servlet.doPost(request, response);

		verify(request, times(1)).getRequestDispatcher("/core/access/signUpPolicyMaker.jsp");
	}

	// code not reusable
	@Test
	public void testPost5() throws Exception {
		AddableHttpRequest request2 = mock(AddableHttpRequest.class);
		HttpServletResponse response2 = mock(HttpServletResponse.class);

		when(request.getServletPath()).thenReturn("/checkCode.do");
		when(request.getParameter("code")).thenReturn("kBh^X!oQYr");

		servlet.doPost(request, response);

		when(request2.getServletPath()).thenReturn("/checkCode.do");
		when(request2.getParameter("code")).thenReturn("kBh^X!oQYr");

		servlet.doPost(request2, response2);

		verify(request, times(1)).getRequestDispatcher("/core/access/signUpFarmer.jsp");
		verify(response2, times(1)).sendRedirect("index.jsp?wrongCode");
	}

	// missing missing parameters during registration, stay on the screen
	@Test
	public void testPost6() throws Exception {
		when(request.getServletPath()).thenReturn("/CreateUser.do");
		when(request.getParameter("firstname")).thenReturn("ExampleName");
		when(request.getParameter("lastname")).thenReturn("ExampleLastname");
		when(request.getParameter("category")).thenReturn("Farmer");
		when(request.getParameter("username")).thenReturn("");
		when(request.getParameter("province")).thenReturn("");
		when(request.getParameter("password")).thenReturn("");

		servlet.doPost(request, response);

		verify(request, times(1)).getRequestDispatcher("/core/access/signUpFarmer.jsp");
	}

	// correct registration farmer
	@Test
	public void testPost7() throws Exception {
		when(request.getServletPath()).thenReturn("/CreateUser.do");
		when(request.getParameter("firstname")).thenReturn("ExampleName");
		when(request.getParameter("lastname")).thenReturn("ExampleLastname");
		when(request.getParameter("category")).thenReturn("Farmer");
		when(request.getParameter("username")).thenReturn("ExampleUsername");
		when(request.getParameter("province")).thenReturn("Khammam");
		when(request.getParameter("password")).thenReturn("ExamplePassword");

		servlet.doPost(request, response);

		verify(request, times(1)).getRequestDispatcher("/core/access/signUpFarm.jsp");
	}

	// correct registration agronomist
	@Test
	public void testPost8() throws Exception {
		when(request.getServletPath()).thenReturn("/CreateUser.do");
		when(request.getParameter("firstname")).thenReturn("ExampleName2");
		when(request.getParameter("lastname")).thenReturn("ExampleLastname2");
		when(request.getParameter("category")).thenReturn("Agronomist");
		when(request.getParameter("province")).thenReturn("Medak");
		when(request.getParameter("password")).thenReturn("ExamplePassword2");

		servlet.doPost(request, response);

		verify(request, times(1)).getRequestDispatcher("/core/access/signUpCompleted.jsp");
	}

	// correct registration policy maker
	@Test
	public void testPost9() throws Exception {
		when(request.getServletPath()).thenReturn("/CreateUser.do");
		when(request.getParameter("firstname")).thenReturn("ExampleName3");
		when(request.getParameter("lastname")).thenReturn("ExampleLastname3");
		when(request.getParameter("category")).thenReturn("PolicyMaker");
		when(request.getParameter("password")).thenReturn("ExamplePassword3");

		servlet.doPost(request, response);

		verify(request, times(1)).getRequestDispatcher("/core/access/signUpCompleted.jsp");
	}
}
