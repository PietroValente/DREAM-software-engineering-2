package beans.access;

import javax.ejb.Remote;

import entities.Person;

//bean that manages log in
@Remote
public interface LogInBeanRemote {

	// check id and password entered, true if user exist
	boolean validateUser(long id, String password);

	// returns the user corresponding to the id, null if the user does not exist
	Person getUser(long id);
}
