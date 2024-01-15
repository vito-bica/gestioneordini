package com.lipari.gestioneordini.View.UserView;
import com.lipari.gestioneordini.Model.User.User;
public class UserView {
	private String role = "";
	public void printUserInfo(User user) {
            
            if(user.getId_role().equals(1)){
                role = "admin";
            }else{
                role = "user";
            }
		System.out.println("User information:"+
							"\nID: "+user.getId()+
							"\nName: "+user.getName()+
							"\nSurname: "+user.getSurname()+
							"\nUsername: "+user.getUsername()+
							"\nPassword: "+user.getPassword()+
							"\nEmail: "+user.getEmail()+
							"\nRole: "+ this.role);
	}
	
	

}
