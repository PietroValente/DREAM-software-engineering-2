package test.acess;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import util.AddableHttpRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.servlet.http.*;

import beans.access.*;
import beans.farm.*;
import entities.Farm;
import entities.Person;
import servelets.acess.SignUpFarmServlet;

// before starting the tests make sure: 
// 1.have silenced all "RequestDispatcher.forward (request, response)" from testing servlet
class SignUpFarmServletTest {

	private AddableHttpRequest request;
	private HttpServletResponse response;
	private SignUpFarmServlet servlet;
	private ViewDataBean ViewDataBean;
	private LogInBean LogInBean;
	private SignUpBean SignUpBean;

	@BeforeEach
	public void setUp() {
		request = mock(AddableHttpRequest.class);
		response = mock(HttpServletResponse.class);
		servlet = new SignUpFarmServlet();
	}

	// missing missing parameters during registration, stay on the screen
	@Test
	public void testPost1() throws Exception {
		when(request.getParameter("id")).thenReturn("1234567890");
		when(request.getParameter("province")).thenReturn("Nirmal");
		when(request.getParameter("Address")).thenReturn("");
		when(request.getParameter("Phone")).thenReturn("");
		when(request.getParameter("LandsNumber")).thenReturn("");

		servlet.doPost(request, response);

		verify(request, times(1)).getRequestDispatcher("/core/access/signUpFarm.jsp");
		
		ViewDataBean = new ViewDataBean();
		LogInBean = new LogInBean();
		Farm farm = ViewDataBean.getFarm(1234567890);
		
		assertNull(farm);
	}

	// correct registration of the farm
	@Test
	public void testPost2() throws Exception {
		SignUpBean = new SignUpBean();
		Person user = new Person("ExampleFarmer", "ExampleFarmer", "Farmer", "ExampleFarmerPassword");
		user.setUsername("ex");
		user.setProvince("Nirmal");
		SignUpBean.addUser(user);

		long owner = user.getId();
		String province = "Nirmal";
		String address = "Example Street, 21";
		String phone = "+91 123 456 789";
		int landsNumber = 3;

		when(request.getParameter("id")).thenReturn(Long.toString(owner));
		when(request.getParameter("province")).thenReturn(province);
		when(request.getParameter("Address")).thenReturn(address);
		when(request.getParameter("Phone")).thenReturn(phone);
		when(request.getParameter("LandsNumber")).thenReturn(Integer.toString(landsNumber));

		servlet.doPost(request, response);

		verify(request, times(1)).getRequestDispatcher("/core/access/signUpCompleted.jsp");

		ViewDataBean = new ViewDataBean();
		LogInBean = new LogInBean();
		Farm farm = ViewDataBean.getFarm(owner);

		assertEquals(owner, farm.getOwner());
		assertEquals(province, LogInBean.getUser(farm.getAgronomist()).getProvince());
		assertEquals(province, LogInBean.getUser(farm.getOwner()).getProvince());
		assertEquals(address, farm.getAddress());
		assertEquals(phone, farm.getPhone());

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime now = LocalDateTime.now();
		String t = dtf.format(now);
		LocalDate today = LocalDate.of(Integer.parseInt(t.substring(0, 4)), Integer.parseInt(t.substring(5, 7)),
				Integer.parseInt(t.substring(8, 10)));

		assertEquals(landsNumber, ViewDataBean.getLands(farm, today).length);
	}
}
