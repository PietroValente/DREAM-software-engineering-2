package entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Comment")
public class Comment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private int id;

	@Column(name = "author", nullable = false, unique = true)
	private long author;

	@Column(name = "discussion", nullable = false, unique = true)
	private int discussion;

	@Column(name = "text", nullable = false, unique = true)
	private String text;

	@Column(name = "timestamp", nullable = false)
	private java.sql.Timestamp timestamp;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public java.sql.Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(java.sql.Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public long getAuthor() {
		return author;
	}

	public void setAuthor(long author) {
		this.author = author;
	}

	public int getDiscussion() {
		return discussion;
	}

	public void setDiscussion(int discussion) {
		this.discussion = discussion;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
