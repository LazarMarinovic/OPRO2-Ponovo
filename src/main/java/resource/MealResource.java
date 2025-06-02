package resource;

import exception.MealException;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.Meal;
import model.MealOrder;
import repository.MealRepository;

import java.util.List;

@Path("/meal/")
public class MealResource {
    @Inject
    private MealRepository mealRepository;


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/addMeal")
    public Response addMeal(Meal meal) {
        Meal m = mealRepository.createMeal(meal);

        return Response.ok().entity(m).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/addMealOrder")
    public Response addMealOrder(MealOrder mealOrder) {
        MealOrder mo = null;

        try {
            mo = mealRepository.createMealOrder(mealOrder);
        } catch (MealException e) {
            return Response.ok().entity(e.getMessage()).build();
        }

        return Response.ok().entity(mo).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getAllMeals")
    public Response getAllMeals() {
        List<Meal> meals = mealRepository.getMeals();

        return Response.ok().entity(meals).build();
    }
}
