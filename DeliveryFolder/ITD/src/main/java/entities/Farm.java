package entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.*;

@Entity
@Table(name = "Farm")
public class Farm implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private long id;

	@Column(name = "address", length = 40, nullable = false)
	private String address;

	@Column(name = "phone", length = 15, nullable = false)
	private String phone;

	@Column(name = "owner", nullable = false)
	private long owner;

	@Column(name = "agronomist", nullable = false)
	private long agronomist;

	@Column(name = "water", nullable = false)
	private int water;

	public Farm() {
		water = 0;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public long getOwner() {
		return owner;
	}

	public void setOwner(long owner) {
		this.owner = owner;
	}

	public long getAgronomist() {
		return agronomist;
	}

	public void setAgronomist(long agronomist) {
		this.agronomist = agronomist;
	}

	public int getWater() {
		return water;
	}

	public void setWater(int water) {
		this.water = water;
	}
}
