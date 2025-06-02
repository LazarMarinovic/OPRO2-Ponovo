package repository;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import model.Meal;
import model.Restaurant;
import java.util.List;

@Dependent
public class RestaurantRepository {

    @Inject
    private EntityManager em;

    @Transactional
    public Restaurant createRestaurant(Restaurant r) {
        return em.merge(r);
    }

    @Transactional
    public List<Restaurant> getRestaurants() {
        List<Restaurant> restaurants = em.createNamedQuery(Restaurant.GET_ALL_RESTAURANTS, Restaurant.class).getResultList();

        for (Restaurant r : restaurants) {
            List<Meal> meals = em.createNamedQuery(Meal.GET_MEALS_FOR_RESTAURANT, Meal.class)
                    .setParameter("id", r.getId())
                            .getResultList();



            r.setMeals(meals);
        }
        return restaurants;
    }
}
