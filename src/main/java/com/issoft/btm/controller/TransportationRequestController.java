package com.issoft.btm.controller;

import com.issoft.btm.dto.TransportationRequestDTO;
import com.issoft.btm.dto.filter.TransportationRequestFilterDTO;
import com.issoft.btm.service.interfaces.TransportationRequestService;
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
@RequestMapping("/transportation")
@RequiredArgsConstructor
public class TransportationRequestController {

    private final TransportationRequestService requestService;

    @PostMapping("/createRequest")
    public ResponseEntity<TransportationRequestDTO> createTransportationRequest(@RequestBody TransportationRequestDTO requestDTO) {
        return ResponseEntity.ok(requestService.createTransportationRequest(requestDTO));
    }

    @GetMapping("/getRequest")
    public ResponseEntity<TransportationRequestDTO> getTransportationRequest(@RequestParam @NotNull Long requestId) {
        return ResponseEntity.ok(requestService.getTransportationRequest(requestId));
    }

    @PutMapping("/updateRequest")
    public ResponseEntity<TransportationRequestDTO> updateTransportationRequest(@RequestBody @Valid TransportationRequestDTO requestDTO) {
        return ResponseEntity.ok(requestService.updateTransportationRequest(requestDTO));
    }

    @GetMapping("/getRequestsByOrderId")
    public ResponseEntity<List<TransportationRequestDTO>> getTransportationRequestsForOrder(@RequestParam Long orderId,
                                                                                            @NotNull Pageable pageable) {
        return ResponseEntity.ok(requestService.getAllTransportationRequestsForOrder(orderId, pageable));
    }

    @PostMapping("/filterRequests")
    public ResponseEntity<List<TransportationRequestDTO>> getFilteredRequests(@RequestBody TransportationRequestFilterDTO filterDTO,
                                                                              @NotNull Pageable pageable) {
        return ResponseEntity.ok(requestService.getFilteredRequests(filterDTO, pageable));
    }

    @DeleteMapping("/deleteRequest")
    public void deleteTransportationRequest(@NotNull Long id) {
        requestService.deleteTransportationRequest(id);
    }

}

