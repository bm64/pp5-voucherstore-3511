package pl.mbernacik.voucherstore.sales.payment;

import pl.mbernacik.payu.PayU;
import pl.mbernacik.voucherstore.sales.ordering.Reservation;

public class PayUPaymentGateway implements PaymentGateway {
    private final PayU payU;
    public PayUPaymentGateway(PayU payU) {
        this.payU = payU;
    }

    @Override
    public PaymentDetails register(Reservation reservation) {
        return null;
    }

    @Override
    public boolean isTrusted(PaymentUpdateStatusRequest paymentUpdateStatusRequest) {
        return payU.isTrusted(paymentUpdateStatusRequest.getBody(), paymentUpdateStatusRequest.getSignature());
    }
}