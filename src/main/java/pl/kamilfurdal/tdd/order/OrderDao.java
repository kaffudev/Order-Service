package pl.kamilfurdal.tdd.order;

import org.springframework.dao.DataAccessException;
import pl.kamilfurdal.tdd.models.OrderEntity;

import java.util.List;

public interface OrderDao {
    OrderEntity findById(long id)throws DataAccessException;
    int insert(OrderEntity order) throws DataAccessException;

    List<OrderEntity> findOrdersByCustomer(long customerId) throws DataAccessException;
}
