package entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Ranking")
public class Ranking implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "farm", nullable = false, unique = true)
	private long farm;
	@Column(name = "place", nullable = false)
	private int place;
	@Column(name = "score", nullable = false)
	private int score;

	public Ranking() {

	}

	public Ranking(long farm, int score) {
		this.farm = farm;
		this.score = score;
		this.place = 0;
	}

	public void setFarm(long farm) {
		this.farm = farm;
	}

	public long getFarm() {
		return this.farm;
	}

	public void setPlace(int place) {
		this.place = place;
	}

	public int getPlace() {
		return this.place;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getScore() {
		return this.score;
	}

}
