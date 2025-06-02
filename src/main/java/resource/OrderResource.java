package resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.Order;
import repository.OrderRepository;
import java.util.List;

@Path("/order/")
public class OrderResource {
    @Inject
    private OrderRepository orderRepository;


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/addOrder")
    public Response addOrder(Order order) {
        Order o = orderRepository.createOrder(order);

        return Response.ok().entity(o).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getAllOrders")
    public Response getAllOrders() {
        List<Order> orders = orderRepository.getOrders();

        return Response.ok().entity(orders).build();
    }
}
