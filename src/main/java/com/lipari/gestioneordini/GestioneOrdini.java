package com.lipari.gestioneordini;

import java.sql.SQLException;
import java.util.*;
import java.util.Map.Entry;

import com.lipari.gestioneordini.Controller.Validator.Validator;
import com.lipari.gestioneordini.Model.User.User;
import com.lipari.gestioneordini.Model.Address.Address;
import com.lipari.gestioneordini.Model.CartItem.CartItem;
import com.lipari.gestioneordini.Model.Order.Order;
import com.lipari.gestioneordini.Model.Product.Product;
import com.lipari.gestioneordini.View.AddressView.AddressView;
import com.lipari.gestioneordini.View.CartItemView.CartItemView;
//import com.lipari.gestioneordini.View.ItemView.ItemView;
import com.lipari.gestioneordini.View.ProductView.ProductView;
import com.lipari.gestioneordini.View.ViewOrder.OrderView;
import com.lipari.gestioneordini.View.UserView.UserView;

public class GestioneOrdini {
    private static UserView uv = new UserView();
    private static OrderView ov = new OrderView();
    private static ProductView pv = new ProductView();
    private static CartItemView civ = new CartItemView();
    private static AddressView av = new AddressView();
    

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
        boolean logged = false;
        Integer user_id = null;
        boolean quit = false;
        
        Dbms database = new Dbms();
        
        while (!quit) {
        	while(!logged){
                System.out.println("Welcome,\nPlease choose an action to perform between:\n"
                        + "1)Register\n2)Login\n3)Quit\nInput: ");
                String command = scanner.nextLine();
                switch(command){
                    case "1" -> {
                        boolean check_pwd = false; //Per ciclare l'inserimento della password finchè non combaciano.
                        boolean check_email = false; //Per validare l'email.
                        
                        //Inizializzazione variabili
                       
                        String name = "";
                        String surname = "";
                        String username = "";
                        String password = "";
                        String conf_pwd = "";
                        String email = "";
                        
                        System.out.println("To register please compile the following form.\nName: ");
                        name = scanner.nextLine();
                        System.out.println("\nSurname: ");
                        surname = scanner.nextLine();
                        System.out.println("\nUsername: ");
                        username = scanner.nextLine();
                        
                        while(!check_pwd){
                            System.out.println("\nPassword: ");
                            password = scanner.nextLine();
                            System.out.println(password);
                            System.out.println("\nConfirm Password: ");
                            conf_pwd = scanner.nextLine();
                            System.out.println(conf_pwd);
                            if(password.equals(conf_pwd) && Validator.isPassword(password)){
                                check_pwd = true;
                            }
                        }
                        
                        while(!check_email){
                            System.out.println("\nEmail: ");
                            email = scanner.nextLine();
                            if(Validator.isEmail(email)){
                                check_email = true;
                            }
                        }
                       
                        User user = new User();
                        user.setName(name);
                        user.setSurname(surname);
                        user.setUsername(username);
                        user.setPassword(password);
                        user.setEmail(email);
                        user.setId_role(1);
                        
                        database.insertUser(user);
                        System.out.println("\nUser added succesfully.");
                        break;
                    }
                    case "2" -> {
                        while(!logged){
                            System.out.println("Username: ");
                            String username = scanner.nextLine();
                            System.out.println("Password: ");
                            String password = scanner.nextLine();
                            Integer temp_id = database.selectLoginParameters(username, password);
                            if (temp_id != null) {
                            	user_id = temp_id;
                            	logged = true;
                            	System.out.println("Accesso effettuato!");
                            } else {
                            	System.out.println("Dati non corretti");
                            }
                            }
                        }
                    case "3" -> {
                        System.out.println("Goodbye, program will now close.");
                        System.exit(0);
                    }
                    default -> System.out.println("Invalid command entered. Please try again.");    
                }
            }
            if (logged) {
            	User current_user = database.getUserByID(user_id);
            	
            	uv.printUserInfo(current_user);

    	        while(logged){
    	            System.out.println("\nYou're logged in as: " + current_user.getUsername()+ ".\nChoose an action to perform:\n"
    	            		+ "1)Add Item\n"
    	            		+ "2)Remove Item\n"
    	            		+ "3)New Order\n"
    	            		+ "4)Cancel Order\n"
    	            		+ "5)History of Orders\n"
    	            		+ "6)Show products sorted by price ASC\n"
    	            		+ "7)Show products sorted by price DESC\n"
    	            		+ "8)Show the best-selling product\n"
    	            		+ "9)Show the least-selling product\n"
    	            		+ "10)Show the product that gave the most profit\n"
    	            		+ "11)Show the product that gave the least profit\n"
    	            		+ "12)Show the user who placed the most orders\n"
    	            		+ "13)Show the user who placed the fewest orders\n"
    	            		+ "14)Show the user with the most spending\n"
    	            		+ "15)Show the user with the least spending\n"
    	            		+ "100)LogOut\n"
    	            		+ "Input:");
    	            String command = scanner.nextLine();
    	            switch(command){
    	                case "1" -> {
                                System.out.println("Type a description for the item: ");
                                String description = scanner.nextLine();
                                System.out.println("Enter how much item to add: ");
                                Integer qty = scanner.nextInt();
                                scanner.nextLine();
                                System.out.println("Enter the price: ");
                                Double price = scanner.nextDouble();
                                scanner.nextLine();
                                Product p = new Product();
                                p.setDescription(description);
                                p.setQuantity(qty);
                                p.setPrice(price);
                                database.insertProduct(p, user_id);
                                break;
                            }
    	                case "2" -> {
                                boolean removed = false;
                                while(!removed){
                                    System.out.println("Choose the product to remove by typing its Product ID: ");
                                    Integer id = scanner.nextInt();
                                    scanner.nextLine();
                                    removed = database.removeProduct(id);
                                    if (removed) {
                                    	System.out.println("Item removed succesfully.");
                                    } else {
                                    	System.out.println("Invalid Product ID. Try again.\n");
                                    }
                                }
                                break;
                            }
    	                case "3" -> {
                                HashMap<Integer,CartItem> cart = new HashMap<>();
                                Double price_cart = 0.0;
                                Boolean isComplete = false; //Per permettere l'inserimento di più prodotti all'interno dell'ordine
                                Integer qty;
                                HashMap<Integer,Product> products = new HashMap<Integer, Product>();
                                while(!isComplete) {

                                    System.out.println("STORAGE Product list:");
                                    products = database.getProducts();
                                    
                                    for (Product p : products.values()) {
                                    	pv.printProductInfo(p);
                                    }
                                    
                                    System.out.println("\nChoose the product to add in the cart by typing its Product ID: ");
                                    Integer id = scanner.nextInt();
                                    scanner.nextLine();
                                    
                                    // Verifica se l'ID inserito è presente nella lista dei prodotti
                                    if(products.containsKey(id)){
//                                        Item current_item = items.get(id);
                                    	Integer avaible_qty = products.get(id).getQuantity();
                                        System.out.println("Choose the quantity to add: ");
                                        qty = scanner.nextInt();
                                        scanner.nextLine();
                                        
                                        if (avaible_qty>=qty) {
                                        	if (cart.containsKey(id)) {
                                        		CartItem cart_item = cart.get(id);
                                        		cart_item.setQuantity(cart_item.getQuantity()+qty);
                                        		price_cart+=qty*products.get(id).getPrice();
                                        		System.out.println("Il prodotto è già presente nel carrello! "
                                        				+ "La quantità selezionata è stata aggiunta!");
                                        	} else {
                                        		CartItem cart_item = new CartItem(id,qty);
                                        		cart.put(id, cart_item);
                                        		price_cart+=qty*products.get(id).getPrice();
                                        		System.out.println("Prodotto aggiunto nel carrello!");
                                        	}
                                        } else {
                                        	System.out.println("Errore: La quantità disponibile per il prodotto è: "+avaible_qty);
                                        }
                                    } else {
                                    	System.out.println("ID prodotto non valido!");
                                    }
                                    // Visualizza prodotti nel carrello
                                    System.out.println("CART:\n");
                                    for (CartItem item : cart.values()) {
                                        civ.printCartItemInfo(item, products.get(item.getId_product()));
                                        civ.printPriceCart(price_cart);
                                    }
                                    
                                    System.out.println("\n1- Add another product to your cart\n2- Complete the order\nInput: ");
                                    String command2 = scanner.nextLine();
                                    switch(command2) {
                                        case "2" -> {
                                        	if (!cart.isEmpty()) {
                                        		Order order = new Order();
                                        		Integer temp_address = 0;
                                        		HashMap<Integer, Address> usedAddress = new HashMap<Integer, Address>();
                                        		usedAddress = database.getAddressListByUserID(user_id);
                     
                                        		System.out.println("Select the address from those available or enter a new one: ");
                                        		System.out.println("0 - Enter a new address");
                                        		
                                        		//Visualizza indirizzi usati in precedenza dall'utente
                                        		for (Address a : usedAddress.values()) {
                                        			System.out.println(++temp_address + " - Address: ");
                                        			av.printAddressInfo(a);
                                        		}
                                        		
                                        		System.out.println("Input: ");
                                        		Integer address_command = scanner.nextInt();
                                        		scanner.nextLine();
                                        		
                                        		if (address_command == 0) {
                                        			//create a new address
                                        			
                                        			String street;
                                        			Integer number;
                                        			String city;
                                        			String cap;
                                        			
                                        			System.out.println("Enter the street. Input: ");
                                        			street = scanner.nextLine();
                                        			System.out.println("Enter the number. Input: ");
                                        			number = scanner.nextInt();
                                        			scanner.nextLine();
                                        			System.out.println("Enter the city. Input: ");
                                        			city = scanner.nextLine();
                                        			System.out.println("Enter the cap. Input: ");
                                        			cap = scanner.nextLine();
                                        			
                                        			Address address = new Address();
                                        			address.setStreet(street);
                                        			address.setNumber(number);
                                        			address.setCity(city);
                                        			address.setCAP(cap);
                                        			
                                        			database.insertAddress(address, user_id);
                                        			usedAddress.put(++temp_address, address);
                                        			
                                        			
                                        		} else if (address_command < 0 && address_command>temp_address) {
                                        			System.out.println("Errore: codice errato");
                                        			break;
                                        		}
                                        		
                                        		order.setId_user(user_id);
                                        		order.createUUID();
                                        		
                                        		Date date = new Date();
                                        		order.setDate_order(date);
                                        		
                                        		if (address_command != 0) {
                                        			order.setAddress(usedAddress.get(address_command));
                                        		} else {
                                        			order.setAddress(usedAddress.get(temp_address));
                                        		}
                                        		
                                        		order.setItems(cart);       
                                        		//Tronca il price_cart alla seconda cifra decimale
                                        		price_cart =Math.floor(price_cart*100) / 100;
                                        		order.setTotal_price(price_cart);
                                        		
                                        		isComplete = database.insertOrder(order);
                                        		
                                        		if (isComplete) {
                                        			System.out.println("Ordine effettuato con successo");
                                        		} else {
                                        			System.out.println("Si è verificato un errore nell'esecuzione dell'ordine!");
                                        		}
                                        		
                                        		
                                        	} else {
                                        		System.out.println("Errore: Non è possibile creare l'ordine se il carrello è vuoto!");
                                        	}
                                        }
                                    }
                                }
                                break;
                            }
    	                case "4" -> {
                                System.out.println("Choose the order to cancel\nInput: ");
                                Integer order_id = scanner.nextInt();
                                scanner.nextLine();
                                
                                boolean removed = database.removeOrderByID(user_id,order_id);
                                
                                if (removed) {
                                	System.out.println("Ordine cancellato correttamente!");
                                } else {
                                	System.out.println("L'ordine selezionato non è stato trovato!");
                                }
                                break;
                            }
    	                case "5" -> {
    	                		HashMap<Integer, Order> orders = new HashMap<Integer, Order>();
    	                		
                                orders = database.getOrdersByUserID(user_id);
                                
                                if (orders.isEmpty()) {
                                	System.out.println("Non è stato trovato nessun ordine!");
                                } else {
                                	
                                	for (Order order : orders.values()) {
//                                		HashMap<Integer, Product> products = new HashMap<Integer, Product>();
//                                		for (CartItem item : order.getItems().values()) {
//                                        	Product p = database.getProductByID(item.getId_product());
//                                        	products.put(p.getId(), p);
//                                        }
//                                    	ov.printOrderInfo(order,products);
                                		ov.printOrderGeneralInfo(order);
                                    }
                                	
                                }
                                break;
                            }
    	                case "6" -> {
    	                	LinkedHashMap<Integer, Product> products = new LinkedHashMap<Integer, Product>();
    	                	products = database.getProductsOrderByPriceASC();
    	                	
    	                	for (Product product : products.values()) {
    	                		pv.printProductInfo(product);
    	                	}
    	                	break;
    	                }
    	                case "7" -> {
    	                	LinkedHashMap<Integer, Product> products = new LinkedHashMap<Integer, Product>();
    	                	products = database.getProductsOrderByPriceDESC();
    	                	
    	                	for (Product product : products.values()) {
    	                		pv.printProductInfo(product);
    	                	}
    	                	break;
    	                }
    	                case "8" -> {
    	                	HashMap<Integer,ArrayList<Product>> products_qty = new HashMap<Integer,ArrayList<Product>>();
    	                	products_qty = database.getBestSellerProduct();
    	                	Integer qty_sold = 0;
    	                	
    	                	for (Integer qty : products_qty.keySet()) {
    	                		qty_sold = qty;
    	                	}
    	                	
    	                	for (ArrayList<Product> products : products_qty.values()) {
    	                		for (Product product : products) {
    	                			pv.printProductInfo(product);
        	                		System.out.println("Quantity sold: " + qty_sold);
    	                		}
    	                	}
    	                	break;
    	                }
    	                case "9" -> {
    	                	HashMap<Integer,ArrayList<Product>> products_qty = new HashMap<Integer,ArrayList<Product>>();
    	                	products_qty = database.getLeastSellerProduct();
    	                	Integer qty_sold = 0;
    	                	
    	                	for (Integer qty : products_qty.keySet()) {
    	                		qty_sold = qty;
    	                	}
    	                	
    	                	for (ArrayList<Product> products : products_qty.values()) {
    	                		for (Product product : products) {
    	                			pv.printProductInfo(product);
        	                		System.out.println("Quantity sold: " + qty_sold);
    	                		}
    	                	}
    	                	break;
    	                }
    	                case "10" -> {
    	                	HashMap<Double,ArrayList<Product>> products_profit = new HashMap<Double,ArrayList<Product>>();
    	                	products_profit = database.getMostProfictProduct();
    	                	Double most_profit = 0.0;

    	                	for (Double profit : products_profit.keySet()) {
    	                		most_profit = profit;
    	                	}
    	                	
    	                	for (ArrayList<Product> products : products_profit.values()) {
    	                		for (Product product : products) {
    	                			pv.printProductInfo(product);
        	                		System.out.println("Profit: " + most_profit);
    	                		}
    	                	}
    	                	
    	                	break;
    	                }
    	                case "11" -> {
    	                	HashMap<Double,ArrayList<Product>> products_profit = new HashMap<Double,ArrayList<Product>>();
    	                	products_profit = database.getLeastProfictProduct();
    	                	Double least_profit = 0.0;

    	                	for (Double profit : products_profit.keySet()) {
    	                		least_profit = profit;
    	                	}
    	                	
    	                	for (ArrayList<Product> products : products_profit.values()) {
    	                		for (Product product : products) {
    	                			pv.printProductInfo(product);
        	                		System.out.println("Profit: " + least_profit);
    	                		}
    	                	}
    	                	break;
    	                }
    	                case "12" -> {
    	                	HashMap<Integer,ArrayList<User>> user_with_most_order = new HashMap<Integer,ArrayList<User>>();
    	                	user_with_most_order = database.getUserWithMostOrder();
    	            		Integer qty_orders = 0;
    	            		
    	            		for (Integer qty : user_with_most_order.keySet()) {
    	                		qty_orders = qty;
    	                	}
    	                	
    	                	for (ArrayList<User> users : user_with_most_order.values()) {
    	                		for (User user : users) {
    	                			uv.printBasicUserInfo(user);
        	                		System.out.println("Number of orders placed: " + qty_orders);
    	                		}
    	                	}
    	                	break;
    	                }
    	                case "13" -> {
    	                	HashMap<Integer,ArrayList<User>> user_with_least_order = new HashMap<Integer,ArrayList<User>>();
    	                	user_with_least_order = database.getUserWithLeastOrder();
    	            		Integer qty_orders = 0;
    	            		
    	            		for (Integer qty : user_with_least_order.keySet()) {
    	                		qty_orders = qty;
    	                	}
    	                	
    	                	for (ArrayList<User> users : user_with_least_order.values()) {
    	                		for (User user : users) {
    	                			uv.printBasicUserInfo(user);
        	                		System.out.println("Number of orders placed: " + qty_orders);
    	                		}
    	                	}
    	                	break;
    	                }
    	                case "14" -> {
    	                	HashMap<Double,ArrayList<User>> user_highest_spending = new HashMap<Double,ArrayList<User>>();
    	                	user_highest_spending = database.getUserHighestSpending();
    	            		Double highest_spending = 0.0;
    	            		
    	            		for (Double spent : user_highest_spending.keySet()) {
    	            			highest_spending = spent;
    	                	}
    	                	
    	                	for (ArrayList<User> users : user_highest_spending.values()) {
    	                		for (User user : users) {
    	                			uv.printBasicUserInfo(user);
        	                		System.out.println("Total spending: " + highest_spending);
    	                		}
    	                	}
    	                	break;
    	                }
    	                case "15" -> {
    	                	HashMap<Double,ArrayList<User>> user_least_spending = new HashMap<Double,ArrayList<User>>();
    	                	user_least_spending = database.getUserLeastSpending();
    	            		Double least_spending = 0.0;
    	            		
    	            		for (Double spent : user_least_spending.keySet()) {
    	            			least_spending = spent;
    	                	}
    	                	
    	                	for (ArrayList<User> users : user_least_spending.values()) {
    	                		for (User user : users) {
    	                			uv.printBasicUserInfo(user);
        	                		System.out.println("Total spending: " + least_spending);
    	                		}
    	                	}
    	                	break;
    	                }
    	                case "100" -> {
                                logged = false;
                                user_id = null;
                                System.out.println("Logout successfully!");
                                break;
                            }
    	                default -> System.out.println("Invalid command typed. Please try again.\n");
    	            }
                        //Aggiungi un nuovo prodotto
                        //Elimina un prodotto
                        //Crea un nuovo ordine
                        //Elimina un ordine
                        //Visualizza gli ordini effettuati dal cliente
                        //LOGOUT
                }
            }
        	
        }
        
        System.gc();
    }
}