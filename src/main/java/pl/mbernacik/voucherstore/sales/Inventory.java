package pl.mbernacik.voucherstore.sales;

public interface Inventory {
    boolean isAvailable(String productId);
}
