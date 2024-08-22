package org.example.managers;

// 'SpecialOfferManager' class allows the creation, management, and application of special offers.

import org.example.models.Order;
import org.example.models.SpecialOffer;

import java.util.HashMap;
import java.util.Map;

public class SpecialOfferManager {
    private Map<String, SpecialOffer> offers;

    public SpecialOfferManager() {
        this.offers = new HashMap<>();

        // Add predefined offers to the system
        initializeOffers();
    }

    // Method to create a new special offer
    public void addOffer(SpecialOffer offer) {
        offers.put(offer.getOfferId(), offer);
        System.out.println("Special Offer added: " + offer);
    }

    // Method to remove a special offer
    public void removeOffer(String offerId) {
        if (offers.containsKey(offerId)) {
            SpecialOffer removedOffer = offers.remove(offerId);
            System.out.println("Special Offer removed: " + removedOffer);
        } else {
            System.out.println("Offer ID not found.");
        }
    }

    // Method to apply an offer to an order
    public void applyOfferToOrder(String offerId, Order order) {
        SpecialOffer offer = offers.get(offerId);
        if (offer != null) {
            double discount = offer.getDiscountAmount();
            double originalTotal = order.getTotalPrice();
            double newTotal = originalTotal - discount;
            order.setTotalPrice(newTotal > 0 ? newTotal : 0);  // Ensure total doesn't go below 0
            System.out.println("Offer applied: " + offer);
            System.out.println("Original Total: $" + originalTotal + ", New Total: $" + order.getTotalPrice());
        } else {
            System.out.println("Offer ID not found.");
        }
    }

    // Method to list all offers
    public void listOffers() {
        System.out.println("Current Special Offers:");
        for (SpecialOffer offer : offers.values()) {
            System.out.println(offer);
        }
    }

    // Initialize predefined special offers
    private void initializeOffers() {
        SpecialOffer discount1 = new SpecialOffer("DIS10", "10% Off on All Orders", 10.00);
        SpecialOffer discount2 = new SpecialOffer("MEAL50", "Flat $50 Off on Meal of the Day", 50.00);

        addOffer(discount1);
        addOffer(discount2);
    }
}

