package entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Person")
public class Person implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private long id;

	@Column(name = "firstname", length = 40, nullable = false)
	private String firstname;

	@Column(name = "lastname", length = 40, nullable = false)
	private String lastname;

	@Column(name = "category", length = 40, nullable = false)
	private String category;

	@Column(name = "password", length = 40, nullable = false)
	private String password;

	@Column(name = "username", length = 40, nullable = true)
	private String username;

	@Column(name = "province", length = 40, nullable = true)
	private String province;

	public Person() {
	}

	public Person(String fn, String ln, String cat, String pass) {
		firstname = fn;
		lastname = ln;
		category = cat;
		password = pass;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
}
