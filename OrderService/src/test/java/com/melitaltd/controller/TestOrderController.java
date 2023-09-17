package com.melitaltd.controller;

import com.melitaltd.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.internal.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
public class TestOrderController {
    @Autowired
    private TestRestTemplate restTemplate;

    public HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-API-KEY", "a1753388-df37-40ca-beb5-48c47e7e065d");
        headers.add("X-API-SECRET", "YmxhYWJsYWJsYWE=");
        return headers;
    }

    @Test
    public void test_order_api_with_valid_request_return_order_id() throws Exception {
        PersonalInformation personalInformation = new PersonalInformation();
        personalInformation.setFirstName("Murtaza");
        personalInformation.setLastName("Abbas");
        personalInformation.setNationalID("123456789");

        Product product = new Product();
        product.setProductPackage(ProductPackage.INTERNET_1_GBPS);
        Product product1 = new Product();
        product1.setProductPackage(ProductPackage.Mobile_POST_PAID);
        List<Product> products = List.of(product);

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setPersonalInformation(personalInformation);
        orderRequest.setProducts(products);
        orderRequest.setInstallationAddress("wieselgrensgatan 9A");

        LocalDateTime installationDateTime = LocalDateTime.now();
        orderRequest.setInstallationDateTime(installationDateTime.plusHours(2));

        HttpEntity<OrderRequest> request = new HttpEntity<>(orderRequest, getHeaders());

        ResponseEntity<Order> orderResponseResponseEntity = restTemplate.exchange("/api/v1/orderservice/order", HttpMethod.POST, request, Order.class);


        Assertions.assertEquals(orderResponseResponseEntity.getStatusCode(), HttpStatus.OK);
        Assert.notNull(orderResponseResponseEntity.getBody().getTraceId());
    }

    @Test
    public void test_order_api_with_request_missing_personal_information_response_bad_request() {
        PersonalInformation personalInformation = new PersonalInformation();
        personalInformation.setFirstName("Murtaza");
        personalInformation.setLastName("Abbas");
        personalInformation.setNationalID("123456789");

        Product product = new Product();
        product.setProductPackage(ProductPackage.INTERNET_1_GBPS);
        Product product1 = new Product();
        product1.setProductPackage(ProductPackage.Mobile_POST_PAID);
        List<Product> products = List.of(product, product1);

        OrderRequest orderRequest = new OrderRequest();
//        dont add personalInformation, now its invalid object
//        orderRequest.setPersonalInformation(personalInformation);
        orderRequest.setProducts(products);
        orderRequest.setInstallationAddress("wieselgrensgatan 9A");

        LocalDateTime installationDateTime = LocalDateTime.now();
        orderRequest.setInstallationDateTime(installationDateTime.plusHours(2));

        HttpEntity<OrderRequest> request = new HttpEntity<>(orderRequest, getHeaders());
        ResponseEntity<String> orderResponseResponseEntity = restTemplate.exchange("/api/v1/orderservice/order", HttpMethod.POST, request, String.class);
        Assertions.assertEquals(orderResponseResponseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
        Assert.notNull(orderResponseResponseEntity);
    }

//    @Test
//    public void test_order_api_without_api_secret_headers_return_bad_request() {
//        OrderRequest orderRequest = new OrderRequest();
//        HttpEntity<OrderRequest> request = new HttpEntity<>(orderRequest);
//        ResponseEntity<String> orderResponseResponseEntity = restTemplate.exchange("/api/v1/orderservice/order", HttpMethod.POST, request, String.class);
//        Assertions.assertEquals(orderResponseResponseEntity.getStatusCode(), HttpStatus.UNAUTHORIZED);
//        Assert.isTrue(orderResponseResponseEntity.getBody().contains("UNAUTHORIZED"));
//    }
}