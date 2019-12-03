package com.udacity.jdnd.course3.lesson2;

import com.udacity.jdnd.course3.lesson2.entity.Order;
import com.udacity.jdnd.course3.lesson2.entity.OrderItem;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;

public class ManyToManyTest {

    private static final String PERSISTENCE_UNIT_NAME = "Order";

    private static EntityManagerFactory factory;

    public static void main(String[] args) {
        // STEP 1: Create a factory for the persistence unit
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

        // STEP 2: Create an EntityManager
        EntityManager em = factory.createEntityManager();

        // STEP 3: Start a transaction
        em.getTransaction().begin();

        // STEP 4: Create an order (entity is in Transient state)
        Order order = new Order();
        order.setCustomerName("John Doe");
        order.setCustomerAddress("123 Main St, Birmingham, AL, 40861");
        order.setCreatedTime(Timestamp.valueOf(LocalDateTime.now()));

        // create order item1
        OrderItem orderItem = new OrderItem();
        orderItem.setItemName("Parachute");
        orderItem.setItemCount(3);
        orderItem.setOrder(order);

        // create order item2
        OrderItem orderItem1 = new OrderItem();
        orderItem1.setItemName("Hand Glider");
        orderItem1.setItemCount(3);
        orderItem1.setOrder(order);

        order.setOrderItems(Arrays.asList(orderItem, orderItem1));

        // STEP 5: Persist the order entity
        em.persist(order);

        // STEP 6: Persist the order items
        em.persist(orderItem);
        em.persist(orderItem1);

        em.getTransaction().commit();

        // entity is persistent now
        System.err.println("Order ID:" + order.getOrderId());
        System.err.println("Order Item ID 1:" + orderItem.getOrderItemId());
        System.err.println("Order Item ID 2:" + orderItem1.getOrderItemId());

        em.close();

        // EXAMPLE: How to read an entity
        readOrder(order.getOrderId(), factory);

        factory.close();
    }

    private static void readOrder(Integer orderId, EntityManagerFactory factory) {
        // STEP 1: Create an EntityManager
        EntityManager em = factory.createEntityManager();

        // STEP 2: use the find() method to load an order
        Order order = em.find(Order.class, orderId);
        // TIP: use em.remove() to delete the corresponding row of the entity from the table

        System.err.println("Order: " + order);

        em.close();
    }

//    private static void deleteOrder(Integer orderId, EntityManagerFactory factory) {
//        // STEP 1: Create an EntityManager
//        EntityManager em = factory.createEntityManager();
//
//        // STEP 2: use the find() method to load an order
//        Order order = new Order();
//        order.setOrderId(orderId);
//        em.remove(order);
//
//        System.err.println("Order: " + order);
//
//        em.close();
//    }
}