package servelets.farm;

import java.io.IOException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.access.SessionBean;
import beans.access.SessionBeanRemote;
import beans.farm.ViewDataBean;
import beans.farm.ViewDataBeanRemote;
import entities.Crop;
import entities.Farm;
import entities.Land;
import util.AddableHttpRequest;

@WebServlet(description = "Manage view data request", urlPatterns = { "/viewdata.do" })
public class ViewDataServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public ViewDataServlet() {
		super();
		SessionBean = new SessionBean();
		ViewDataBean = new ViewDataBean();
	}

	@EJB
	private SessionBeanRemote SessionBean;
	private ViewDataBeanRemote ViewDataBean;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(new AddableHttpRequest(request), response);
	}
	
	public void doGet(AddableHttpRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		long user = SessionBean.farmer(request.getCookies());
		if (user != -1) {
			Farm farm = ViewDataBean.getFarm(user);
			request.addParameter("ID", Long.toString(farm.getId()));
			request.addParameter("Address", farm.getAddress());
			request.addParameter("Phone", farm.getPhone());
			request.addParameter("Agronomist", ViewDataBean.getPerson(farm.getAgronomist()));
			request.addParameter("Water", Long.toString(farm.getWater()) + "L");
			LocalDate day;
			try {
				day = LocalDate.parse(request.getParameter("date"));
			} catch (Exception e) {
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDateTime now = LocalDateTime.now();
				String t = dtf.format(now);
				day = LocalDate.of(Integer.parseInt(t.substring(0, 4)), Integer.parseInt(t.substring(5, 7)),
						Integer.parseInt(t.substring(8, 10)));
			}
			request.addParameter("Day", day.toString());
			Land[] lands = ViewDataBean.getLands(farm, day);
			request.addParameter("LandNumber", Integer.toString(lands.length));
			for (int i = 0; i < lands.length; i++) {
				request.addParameter("Land" + i + "ID", Long.toString(lands[i].getId()));
				request.addParameter("Land" + i + "Dimension", Integer.toString(lands[i].getDimension()));
				request.addParameter("Land" + i + "Empty", Boolean.toString(lands[i].getEmpty()));
				int Humidity = lands[i].getHumidity();
				int Host = lands[i].getHost();
				if (Humidity != -1 && Host != -1) {
					request.addParameter("Land" + i + "Humidity", Long.toString(Humidity));
					request.addParameter("Land" + i + "Host", (ViewDataBean.getProduct(Host)).getName());
				} else {
					request.addParameter("Land" + i + "Humidity", Long.toString(0));
					request.addParameter("Land" + i + "Host", Long.toString(0));
				}
			}
			Crop[] crops = ViewDataBean.getOldCrops(farm);
			request.addParameter("CropsNumber", Integer.toString(crops.length));
			for (int i = 0; i < crops.length; i++) {
				request.addParameter("CropDate" + i, crops[i].getDate().toString());
				request.addParameter("CropProduct" + i, (ViewDataBean.getProduct(crops[i].getProduct())).getName());
				request.addParameter("CropQuantity" + i, Integer.toString(crops[i].getQuantity()));
				request.addParameter("CropScore" + i, Integer.toString(crops[i].getScore()));
			}
			RequestDispatcher view = request.getRequestDispatcher("/core/farm/view_update.jsp");
			view.forward(request, response);
		} else {
			RequestDispatcher view = request.getRequestDispatcher("/index.jsp");
			view.forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
