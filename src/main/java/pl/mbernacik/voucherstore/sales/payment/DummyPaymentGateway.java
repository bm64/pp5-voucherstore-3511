package pl.mbernacik.voucherstore.sales.payment;

import pl.mbernacik.voucherstore.sales.ordering.Reservation;

public class DummyPaymentGateway implements PaymentGateway {
    @Override
    public PaymentDetails register(Reservation reservation) {
        return null;
    }

    @Override
    public boolean isTrusted(PaymentUpdateStatusRequest paymentUpdateStatusRequest) {
        return true;
    }
}