package pl.kamilfurdal.tdd.service;

import org.hibernate.service.spi.ServiceException;
import pl.kamilfurdal.tdd.models.OrderSummary;

import java.util.List;

public interface OrderService {

    List<OrderSummary> getOrderSummary(long customerId) throws ServiceException;
}
