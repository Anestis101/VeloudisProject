package city.org.rs;

import java.util.ArrayList;
import java.util.List;

//Singleton class
public final class ProductDAO {
	private static ProductDAO instance; //object to hold single class instance
	private static List<Product> data = new ArrayList<>(); //the data store...
	
	//Static block
	//Executed only once prior to constructor
	//The data list is thus initialized irrespective of whether a DAO object exists (separation of concerns): 
	//DAO objects are meant to perform CRUD on an object, not to initialize it...
	static {
		//add methods here belong to the List and should of course not be confused with custom add method below!
		data.add(new Product(1, "iPhone 12", 1339.00f));
		data.add(new Product(2, "Galaxy s21", 1349.00f));
		data.add(new Product(3, "OnePlus 9 Pro", 1219.00f));
	} 
	//----
	//Singleton class "design pattern": defines private constructor that can only be called from within the class itself
	
	//Private constructor
	private ProductDAO() {
		
	}
	
	//Creates and returns single class instance
	//The if statement ensures that, no matter how many times getInstance() is called (from external objects), it will only create 
	//a single instance of the class
	public static ProductDAO getInstance() {
		if (instance == null) {
			instance = new ProductDAO(); //call private constructor
		}
		
		return instance;				
	}
	//----

	
	//----
	//Operations exposed to servlet
	
	//Get product from data list
	//Returns Product object retrieved from list (on success) or null (on failure)
	public Product get(int id) {
		Product productToFind = new Product(id); //create a new product obj. with specified id
		int index = data.indexOf(productToFind); //search for a product in data with the same id as the newly-created product 
		if (index >= 0) {
			return data.get(index); //product found and returned
		}
		return null;
	}
	
	//Remove product from data list
	//Returns true (on success) or false (on failure)
	public boolean delete(int id) {
		Product productToFind = new Product(id); //create a new product obj. with specified id
		int index = data.indexOf(productToFind); //search for a product in data with the same id as the newly-created product
		if (index >= 0) {
			data.remove(index); //product found and removed
			// After removing, adjust the ids of the remaining products
			for (int i = index; i < data.size(); i++) {
				Product product = data.get(i);
				product.setId(product.getId() - 1);
			}
			return true;
		}
		
		return false;
	}
	
	//Update product on data list
	//Returns true (on success) or false (on failure)
	public boolean update(Product product) {
		int index = data.indexOf(product); //search for a product in data with the same id as the one in the argument product
		if (index >= 0) {
			data.set(index, product);//replaces at index with argument product
			return true;
		}
		return false;
	}
	
	//Add product to data list
	//Returns new product id (no failure case here...)
	public int add(Product product) {
		int newId = data.size() + 1;//id set by application (not client)
		product.setId(newId); //Product class setter
		data.add(product); //appends product at end of list
				
		return newId;
	}

	//List all products
	//Returns entire data list (no failure case here...)
	public List<Product> listAll() {
		return new ArrayList<Product>(data);
	}
	//----
			
}

