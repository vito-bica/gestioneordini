package com.lipari.gestioneordini.Model.Address;

public class Address{
	private Integer id;
	private String street;
	private Integer number;
	private String city;
	private String cap;
	
	public Address(String Street, Integer Number, String City, String CAP) {
		super();
		this.street = Street;
		this.number = Number;
		this.city = City;
		this.cap = CAP;
	}
	
	public Address() {
		
	}
	
	public String getStreet() {
		return this.street;
	}
	
	public Integer getNumber() {
		return this.number;
	}
	
	public String getCity() {
		return this.city;
	}
	
	public String getCAP() {
		return this.cap;
	}
	
	public String PrintFullAddress() {
		return this.street + " " + this.number + ", " + this.city + ", " + this.cap;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setCAP(String cAP) {
		this.cap = cAP;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
}
