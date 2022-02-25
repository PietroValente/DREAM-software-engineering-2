package entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.*;

@Entity
@Table(name = "Land")
public class Land implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private long id;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "date", nullable = false)
	private LocalDate date;

	@Column(name = "dimension", nullable = false)
	private int dimension;

	@Column(name = "farm", nullable = false)
	private long farm;

	@Column(name = "empty", nullable = false)
	private boolean empty;

	@Column(name = "humidity", nullable = true)
	private Integer humidity;

	@Column(name = "host", nullable = false)
	private Integer host;

	public Land() {
		dimension = 1;
		empty = true;
		humidity = null;
		host = null;
	}

	public Land(long id, LocalDate date, int dimension, long farm) {
		this.id = id;
		this.date = date;
		this.dimension = dimension;
		this.farm = farm;
		empty = true;
		humidity = null;
		host = null;
	}

	public Land(long id, LocalDate date) {
		this.id = id;
		this.date = date;
		empty = true;
		humidity = null;
		host = null;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public int getDimension() {
		return dimension;
	}

	public void setDimension(int dimension) {
		this.dimension = dimension;
	}

	public long getFarm() {
		return farm;
	}

	public void setFarm(long farm) {
		this.farm = farm;
	}

	public boolean getEmpty() {
		return empty;
	}

	public void setEmpty(boolean empty) {
		this.empty = empty;
	}

	public int getHumidity() {
		if (humidity != null) {
			return humidity;
		}
		return -1;
	}

	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}

	public int getHost() {
		if (host != null) {
			return host;
		}
		return -1;
	}

	public void setHost(int host) {
		this.host = host;
	}
}
