package com.melitaltd.services;

import com.melitaltd.amq.OrderProducer;
import com.melitaltd.exception.ServiceException;
import com.melitaltd.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
public class TestOrderService {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private OrderProducer orderProducer;

    @InjectMocks
    @Spy
    private OrderService orderService;

    @Test
    public void test_send_request_message_with_valid_request_object() {
        PersonalInformation personalInformation = new PersonalInformation();
        personalInformation.setFirstName("Murtaza");
        personalInformation.setLastName("Abbas");
        personalInformation.setNationalID("123456789");

        Product product = new Product();
        product.setProductPackage(ProductPackage.INTERNET_1_GBPS);
        product.setProductPackage(ProductPackage.Mobile_POST_PAID);
        List<Product> products = List.of(product);

        Order orderRequest = new Order();
        orderRequest.setPersonalInformation(personalInformation);
        orderRequest.setProducts(products);
        orderRequest.setInstallationAddress("wieselgrensgatan 9A");

        LocalDateTime installationDateTime = LocalDateTime.now();
        orderRequest.setInstallationDateTime(installationDateTime.plusHours(2));

        Mockito.doNothing().when(orderProducer).sendRequestMessage(any());
        boolean msgSent = orderService.sendRequestMessage(orderRequest, UUID.randomUUID().toString());
        assertTrue(msgSent);
    }

    @Test
    public void test_send_request_message_with_null_request_object() {
        assertThrows(ServiceException.class,
                () -> {
                    Order orderRequest = null;
                    orderService.sendRequestMessage(orderRequest, UUID.randomUUID().toString());
                });

    }
}
