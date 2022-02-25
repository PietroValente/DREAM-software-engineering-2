package beans.ranking;

import javax.ejb.Remote;
import entities.Ranking;

//bean that manages ranking
@Remote
public interface ManageRankingBeanRemote {

	// keeps the ranking updated
	public void updateRanking();

	// allows to see the ranking
	Ranking[] viewRanking();
}
