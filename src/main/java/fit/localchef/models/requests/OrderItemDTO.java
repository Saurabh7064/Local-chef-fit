package fit.localchef.models.requests;

import lombok.Data;

@Data
public class OrderItemDTO {
    private Integer mealId;
    private Integer quantity;
}
