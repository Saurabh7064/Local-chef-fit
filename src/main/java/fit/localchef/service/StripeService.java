package fit.localchef.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StripeService {

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    public StripeService() {
        Stripe.apiKey = "sk_test_51QXFHXELPuZlz0arF93NQejkPJElOM86qKJmiB0Vqfs3eSHlf7NpWSGRSUVu5SgpkAReMyIsclkfJsUVraB68O4N002kwmrLza";
    }

    public Charge createCharge(Double amount, String stripeToken, String description) throws StripeException {
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", (int)(amount * 100)); // Stripe works with cents
        chargeParams.put("currency", "usd");
        chargeParams.put("description", description);
        chargeParams.put("source", stripeToken); // Stripe Token from client-side

        return Charge.create(chargeParams);
    }
}

