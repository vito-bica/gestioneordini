package com.lipari.gestioneordini.Model.Product;

public class Product {
	private Integer id;
	private String description;
	private Integer quantity;
	private Double price;
	
	
	public Product(Integer id, String description, Integer quantity, Double price) {
		super();
		this.id = id;
		this.description = description;
		this.quantity = quantity;
		this.price = price;
	}
	
	public Product() {
		
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Double getPrice() {
		return price;
	}


	public void setPrice(Double price) {
		this.price = price;
	}


	public Integer getQuantity() {
		return quantity;
	}


	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	
	
	
	
	

}
