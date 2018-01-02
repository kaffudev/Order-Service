package pl.kamilfurdal.tdd.service.impl;

import org.hibernate.service.spi.ServiceException;
import org.springframework.dao.DataAccessException;
import pl.kamilfurdal.tdd.models.OrderEntity;
import pl.kamilfurdal.tdd.models.OrderSummary;
import pl.kamilfurdal.tdd.order.OrderDao;
import pl.kamilfurdal.tdd.service.OrderService;
import pl.kamilfurdal.tdd.transformers.OrderEntityToOrderSummaryTransformer;

import java.util.LinkedList;
import java.util.List;

public class OrderServiceImpl implements OrderService {

    private OrderDao orderDao = null;
    private OrderEntityToOrderSummaryTransformer transformer = null;

    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public void setTransformer(OrderEntityToOrderSummaryTransformer transformer) {
        this.transformer = transformer;
    }

    @Override
    public List<OrderSummary> getOrderSummary(long customerId) throws ServiceException {
        List<OrderSummary> resultList = new LinkedList<>();

        try {
            List<OrderEntity> orderEntityList = this.orderDao.findOrdersByCustomer(customerId);
            for (OrderEntity currentOrderEntity : orderEntityList) {
                OrderSummary orderSummary = this.transformer.transform(currentOrderEntity);
                resultList.add(orderSummary);
            }

        } catch (DataAccessException e){
            throw new ServiceException("Data access error occured", e);
        }

        return resultList;
    }
}
