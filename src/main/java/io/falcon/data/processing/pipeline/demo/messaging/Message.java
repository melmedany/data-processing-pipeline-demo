package io.falcon.data.processing.pipeline.demo.messaging;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "messages")
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;

	@Column(name = "content")
	private String content;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date")
	private Date date;

	protected Message() {
	}

	public Message(String content) {
		this.content = content;
	}

	public Message(Long id, String content, Date date) {
		this.id = id;
		this.content = content;
		this.date = date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		String msg = "{\"id\":\"" + id + "\",\"content\":\"" + content + "\"";
		if (date != null)
			msg = msg.concat(",\"date\":\"\"" + date.getTime() + "\"");
		msg = msg.concat("}");
		return msg;
	}
}
