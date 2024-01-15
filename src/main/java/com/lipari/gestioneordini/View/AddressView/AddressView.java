package com.lipari.gestioneordini.View.AddressView;

import com.lipari.gestioneordini.Model.Address.Address;

public class AddressView {
	
	public void printAddressInfo(Address a) {
		System.out.println("Street: " + a.getStreet() + ""
				+ "\nNumber: " + a.getNumber() + "\nCity: " + a.getCity() + "\nCAP: "
						+ "" + a.getCAP() + "\n");
	}

}
