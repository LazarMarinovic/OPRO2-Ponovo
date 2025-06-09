package repository;

import exception.MealException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import model.Meal;
import model.MealOrder;
import model.Order;

import java.util.List;
@ApplicationScoped
public class MealRepository {

    @Inject
    private EntityManager em;

    @Transactional
    public Meal createMeal(Meal meal) {
        return em.merge(meal);
    }

    @Transactional
    public List<Meal> getMeals() {
        return em.createNamedQuery(Meal.GET_ALL_MEALS, Meal.class).getResultList();
    }

    @Transactional
    public MealOrder createMealOrder(MealOrder mealOrder) throws MealException {
        Order order = em.find(Order.class, mealOrder.getOrder().getId());
        Meal meal = em.find(Meal.class, mealOrder.getMeal().getId());

        if(order == null || meal == null) {
            throw new MealException("Meal or Order does not exist");
        }

        MealOrder mo = new MealOrder(meal, order);
        return em.merge(mo);
    }

    public Meal findMealById(Long id) {
        if (id == null) {
            return null;
        }
        return em.find(Meal.class, id);
    }
}
