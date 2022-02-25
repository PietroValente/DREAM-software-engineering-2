package test.farm;

import servelets.farm.AddLandServlet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.servlet.http.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import beans.access.*;
import beans.farm.*;
import entities.Farm;
import entities.Person;

// before starting the tests make sure 
//1.have silenced all "RequestDispatcher.forward (request, response)" from testing servlet
class AddLandServletTest {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private AddLandServlet servlet;
	private SessionBean SessionBean;
	private ViewDataBean ViewDataBean;

	@BeforeEach
	public void setUp() {
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		servlet = new AddLandServlet();
		SessionBean = new SessionBean();
		ViewDataBean = new ViewDataBean();
	}

	// insert a land
	@Test
	public void testPost1() throws Exception {
		long id = 1039380987L;

		Person user = new Person();
		user.setId(id);
		long session = SessionBean.getSessionID(user);
		Cookie[] requestCookies = new Cookie[1];
		requestCookies[0] = new Cookie("AccessSession", Long.toString(session));

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime now = LocalDateTime.now();
		String t = dtf.format(now);
		LocalDate today = LocalDate.of(Integer.parseInt(t.substring(0, 4)), Integer.parseInt(t.substring(5, 7)),
				Integer.parseInt(t.substring(8, 10)));

		Farm farm = ViewDataBean.getFarm(id);
		int landsBefore = ViewDataBean.getLands(farm, today).length;

		when(request.getParameter("Dimension")).thenReturn("100");
		when(request.getCookies()).thenReturn(requestCookies);

		servlet.doPost(request, response);

		verify(request, times(1)).getRequestDispatcher("/viewdata.do");

		int landsPost = ViewDataBean.getLands(farm, today).length;

		assertEquals(landsPost, landsBefore - 1);
	}
}
