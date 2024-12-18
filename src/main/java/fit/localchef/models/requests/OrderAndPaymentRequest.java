package fit.localchef.models.requests;

import lombok.Data;
import java.util.List;

@Data
public class OrderAndPaymentRequest {
    private List<OrderItemDTO> orderItems; // List of meals and quantities
    private String stripeToken;           // Stripe payment token
}
