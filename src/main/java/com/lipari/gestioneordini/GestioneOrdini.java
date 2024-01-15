package com.lipari.gestioneordini;

import java.sql.SQLException;
import java.util.*;
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
    private static Integer id_count = 0;
    private static Integer id_count_product = 0;
//    private static Integer id_count_order = 0;
    
//    private static HashMap<Integer, User> users = new HashMap<>();
//    private static HashMap<Integer, Item> items = new HashMap<>();
//    private static HashMap<Integer, Order> orders = new HashMap<>();
//    private static HashMap<Integer, String> address_list = new HashMap<>();
//    private static ItemView iv = new ItemView();
    private static UserView uv = new UserView();
    private static OrderView ov = new OrderView();
    private static ProductView pv = new ProductView();
    private static CartItemView civ = new CartItemView();
    private static AddressView av = new AddressView();
    
//    static User admin1 = new User(++id_count,"Mattia","Ruberto","m.ruberto","?TestPassword?","mattia.ruberto@liparipeople.com",1);
//    static User admin2 = new User(++id_count,"Vito","Bica","v.bica","!TestPassword!","vito.bica@liparipeople.com",1);
    
//    static Item item1 = new Item(++id_count_product,id_count_product,"Prodotto 1",3,15.00);
//    static Item item2 = new Item(++id_count_product,id_count_product,"Prodotto 3",5,20.00);
    // L'id_count è pre-incrementato per permettere al programma di gestire id incrementali in runtime come farebbe la chiave primaria di un DBMS con auto-increment.
    // E' stato scelto il pre-incremento per consentire ad un account di tipo admin di visualizzare il numero di utenti registrati in piattaforma.
    // E' possibile farlo anche con il post-incremento ricordandosi però di diminuire il conteggio finale di 1 in quanto ad ogni nuova registrazione
    // l'id_count viene incrementato preventivamente e non rappresenta il numero reale di utenti registrati.
    
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
        boolean logged = false;
        Integer user_id = null;
        boolean quit = false;
        
//        users.put(1, admin1);
//        users.put(2,admin2);
        
//        items.put(item1.getId(), item1);
//        items.put(item2.getId(), item2);
        
        Dbms database = new Dbms();
        
        
//        try {
//			System.out.println(database.getConnection().isClosed());
//        	database.selectUsers();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
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
                        //Creazione di un nuovo utente.
//                        User new_user = new User(++id_count,name,surname,username,password,email,0);
//                        //Aggiunta dell'utente alla lista degli utenti registrati.
//                        uv.printUserInfo(new_user);
//                        System.out.println("\nAdded new user with this information.\n");
//                        users.put(id_count,new_user);
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
//                            for(User user : users.values()){
//                                if(user.getUsername().equals(username) && user.getPassword().equals(password)){
//                                        user_id = user.getId();
//                                        logged = true;
//                                        uv.printUserInfo(user);
//                                    }
//                                    break;
//                                }
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
            	
//            	User current_user = users.get(user_id);
//            	Integer count_order_user;
            	
            	
    	        while(logged){
    	            System.out.println("\nYou're logged in as: " + current_user.getUsername()+ ".\nChoose an action to perform:\n1)Add Item\n2)Remove Item\n3)New Order\n4)Cancel Order\n5)History of Orders\n6)LogOut\nInput:");
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
//                                Item item = new Item(user_id,++id_count_product,description,qty,price);
                                
                                //                            items.put(id_count_product, item);
                            }
    	                case "2" -> {
                                boolean removed = false;
                                while(!removed){
//                                    iv.printItems(items);
                                    System.out.println("Choose the product to remove by typing its Product ID: ");
                                    Integer id = scanner.nextInt();
                                    scanner.nextLine();
                                    removed = database.removeProduct(id);
//                                    if(items.containsKey(id)){
//                                        items.remove(id);
//                                        removed = true;
//                                        System.out.println("Item removed succesfully.");
//                                    }else{
//                                        System.out.println("Invalid Product ID. Try again.\n");
//                                    }
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
                                    
                                
//                                    for (Item i : items.values()) {
//                                        System.out.println("\n"+i.toString());
//                                    }
                                    
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
                                        //Verifica se il magazzino contiene la quantità richiesta per il prodotto
//                                        if (current_item.getQuantity()>=qty) {
//                                            if (cart.containsKey(id)) {
//                                                cart.get(id).setQuantity(cart.get(id).getQuantity()+qty);
//                                            }
//                                            else {
//                                                Item cart_item = new Item(current_item.getId(),current_item.getId_product(),current_item.getDescription(),qty,current_item.getPrice());
//                                                cart.put(id, cart_item);
//                                            }
//                                            current_item.setQuantity(current_item.getQuantity()-qty);
//                                        }
//                                        else {
//                                            System.out.println("Error: The quantity available for the product is: "+current_item.getQuantity());
//                                        }
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
//                                            System.out.println("Select the address from those available or enter a new one: ");
//                                            System.out.println("0 - Enter a new address");
//                                            //Visualizza indirizzi usati in precedenza dall'utente
//                                            Integer id_count_address = 0;
//                                            for (Order o : orders.values()) {
//                                                if (current_user.getId().equals(o.getId_user())) {
//                                                    address_list.put(++id_count_address,o.getAddress());
//                                                    System.out.println(id_count_address+" - "+o.getAddress());
//                                                }
//                                            }
//                                            String address_command = scanner.nextLine();
//                                            if (address_command.equals("0")){
//                                                System.out.println("Enter the new address:\nInput: ");
//                                                address = scanner.nextLine();
//                                                address_list.put(id_count_address, address);
//                                            }
//                                            else if (address_list.containsKey(Integer.valueOf(address_command))){
//                                                address = address_list.get(Integer.valueOf(address_command));
//                                            }
//                                            else {
//                                                System.out.println("Invalid command!");
//                                            }
//                                            
//                                            isComplete=true;
//                                            //orders.put(++id_count_order, new Order(current_user.getId(), address, cart));
//                                            System.out.println(orders.get(id_count_order).toString());
//                                            System.out.println("Order placed successfully!");
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
                                        			
                                        			boolean isAdded = database.insertAddress(address, user_id);
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
                                        		
                                        		
                                        		
//                                              Integer id_count_address = 0;
//                                              for (Order o : orders.values()) {
//                                                  if (current_user.getId().equals(o.getId_user())) {
//                                                      address_list.put(++id_count_address,o.getAddress());
//                                                      System.out.println(id_count_address+" - "+o.getAddress());
//                                                  }
//                                              }
                                        		
                                        	} else {
                                        		System.out.println("Errore: Non è possibile creare l'ordine se il carrello è vuoto!");
                                        	}
                                        }
                                    }
                                }
                                break;
                            }
    	                case "4" -> {
//                                count_order_user = 0;
                                
                                System.out.println("Choose the order to cancel\nInput: ");
                                Integer order_id = scanner.nextInt();
                                scanner.nextLine();
                                
                                
                                
                                boolean removed = database.removeOrderByID(user_id,order_id);
                                
                                if (removed) {
                                	System.out.println("Ordine cancellato correttamente!");
                                } else {
                                	System.out.println("L'ordine selezionato non è stato trovato!");
                                }
                                
                                
//                                for (Order o : orders.values()) {
//                                    if (current_user.getId().equals(o.getId_user())){
//                                        count_order_user++;
//                                        System.out.println(o.toString());
//                                    }
//                                }
//                                
//                                if (count_order_user==0) {
//                                    System.out.println("No orders were found");
//                                }
//                                else {
//                                    System.out.println("Choose the order to cancel\nInput: ");
//                                    String remove_order = scanner.nextLine();
//                                    if (orders.containsKey(Integer.valueOf(remove_order))){
//                                        orders.remove(Integer.valueOf(remove_order));
//                                        System.out.println("Order canceled successfully!");
//                                    }
//                                    else {
//                                        System.out.println("Invalid order ID!");
//                                    }
//                                    
//                                }
                                break;
                            }
    	                case "5" -> {
    	                		HashMap<Integer, Order> orders = new HashMap<Integer, Order>();
    	                		
//                                count_order_user = 0;
                                
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
                                

//                                for (Order o : orders.values()) {
//                                    if (current_user.getId().equals(o.getId_user())){
//                                        count_order_user++;
//                                        System.out.println(o.toString());
//                                    }
//                                }
//                                if (count_order_user==0) {
//                                    System.out.println("No orders were found");
//                                }
                                break;
                            }
    	                case "6" -> {
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