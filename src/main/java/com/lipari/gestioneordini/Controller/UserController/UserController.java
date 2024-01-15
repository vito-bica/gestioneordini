package com.lipari.gestioneordini.Controller.UserController;

import com.lipari.gestioneordini.Model.User.User;
import com.lipari.gestioneordini.View.UserView.UserView;

public class UserController {
	private User userModel;     
	private UserView userView;
	
	public UserController(User userModel, UserView userView) {
		super();
		this.userModel = userModel;
		this.userView = userView;
	}
	
	public void setUserId(Integer id) {
		userModel.setId(id);
	}
	
	public Integer getUserId() {
		return userModel.getId();
	}
	
	public void setUserName(String name) {
		userModel.setName(name);
	}
	
	public String getUserName() {
		return userModel.getName();
	}
	
	public void setUserSurname(String surname) {
		userModel.setSurname(surname);
	}
	
	public String getUserSurname() {
		return userModel.getSurname();
	}
	
	public void setUserUsername(String username) {
		userModel.setUsername(username);
	}
	
	public String getUserUsername() {
		return userModel.getUsername();
	}
	
	public void setUserPassword(String password) {
		userModel.setPassword(password);
	}
	
	public String getUserPassword() {
		return userModel.getPassword();
	}
	
	public void setUserEmail(String email) {
		userModel.setEmail(email);
	}
	
	public String getUserEmail() {
		return userModel.getEmail();
	}
	
	public void setUserId_role(Integer id_role) {
		userModel.setId_role(id_role);
	}
	
	public Integer getUserIdRole() {
		return userModel.getId_role();
	}
	
}
