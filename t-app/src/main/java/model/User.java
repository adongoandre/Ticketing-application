package model;

import java.sql.Timestamp;

public class User {

    private int num;
    private String username;
    private String password_hash;
    private String email;
    private String full_name;
    private Timestamp created_at;
    private Timestamp updated_at;
    private String first_name;
    private String last_name;
    private String role; // New field

    // Default constructor
    public User() {
        super();
    }

    // Constructor with all fields including role
    public User(int num, String username, String password_hash, String email, String full_name, Timestamp created_at,
                Timestamp updated_at, String role) {
        super();
        this.num = num;
        this.username = username;
        this.password_hash = password_hash;
        this.email = email;
        this.full_name = full_name;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.role = role;
    }

    // Constructor for creating a user with essential fields including role
    public User(int num, String username, String email, String full_name, Timestamp updated_at) {
        super();
        this.num = num;
        this.username = username;
        this.email = email;
        this.full_name = full_name;
        this.updated_at = updated_at;
        
    }

    // Constructor for inserting a new user including role
    public User(String username, String password_hash, String email, String full_name, Timestamp created_at, String role) {
        super();
        this.username = username;
        this.password_hash = password_hash;
        this.email = email;
        this.full_name = full_name;
        this.created_at = created_at;
        this.role = role;
    }

    // Constructor for creating a user without timestamps but with role
    public User(int num, String username, String email, String full_name, String role) {
        super();
        this.num = num;
        this.username = username;
        this.email = email;
        this.full_name = full_name;
        this.role = role;
    }

    public User(int num, String username, String password_hash, String email, Timestamp created_at, String role) {
		super();
		this.num = num;
		this.username = username;
		this.password_hash = password_hash;
		this.email = email;
		this.created_at = created_at;
		this.role = role;
	}
    
    

	public User(String email) {
		super();
		this.email = email;
	}

	// Getters and Setters
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
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

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
