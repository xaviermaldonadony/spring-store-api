package com.example.store.payments;

import com.example.store.entities.OrderItem;
import com.example.store.entities.PaymentStatus;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.stripe.param.checkout.SessionCreateParams;
import com.example.store.entities.Order;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class StripePaymentGateway implements PaymentGateway {
    @Value("${websiteUrl}")
    private String websiteUrl;

    @Value("${stripe.webhookSecretKey}")
    private String webhookSecretKey;

    @Override
    public CheckoutSession createCheckoutSession(Order order) {
        try {
            var builder = SessionCreateParams.builder()
                    .setClientReferenceId(order.getId().toString()) // Set the direct link to our order
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl(websiteUrl + "/checkout-success?orderId=" + order.getId())
                    .setCancelUrl(websiteUrl + "/checkout-cancel")
                    .setPaymentIntentData(createPaymentIntent(order));

            order.getItems().forEach(item -> {
                var lineItem = createLineItem(item);
                builder.addLineItem(lineItem);
            });

            var session = Session.create(builder.build());

            return new CheckoutSession(session.getUrl());
        } catch (StripeException ex) {
            System.out.println(ex.getMessage());
            throw new PaymentException();
        }
    }

    private static SessionCreateParams.PaymentIntentData createPaymentIntent(Order order) {
        // We can still pass metadata if needed for other purposes, but it's not our primary link.
        return SessionCreateParams.PaymentIntentData.builder()
                .putMetadata("order_id", order.getId().toString())
                .build();
    }

    @Override
    public Optional<PaymentResult> parseWebhookRequest(WebhookRequest request) {
        var payload = request.getPayload();
        var signature = request.getHeaders().get("stripe-signature");

        // ===== START DIAGNOSTIC LOGS =====
        System.out.println("Attempting to verify webhook signature.");
        System.out.println("Signature Header: " + signature);
        System.out.println("Webhook Secret Loaded: " + (webhookSecretKey != null && !webhookSecretKey.isEmpty() ? "Present" : "MISSING or EMPTY"));
        // ===== END DIAGNOSTIC LOGS =====

        try {
            var event = Webhook.constructEvent(payload, signature, webhookSecretKey);

            System.out.println("SIGNATURE VERIFIED SUCCESSFULLY. Event Type: " + event.getType());

            return switch (event.getType()) {
                case "checkout.session.completed" ->
                        Optional.of(new PaymentResult(extractOrderId(event), PaymentStatus.PAID));

                case "payment_intent.payment_failed" ->
                        Optional.of(new PaymentResult(extractOrderId(event), PaymentStatus.FAILED));

                default -> Optional.empty();
            };
        } catch (SignatureVerificationException e) {
            System.out.println("SIGNATURE VERIFICATION FAILED: " + e.getMessage());
            throw new PaymentException("Invalid signature");
        }
    }

    private Long extractOrderId(Event event) {
        StripeObject stripeObject = event.getDataObjectDeserializer().getObject().orElseThrow(
                () -> new PaymentException("Could not deserialize Stripe event.")
        );

        if (stripeObject instanceof Session session) {
            // For completed checkouts, the client_reference_id is the most reliable link.
            return Long.valueOf(session.getClientReferenceId());
        } else if (stripeObject instanceof PaymentIntent paymentIntent) {
            // For other events, fall back to the metadata.
            return Long.valueOf(paymentIntent.getMetadata().get("order_id"));
        } else {
            throw new PaymentException("Unexpected event type: " + stripeObject.getClass().getName());
        }
    }

    private SessionCreateParams.LineItem createLineItem(OrderItem item) {
        return SessionCreateParams.LineItem.builder()
                .setQuantity(Long.valueOf(item.getQuantity()))
                .setPriceData(createPrriPriceData(item))
                .build();
    }

    private SessionCreateParams.LineItem.PriceData createPrriPriceData(OrderItem item) {
        return SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("usd")
                .setUnitAmountDecimal(
                    item.getUnitPrice().multiply(BigDecimal.valueOf(100)))
                .setProductData(createProductData(item))
                .build();
    }

    private SessionCreateParams.LineItem.PriceData.ProductData createProductData(OrderItem item) {
        return SessionCreateParams.LineItem.PriceData.ProductData.builder()
                .setName(item.getProduct().getName())
                .build();
    }
}