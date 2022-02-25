package entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.*;

@Entity
@Table(name = "Report")
public class Report implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private long id;

	@Column(name = "farm", nullable = false)
	private long farm;

	@Column(name = "agronomist", nullable = false)
	private long agronomist;

	@Column(name = "date", nullable = false)
	private LocalDate date;

	@Column(name = "advice", length = 40, nullable = false)
	private String advice;

	@Column(name = "description", length = 100, nullable = false)
	private String description;

	public Report() {

	}

	public void setID(long id) {
		this.id = id;
	}

	public long getID() {
		return this.id;
	}

	public void setFarm(long farm) {
		this.farm = farm;
	}

	public long getFarm() {
		return this.farm;
	}

	public void setAgronomist(long agronomist) {
		this.agronomist = agronomist;
	}

	public long getAgronomist() {
		return this.agronomist;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalDate getDate() {
		return this.date;
	}

	public void setAdvice(String advice) {
		this.advice = advice;
	}

	public String getAdvice() {
		return this.advice;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

}
