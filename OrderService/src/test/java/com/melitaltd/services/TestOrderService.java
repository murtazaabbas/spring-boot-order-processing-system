package com.melitaltd.services;

import com.melitaltd.amq.RabbitMQProducer;
import com.melitaltd.exception.ServiceException;
import com.melitaltd.model.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class TestOrderService {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private RabbitMQProducer rabbitMQProducer;

    @InjectMocks
    @Spy
    private OrderService orderService;

    @Test
    public void test_send_request_message_with_valid_request_object(){
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

        Mockito.when(modelMapper.map(any(), any())).thenReturn(new Order());
        Mockito.doNothing().when(rabbitMQProducer).sendRequestMessage(any());
        Mockito.when(orderService.sendRequestMessage(orderRequest)).thenCallRealMethod();
        OrderResponse orderResponse = orderService.sendRequestMessage(orderRequest);
        Assert.assertEquals(orderResponse.getOrderId(), "12345");
    }

    @Test(expected=ServiceException.class)
    public void test_send_request_message_with_null_request_object(){
        OrderRequest orderRequest = null;
        Mockito.when(orderService.sendRequestMessage(orderRequest)).thenCallRealMethod();
        OrderResponse orderResponse = orderService.sendRequestMessage(orderRequest);
    }
}