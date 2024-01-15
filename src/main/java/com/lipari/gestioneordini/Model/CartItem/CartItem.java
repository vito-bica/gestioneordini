package com.lipari.gestioneordini.Model.CartItem;

public class CartItem {
	private Integer id_product;
	private Integer quantity;
	
	
	public CartItem(Integer id_product, Integer quantity) {
		super();
		this.id_product = id_product;
		this.quantity = quantity;
	}



	public Integer getId_product() {
		return id_product;
	}


	public void setId_product(Integer id_product) {
		this.id_product = id_product;
	}


	public Integer getQuantity() {
		return quantity;
	}


	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	
	
	
	

}
