package dojo.supermarket.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Teller {

    private final SupermarketCatalog catalog;
    private Map<Product, Offer> offers = new HashMap<>();

    public Teller(SupermarketCatalog catalog) {
        this.catalog = catalog;
    }

    public void addSpecialOffer(SpecialOfferType offerType, Product product, double argument) {
        this.offers.put(product, new Offer(offerType, product, argument));
    }

    public Receipt checksOutArticlesFrom(ShoppingCart theCart) {
        Receipt receipt = new Receipt();
        List<ProductQuantity> productQuantities = theCart.getItems();
        addProductToReceipt(receipt, productQuantities);
        theCart.handleOffers(receipt, this.offers, this.catalog);

        return receipt;
    }

    private void addProductToReceipt(Receipt receipt, List<ProductQuantity> productQuantities) {
        for (ProductQuantity pq: productQuantities) {
            double price = getPrice(pq.getQuantity(), this.catalog.getUnitPrice(pq.getProduct()));
            receipt.addProduct(pq.getProduct(), pq.getQuantity(), this.catalog.getUnitPrice(pq.getProduct()), price);
        }
    }

    private double getPrice(double quantity, double unitPrice) {
        return quantity * unitPrice;
    }

}
