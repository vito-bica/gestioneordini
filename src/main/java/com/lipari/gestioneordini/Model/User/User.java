package com.lipari.gestioneordini.Model.User;

public class User {
	private Integer id;
	private String name;
	private String surname;
	private String username;
	private String password;
	private String email;
	private Integer id_role;	
	
	public User() {
		
	}
	
	
	public User(Integer id, String name, String surname, String username, String password, String email, Integer id_role) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.username = username;
		this.password = password;
		this.email = email;
		this.id_role = id_role;
	}

        public void printUser(){
            System.out.println("ID");
        }


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	

	public void setSurname(String surname) {
		this.surname = surname;
	}


	public String getSurname() {
		return surname;
	}


	public void setCognome(String surname) {
		this.surname = surname;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public Integer getId_role() {
		return id_role;
	}


	public void setId_role(Integer id_role) {
		this.id_role = id_role;
	}



	
	
	
	
	
	
	
	

}
