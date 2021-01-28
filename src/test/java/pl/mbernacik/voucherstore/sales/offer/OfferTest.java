package pl.mbernacik.voucherstore.sales.offer;


import org.junit.Test;
import pl.mbernacik.voucherstore.sales.basket.BasketLine;
import pl.mbernacik.voucherstore.sales.offer.Offer;
import pl.mbernacik.voucherstore.sales.offer.OfferMaker;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.assertj.core.api.Assertions.*;

public class OfferTest {
    @Test
    public void itCalculateOfferBasedOnBasketItems() {
        List<BasketLine> basketItems = Arrays.asList(
                new BasketLine("prod1", 2),
                new BasketLine("prod2", 1)
        );

        OfferMaker offerMaker = thereIsOfferMaker();
        Offer offer = offerMaker.calculateOffer(basketItems);

        assertThat(offer.getTotal())
                .isEqualTo(BigDecimal.valueOf(30));
    }

    @Test
    public void itCalculateOfferBasedOnSingleBasketItem() {
        List<BasketLine> basketItems = Collections.singletonList(
                new BasketLine("prod2", 1)
        );

        OfferMaker offerMaker = thereIsOfferMaker();
        Offer offer = offerMaker.calculateOffer(basketItems);

        assertThat(offer.getTotal())
                .isEqualTo(BigDecimal.valueOf(10));
    }

    private OfferMaker thereIsOfferMaker() {
        return new OfferMaker();
    }
}