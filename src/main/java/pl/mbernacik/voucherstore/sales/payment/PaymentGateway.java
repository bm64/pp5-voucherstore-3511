package pl.mbernacik.voucherstore.sales.payment;


import pl.mbernacik.voucherstore.sales.ordering.Reservation;

public interface PaymentGateway {
    PaymentDetails register(Reservation reservation);

    boolean isTrusted(PaymentUpdateStatusRequest paymentUpdateStatusRequest);
}