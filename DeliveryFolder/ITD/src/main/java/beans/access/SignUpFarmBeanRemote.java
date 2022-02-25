package beans.access;

import javax.ejb.Remote;

import entities.Farm;

//bean that manages the sign up process of a farm
@Remote
public interface SignUpFarmBeanRemote {

	// add a new farm and its lands
	void addFarm(Farm farm, String province, String landsnumber);

}
