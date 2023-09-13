package com.melitaltd.models;

import lombok.Data;

import java.util.List;

@Data
public class Product {
    private InternetPackage internetPackage;
    private TVPackage tvPackage;
    private Telephony telephony;
    private MobilePackage mobilePackage;
}
