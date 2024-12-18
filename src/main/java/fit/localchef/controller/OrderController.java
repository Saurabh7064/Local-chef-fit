package fit.localchef.controller;


import com.stripe.exception.StripeException;
import fit.localchef.models.Customer;
import fit.localchef.models.Order;
import fit.localchef.models.requests.OrderAndPaymentRequest;
import fit.localchef.service.CustomerService;
import fit.localchef.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final CustomerService customerService;

    @PostMapping("/place-order")
    public ResponseEntity<String> placeOrderAndPay(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody OrderAndPaymentRequest request) throws StripeException {

        // Get customer based on authenticated user
        Customer customer = customerService.findByEmail(userDetails.getUsername());

        // Call the combined service method to handle order and payment
        String paymentStatus = orderService.placeOrderAndProcessPayment(customer, request);

        // Return response based on the payment result
        return ResponseEntity.ok(paymentStatus);
    }


    // Endpoint to process payment for an order
    @PostMapping("/{orderId}/payment")
    public ResponseEntity<String> processPayment(@PathVariable Integer orderId,
                                                 @RequestParam String stripeToken) throws StripeException {
        String paymentStatus = orderService.processPayment(orderId, stripeToken);
        return ResponseEntity.ok(paymentStatus);
    }

    // Endpoint to list all orders for a customer
    @GetMapping
    public ResponseEntity<List<Order>> getCustomerOrders(@AuthenticationPrincipal UserDetails userDetails) {
        Customer customer = customerService.findByEmail(userDetails.getUsername());
        List<Order> orders = orderService.getOrdersByCustomer(customer.getId());
        return ResponseEntity.ok(orders);
    }


}

