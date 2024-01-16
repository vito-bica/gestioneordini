package com.lipari.gestioneordini;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.UUID;

import com.lipari.gestioneordini.Model.Address.Address;
import com.lipari.gestioneordini.Model.CartItem.CartItem;
import com.lipari.gestioneordini.Model.Order.Order;
import com.lipari.gestioneordini.Model.Product.Product;
import com.lipari.gestioneordini.Model.User.User;
import com.mysql.cj.jdbc.MysqlDataSource;
import com.mysql.cj.protocol.Resultset;


public class Dbms {
	private Connection con;
	
	public Connection getConnection() throws SQLException {
		if (con == null) {
			MysqlDataSource dataSource = new MysqlDataSource();
			dataSource.setServerName("127.0.0.1");
			dataSource.setPortNumber(3306);
			dataSource.setUser("root");
			dataSource.setPassword("vito1234");
			dataSource.setDatabaseName("gestione-ordini-lipari");
			
			con = dataSource.getConnection();
		}
		
		return con;
	}
		

	// PRODUCT QUERY
	
	public HashMap<Integer,Product> getProducts() throws SQLException{
		String query = "SELECT id, description, quantity, price FROM product";
		
		PreparedStatement ps = getConnection().prepareStatement(query);
		
		ResultSet rs = ps.executeQuery();
		
		HashMap<Integer,Product> products = new HashMap<Integer, Product>();
		
		while (rs.next()) {
			Integer id = rs.getInt(1);
			String description = rs.getString(2);
			Integer quantity = rs.getInt(3);
			Double price = rs.getDouble(4);
			
			Product p = new Product(id, description, quantity, price);
			
			products.put(id, p);
		}

		return products;
	}
	
	public void insertProduct(Product p, Integer id_user) throws SQLException {
		String query = "INSERT INTO product(id_user,description,quantity,price) "
				+ "VALUES(?,?,?,?)";

		PreparedStatement ps = getConnection().prepareStatement(query);
		
		ps.setInt(1, id_user);
		ps.setString(2, p.getDescription());
		ps.setInt(3, p.getQuantity());
		ps.setDouble(4, p.getPrice());
		
		ps.executeUpdate();
		
		System.out.println("Prodotto inserito correttamente");
	}
	
	public boolean removeProduct(Integer id_product) throws SQLException {
		String query = "DELETE FROM product WHERE id=" + id_product;
		
		PreparedStatement ps = getConnection().prepareStatement(query);
		
		int row_deleted = ps.executeUpdate();
		
		if (row_deleted>0) {
			return true;
		} else {
			return false;
		}
	}
	
	public Product getProductByID(Integer id) throws SQLException {
		String query = "SELECT description, quantity, price FROM product WHERE id=?";
		
		PreparedStatement ps = getConnection().prepareStatement(query);
		ps.setInt(1, id);
		
		ResultSet rs = ps.executeQuery();
		Product p = null;
		
		while(rs.next()) {
			String description = rs.getString(2);
			Integer quantity = rs.getInt(3);
			Double price = rs.getDouble(4);
			
			p = new Product(id, description, quantity, price);	
		}
		return p;
		
	}
	
	public LinkedHashMap<Integer, Product> getProductsOrderByPriceASC() throws SQLException{
		LinkedHashMap<Integer, Product> products = new LinkedHashMap<Integer, Product>();
		
		String query = "SELECT id, description, quantity, price FROM product ORDER BY price";
		
		PreparedStatement ps = getConnection().prepareStatement(query);
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			Product p = new Product();
			p.setId(rs.getInt(1));
			p.setDescription(rs.getString(2));
			p.setQuantity(rs.getInt(3));
			p.setPrice(rs.getDouble(4));
			
			products.put(p.getId(), p);
		}
		return products;
	}
	
	public LinkedHashMap<Integer, Product> getProductsOrderByPriceDESC() throws SQLException{
		LinkedHashMap<Integer, Product> products = new LinkedHashMap<Integer, Product>();
		
		String query = "SELECT id, description, quantity, price FROM product ORDER BY price DESC";
		
		PreparedStatement ps = getConnection().prepareStatement(query);
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			Product p = new Product();
			p.setId(rs.getInt(1));
			p.setDescription(rs.getString(2));
			p.setQuantity(rs.getInt(3));
			p.setPrice(rs.getDouble(4));
			
			products.put(p.getId(), p);
		}
		return products;
	}
	
	public HashMap<Integer,ArrayList<Product>> getBestSellerProduct() throws SQLException {
		HashMap<Integer,ArrayList<Product>> products_qty = new HashMap<Integer, ArrayList<Product>>();
		ArrayList<Product> products = new ArrayList<Product>();
		Integer qty_selled = 0;
		
		
		
		String query = "SELECT p.id, p.description, p.quantity, p.price, SUM(pio.quantity) as somma "
				+ "FROM product_in_order pio INNER JOIN product p "
				+ "ON p.id=pio.id_product "
				+ "group by id_product "
				+ "having somma in (SELECT Max(somma) as max_somma FROM ("
				+ "SELECT id_product, SUM(pio.quantity) as somma "
				+ "FROM product_in_order pio INNER JOIN product p ON p.id=pio.id_product "
				+ "group by id_product) AS tab)";
		
		PreparedStatement ps = getConnection().prepareStatement(query);
		
		ResultSet rs = ps.executeQuery();
		
		
		while(rs.next()) {
			Product p = new Product();
			p.setId(rs.getInt(1));
			p.setDescription(rs.getString(2));
			p.setQuantity(rs.getInt(3));
			p.setPrice(rs.getDouble(4));
			qty_selled = rs.getInt(5);
			
			products.add(p);
		}
		products_qty.put(qty_selled, products);
		return products_qty;
	}
	
	public HashMap<Integer,ArrayList<Product>> getLeastSellerProduct() throws SQLException {
		HashMap<Integer,ArrayList<Product>> products_qty = new HashMap<Integer, ArrayList<Product>>();
		ArrayList<Product> products = new ArrayList<Product>();
		Integer qty_selled = 0;
		
		String query = "SELECT p.id, p.description, p.quantity, p.price, SUM(pio.quantity) as somma "
				+ "FROM product_in_order pio, product p "
				+ "WHERE p.id=pio.id_product "
				+ "group by id_product "
				+ "having somma in ("
				+ "SELECT Min(somma) as max_somma FROM ("
				+ "SELECT id_product, SUM(pio.quantity) as somma "
				+ "FROM product_in_order pio, product p "
				+ "WHERE p.id=pio.id_product "
				+ "group by id_product) AS tab)";
		
		PreparedStatement ps = getConnection().prepareStatement(query);
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			Product p = new Product();
			p.setId(rs.getInt(1));
			p.setDescription(rs.getString(2));
			p.setQuantity(rs.getInt(3));
			p.setPrice(rs.getDouble(4));
			qty_selled = rs.getInt(5);
			
			products.add(p);
		}
		products_qty.put(qty_selled, products);
		return products_qty;
	}
	
	
	public HashMap<Double,ArrayList<Product>> getMostProfictProduct() throws SQLException{
		HashMap<Double,ArrayList<Product>> products_profit = new HashMap<Double,ArrayList<Product>>();
		ArrayList<Product> products = new ArrayList<Product>();
		Double most_profit = 0.0;
		
		String query = "SELECT p.id,p.description, p.quantity, p.price, (p.price*tab1.somma) as profit FROM product p INNER JOIN ("
				+ "SELECT id_product, SUM(quantity) as somma FROM product_in_order pio group by pio.id_product) as tab1 "
				+ "ON p.id=tab1.id_product "
				+ "HAVING profit=(SELECT MAX(p.price*tab1.somma) as max_profit FROM product p INNER JOIN ("
				+ "SELECT id_product, SUM(quantity) as somma FROM product_in_order pio group by pio.id_product) as tab1 "
				+ "ON p.id=tab1.id_product)";
		
		PreparedStatement ps = getConnection().prepareStatement(query);
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			Product p = new Product();
			
			p.setId(rs.getInt(1));
			p.setDescription(rs.getString(2));
			p.setQuantity(rs.getInt(3));
			p.setPrice(rs.getDouble(4));
			most_profit = rs.getDouble(5);
			
			products.add(p);
		}
		products_profit.put(most_profit, products);
		return products_profit;
	}
	
	public HashMap<Double,ArrayList<Product>> getLeastProfictProduct() throws SQLException{
		HashMap<Double,ArrayList<Product>> products_profit = new HashMap<Double,ArrayList<Product>>();
		ArrayList<Product> products = new ArrayList<Product>();
		Double least_profit = 0.0;
		
		String query = "SELECT p.id,p.description, p.quantity, p.price, (p.price*tab1.somma) as profit FROM product p INNER JOIN ("
				+ "SELECT id_product, SUM(quantity) as somma FROM product_in_order pio group by pio.id_product) as tab1 "
				+ "ON p.id=tab1.id_product "
				+ "HAVING profit=(SELECT MIN(p.price*tab1.somma) as min_profit FROM product p INNER JOIN ("
				+ "SELECT id_product, SUM(quantity) as somma FROM product_in_order pio group by pio.id_product) as tab1 "
				+ "ON p.id=tab1.id_product)";
		
		PreparedStatement ps = getConnection().prepareStatement(query);
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			Product p = new Product();
			
			p.setId(rs.getInt(1));
			p.setDescription(rs.getString(2));
			p.setQuantity(rs.getInt(3));
			p.setPrice(rs.getDouble(4));
			least_profit = rs.getDouble(5);
			
			products.add(p);
		}
		products_profit.put(least_profit, products);
		return products_profit;
	}
	
	public HashMap<Integer,ArrayList<User>> getUserWithMostOrder() throws SQLException{
		HashMap<Integer,ArrayList<User>> user_with_most_order = new HashMap<Integer,ArrayList<User>>();
		ArrayList<User> users = new ArrayList<User>();
		Integer qty_orders = 0;
		
		String query = "select u.id, u.name, u.surname, u.username, u.email, COUNT(*) as qty_orders FROM user u INNER JOIN t_order o "
				+ "ON u.id=o.id_user group by u.id "
				+ "HAVING qty_orders=("
				+ "SELECT MAX(qty_orders) FROM("
				+ "select COUNT(*) as qty_orders FROM user u INNER JOIN t_order o ON u.id=o.id_user "
				+ "group by u.id) AS num_orders_per_user)";
		
		PreparedStatement ps = getConnection().prepareStatement(query);
		
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			User u = new User();
			u.setId(rs.getInt(1));
			u.setName(rs.getString(2));
			u.setSurname(rs.getString(3));
			u.setUsername(rs.getString(4));
			u.setEmail(rs.getString(5));
			qty_orders=rs.getInt(6);
			
			users.add(u);
		}
		
		user_with_most_order.put(qty_orders, users);
		
		return  user_with_most_order;
		
	}
	
	public HashMap<Integer,ArrayList<User>> getUserWithLeastOrder() throws SQLException{
		HashMap<Integer,ArrayList<User>> user_with_least_order = new HashMap<Integer,ArrayList<User>>();
		ArrayList<User> users = new ArrayList<User>();
		Integer qty_orders = 0;
		
		String query = "select u.id, u.name, u.surname, u.username, u.email, COUNT(*) as qty_orders FROM user u INNER JOIN t_order o "
				+ "ON u.id=o.id_user group by u.id "
				+ "HAVING qty_orders=("
				+ "SELECT MIN(qty_orders) FROM("
				+ "select COUNT(*) as qty_orders FROM user u INNER JOIN t_order o ON u.id=o.id_user "
				+ "group by u.id) AS num_orders_per_user)";
		
		PreparedStatement ps = getConnection().prepareStatement(query);
		
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			User u = new User();
			u.setId(rs.getInt(1));
			u.setName(rs.getString(2));
			u.setSurname(rs.getString(3));
			u.setUsername(rs.getString(4));
			u.setEmail(rs.getString(5));
			qty_orders=rs.getInt(6);
			
			users.add(u);
		}
		
		user_with_least_order.put(qty_orders, users);
		
		return  user_with_least_order;
		
	}
	
	public HashMap<Double,ArrayList<User>> getUserHighestSpending() throws SQLException{
		HashMap<Double,ArrayList<User>> user_highest_spending = new HashMap<Double, ArrayList<User>>();
		ArrayList<User> users = new ArrayList<User>();
		Double highest_spending = 0.0;
		
		String query = "SELECT u.id, u.name, u.surname, u.username, u.email, SUM(o.price) as total_spending "
				+ "FROM user u INNER JOIN t_order o ON u.id=o.id_user GROUP BY u.id "
				+ "HAVING total_spending=("
				+ "SELECT MAX(total_spending) as max_spending FROM("
				+ "SELECT SUM(o.price) as total_spending FROM t_order o GROUP BY o.id_user) AS list_total_spending)";
		
		PreparedStatement ps = getConnection().prepareStatement(query);
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			User u = new User();
			u.setId(rs.getInt(1));
			u.setName(rs.getString(2));
			u.setSurname(rs.getString(3));
			u.setUsername(rs.getString(4));
			u.setEmail(rs.getString(5));
			highest_spending=rs.getDouble(6);
			
			users.add(u);
		}
		user_highest_spending.put(highest_spending, users);
		return user_highest_spending;
	}
	
	public HashMap<Double,ArrayList<User>> getUserLeastSpending() throws SQLException{
		HashMap<Double,ArrayList<User>> user_least_spending = new HashMap<Double, ArrayList<User>>();
		ArrayList<User> users = new ArrayList<User>();
		Double least_spending = 0.0;
		
		String query = "SELECT u.id, u.name, u.surname, u.username, u.email, SUM(o.price) as total_spending "
				+ "FROM user u INNER JOIN t_order o ON u.id=o.id_user GROUP BY u.id "
				+ "HAVING total_spending=("
				+ "SELECT MIN(total_spending) as max_spending FROM("
				+ "SELECT SUM(o.price) as total_spending FROM t_order o GROUP BY o.id_user) AS list_total_spending)";
		
		PreparedStatement ps = getConnection().prepareStatement(query);
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			User u = new User();
			u.setId(rs.getInt(1));
			u.setName(rs.getString(2));
			u.setSurname(rs.getString(3));
			u.setUsername(rs.getString(4));
			u.setEmail(rs.getString(5));
			least_spending=rs.getDouble(6);
			
			users.add(u);
		}
		user_least_spending.put(least_spending, users);
		return user_least_spending;
	}
	
	
	//USER QUERY
	
	public void insertUser(User user) throws SQLException{
		String query = "INSERT INTO user(name, surname, username, password, email, id_role) "
				+ "VALUES(?, ?, ?, ?, ?, ?)";
		
		PreparedStatement ps = getConnection().prepareStatement(query);
		
		ps.setString(1, user.getName());
		ps.setString(2, user.getSurname());
		ps.setString(3, user.getUsername());
		ps.setString(4, user.getPassword());
		ps.setString(5, user.getEmail());
		ps.setInt(6, user.getId_role());
		
		ps.executeUpdate();
	
	}
	
	public void selectUsers() throws SQLException {
		String query = "SELECT * FROM user";
		PreparedStatement ps = getConnection().prepareStatement(query);
		
		ResultSet rs = ps.executeQuery();
		
		
		if (rs.next() == false) { 
			System.out.println("Nessun utente registrato!"); 
		} else {
			
			do {
				System.out.println("id = " + rs.getInt(1));
				System.out.println("name = " + rs.getString(2));
				System.out.println("surname = " + rs.getString(3));
				System.out.println("username = " + rs.getString(4));
				System.out.println("password = " + rs.getString(5));
				System.out.println("email = " + rs.getString(6));
				System.out.println("id_role = " + rs.getInt(7));
			} while(rs.next());
		}
	}
	
	public Integer selectLoginParameters(String username, String password) throws SQLException {
		String query = "SELECT id, username, password FROM user WHERE username=? AND password=?";
		
		PreparedStatement ps = getConnection().prepareStatement(query);
		
		ps.setString(1, username);
		ps.setString(2, password);
		
		ResultSet rs = ps.executeQuery();
		
		if (rs.next() == false) {
			return null;
		} else {
			return rs.getInt(1);
		}
		
		
	}
	

	
	public String[] selectUserByID(Integer id) throws SQLException {
		String query = "SELECT * FROM user WHERE id="+id;
		
		PreparedStatement ps = getConnection().prepareStatement(query);
		
		ResultSet rs = ps.executeQuery();
		rs.next();
		
		String name = rs.getString(2);
		String surname = rs.getString(3);
		String username = rs.getString(4);
		String password = rs.getString(5);
		String email = rs.getString(6);
		String id_role = rs.getString(7);
		
		String[] user_info= {name,surname,username,password,email,id_role};
		
		return user_info;
		
	}
	
	public User getUserByID(Integer id) throws SQLException {
		String query = "SELECT * FROM user WHERE id=?";
		
		PreparedStatement ps = getConnection().prepareStatement(query);
		
		ps.setInt(1, id);
		
		ResultSet rs = ps.executeQuery();
		rs.next();
		

		
		User user = new User();
		user.setId(id);
		user.setName(rs.getString(2));
		user.setSurname(rs.getString(3));
		user.setUsername(rs.getString(4));
		user.setPassword(rs.getString(5));
		user.setEmail(rs.getString(6));
		user.setId_role(rs.getInt(7));
		
		return user;
		
		
	}
	
	public boolean insertOrder(Order o) throws SQLException{
		Integer id_order = null;
		
		String query_order = "INSERT INTO t_order(id_user,UUID_code,date_order,address, price) VALUES(?, ?, ?, ?, ?)";
		
		PreparedStatement ps = getConnection().prepareStatement(query_order, Statement.RETURN_GENERATED_KEYS);
		
		ps.setInt(1, o.getId_user());
		ps.setString(2, o.getUUID());
		ps.setDate(3, o.getSQLDate());
		ps.setInt(4, o.getAddress().getId());
		ps.setDouble(5, o.getTotal_price());
		
		Integer row_added = ps.executeUpdate();
		
		if (row_added == 1) {
			ResultSet generatedKeys = ps.getGeneratedKeys();
			if(generatedKeys.next()) {
				id_order = generatedKeys.getInt(1);
				o.setId(id_order);
				System.out.println("ID ORDER GENERATED: "+id_order);
			} else {
				return false;
			}
		} else {
			return false;
		}
				
		
		String query_item = "INSERT INTO product_in_order(id_order, id_product, quantity) VALUES(?, ?, ?)";
		ps = getConnection().prepareStatement(query_item);
		
		String query_qty = "UPDATE product SET quantity = quantity-? WHERE id=?";
		PreparedStatement ps2 = getConnection().prepareStatement(query_qty);
		
		for(CartItem item : o.getItems().values()) {
			ps.setInt(1, id_order);
			ps.setInt(2, item.getId_product());
			ps.setInt(3, item.getQuantity());
			ps.addBatch();
			
			ps2.setInt(1, item.getQuantity());
			ps2.setInt(2, item.getId_product());
			ps2.addBatch();
		}
		
		ps.executeBatch();
		ps2.executeBatch();
		
		
		return true;
		
	}
	
	public HashMap<Integer, Order> getOrdersByUserID(Integer id) throws SQLException{
		HashMap<Integer, Order> orders = new HashMap<Integer, Order>();
		
//		String query = "SELECT id, UUID_code, date_order, address, price FROM t_order WHERE id_user=?";
		String query = "SELECT o.id, o.UUID_code, o.date_order, o.price, a.id, "
				+ "a.street, a.house_number, a.city, a.cap FROM t_order o "
				+ "INNER JOIN address a ON o.address=a.id WHERE o.id_user=?";
		
		PreparedStatement ps = getConnection().prepareStatement(query);
		
		ps.setInt(1, id);
		
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			Order o = new Order();
			o.setId(rs.getInt(1));
			o.setId_user(id);
			o.setUUID(rs.getString(2));
			o.setDate_order(rs.getDate(3));
			o.setTotal_price(rs.getDouble(4));
			
			Address a = new Address();
			a.setId(rs.getInt(5));
			a.setStreet(rs.getString(6));
			a.setNumber(rs.getInt(7));
			a.setCity(rs.getString(8));
			a.setCAP(rs.getString(9));
			o.setAddress(a);

			orders.put(o.getId(), o);
		}
		
		return orders;
	}
	
	public boolean removeOrderByID(Integer id_user, Integer id_order) throws SQLException {
		
		String query_order = "DELETE FROM t_order WHERE id=? AND id_user=?";
		PreparedStatement ps1 = getConnection().prepareStatement(query_order);
		ps1.setInt(1, id_order);
		ps1.setInt(2, id_user);
		
		Integer rows_removed = ps1.executeUpdate();
		
		if (rows_removed == 1) {
			
			String query_items = "DELETE FROM product_in_order WHERE id_order=?";
			PreparedStatement ps2 = getConnection().prepareStatement(query_items);
			ps2.setInt(1, id_order);
			
			ps2.executeUpdate();
			
			return true;
			
		} else {
			return false;
		}
		
		
		
	}
	
	public HashMap<Integer,Address> getAddressListByUserID(Integer id) throws SQLException{
		
		HashMap<Integer, Address> addressList = new HashMap<Integer, Address>();
		
		String query = "SELECT id, street, house_number, city, cap FROM address WHERE id_user=?";
		
		PreparedStatement ps = getConnection().prepareStatement(query);
		
		ps.setInt(1, id);
		
		ResultSet rs = ps.executeQuery();
		Integer counter_address = 0;
		while (rs.next()) {
			Address a = new Address();
			a.setId(rs.getInt(1));
			a.setStreet(rs.getString(2));
			a.setNumber(rs.getInt(3));
			a.setCity(rs.getString(4));
			a.setCAP(rs.getString(5));
			
			addressList.put(++counter_address, a);
		}
		
		return addressList;
	}

	
	
	public boolean insertAddress(Address a, Integer id_user) throws SQLException {
		String query = "INSERT INTO address(id_user, street, house_number, city, cap) VALUES(?, ?, ?, ?, ?)";
		
		PreparedStatement ps = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		
		ps.setInt(1, id_user);
		ps.setString(2, a.getStreet());
		ps.setInt(3, a.getNumber());
		ps.setString(4, a.getCity());
		ps.setString(5, a.getCAP());
		
		
		int rows_added = ps.executeUpdate();
		
		if (rows_added == 1) {
			
			ResultSet generatedKeys = ps.getGeneratedKeys();
			
			if(generatedKeys.next()) {
				Integer id_address = generatedKeys.getInt(1);
				a.setId(id_address);
				System.out.println("ID ADDRESS GENERATED: "+a.getId());
				System.out.println("Indirizzo aggiunto correttamente");
				return true;
			} 
		}
		
		System.out.println("Si è verificato un errore nell'inserimento dell'indirizzo");
		return false;
		
	}
	


	
		
	

}
