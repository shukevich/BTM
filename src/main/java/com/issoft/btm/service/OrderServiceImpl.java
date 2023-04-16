package com.issoft.btm.service;

import com.issoft.btm.dto.OrderDTO;
import com.issoft.btm.dto.filter.OrderFilterDTO;
import com.issoft.btm.mapper.OrderMapper;
import com.issoft.btm.mapper.util.CycleAvoidingMappingContext;
import com.issoft.btm.model.OrderModel;
import com.issoft.btm.repository.OrderRepository;
import com.issoft.btm.service.interfaces.OrderService;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final CycleAvoidingMappingContext context;


    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        OrderModel order = orderRepository.save(orderMapper.toOrderModel(orderDTO, context));
        return orderMapper.toOrderDTO(order, context);
    }

    @Override
    public OrderDTO getOrder(Long orderId) {
        return orderMapper.toOrderDTO(orderRepository.findById(orderId).orElseThrow(IllegalArgumentException::new), context);
    }

    @Override
    public OrderDTO updateOrder(OrderDTO orderDTO, Long orderId) {
        orderDTO.setId(orderId);
        OrderModel orderModel = orderMapper.toOrderModel(orderDTO, context);
        return orderMapper.toOrderDTO(orderRepository.save(orderModel), context);
    }

    @Override
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    @Override
    public List<OrderDTO> getAllOrders(Pageable page) {
        return orderRepository.findAll(page)
                .stream()
                .map(orderModel -> orderMapper.toOrderDTO(orderModel, context))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getFilteredOrders(OrderFilterDTO filter, Pageable page) {
        List<OrderModel> orders = orderRepository.findAllBySpecification(filter, page).toList();
        return orders.stream().map(model -> orderMapper.toOrderDTO(model, context)).collect(Collectors.toList());
    }
}
