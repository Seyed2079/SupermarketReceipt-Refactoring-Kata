package dojo.supermarket.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCart {

    private final List<ProductQuantity> items = new ArrayList<>();
    Map<Product, Double> productQuantities = new HashMap<>();


    List<ProductQuantity> getItems() {
        return new ArrayList<>(items);
    }

    void addItem(Product product) {
        this.addItemQuantity(product, 1.0);
    }

    Map<Product, Double> productQuantities() {
        return productQuantities;
    }


    public void addItemQuantity(Product product, double quantity) {
        items.add(new ProductQuantity(product, quantity));
        if (productQuantities.containsKey(product)) {
            productQuantities.put(product, productQuantities.get(product) + quantity);
        } else {
            productQuantities.put(product, quantity);
        }
    }

    void handleOffers(Receipt receipt, Map<Product, Offer> offers, SupermarketCatalog catalog) {
        for (Product p: productQuantities().keySet()) {
            if (offers.containsKey(p)) {
                Offer offer = offers.get(p);
                Discount discount = handleDiscount(p, offer, catalog);
                if (discount != null)
                    receipt.addDiscount(discount);
            }

        }
    }

    public Discount handleDiscount(Product product, Offer offer,SupermarketCatalog catalog) {
        double quantity = productQuantities.get(product);
        double unitPrice = catalog.getUnitPrice(product);
        int quantityAsInt = (int) quantity;
        Discount discount = null;
        DiscountCalculator discountCalculator = new DiscountCalculator();
        discountCalculator.setOffer(offer);
        discountCalculator.setProduct(product);
        discountCalculator.setQuantity(quantity);
        discountCalculator.setUnitPrice(unitPrice);
        discount = discountCalculator.execute();
        return discount;
    }

}
