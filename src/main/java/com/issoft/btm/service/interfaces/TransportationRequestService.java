package com.issoft.btm.service.interfaces;

import com.issoft.btm.dto.TransportationRequestDTO;
import com.issoft.btm.dto.filter.TransportationRequestFilterDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TransportationRequestService {

    TransportationRequestDTO createTransportationRequest(TransportationRequestDTO transportationRequestDTO);

    TransportationRequestDTO updateTransportationRequest(TransportationRequestDTO transportationRequestDTO);

    TransportationRequestDTO getTransportationRequest(Long id);

    void deleteTransportationRequest(Long id);

    List<TransportationRequestDTO> getAllTransportationRequests(Pageable page);

    List<TransportationRequestDTO> getAllTransportationRequestsForOrder(Long orderId, Pageable pageable);

    List<TransportationRequestDTO> getFilteredRequests(TransportationRequestFilterDTO filter, Pageable page);

}
