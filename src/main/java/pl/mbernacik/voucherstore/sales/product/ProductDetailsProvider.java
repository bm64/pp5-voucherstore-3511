package pl.mbernacik.voucherstore.sales.product;

import pl.mbernacik.voucherstore.sales.product.ProductDetails;

public interface ProductDetailsProvider {
    ProductDetails getByProductId(String productId);
}