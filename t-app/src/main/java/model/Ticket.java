package model;

import java.sql.Timestamp;

public class Ticket {
	
	private long ticket_id;
	private int num;
	private int user_id;
	private String description_;
	private byte[] image;
	private String priority;   
	private String status;
	private Timestamp created_at;
    private Timestamp updated_at;
    private String satisfaction;
     
	public Ticket() {
		super();
	}

	public Ticket(int num, String satisfaction) {
		super();
		this.num = num;
		this.satisfaction = satisfaction;
	}

	public Ticket(int num, String priority, String status) {
		super();
		this.num = num;
		this.priority = priority;
		this.status = status;
	}

	public Ticket( int num, long ticket_id, int user_id, String description_, byte[] image, String priority,
			String status, Timestamp created_at, Timestamp updated_at) {
		super();
		this.ticket_id = ticket_id;
		this.num = num;
		this.user_id = user_id;
		this.description_ = description_;
		this.image = image;
		this.priority = priority;
		this.status = status;
		this.created_at = created_at;
		this.updated_at = updated_at; 
	}

	public Ticket(int num, long ticket_id, int user_id, String description_, byte[] image, String priority, String status,
			Timestamp created_at, Timestamp updated_at, String satisaction) {
		super();
		this.num = num;
		this.ticket_id = ticket_id;
		this.user_id = user_id;
		this.description_ = description_;
		this.image = image;
		this.priority = priority;
		this.status = status;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.satisfaction = satisaction;
	}

	public Ticket(long ticket_id, int user_id,String description_, byte[] image, String priority, String status,
			Timestamp created_at, String satisfaction) {
		super();
		this.ticket_id = ticket_id;
		this.user_id = user_id;
		this.description_ = description_;
		this.image = image;
		this.priority = priority;
		this.status = status;
		this.created_at = created_at;
		this.satisfaction = satisfaction;
	}

	public Ticket(int num,int user_id, String description_, byte[] image, String priority, String status,
			Timestamp updated_at) {
		super();
		this.num = num;
		this.description_ = description_;
		this.image = image;
		this.priority = priority;
		this.status = status;
		this.updated_at = updated_at;
	}

	public Ticket(int num, String description_, byte[] image, String priority, Timestamp updated_at) {
		super();
		this.num = num;
		this.description_ = description_;
		this.image = image;
		this.priority = priority;
		this.updated_at = updated_at;
	}

	public long getTicket_id() {
		return ticket_id;
	}

	public void setTicket_id(long ticket_id) {
		this.ticket_id = ticket_id;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getDescription_() {
		return description_;
	}

	public void setDescription_(String description_) {
		this.description_ = description_;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}

	public Timestamp getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Timestamp updated_at) {
		this.updated_at = updated_at;
	}

	public String getSatisfaction() {
		return satisfaction;
	}

	public void setSatisfaction(String satisfaction) {
		this.satisfaction = satisfaction;
	}
	
	
	
    
}
