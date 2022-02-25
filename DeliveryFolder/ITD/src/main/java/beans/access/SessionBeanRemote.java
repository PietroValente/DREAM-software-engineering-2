package beans.access;

import javax.ejb.Remote;
import javax.servlet.http.Cookie;

import entities.Person;

//bean that manages session control
@Remote
public interface SessionBeanRemote {

	// checks the validity of the AccessSession cookie and returns the associated
	// user ID,
	// or -1 if the session does not exist
	long farmer(Cookie[] cookies);

	// extracts the AccessSession cookie code, or returns -1 if it doesn't exist
	long session(Cookie[] cookies);

	// generates a new SessionAccess value to be associated with Person p
	long getSessionID(Person p);

	// delete the SessionAccess value
	void deleteSessionID(Cookie[] cookies);
}
