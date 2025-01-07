package city.org.rc;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;

public class ProductClientTest {
	private static String baseURI = "http://localhost:8081/MyWebsite/rest/products";
	
	
	/* NOTE:
	 * ----
	 * This code is "buggy" in the sense that if one calls testDelete() to delete, say, the product with ID 3,
	 * and then performs a testGet() with the same product ID, an exception will be raised and execution will stop
	 */
	public static void main(String[] args) {
		testList();
		testGet();
		testAdd();
		testUpdate();
		testDelete();
		testList();
	}

	//DELETE request 
	/* NOTE:
	 * ----
	 * The Response.class creates an object that carries a representation of the current instantiation of the Response class.
	 * As explained in the comments in the (server-side) ProductResource class, the Response class is not instantiated 
	 * directly and hence there are no named instances of it to which we can refer. Instead, instantiation is performed 
	 * through builders (the application of the build() method - see ProductResource class). If we want to refer to the 
	 * current instantiation and get a representation of it, the Response.class object must be used. 
	 * 
	 * Same note applies to all request-constructing methods below.
	 */
	private static void testDelete() {
		WebTarget target = getWebTarget();
		String productId = "3";
		/* The following statement sends an HTTP DELETE request to a resource identified by the productId path parameter 
		 * using the target WebTarget object, and returns a Response object that contains the server's response to the request.
		 * 
		 * Here's a breakdown of how this statement works:
		 * target.path(productId) - Adds the productId path parameter to the URI with which the target endpoint was primed upon creation
		 * 						    This essentially creates a new endpoint...
		 * .request() - Creates an invocation builder for the target endpoint, which can be used to build and send HTTP requests
		 * .delete(Response.class) - Sends an HTTP DELETE request to the target endpoint and returns a 
		 * Response object that represents the server's response. 
		 * The Response.class argument specifies that you expect the response to be mapped to a Response object, which will contain the 
		 * HTTP status, headers, and body of the server's response.
		 */
		Response response = target.path(productId).request().delete(Response.class);
		System.out.println(response);		
	}

	//UPDATE request
	/* NOTE:
	 * ----
	 * When the body of a request is significant, i.e. in case of POST or PUT request, we need to specify its content. This is 
	 * achieved through the method entity() of the class Entity. The first parameter of this method specifies the content of the 
	 * body, and the second the media type in which this content is serialised. Note that 
	 * the "put" method accepts two params: the body content and its media type, as well as the type that the response will be converted
	 * to (here the Response class itself).
	 * 
	 * Same note applies to POST requests.
	 */
	private static void testUpdate() {
		WebTarget target = getWebTarget();
		Product product = new Product("ZenFoneX", 100f);
		String productId = "4";
		Response response = target.path(productId).request()
				.put(Entity.entity(product, MediaType.APPLICATION_JSON), Response.class);
		System.out.println(response);		
	}

	//creates client object with any filters/interceptors registered with the config object (here null)
	//returns baseURI (i.e. servlet URI) as the client's target URI 
	static WebTarget getWebTarget() {
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		
		return client.target(baseURI); //target object - represents endpoint to which requests may be sent and from which responses are created		
	}
	
	//GET all request
	static void testList() {
		WebTarget target = getWebTarget(); 
		
		String response = target.request().accept(MediaType.APPLICATION_JSON).get(String.class);
		
		System.out.println(response);		
	}

	//GET ID request
	static void testGet() {
		WebTarget target = getWebTarget();
		String productId = "2";
		String product = target.path(productId).request().accept(MediaType.APPLICATION_JSON).get(String.class);
		
		System.out.println(product);		
	}
	
	//ADD request
	static void testAdd() {
		WebTarget target = getWebTarget();
		Product product = new Product("ZenFoneX", 888.88f);
		Response response = target.request()
				.post(Entity.entity(product, MediaType.APPLICATION_JSON), Response.class);
		
		System.out.println(response.getLocation().toString());
	}
}
