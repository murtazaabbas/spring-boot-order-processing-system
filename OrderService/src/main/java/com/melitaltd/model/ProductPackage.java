package com.melitaltd.model;

public enum ProductPackage {
    INTERNET_250_MBPS("internet", "250mbs"),
    INTERNET_1_GBPS("internet", "1gbps"),
    MOBILE_PREPAID("mobile", "prepaid"),
    Mobile_POST_PAID("mobile", "postpaid"),
    TELEPHONY_FREE_ON_NET("telephony", "freeonnet"),
    TELEPHONY_UNLIMITE("telephony", "unlimited"),
    TV_CHANNEL_90("tv", "channel90"),
    TV_CHANNEL_140("tv", "channel140");

    private final String product;
    private final String productPackage;

    ProductPackage(String product, String productPackage) {
        this.product = product;
        this.productPackage = productPackage;
    }

    public String getProduct() {
        return product;
    }

    public String getProductPackage() {
        return productPackage;
    }
}
