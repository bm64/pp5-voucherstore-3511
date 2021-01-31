package pl.mbernacik.voucherstore.sales;

import pl.mbernacik.voucherstore.productcatalog.Product;
import pl.mbernacik.voucherstore.productcatalog.ProductCatalogFacade;
import pl.mbernacik.voucherstore.sales.basket.Basket;
import pl.mbernacik.voucherstore.sales.basket.InMemoryBasketStorage;
import pl.mbernacik.voucherstore.sales.offer.Offer;
import pl.mbernacik.voucherstore.sales.offer.OfferMaker;
import pl.mbernacik.voucherstore.sales.ordering.ClientData;
import pl.mbernacik.voucherstore.sales.ordering.OfferChangedException;
import pl.mbernacik.voucherstore.sales.ordering.Reservation;
import pl.mbernacik.voucherstore.sales.payment.PaymentDetails;
import pl.mbernacik.voucherstore.sales.payment.PaymentGateway;
import pl.mbernacik.voucherstore.sales.payment.PaymentUpdateStatusRequest;
import pl.mbernacik.voucherstore.sales.payment.PaymentVerificationException;

public class SalesFacade {

    ProductCatalogFacade productCatalogFacade;
    InMemoryBasketStorage basketStorage;
    CurrentCustomerContext currentCustomerContext;
    Inventory inventory;
    OfferMaker offerMaker;
    PaymentGateway paymentGateway;

    public SalesFacade(ProductCatalogFacade productCatalogFacade, InMemoryBasketStorage basketStorage, CurrentCustomerContext currentCustomerContext, Inventory inventory, OfferMaker offerMaker, PaymentGateway paymentGateway) {
        this.productCatalogFacade = productCatalogFacade;
        this.basketStorage = basketStorage;
        this.currentCustomerContext = currentCustomerContext;
        this.inventory = inventory;
        this.offerMaker = offerMaker;
        this.paymentGateway = paymentGateway;
    }

    public void addProduct(String productId1) {
        Product product = productCatalogFacade.getById(productId1);
        Basket basket = basketStorage.loadForCustomer(getCurrentCustomerId())
                .orElse(Basket.empty());

        basket.add(product, inventory);

        basketStorage.addForCustomer(getCurrentCustomerId(), basket);
    }

    private String getCurrentCustomerId() {
        return currentCustomerContext.getCurrentCustomerId();
    }

    public Offer getCurrentOffer() {
        Basket basket = basketStorage.loadForCustomer(getCurrentCustomerId())
                .orElse(Basket.empty());
        return offerMaker.calculateOffer(basket.getBasketItems());
    }

    public PaymentDetails acceptOffer(Offer seenOffer, ClientData clientData) {
        Basket basket = basketStorage.loadForCustomer(getCurrentCustomerId())
                .orElse(Basket.empty());

        Offer currentOffer = offerMaker.calculateOffer(basket.getBasketItems());

        if (!seenOffer.isEqual(currentOffer)) {
            throw new OfferChangedException();
        }

        Reservation reservation = Reservation.of(currentOffer, clientData);

        PaymentDetails reservationPaymentDetails = paymentGateway.register(reservation);

        return  reservationPaymentDetails;
    }

    public void handlePaymentStatusChanged(PaymentUpdateStatusRequest paymentUpdateStatusRequest) {
        if (!paymentGateway.isTrusted(paymentUpdateStatusRequest)) {
            throw new PaymentVerificationException();
        }
    }
}