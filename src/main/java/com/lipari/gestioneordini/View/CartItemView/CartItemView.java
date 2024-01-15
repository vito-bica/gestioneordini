package com.lipari.gestioneordini.View.CartItemView;

import java.util.HashMap;

import com.lipari.gestioneordini.Model.CartItem.CartItem;
import com.lipari.gestioneordini.Model.Product.Product;

public class CartItemView {
	
	public void printCartItemInfo(CartItem item, Product product) {
		Double total_price = item.getQuantity() * product.getPrice();
		System.out.println("ID Product: " + item.getId_product() + "\nDescription: " + product.getDescription() + ""
				+ "\nQuantity: " + item.getQuantity() + "\nPrice item: " + product.getPrice() + ""
						+ "\nTotal Price: " + total_price + "\n");
	}
	
	public void printPriceCart(Double price_cart) {
		price_cart = Math.floor(price_cart*100) / 100;
		System.out.println("Total price cart: "+ price_cart +"\n");
	}
	


}
