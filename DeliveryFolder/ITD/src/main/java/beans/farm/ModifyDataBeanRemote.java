package beans.farm;

import javax.ejb.Remote;
import entities.Crop;
import entities.Farm;
import entities.Land;

//bean that manages update of farm data
@Remote
public interface ModifyDataBeanRemote {

	// updates the farm that has the corresponding farm.id, returns true if the
	// change is successful
	boolean updateFarm(Farm farm);

	// updates the land in that date,
	// returns true if the change is successful
	boolean updateLand(Land land);

	// delete the relative land from database
	boolean deleteLand(Land land);

	// insert the relative land
	void addLand(int dimension, long farmID);

	// add the new crop
	void addCrop(Crop crop, Land land);
}
