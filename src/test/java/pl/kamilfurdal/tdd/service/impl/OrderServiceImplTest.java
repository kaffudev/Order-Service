package pl.kamilfurdal.tdd.service.impl;

import org.hibernate.internal.CriteriaImpl;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import pl.kamilfurdal.tdd.models.OrderEntity;
import pl.kamilfurdal.tdd.models.OrderSummary;
import pl.kamilfurdal.tdd.order.OrderDao;
import pl.kamilfurdal.tdd.transformers.OrderEntityToOrderSummaryTransformer;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class OrderServiceImplTest {

    private final static long CUSTOMER_ID = 1l;

    @Test
    public void testGetOrderSummarySuccess(){

        //Setup
        OrderServiceImpl target = new OrderServiceImpl();

        //mocking
        OrderDao mockOrderDao = Mockito.mock(OrderDao.class);
        target.setOrderDao(mockOrderDao);

        OrderEntityToOrderSummaryTransformer mockTransformer = Mockito.mock(OrderEntityToOrderSummaryTransformer.class);
        target.setTransformer(mockTransformer);
        //mocking

        OrderEntity orderEntityFixture = new OrderEntity();
        List<OrderEntity> orderEntityListFixture = new LinkedList<>();
        orderEntityListFixture.add(orderEntityFixture);

        //stubbing
        OrderSummary orderSummaryFixture = new OrderSummary();
        Mockito.when(mockOrderDao.findOrdersByCustomer(CUSTOMER_ID)).thenReturn(orderEntityListFixture);
        Mockito.when(mockTransformer.transform(orderEntityFixture)).thenReturn(orderSummaryFixture);

        //stubbing

        //Execution
        List<OrderSummary> result = target.getOrderSummary(CUSTOMER_ID);

        //Verification
        Mockito.verify(mockOrderDao).findOrdersByCustomer(CUSTOMER_ID);
        Mockito.verify(mockTransformer).transform(orderEntityFixture);

        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.size());
        Assert.assertSame(orderSummaryFixture, result.get(0));

    }

}