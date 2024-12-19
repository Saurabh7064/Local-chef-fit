package fit.localchef.service;


import fit.localchef.models.*;
import fit.localchef.models.requests.OrderAndPaymentRequest;
import fit.localchef.models.requests.OrderItemDTO;
import fit.localchef.repository.MealRepository;
import fit.localchef.repository.OrderRepository;
import fit.localchef.repository.OrderItemRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private StripeService stripeService;

    // Create an order
    public Order createOrder(Customer customer, List<OrderItemDTO> orderItems) {
        AtomicReference<Double> totalAmount = new AtomicReference<>(0.0);

        // Fetch and validate meals
        List<OrderItem> items = orderItems.stream().map(dto -> {
            Meal meal = mealRepository.findById(dto.getMealId())
                    .orElseThrow(() -> new RuntimeException("Meal not found with ID: " + dto.getMealId()));
            OrderItem item = new OrderItem();
            item.setMeal(meal);
            item.setChef(meal.getChef());
            item.setQuantity(dto.getQuantity());
            item.setPrice(meal.getPricePerServing() * dto.getQuantity());

            totalAmount.updateAndGet(v -> v + item.getPrice()); // Update total amount
            return item;
        }).collect(Collectors.toList());

        // Create and save order
        Order order = new Order();
        order.setCustomer(customer);
        order.setItems(items);
        order.setTotalAmount(totalAmount.get());
        order.setPaymentStatus("PENDING");

        order = orderRepository.save(order);

        // Save order items
        for (OrderItem item : items) {
            item.setOrder(order);
            orderItemRepository.save(item);
        }

        return order;
    }

    // Process payment
    public String processPayment(Integer orderId, String stripeToken) throws StripeException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

        // Use StripeService to process payment
        Charge charge = stripeService.createCharge(order.getTotalAmount(), stripeToken, "Order ID: " + orderId);

        if (charge.getPaid()) {
            order.setPaymentStatus("COMPLETED");
            orderRepository.save(order);
            return "Payment Successful for Order ID: " + orderId;
        } else {
            order.setPaymentStatus("FAILED");
            orderRepository.save(order);
            throw new RuntimeException("Payment failed for Order ID: " + orderId);
        }
    }


    public String placeOrderAndProcessPayment(Customer customer, OrderAndPaymentRequest request) throws StripeException {
        AtomicReference<Double> totalAmount = new AtomicReference<>(0.0);

        // Validate meals and calculate total amount
        List<OrderItem> items = request.getOrderItems().stream().map(dto -> {
            Meal meal = mealRepository.findById(dto.getMealId())
                    .orElseThrow(() -> new RuntimeException("Meal not found with ID: " + dto.getMealId()));
            OrderItem item = new OrderItem();
            item.setMeal(meal);
            item.setChef(meal.getChef());
            item.setQuantity(dto.getQuantity());
            item.setPrice(meal.getPricePerServing() * dto.getQuantity());
            totalAmount.updateAndGet(v -> v + item.getPrice()); // Update total amount
            return item;
        }).collect(Collectors.toList());

        // Create order
        Order order = new Order();
        order.setCustomer(customer);
        order.setItems(items);
        order.setTotalAmount(totalAmount.get());
        order.setPaymentStatus("PENDING");
        order = orderRepository.save(order);

        // Save order items
        for (OrderItem item : items) {
            item.setOrder(order);
            orderItemRepository.save(item);
        }

        // Process payment with Stripe
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", (int) (totalAmount.get() * 100)); // Convert to cents
        chargeParams.put("currency", "usd");
        chargeParams.put("description", "Order #" + order.getId());
        chargeParams.put("source", "tok_visa");

        Charge charge = Charge.create(chargeParams);

        // Update payment status
        if (charge.getPaid()) {
            order.setPaymentStatus("COMPLETED");
        } else {
            order.setPaymentStatus("FAILED");
        }

        orderRepository.save(order);

        // Return payment status
        return charge.getPaid() ? "Payment Successful" : "Payment Failed";
    }

    public List<Order> getOrdersByCustomer(Integer customerId) {
        return orderRepository.findByCustomerId(customerId);
    }


}
