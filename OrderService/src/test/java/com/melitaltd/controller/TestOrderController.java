package com.melitaltd.controller;

import com.melitaltd.amq.RabbitMQProducer;
import com.melitaltd.config.SecurityProperties;
import com.melitaltd.controllers.OrderController;
import com.melitaltd.services.OrderService;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.http.*;
import com.melitaltd.model.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@Profile("dev")
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestOrderController {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private SecurityProperties properties;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private RabbitMQProducer rabbitMQProducer;

    @InjectMocks
    @Spy
    private OrderService orderService;

    @InjectMocks
    @Spy
    private OrderController orderController;

    public HttpHeaders getHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-API-KEY", properties.getKey());
        headers.add("X-API-SECRET", properties.getSecret());
        return headers;
    }

    @Before
    void setup() {
        MockitoAnnotations.initMocks(this);
        rabbitMQProducer = Mockito.mock(RabbitMQProducer.class);
        orderService = Mockito.mock(OrderService.class);
        orderController = Mockito.mock(OrderController.class);
        modelMapper = Mockito.mock(ModelMapper.class);
    }

    @Test
    public void test_order_api_with_valid_request_return_order_id() {
        PersonalInformation personalInformation = new PersonalInformation();
        personalInformation.setFirstName("Murtaza");
        personalInformation.setLastName("Abbas");
        personalInformation.setNationalID("123456789");

        Product product = new Product();
        product.setInternetPackage(InternetPackage.INTERNET_1_GBPS);
        product.setMobilePackage(MobilePackage.POST_PAID);
        List<Product> products = List.of(product);

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setPersonalInformation(personalInformation);
        orderRequest.setProducts(products);
        orderRequest.setInstallationAddress("wieselgrensgatan 9A");

        LocalDateTime installationDateTime = LocalDateTime.now();
        orderRequest.setInstallationDateTime(installationDateTime.plusHours(2));

        HttpEntity<OrderRequest> request = new HttpEntity<>(orderRequest, getHeaders());

        Mockito.when(modelMapper.map(any(), any())).thenReturn(new Order());
        Mockito.doNothing().when(rabbitMQProducer).sendRequestMessage(any());
        ResponseEntity<OrderResponse> orderResponseResponseEntity = restTemplate.exchange("/api/v1/orderservice/order", HttpMethod.POST, request, OrderResponse.class);


        Assertions.assertEquals(orderResponseResponseEntity.getStatusCode(), HttpStatus.OK);
        Assert.notNull(orderResponseResponseEntity.getBody().getOrderId());
    }

    @Test
    public void test_order_api_with_request_missing_personal_information_response_bad_request() {
        PersonalInformation personalInformation = new PersonalInformation();
        personalInformation.setFirstName("Murtaza");
        personalInformation.setLastName("Abbas");
        personalInformation.setNationalID("123456789");

        Product product = new Product();
        product.setInternetPackage(InternetPackage.INTERNET_1_GBPS);
        product.setMobilePackage(MobilePackage.POST_PAID);
        List<Product> products = List.of(product);

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
}
