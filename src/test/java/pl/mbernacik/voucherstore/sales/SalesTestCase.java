package pl.mbernacik.voucherstore.sales;

import pl.mbernacik.voucherstore.productcatalog.Product;
import pl.mbernacik.voucherstore.productcatalog.ProductCatalogConfiguration;
import pl.mbernacik.voucherstore.productcatalog.ProductCatalogFacade;
import pl.mbernacik.voucherstore.sales.basket.InMemoryBasketStorage;
import pl.mbernacik.voucherstore.sales.offer.OfferMaker;
import pl.mbernacik.voucherstore.sales.payment.DummyPaymentGateway;
import pl.mbernacik.voucherstore.sales.payment.PaymentGateway;
import pl.mbernacik.voucherstore.sales.product.ProductDetails;

import java.math.BigDecimal;
import java.util.UUID;

public class SalesTestCase {

    ProductCatalogFacade productCatalog;
    InMemoryBasketStorage basketStorage;
    CurrentCustomerContext currentCustomerContext;
    Inventory inventory;
    String customerId;
    OfferMaker offerMaker;
    PaymentGateway paymentGateway;

    protected CurrentCustomerContext thereIsCurrentCustomerContext() {
        return () -> customerId;
    }

    protected Inventory therIsInventory() {
        return productId -> true;
    }

    protected OfferMaker thereIsOfferMaker(ProductCatalogFacade productCatalogFacade) {
        return new OfferMaker(productId -> {
            Product product = productCatalogFacade.getById(productId);

            return new ProductDetails(productId, product.getDescription(), product.getPrice());
        });
    }

    protected InMemoryBasketStorage thereIsBasketStorage() {
        return new InMemoryBasketStorage();
    }

    protected PaymentGateway thereIsPaymentGateway() {
        return new DummyPaymentGateway();
    }

    protected ProductCatalogFacade thereIsProductCatalog() {
        return new ProductCatalogConfiguration().productCatalogFacade();
    }

    protected String thereIsCustomerWhoIsDoingSomeShoping() {
        customerId = UUID.randomUUID().toString();
        return new String(customerId);
    }

    protected String thereIsProductAvailable() {
        var id = productCatalog.createProduct();
        productCatalog.applyPrice(id, BigDecimal.valueOf(10));
        productCatalog.updateProductDetails(id, "desc", "http://image");
        return id;
    }

    protected SalesFacade thereIsSalesModule() {
        return new SalesFacade(
                productCatalog,
                basketStorage,
                currentCustomerContext,
                inventory,
                offerMaker,
                paymentGateway
        );
    }
}