package model;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(name = Meal.GET_MEALS_FOR_RESTAURANT, query = "SELECT m FROM Meal m WHERE m.restaurant.id = :id"),
        @NamedQuery(name = Meal.GET_ALL_MEALS, query = "SELECT m FROM Meal m"),
})
public class Meal {

    public static final String GET_ALL_MEALS = "Meal.getAllMeals";
    public static final String GET_MEALS_FOR_RESTAURANT = "Meal.getMealsForRestaurant";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "meal_seq")
    private Long id;
    private String name;
    private double price;

    @ManyToOne
    private Restaurant restaurant;

    public Meal() {
        super();
    }

    public Meal(String name, double price) {
        super();
        this.name = name;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Meal meal = (Meal) o;
        return Double.compare(price, meal.price) == 0 && Objects.equals(id, meal.id) && Objects.equals(name, meal.name) && Objects.equals(restaurant, meal.restaurant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, restaurant);
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", restaurant=" + restaurant +
                '}';
    }
}