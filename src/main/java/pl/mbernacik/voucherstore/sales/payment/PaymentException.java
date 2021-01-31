package pl.mbernacik.voucherstore.sales.payment;

import pl.mbernacik.payu.exceptions.PayUException;

public class PaymentException extends IllegalStateException {
    public PaymentException(PayUException e) {
        super(e);
    }
}