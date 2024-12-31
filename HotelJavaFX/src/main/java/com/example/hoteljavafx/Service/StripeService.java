package com.example.hoteljavafx.Service;


import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

public class StripeService {

    public StripeService() {
        Stripe.apiKey = "sk_test_51QUTneRpAHGhTUni3ktPC6PsUpii3IwhGyljf1GRkxk4wzGW4fjXBoz42vWb6bPTINNfKBxGUi5rBi43Vc5uJpJ500gJdX3ye0"; // Use your Stripe secret API key
    }

    public PaymentIntent createPaymentIntent(long amount) throws Exception {
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(amount) // Amount in cents (e.g., $10 = 1000 cents)
                .setCurrency("usd") // $
                .build();

        return PaymentIntent.create(params);
    }
}