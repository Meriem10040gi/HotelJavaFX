package com.example.hoteljavafx.Utils;

import com.example.hoteljavafx.Controller.PayementController;
import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class StripePaymentServer {

    public static void startPaymentServer() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);
            server.createContext("/create-payment-intent", new PaymentIntentHandler());
            server.createContext("/payment-result", new PaymentResultHandler());
            server.setExecutor(null);
            server.start();
            System.out.println("Server started on port 8081");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class PaymentIntentHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                String response = StripePaymentServer.createPaymentIntent();
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
        }
    }
    public static String createPaymentIntent() {
        try {
            // Set your Stripe secret key
            Stripe.apiKey = "sk_test_51QUTneRpAHGhTUni3ktPC6PsUpii3IwhGyljf1GRkxk4wzGW4fjXBoz42vWb6bPTINNfKBxGUi5rBi43Vc5uJpJ500gJdX3ye0";

            // Create a PaymentIntent
            Map<String, Object> params = new HashMap<>();
            params.put("amount", 5000); // Amount in cents ($50.00)
            params.put("currency", "usd");

            PaymentIntent paymentIntent = PaymentIntent.create(params);

            // Return clientSecret and amount as JSON
            return "{ \"clientSecret\": \"" + paymentIntent.getClientSecret() + "\", \"amount\": " + paymentIntent.getAmount() + " }";

        } catch (Exception e) {
            e.printStackTrace();
            return "{ \"error\": \"" + e.getMessage() + "\" }";
        }
    }
    static class PaymentResultHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                System.out.println("Payment Result Received: " + requestBody);
                PayementController.processPaymentResult(requestBody);
                String response = "Payment result processed successfully!";
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else {
                exchange.sendResponseHeaders(405, -1); // Method not allowed
            }
        }
    }
}
