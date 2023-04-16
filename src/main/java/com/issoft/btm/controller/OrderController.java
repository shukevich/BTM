package com.issoft.btm.controller;

import com.issoft.btm.dto.OrderDTO;
import com.issoft.btm.dto.filter.OrderFilterDTO;
import com.issoft.btm.service.interfaces.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/createOrder")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody @Valid OrderDTO orderDTO) {
        return ResponseEntity.ok(orderService.createOrder(orderDTO));
    }

    @PutMapping("/updateOrder/{orderId}")
    public ResponseEntity<OrderDTO> updateOrder(@RequestBody @Valid OrderDTO orderDTO, @PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.updateOrder(orderDTO, orderId));
    }

    @GetMapping("/getOrder/{orderId}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable @NotNull Long orderId) {
        return ResponseEntity.ok(orderService.getOrder(orderId));
    }

    @PostMapping("/filterOrder")
    public ResponseEntity<List<OrderDTO>> filterOrders(@RequestBody OrderFilterDTO filterDTO, @NotNull Pageable pageable) {
        return ResponseEntity.ok(orderService.getFilteredOrders(filterDTO, pageable));
    }

    @DeleteMapping("/deleteOrder/{orderId}")
    public void deleteOrder(@PathVariable @NotNull Long orderId) {
        orderService.deleteOrder(orderId);
    }

}
