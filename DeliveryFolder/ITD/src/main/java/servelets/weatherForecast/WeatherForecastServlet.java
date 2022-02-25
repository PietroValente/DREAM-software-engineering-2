package servelets.weatherForecast;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.access.SessionBean;
import beans.access.SessionBeanRemote;

@WebServlet(description = "Manage weather forecast request", urlPatterns = { "/weatherforecast.do" })
public class WeatherForecastServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@EJB
	private SessionBeanRemote SessionBean;

	public WeatherForecastServlet() {
		super();
		SessionBean = new SessionBean();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (SessionBean.farmer(request.getCookies()) != 0) {
			response.sendRedirect("https://tsdps.telangana.gov.in/");
		} else {
			RequestDispatcher view = request.getRequestDispatcher("/index.jsp");
			view.forward(request, response);
		}
	}
}
