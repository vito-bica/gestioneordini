package com.lipari.gestioneordini.View.ViewOrder;

import com.lipari.gestioneordini.Model.Order.Order;
import com.lipari.gestioneordini.Model.Product.Product;

import java.util.HashMap;

import com.lipari.gestioneordini.Model.CartItem.CartItem;

public class OrderView {
	
//	public void printOrderInfo(Order order, HashMap<Integer,Product> products) {
//		System.out.println("Order Information:"+
//							"\nUUID Order: "+order.getUUID()+
//							"\nID User: "+order.getId_user()+
//							"\nDate: "+order.getDate_order()+
//							"\nShipping Address: "+order.getAddress());
//		
//		for (CartItem item : order.getItems().values()) {
//			System.out.println("\nItems:"+
//								"\n"+item.getId_product()+
//                                                                "\nDescription: " + 
//								products.get(item.getId_product()).getDescription() +
//								"\nQuantity: "+item.getQuantity());
//		}
//	}
	
	public void printOrderGeneralInfo(Order o) {
		System.out.println("Order ID: " + o.getId() + "\nUUID: " + o.getUUID() + "\nDate: "
				+ "" + o.getDate_order() + "\nShipping address: " + o.getAddress().PrintFullAddress() + ""
						+ "\nTotal price: " + o.getTotal_price() + "\n");
	}
	
	

}
