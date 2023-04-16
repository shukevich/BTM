package com.issoft.btm.service.interfaces;

import com.issoft.btm.dto.OrderDTO;
import com.issoft.btm.dto.filter.OrderFilterDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {

    OrderDTO createOrder(OrderDTO orderDTO);

    OrderDTO getOrder(Long orderId);

    OrderDTO updateOrder(OrderDTO orderDTO, Long orderId);

    void deleteOrder(Long orderId);

    List<OrderDTO> getAllOrders(Pageable page);

    List<OrderDTO> getFilteredOrders(OrderFilterDTO filter, Pageable page);
}
