package com.lipari.gestioneordini.View.ProductView;

import com.lipari.gestioneordini.Model.Product.Product;

public class ProductView {
	
	public void printProductInfo(Product p) {
		System.out.println("Product ID: " + p.getId() + "\nDescription: " + p.getDescription() + ""
				+ "\nQuantity: " + p.getQuantity() + "\nPrice: " + p.getPrice() + "\n");
	}

}
