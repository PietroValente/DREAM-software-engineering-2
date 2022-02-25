package test.weatherForecast;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import beans.access.SessionBean;
import entities.Person;
import servelets.weatherForecast.WeatherForecastServlet;

import javax.servlet.http.*;

//before starting the tests make sure 
//1.have silenced all "RequestDispatcher.forward (request, response)" from testing servlet
class WeatherForecastServletTest {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private WeatherForecastServlet servlet;
	private SessionBean SessionBean;
	private Cookie[] requestCookies;
	private long id;

	@BeforeEach
	public void setUp() {
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		servlet = new WeatherForecastServlet();
		SessionBean = new SessionBean();
		id = 1039380987L;

		Person user = new Person();
		user.setId(id);
		long session = SessionBean.getSessionID(user);
		requestCookies = new Cookie[1];
		requestCookies[0] = new Cookie("AccessSession", Long.toString(session));
	}

	// correct weather forecast redirect
	@Test
	public void testGet1() throws Exception {
		when(request.getCookies()).thenReturn(requestCookies);
		
		servlet.doGet(request, response);
		
		verify(response, times(1)).sendRedirect("https://tsdps.telangana.gov.in/");
	}
}