package org.example.models;

/*  the SpecialOffer class, will manage special offers like discounts or meal deals.
 * This class will have properties such as Offer ID, Description, and Discount Amount or Deal Details.
 */

public class SpecialOffer {
    private String offerId;// 'offerId': Unique identifier for the special offer.
    private String description; // 'description': Text description of the offer or deal.
    private double discountAmount;  // This can be a percentage or a fixed amount
    // 'discountAmount': The amount or percentage of the discount offered.

    // Constructor
    public SpecialOffer(String offerId, String description, double discountAmount) {
        this.offerId = offerId;
        this.description = description;
        this.discountAmount = discountAmount;
    }

    // Getters and Setters
    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    @Override
    public String toString() {
        return "SpecialOffer{" +
                "offerId='" + offerId + '\'' +
                ", description='" + description + '\'' +
                ", discountAmount=" + discountAmount +
                '}';
    }
}

