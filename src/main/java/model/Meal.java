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

    private String imagePath;

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

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meal meal = (Meal) o;
        return Double.compare(meal.price, price) == 0 &&
                Objects.equals(id, meal.id) &&
                Objects.equals(name, meal.name) &&
                Objects.equals(restaurant, meal.restaurant) &&
                Objects.equals(imagePath, meal.imagePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, restaurant, imagePath);
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", restaurant=" + (restaurant != null ? restaurant.getId() : "null") +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }

//Stavka 6.Entitet u kom se nalazi putanja do fajla ce imati i varijablu koja predstavlja taj fajl i u koju ce se ucitavati fajl sa fajlsistema?Nisam siguran

    @Transient // Da se ne bi cuvalo u bazu
    private byte[] imageData;

    public byte[] getImageData() {
        if (imagePath != null) {
            try {
                return java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(imagePath));
            } catch (Exception ignored) {}
        }
        return null;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }
}