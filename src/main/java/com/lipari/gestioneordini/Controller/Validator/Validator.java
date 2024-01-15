package com.lipari.gestioneordini.Controller.Validator;
import java.util.regex.Pattern;
public class Validator {
	public static boolean isEmail(String email) {
            Pattern pattern = Pattern.compile("^(.+)@(\\S+)$");
            return pattern.matcher(email).matches();
        }
	public static boolean isPassword(String pwd) {
		Pattern pattern = Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$");
                return pattern.matcher(pwd).matches();
	}
}

