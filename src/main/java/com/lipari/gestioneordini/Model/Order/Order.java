package com.lipari.gestioneordini.Model.Order;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.lipari.gestioneordini.Model.Address.Address;
import com.lipari.gestioneordini.Model.CartItem.CartItem;
import java.util.UUID;

public class Order {
	private Integer id;
	private Integer id_user;
	private String uuid;
	private Date date_order;
	private Address address;
	private Double total_price;
	private HashMap<Integer,CartItem> items;
	
	public Order() {
	}	

	public Order(Integer id, Integer id_user, Address address, HashMap<Integer,CartItem> items) {
		this.id = id;
		this.id_user = id_user;
		this.date_order = new Date();
		this.address = address;
		this.items = items;
	}
	
	public java.sql.Date getSQLDate(){
		java.sql.Date mySQLDate = new java.sql.Date(date_order.getTime());
		return mySQLDate;
	}


	public String getUUID() {
		return this.uuid;
	}
	
	public void createUUID() {
		UUID uuid = UUID.randomUUID();
        this.uuid = uuid.toString();
		
	}

	public Integer getId_user() {
		return id_user;
	}

	public void setId_user(Integer id_user) {
		this.id_user = id_user;
	}

        
    public String getUUID(Integer id){
    	return this.uuid;
    }
    

	public void setUUID(String uuid) {
		this.uuid = uuid;
	}


	public Double getTotal_price() {
		return total_price;
	}

	public void setTotal_price(Double total_price) {
		this.total_price = total_price;
	}
	
//	public void calculateTotalPrice() {
//		Double tot_price = 0.00;
//		for (CartItem item : this.getProducts().values()) {
//			tot_price += item.getPrice()*item.getQuantity();
//		}
//		this.total_price = tot_price;
//	}
	
//	public String toString() {
//		return "Info Order:"+
//				"\nUUID: "+this.getUUID()+
//				"\nID USER: "+this.getId_user()+
//				"\nDATE ORDER: "+this.getDate_order()+
//				"\nADDRESS: "+this.getAddress()+
//				"\nTOTAL PRICE: "+this.getTotal_price();
//	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}



	public HashMap<Integer, CartItem> getItems() {
		return items;
	}

	public void setItems(HashMap<Integer, CartItem> items) {
		this.items = items;
	}

	public Date getDate_order() {
		return date_order;
	}

	public void setDate_order(Date date_order) {
		this.date_order = date_order;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Address getAddress() {
		return address;
	}
	
	
	
	

	
	
	
	
	

}
