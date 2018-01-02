package pl.kamilfurdal.tdd.transformers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import pl.kamilfurdal.tdd.models.OrderEntity;
import pl.kamilfurdal.tdd.models.OrderItemEntity;
import pl.kamilfurdal.tdd.models.OrderSummary;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.UUID;

public class OrderEntityToOrderSummaryTransformerTest {

    private OrderEntityToOrderSummaryTransformer target = null;

    @Before
    public void setup(){
        target = new OrderEntityToOrderSummaryTransformer();
    }

    @Test
    public void testTransformSuccess(){

        //given
        String orderNumberFixture = UUID.randomUUID().toString();

        OrderEntity orderEntityFixture = new OrderEntity();
        orderEntityFixture.setOrderNumber(orderNumberFixture);
        orderEntityFixture.setOrderItemList(new LinkedList<OrderItemEntity>());


        //when

        OrderItemEntity itemFixture1 = new OrderItemEntity();
        itemFixture1.setQuantity(2);
        itemFixture1.setSellingPrice(new BigDecimal("6.00"));
        orderEntityFixture.getOrderItemList().add(itemFixture1);

        OrderItemEntity itemFixture2 = new OrderItemEntity();
        itemFixture2.setQuantity(1);
        itemFixture2.setSellingPrice(new BigDecimal("2.00"));
        orderEntityFixture.getOrderItemList().add(itemFixture2);

        OrderSummary result = target.transform(orderEntityFixture);

        //then

        Assert.assertNotNull(result);
        Assert.assertEquals(orderNumberFixture, result.getOrderNumber());
        Assert.assertEquals(3, result.getItemCount());
        Assert.assertEquals(new BigDecimal("14.00"), result.getTotalAmount());

    }

    @Test(expected = IllegalArgumentException.class)
    public void testTransformInputIsNull(){
        target.transform(null);

    }

    @Test
    public void testTransformNoItemsInOrder(){
        String orderNumberFixture = UUID.randomUUID().toString();

        OrderEntity orderEntityFixture = new OrderEntity();
        orderEntityFixture.setOrderNumber(orderNumberFixture);
        orderEntityFixture.setOrderItemList(new LinkedList<OrderItemEntity>());

        OrderSummary result = target.transform(orderEntityFixture);

        Assert.assertNotNull(result);
        Assert.assertEquals(orderNumberFixture, result.getOrderNumber());
        Assert.assertEquals(0, result.getItemCount());
        Assert.assertEquals(new BigDecimal("0.00"), result.getTotalAmount());

    }
}