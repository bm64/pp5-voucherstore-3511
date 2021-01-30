package pl.mbernacik.voucherstore.sales;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.mbernacik.payu.JavaHttpPayUApiClient;
import pl.mbernacik.payu.PayU;
import pl.mbernacik.payu.PayUCredentials;
import pl.mbernacik.voucherstore.productcatalog.ProductCatalogFacade;
import pl.mbernacik.voucherstore.sales.basket.InMemoryBasketStorage;
import pl.mbernacik.voucherstore.sales.offer.OfferMaker;
import pl.mbernacik.voucherstore.sales.product.ProductCatalogProductDetailsProvider;
import pl.mbernacik.voucherstore.sales.product.ProductDetailsProvider;

@Configuration
public class SalesConfiguration {

    @Bean
    SalesFacade salesFacade(ProductCatalogFacade productCatalogFacade, OfferMaker offerMaker, PaymentGateway paymentGateway) {
        return new SalesFacade(
                productCatalogFacade,
                new InMemoryBasketStorage(),
                () -> "customer_1",
                (productId) -> true,
                offerMaker,
                paymentGateway
        );
    }

    @Bean
    PaymentGateway payUPaymentGateway() {
        return new PayUPaymentGateway(new PayU(
                PayUCredentials.sandbox(),
                new JavaHttpPayUApiClient()
        ));
    }

    @Bean
    OfferMaker offerMaker(ProductDetailsProvider productDetailsProvider) {
        return new OfferMaker(productDetailsProvider);
    }

    @Bean
    ProductDetailsProvider productDetailsProvider(ProductCatalogFacade productCatalogFacade) {
        return new ProductCatalogProductDetailsProvider(productCatalogFacade);
    }
}