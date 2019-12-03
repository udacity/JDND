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

        // STEP 2: Create an EntityManager

        // STEP 3: Start a transaction

        // STEP 4: Create an order (entity is in Transient state)

        // create order item1

        // create order item2

        // STEP 5: Persist the order entity

        // STEP 6: Persist the order items

        // entity is persistent now

    }

    private static void readOrder(Integer orderId, EntityManagerFactory factory) {
        // STEP 1: Create an EntityManager

        // STEP 2: use the find() method to load an order

        // TIP: use em.remove() to delete the corresponding row of the entity from the table

    }

   private static void deleteOrder(Integer orderId, EntityManagerFactory factory) {
       // STEP 1: Create an EntityManager
       EntityManager em = factory.createEntityManager();

       // STEP 2: use the find() method to load an order
       Order order = new Order();
       order.setOrderId(orderId);
       em.remove(order);

       System.err.println("Order: " + order);

       em.close();
   }
}