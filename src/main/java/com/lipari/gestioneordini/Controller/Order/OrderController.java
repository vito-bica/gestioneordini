package com.lipari.gestioneordini.Controller.Order;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lipari.gestioneordini.Model.Address.Address;
import com.lipari.gestioneordini.Model.CartItem.CartItem;
import com.lipari.gestioneordini.Model.Order.Order;
import com.lipari.gestioneordini.View.ViewOrder.OrderView;

public class OrderController {
	private Order orderModel;
	private OrderView orderView;
	
	public OrderController(Order orderModel, OrderView orderView) {
		super();
		this.orderModel = orderModel;
		this.orderView = orderView;
	}

	public Order getOrderModel() {
		return orderModel;
	}

	public void setOrderModel(Order orderModel) {
		this.orderModel = orderModel;
	}
	

	public OrderView getOrderView() {
		return orderView;
	}

	public void setOrderView(OrderView orderView) {
		this.orderView = orderView;
	}
	
	public String getUUID() {
		return orderModel.getUUID();
	}
	
	public void setOrderId_user(Integer id_user) {
		orderModel.setId_user(id_user);
	}
	
	public Integer getOrderId_user() {
		return orderModel.getId_user();
	}
	
	public void setOrderDate(Date date) {
		orderModel.setDate_order(date);
	}
	
	public Date getOrderDate() {
		return orderModel.getDate_order();
	}
	
	public void setOrderAddress(Address address) {
		orderModel.setAddress(address);
	}
	
	public Address getOrderAddress() {
		return orderModel.getAddress();
	}
	
	public void setOrderProducts(HashMap<Integer, CartItem> items) {
		orderModel.setItems(items);
	}
	
	public HashMap<Integer, CartItem> getOrderProducts(){
		return orderModel.getItems();
	}
	
	
	
	
	
	
	
	
	

}
