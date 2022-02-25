package beans.access;

import javax.ejb.Remote;

import entities.Person;

//bean that manages sign up
@Remote
public interface SignUpBeanRemote {

	// add a new user
	void addUser(Person person);

	// check if a code is valid
	String checkCode(String id);
}
