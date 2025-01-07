
package city.org.rs;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.OPTIONS;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/products")
public class ProductResource {
	
	//DAO class creates and returns its single instance
	//This instance is then used by all APIs below to call the appropriate DAO methods
	private ProductDAO dao = ProductDAO.getInstance();
	
	//API to list all products
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> list() {
		return dao.listAll();
	}
	
	//API to insert new product
	/* NOTE:
	 * ----
	 * When the below method is invoked by a request it returns a response inside a Response object.
	 * Response is a class that CANNOT be instantiated directly (e.g. through the "new" command).
	 * Instead, it uses the so called "builder design pattern" whereby object creation is delegated 
	 * to a set of builders (hence the .build() method call below). This is because responses are complex 
	 * entities: a response comprises 3 lines (status line, headers, body) and, if headers are present, they may 
	 * encompass many (optional) key/value pairs. As such, responses need to be represented through complex 
	 * objects. 
	 * 
	 * These complex objects can be constructed through overloaded constructors but the whole thing
	 * gets really messy when one considers optional parameters (each requires a separate constructor). 
	 * Alternatively, one could implement a single no-args constructor and then rely on a multitude 
	 * of setter methods for setting all the different header parameters and options but this too gets 
	 * messy. Instead with the builder design pattern a separate builder is used for creating each
	 * different part of a response message. For instance, in the POST API below, a builder is used
	 * for building (instantiating) a response that returns the created URI, whereas in the PUT API below, a
	 * different builder is invoked for creating a different response that reports a status code.  
	 * 
	 * 
	 * Same comment applies to the PUT, DELETE and GET APIs below.
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response add(Product product) throws URISyntaxException {
		int newProductId = dao.add(product);
		URI uri = new URI("/products/" + newProductId);
		
		//created(uri) returns a new ResponseBuilder object for the created resource which sets the location of the new resource through its URI parameter
		//build() returns a response instance from the current ResponseBuilder (i.e. the one returned by created(uri))
		return Response.created(uri).build();
	}

	//API to update existing product
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response update(@PathParam("id") int id, Product product) {
		product.setId(id);
		if (dao.update(product)) {
			//ok() returns a new ResponseBuilder object with an OK status
			//build() returns a response instance from the current ResponseBuilder (i.e. the one returned by created(uri))
			return Response.ok().build();
		} else {
			//notModified() returns a new ResponseBuilder object with a non-modified status
			//build() returns a response instance from the current ResponseBuilder (i.e. the one returned by created(uri))
			return Response.notModified().build();
		}
	}
	
	//API to delete product
	@DELETE
	@Path("{id}")
	public Response delete(@PathParam("id") int id) {
		if (dao.delete(id)) {
			return Response.ok().build();					
		} else {
			return Response.notModified().build();
		}
	}
	
	//API to get  product
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("id") int id) {
		Product product = dao.get(id);
		if (product != null) {
			//returns a new ResponseBuilder object with the supplied status and a representation
			//of the object that we are getting
			return Response
					.ok(product, MediaType.APPLICATION_JSON)
					.build();
		} else {
			//status(Response.Status.NOT_FOUND) returns a new ResponseBuilder object with the supplied status
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}

	
}
