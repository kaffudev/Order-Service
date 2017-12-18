package pl.kamilfurdal.tdd.transformers;

import pl.kamilfurdal.tdd.models.OrderEntity;
import pl.kamilfurdal.tdd.models.OrderItemEntity;
import pl.kamilfurdal.tdd.models.OrderSummary;

import java.math.BigDecimal;

public class OrderEntityToOrderSummaryTransformer {

    public OrderSummary transform(OrderEntity orderEntity){

        if (orderEntity == null){
            throw new IllegalArgumentException("Order can not be null");
        }

        OrderSummary orderSummaryResult = new OrderSummary();

        orderSummaryResult.setOrderNumber(orderEntity.getOrderNumber());

        int itemCount = 0;
        BigDecimal totalAmount = new BigDecimal("0.00");

        for (OrderItemEntity currentItem : orderEntity.getOrderItemList()) {
            itemCount += currentItem.getQuantity();
            BigDecimal itemTotal = currentItem.getSellingPrice().multiply(BigDecimal.valueOf(currentItem.getQuantity()));
            totalAmount = totalAmount.add(itemTotal);
        }


        orderSummaryResult.setItemCount(itemCount);
        orderSummaryResult.setTotalAmount(totalAmount);

        return orderSummaryResult;
    }

}
