package servelets.report;

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

import beans.access.SessionBeanRemote;
import beans.farm.ViewDataBeanRemote;
import beans.report.ViewReportBean;
import beans.report.ViewReportBeanRemote;
import entities.Farm;
import entities.Report;
import util.AddableHttpRequest;
import beans.farm.ViewDataBean;
import beans.access.SessionBean;

@WebServlet(description = "Manage report/advise request", urlPatterns = { "/reportadvise.do", "/viewdailyreport.do",
		"/viewallreports.do" })
public class ReportAdviseServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public ReportAdviseServlet() {
		super();
		SessionBean = new SessionBean();
		ReportBean = new ViewReportBean();
		ViewDataBean = new ViewDataBean();
	}

	@EJB
	private SessionBeanRemote SessionBean;
	private ViewReportBeanRemote ReportBean;
	private ViewDataBeanRemote ViewDataBean;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(new AddableHttpRequest(request), response);
	}	
	
	protected void doGet(AddableHttpRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (SessionBean.farmer(request.getCookies()) != 0) {

			String action = request.getServletPath();
			LocalDate day;
			long farmer = SessionBean.farmer(request.getCookies());
			Farm f = ViewDataBean.getFarm(farmer);
			String agronomist = ViewDataBean.getPerson(f.getAgronomist());

			try {
				day = LocalDate.parse(request.getParameter("date"));
			} catch (Exception e) {
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDateTime now = LocalDateTime.now();
				String t = dtf.format(now);
				day = LocalDate.of(Integer.parseInt(t.substring(0, 4)), Integer.parseInt(t.substring(5, 7)),
						Integer.parseInt(t.substring(8, 10)));
			}

			if (action.equals("/reportadvise.do")) {
				request.addParameter("Day", day.toString());
				RequestDispatcher view = request.getRequestDispatcher("/core/report/select_reports.jsp");
				view.forward(request, response);
				return;
			}

			else if (action.equals("/viewallreports.do")) {
				Report[] reports = ReportBean.viewAllReports(farmer);
				request.addParameter("ReportNumber", Integer.toString(reports.length));
				for (int i = 0; i < reports.length; i++) {
					request.addParameter("Report" + i + "ID", Long.toString(reports[i].getID()));
					request.addParameter("Report" + i + "Farm", Long.toString(reports[i].getFarm()));
					request.addParameter("Report" + i + "Agronomist", agronomist);
					request.addParameter("Report" + i + "Date", reports[i].getDate().toString());
					request.addParameter("Report" + i + "Advice", reports[i].getAdvice());
					request.addParameter("Report" + i + "Description", reports[i].getDescription());
				}
				RequestDispatcher view = request.getRequestDispatcher("/core/report/view_all_reports.jsp");
				view.forward(request, response);
				return;
			}

			else if (action.equals("/viewdailyreport.do")) {
				Report[] reports = ReportBean.viewAllReports(farmer, day);
				request.addParameter("ReportNumber", Integer.toString(reports.length));
				for (int i = 0; i < reports.length; i++) {
					request.addParameter("Report" + i + "ID", Long.toString(reports[i].getID()));
					request.addParameter("Report" + i + "Farm", Long.toString(reports[i].getFarm()));
					request.addParameter("Report" + i + "Agronomist", agronomist);
					request.addParameter("Report" + i + "Date", reports[i].getDate().toString());
					request.addParameter("Report" + i + "Advice", reports[i].getAdvice());
					request.addParameter("Report" + i + "Description", reports[i].getDescription());
				}
				RequestDispatcher view = request.getRequestDispatcher("/core/report/view_all_reports.jsp");
				view.forward(request, response);
				return;
			}

		} else {
			RequestDispatcher view = request.getRequestDispatcher("/index.jsp");
			view.forward(request, response);
		}
	}
}
