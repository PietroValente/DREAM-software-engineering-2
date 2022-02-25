package entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SessionID")
public class SessionID implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private long id;

	@Column(name = "timestamp", nullable = false)
	private java.sql.Timestamp timestamp;

	@Column(name = "farmer", nullable = false, unique = true)
	private long farmer;

	public SessionID(long id, long farmer) {
		this.id = id;
		this.farmer = farmer;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public java.sql.Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(java.sql.Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public long getFarmer() {
		return farmer;
	}

	public void setFarmer(long farmer) {
		this.farmer = farmer;
	}

}
