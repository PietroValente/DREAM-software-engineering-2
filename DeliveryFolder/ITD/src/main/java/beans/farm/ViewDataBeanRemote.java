package beans.farm;

import java.time.LocalDate;

import javax.ejb.Remote;

import entities.Crop;
import entities.Farm;
import entities.Land;
import entities.Product;

//bean that manages view of farm data
@Remote
public interface ViewDataBeanRemote {

	// returns the farm associated with the user
	Farm getFarm(long user);

	// returns the name and surname associated with the user
	String getPerson(long user);

	// returns the lands associated with a farm on a certain date
	Land[] getLands(Farm farm, LocalDate date);

	// returns the product object associated to id
	Product getProduct(int id);

	// returns the old crops associated with a farm
	Crop[] getOldCrops(Farm farm);

}
