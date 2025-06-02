package model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class MealOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mealorder_seq")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    Meal meal;

    @ManyToOne(cascade = CascadeType.ALL)
    Order order;

    public MealOrder() {
        super();
    }

    public MealOrder(Meal meal, Order order) {
        super();
        this.meal = meal;
        this.order = order;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Meal getMeal() {
        return meal;
    }

    public Order getOrder() {
        return order;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MealOrder mealOrder = (MealOrder) o;
        return Objects.equals(id, mealOrder.id) && Objects.equals(meal, mealOrder.meal) && Objects.equals(order, mealOrder.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, meal, order);
    }

    @Override
    public String toString() {
        return "MealOrder{" +
                "id=" + id +
                ", meal=" + meal +
                ", order=" + order +
                '}';
    }
}
