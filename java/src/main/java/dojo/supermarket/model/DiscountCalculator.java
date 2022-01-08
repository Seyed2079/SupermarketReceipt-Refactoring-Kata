package dojo.supermarket.model;

public class DiscountCalculator {
    private Product product ;
    private double quantity;
    private Offer offer;
    private double unitPrice;
    int quantityAsInt;

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
        this.quantityAsInt = (int)quantity;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Discount getDiscountForTwoForAmount(double quantity, double unitPrice, int quantityAsInt, int x) {
        Discount discount = null;
        if (quantityAsInt >= 2) {
            double discountN = calculateDiscountN(quantity, unitPrice, quantityAsInt, x);
            discount = new Discount(product, "2 for " + offer.argument, -discountN);
        }
        return discount;
    }

    private double calculateDiscountN(double quantity, double unitPrice, int quantityAsInt, int x) {
        int intDivision = quantityAsInt / x;
        double pricePerUnit = getPricePerUnit(intDivision);
        double theTotal = (quantityAsInt % 2) * unitPrice;
        double total = pricePerUnit + theTotal;
        return unitPrice * quantity - total;
    }

    private double getPricePerUnit(int intDivision) {
        return offer.argument * intDivision;
    }

    public Discount getDiscountForThreeForTwo(double quantity, double unitPrice, int quantityAsInt, int numberOfXs) {
        Discount discount = null;
        if (offer.offerType == SpecialOfferType.ThreeForTwo && quantityAsInt > 2) {
            double discountAmount = quantity * unitPrice - ((numberOfXs * 2 * unitPrice) + quantityAsInt % 3 * unitPrice);
            discount = new Discount(product, "3 for 2", -discountAmount);
        }
        return discount;
    }

    public Discount getDiscountForFiveForAmount(double quantity, double unitPrice, int quantityAsInt, int x, int numberOfXs) {
        Discount discount = null;
        if (offer.offerType == SpecialOfferType.FiveForAmount && quantityAsInt >= 5) {
            double discountTotal = unitPrice * quantity - (offer.argument * numberOfXs + quantityAsInt % 5 * unitPrice);
            discount = new Discount(product, x + " for " + offer.argument, -discountTotal);
        }
        return discount;
    }

    public Discount getDiscountForTenPercentDiscount(double quantity, double unitPrice) {
        Discount discount = null;
        if (offer.offerType == SpecialOfferType.TenPercentDiscount) {
            discount = new Discount(product, offer.argument + "% off", -quantity * unitPrice * offer.argument / 100.0);
        }
        return discount;
    }

    public Discount execute() {
        int specialAmount = getSpecialAmount(offer);
        Discount discount = null;
        if (offer.offerType == SpecialOfferType.TwoForAmount) {
            discount = getDiscountForTwoForAmount(quantity, unitPrice, quantityAsInt,specialAmount);
        }

        int numberOfXs = quantityAsInt / specialAmount;
        if (offer.offerType == SpecialOfferType.ThreeForTwo && quantityAsInt > 2) {
            discount = getDiscountForThreeForTwo(quantity, unitPrice, quantityAsInt, numberOfXs);
        }
        if (offer.offerType == SpecialOfferType.TenPercentDiscount) {
            discount = getDiscountForTenPercentDiscount(quantity,unitPrice);
        }
        if (offer.offerType == SpecialOfferType.FiveForAmount && quantityAsInt >= 5) {
            discount = getDiscountForFiveForAmount(quantity,unitPrice, quantityAsInt, specialAmount, numberOfXs);
        }
        return discount;
    }

    private int getSpecialAmount(Offer offer) {
        int x = 1;
        if (offer.offerType == SpecialOfferType.ThreeForTwo) {
            x = 3;
        } else if (offer.offerType == SpecialOfferType.TwoForAmount) {
            x = 2;
        } if (offer.offerType == SpecialOfferType.FiveForAmount) {
            x = 5;
        }

        return x;
    }
}
