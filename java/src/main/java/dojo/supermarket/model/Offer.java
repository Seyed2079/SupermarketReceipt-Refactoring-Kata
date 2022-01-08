package dojo.supermarket.model;

public class Offer {
    SpecialOfferType offerType;
    private final Product product;
    double argument;

    public Offer(SpecialOfferType offerType, Product product, double argument) {
        this.offerType = offerType;
        this.argument = argument;
        this.product = product;
    }

    Product getProduct() {
        return this.product;
    }

    public Discount getDiscount(double quantity, double unitPrice) {
        int quantityAsInt = (int) quantity;
        Discount discount = null;
        int x = 1;
        if (offerType == SpecialOfferType.ThreeForTwo) {
            x = 3;

        } else if (offerType == SpecialOfferType.TwoForAmount) {
            x = 2;
            if (quantityAsInt >= 2) {
                int intDivision = quantityAsInt / x;
                double pricePerUnit = argument * intDivision;
                double theTotal = (quantityAsInt % 2) * unitPrice;
                double total = pricePerUnit + theTotal;
                double discountN = unitPrice * quantity - total;
                discount = new Discount(product, "2 for " + argument, -discountN);
            }

        }
        if (offerType == SpecialOfferType.FiveForAmount) {
            x = 5;
        }
        int numberOfXs = quantityAsInt / x;
        if (offerType == SpecialOfferType.ThreeForTwo && quantityAsInt > 2) {
            double discountAmount = quantity * unitPrice - ((numberOfXs * 2 * unitPrice) + quantityAsInt % 3 * unitPrice);
            discount = new Discount(product, "3 for 2", -discountAmount);
        }
        if (offerType == SpecialOfferType.TenPercentDiscount) {
            discount = new Discount(product, argument + "% off", -quantity * unitPrice * argument / 100.0);
        }
        if (offerType == SpecialOfferType.FiveForAmount && quantityAsInt >= 5) {
            double discountTotal = unitPrice * quantity - (argument * numberOfXs + quantityAsInt % 5 * unitPrice);
            discount = new Discount(product, x + " for " + argument, -discountTotal);
        }
        return discount;
    }
}
