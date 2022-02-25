package servelets.acess;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import beans.access.SignUpBean;
import beans.access.SignUpBeanRemote;
import entities.Person;
import util.AddableHttpRequest;

@WebServlet(description = "Manage sign up requests", urlPatterns = { "/checkCode.do", "/CreateUser.do",
		"/continue.do" })
public class SignUpServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@EJB
	private SignUpBeanRemote signupBean;

	public SignUpServlet() {
		super();
		signupBean = new SignUpBean();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(new AddableHttpRequest(request), response);
	}

	public void doPost(AddableHttpRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		if (action.equals("/checkCode.do")) {
			String cod = request.getParameter("code");
			if (cod == null || cod.compareTo("") == 0) {
				response.sendRedirect("index.jsp?wrongCode");
				return;
			}
			String type = signupBean.checkCode(cod);
			if (type != null) {
				if (type.compareTo("Farmer") == 0) {
					RequestDispatcher view = request.getRequestDispatcher("/core/access/signUpFarmer.jsp");
					view.forward(request, response);
					return;
				} else if (type.compareTo("Agronomist") == 0) {
					RequestDispatcher view = request.getRequestDispatcher("/core/access/signUpAgronomist.jsp");
					view.forward(request, response);
					return;
				} else {
					RequestDispatcher view = request.getRequestDispatcher("/core/access/signUpPolicyMaker.jsp");
					view.forward(request, response);
					return;
				}
			} else {
				response.sendRedirect("index.jsp?wrongCode");
			}
		} else if (action.equals("/continue.do")) {
			RequestDispatcher view = request.getRequestDispatcher("index.jsp");
			view.forward(request, response);
		} else if (action.equals("/CreateUser.do")) {
			long code = 0;
			String firstname = request.getParameter("firstname");
			String lastname = request.getParameter("lastname");
			String category = request.getParameter("category");
			String username = request.getParameter("username");
			String province = request.getParameter("province");
			String password = request.getParameter("password");
			request.addParameter("firstname", firstname);
			request.addParameter("lastname", lastname);
			request.addParameter("category", category);
			request.addParameter("password", password);
			Person user = new Person(request.getParameter("firstname"), request.getParameter("lastname"),
					request.getParameter("category"), request.getParameter("password"));
			if (category.equalsIgnoreCase("Farmer")) {
				if (firstname.compareTo("") == 0 || lastname.compareTo("") == 0 || username.compareTo("") == 0
						|| password.compareTo("") == 0) {
					RequestDispatcher view = request.getRequestDispatcher("/core/access/signUpFarmer.jsp");
					view.forward(request, response);
					return;
				}
				user.setUsername(username);
				user.setProvince(province);
				signupBean.addUser(user);
				code = user.getId();
				String id = Long.toString(code);
				request.addParameter("id", id);
				request.addParameter("province", province);
				RequestDispatcher view1 = request.getRequestDispatcher("/core/access/signUpFarm.jsp");
				view1.forward(request, response);
			} else if (category.equalsIgnoreCase("Agronomist")) {
				if (firstname.compareTo("") == 0 || lastname.compareTo("") == 0 || password.compareTo("") == 0) {
					RequestDispatcher view = request.getRequestDispatcher("/core/access/signUpAgronomist.jsp");
					view.forward(request, response);
					return;
				}
				user.setProvince(province);
				signupBean.addUser(user);
				code = user.getId();
				String id = Long.toString(code);
				request.addParameter("id", id);
				RequestDispatcher view1 = request.getRequestDispatcher("/core/access/signUpCompleted.jsp");
				view1.forward(request, response);
			} else {
				if (firstname.compareTo("") == 0 || lastname.compareTo("") == 0 || password.compareTo("") == 0) {
					RequestDispatcher view = request.getRequestDispatcher("/core/access/signUpPolicyMaker.jsp");
					view.forward(request, response);
					return;
				}
				signupBean.addUser(user);
				code = user.getId();
				String id = Long.toString(code);
				request.addParameter("id", id);
				RequestDispatcher view1 = request.getRequestDispatcher("/core/access/signUpCompleted.jsp");
				view1.forward(request, response);
			}
		}
	}
}
