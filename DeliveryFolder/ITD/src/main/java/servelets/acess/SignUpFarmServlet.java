package servelets.acess;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import beans.access.SignUpFarmBean;
import beans.access.SignUpFarmBeanRemote;
import entities.Farm;
import util.AddableHttpRequest;

@WebServlet(description = "Manage sign up of a Farm", urlPatterns = { "/CreateFarm.do" })
public class SignUpFarmServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@EJB
	private SignUpFarmBeanRemote signupFarmBean;

	public SignUpFarmServlet() {
		super();
		signupFarmBean = new SignUpFarmBean();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(new AddableHttpRequest(request), response);
	}

	public void doPost(AddableHttpRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		String province = request.getParameter("province");
		String address = request.getParameter("Address");
		String phone = request.getParameter("Phone");
		String landsnumber = request.getParameter("LandsNumber");
		if (address.compareTo("") == 0 || phone.compareTo("") == 0 || landsnumber.compareTo("") == 0) {
			RequestDispatcher view = request.getRequestDispatcher("/core/access/signUpFarm.jsp");
			view.forward(request, response);
			return;
		}
		Long ownerID = Long.parseLong(id);
		Farm farm = new Farm();
		farm.setOwner(ownerID);
		farm.setAddress(address);
		farm.setPhone(phone);
		signupFarmBean.addFarm(farm, province, landsnumber);
		RequestDispatcher view = request.getRequestDispatcher("/core/access/signUpCompleted.jsp");
		view.forward(request, response);
	}

}
