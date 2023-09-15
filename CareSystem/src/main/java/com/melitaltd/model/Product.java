package com.melitaltd.model;

import lombok.Data;

@Data
public class Product {
    private InternetPackage internetPackage;
    private TVPackage tvPackage;
    private Telephony telephony;
    private MobilePackage mobilePackage;
}
