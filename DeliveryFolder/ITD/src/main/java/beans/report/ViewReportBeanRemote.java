package beans.report;

import java.time.LocalDate;

import javax.ejb.Remote;

import entities.Report;

//bean that allows a farm to view its reports
@Remote
public interface ViewReportBeanRemote {
	
	// allows to see all the reports
	Report[] viewAllReports(long farmer);

	// allows to see the reports related to a certain date
	Report[] viewAllReports(long farmer, LocalDate day);
}
