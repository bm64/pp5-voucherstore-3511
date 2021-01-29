package pl.mbernacik.voucherstore.sales.offer;

public interface ProductDetailsProvider {
    ProductDetails getByProductId(String productId);
}