package dojo.supermarket.model;
import dojo.supermarket.model.DiscountCalculator;

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


    public Discount handleDiscount(SupermarketCatalog catalog, double quantity) {
        double unitPrice = catalog.getUnitPrice(this.product);
        int quantityAsInt = (int) quantity;
        Discount discount = null;
        DiscountCalculator discountCalculator = new DiscountCalculator();
        discountCalculator.setOffer(this);
        discountCalculator.setProduct(this.product);
        discountCalculator.setQuantity(quantity);
        discountCalculator.setUnitPrice(unitPrice);
        discount = discountCalculator.execute();
        return discount;
    }
}
