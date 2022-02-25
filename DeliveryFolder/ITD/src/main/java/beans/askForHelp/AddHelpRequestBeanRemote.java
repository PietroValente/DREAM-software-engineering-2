package beans.askForHelp;

import javax.ejb.Remote;

import entities.Help_Request;

//bean that allows to insert new help requests
@Remote
public interface AddHelpRequestBeanRemote {

	// method to insert request in the system
	long addRequest(String subject, String description, long farmer);
	
	// find request
	Help_Request findRequest(long id);
}
