package test.farm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import beans.access.SessionBean;
import beans.farm.ViewDataBean;
import entities.Farm;
import entities.Person;
import servelets.farm.DeleteLandServlet;

import javax.servlet.ServletException;
import javax.servlet.http.*;

// before starting the tests make sure 
// 1.have silenced all "RequestDispatcher.forward (request, response)" from testing servlet
// 2.the farm of the user whose id is entered has at least 1 land
class DeleteLandServletTest {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private DeleteLandServlet servlet;
	private SessionBean SessionBean;
	private ViewDataBean ViewDataBean;
	private Cookie[] requestCookies;
	private long id;
	
	@BeforeEach 
	public void setUp() {
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		servlet = new DeleteLandServlet();
		SessionBean = new SessionBean();
		ViewDataBean = new ViewDataBean();
		
		id = 1039380987L;
		Person user = new Person();
		user.setId(id);
		long session = SessionBean.getSessionID(user);
		requestCookies = new Cookie[1];
		requestCookies[0] = new Cookie("AccessSession", Long.toString(session));
	}
	
	// correct deletion of a land
	@Test
	void testPost1() throws ServletException, IOException {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime now = LocalDateTime.now();
		String t = dtf.format(now);
		LocalDate today = LocalDate.of(Integer.parseInt(t.substring(0, 4)), Integer.parseInt(t.substring(5, 7)),
				Integer.parseInt(t.substring(8, 10)));
		
		Farm farm = ViewDataBean.getFarm(id);
		int landsBefore = ViewDataBean.getLands(farm, today).length;
		
		when(request.getParameter("LandID")).thenReturn(Long.toString(ViewDataBean.getLands(farm, today)[0].getId()));
		when(request.getCookies()).thenReturn(requestCookies);
		
		servlet.doPost(request, response);
		
		verify(request, times(1)).getRequestDispatcher("/viewdata.do");
		
		int landsPost = ViewDataBean.getLands(farm, today).length;
		
		assertEquals(landsPost,landsBefore-1);
		
	}

}
