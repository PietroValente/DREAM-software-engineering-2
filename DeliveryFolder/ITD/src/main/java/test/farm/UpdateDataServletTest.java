package test.farm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import beans.access.SessionBean;
import beans.farm.ViewDataBean;
import entities.Farm;
import entities.Person;
import servelets.farm.UpdateDataServlet;
import util.AddableHttpRequest;

import javax.servlet.http.*;

//before starting the tests make sure 
//1.have silenced all "RequestDispatcher.forward (request, response)" from testing servlet
class UpdateDataServletTest {

	private AddableHttpRequest request;
	private HttpServletResponse response;
	private UpdateDataServlet servlet;
	private SessionBean SessionBean;
	private ViewDataBean ViewDataBean;
	private Cookie[] requestCookies;
	private long id;

	@BeforeEach
	public void setUp() {
		request = mock(AddableHttpRequest.class);
		response = mock(HttpServletResponse.class);
		servlet = new UpdateDataServlet();
		SessionBean = new SessionBean();
		ViewDataBean = new ViewDataBean();
		id = 1039380987L;

		Person user = new Person();
		user.setId(id);
		long session = SessionBean.getSessionID(user);
		requestCookies = new Cookie[1];
		requestCookies[0] = new Cookie("AccessSession", Long.toString(session));
	}

	// correct update farm data page request
	@Test
	public void testGet1() throws Exception {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime now = LocalDateTime.now();
		String t = dtf.format(now);
		LocalDate today = LocalDate.of(Integer.parseInt(t.substring(0, 4)), Integer.parseInt(t.substring(5, 7)),
				Integer.parseInt(t.substring(8, 10)));
		
		when(request.getCookies()).thenReturn(requestCookies);
		when(request.getParameter("Day")).thenReturn(today.toString());
		
		servlet.doGet(request, response);
		
		verify(request, times(1)).getRequestDispatcher("/core/farm/modify_update.jsp");
	}
	
	// correct update farm data request
	@Test
	public void testPost1() throws Exception {
		Farm farm = ViewDataBean.getFarm(id);
		String Address = "New Street";
		String Phone = "+91 123 456 789";
		int water = 5000;
		when(request.getCookies()).thenReturn(requestCookies);
		when(request.getParameter("FarmID")).thenReturn(Long.toString(farm.getId()));
		when(request.getParameter("Address")).thenReturn(Address);
		when(request.getParameter("Phone")).thenReturn(Phone);
		when(request.getParameter("Water")).thenReturn(Integer.toString(water));
		when(request.getParameter("numberLands")).thenReturn("0");
		
		servlet.doPost(request, response);
		
		verify(request, times(1)).getRequestDispatcher("/viewdata.do");
		
		farm = ViewDataBean.getFarm(id);
		
		assertEquals(farm.getAddress(),Address);
		assertEquals(farm.getPhone(),Phone);
		assertEquals(farm.getWater(),water);
	}

}
