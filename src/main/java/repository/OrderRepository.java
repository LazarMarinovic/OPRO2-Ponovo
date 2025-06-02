package repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import model.Order;
import java.util.List;
@ApplicationScoped
public class OrderRepository {

    @Inject
    private EntityManager em;

    @Transactional
    public Order createOrder(Order order) {
        return em.merge(order);
    }

    @Transactional
    public List<Order> getOrders() {
        return em.createNamedQuery(Order.GET_ORDERS, Order.class).getResultList();
    }
}
