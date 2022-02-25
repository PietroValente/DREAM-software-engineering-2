package servelets.farm;

import java.io.IOException;
import java.time.LocalDate;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import beans.access.SessionBean;
import beans.access.SessionBeanRemote;
import beans.farm.ModifyDataBean;
import beans.farm.ModifyDataBeanRemote;
import beans.farm.ViewDataBean;
import beans.farm.ViewDataBeanRemote;
import entities.Crop;
import entities.Farm;
import entities.Land;
import util.AddableHttpRequest;

@WebServlet(description = "Manage view data request", urlPatterns = { "/modifyupdate.do" })
public class UpdateDataServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@EJB
	private SessionBeanRemote SessionBean;
	private ViewDataBeanRemote ViewDataBean;
	private ModifyDataBeanRemote ModifyDataBean;
	
	public UpdateDataServlet() {
		super();
		SessionBean = new SessionBean();
		ViewDataBean = new ViewDataBean();
		ModifyDataBean = new ModifyDataBean();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(new AddableHttpRequest(request), response);
	}
	
	public void doGet(AddableHttpRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		long user = SessionBean.farmer(request.getCookies());
		if (user != 0) {
			Farm farm = ViewDataBean.getFarm(user);
			request.addParameter("ID", Long.toString(farm.getId()));
			request.addParameter("Address", farm.getAddress());
			request.addParameter("Phone", farm.getPhone());
			request.addParameter("Agronomist", ViewDataBean.getPerson(farm.getAgronomist()));
			request.addParameter("Water", Long.toString(farm.getWater()));
			LocalDate day = LocalDate.parse(request.getParameter("Day"));
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
					request.addParameter("Land" + i + "Host", ViewDataBean.getProduct(Host).getName());
					request.addParameter("Land" + i + "HostNumber",Integer.toString(Host));
				} else {
					request.addParameter("Land" + i + "Humidity", Long.toString(0));
					request.addParameter("Land" + i + "Host", Long.toString(0));
				}
			}
			RequestDispatcher view = request.getRequestDispatcher("/core/farm/modify_update.jsp");
			view.forward(request, response);
		} else {
			RequestDispatcher view = request.getRequestDispatcher("/index.jsp");
			view.forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		long user = SessionBean.farmer(request.getCookies());
		boolean modify = true;
		if (user != 0) {
			Farm farm = ViewDataBean.getFarm(user);
			if (farm.getId() != Long.parseLong(request.getParameter("FarmID"))) {
				response.sendRedirect("index.jsp?genericError");
			}
			if (request.getParameter("Address") != "") {
				farm.setAddress(request.getParameter("Address"));
			}
			if (request.getParameter("Phone") != "") {
				farm.setPhone(request.getParameter("Phone"));
			}
			if (request.getParameter("Water") != "") {
				farm.setWater(Integer.parseInt(request.getParameter("Water")));
			}
			modify = modify && ModifyDataBean.updateFarm(farm);
			for (int i = 0; i < Integer.parseInt(request.getParameter("numberLands")); i++) {
				if (request.getParameter("Dimension" + i) != "" && request.getParameter("Humidity" + i) != ""
						&& request.getParameter("Host" + i) != "") {
					if (request.getParameter("Crop" + i) == null
							|| request.getParameter("Crop" + i).compareTo("") == 0) {
						Land land = new Land();
						land.setId(Long.parseLong(request.getParameter("LandID" + i)));
						land.setDate(LocalDate.parse(request.getParameter("date")));
						land.setEmpty(false);
						land.setFarm(farm.getId());
						land.setDimension(Integer.parseInt(request.getParameter("Dimension" + i)));
						land.setHumidity(Integer.parseInt(request.getParameter("Humidity" + i)));
						land.setHost(Integer.parseInt(request.getParameter("Host" + i)));
						modify = modify && ModifyDataBean.updateLand(land);
					} else {
						Land land = new Land(Long.parseLong(request.getParameter("LandID" + i)),
								LocalDate.parse(request.getParameter("date")),
								Integer.parseInt(request.getParameter("Dimension" + i)), farm.getId());
						modify = modify && ModifyDataBean.updateLand(land);
						Crop crop = new Crop();
						crop.setDate(LocalDate.parse(request.getParameter("date")));
						crop.setProduct(Integer.parseInt(request.getParameter("Host" + i)));
						crop.setQuantity(Integer.parseInt(request.getParameter("Crop" + i)));
						crop.setFarm(farm.getId());
						ModifyDataBean.addCrop(crop, land);
					}
				}
			}
			RequestDispatcher view = request.getRequestDispatcher("/viewdata.do");
			view.forward(request, response);
		} else {
			RequestDispatcher view = request.getRequestDispatcher("/index.jsp");
			view.forward(request, response);
		}
	}
}
