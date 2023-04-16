package com.issoft.btm.service;

import com.issoft.btm.dto.TransportationRequestDTO;
import com.issoft.btm.dto.filter.TransportationRequestFilterDTO;
import com.issoft.btm.mapper.TransportationRequestMapper;
import com.issoft.btm.mapper.util.CycleAvoidingMappingContext;
import com.issoft.btm.model.TransportationRequestModel;
import com.issoft.btm.repository.TransportationRequestRepository;
import com.issoft.btm.service.interfaces.TransportationRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TransportationRequestServiceImpl implements TransportationRequestService {

    private final TransportationRequestRepository transportationRequestRepository;
    private final TransportationRequestMapper transportationRequestMapper;
    private final CycleAvoidingMappingContext context;


    @Override
    public TransportationRequestDTO createTransportationRequest(TransportationRequestDTO transportationRequestDTO) {
        return transportationRequestMapper
                .toTransportationRequestDTO(transportationRequestRepository.save(transportationRequestMapper.toTransportationRequestModel(transportationRequestDTO, context)), context);
    }

    @Override
    public TransportationRequestDTO updateTransportationRequest(TransportationRequestDTO transportationRequestDTO) {
            TransportationRequestModel requestModel =
                    transportationRequestMapper.toTransportationRequestModel(transportationRequestDTO, context);
            return transportationRequestMapper.toTransportationRequestDTO(transportationRequestRepository.save(requestModel), context);

    }

    @Override
    public TransportationRequestDTO getTransportationRequest(Long id) {
        return transportationRequestMapper.toTransportationRequestDTO(transportationRequestRepository
                .findById(id)
                .orElseThrow(IllegalArgumentException::new), context);
    }

    @Override
    public void deleteTransportationRequest(Long id) {
        transportationRequestRepository.deleteById(id);
    }

    @Override
    public List<TransportationRequestDTO> getAllTransportationRequests(Pageable page) {
        return transportationRequestRepository.findAll(page)
                .stream()
                .map(request -> transportationRequestMapper.toTransportationRequestDTO(request, context))
                .collect(Collectors.toList());
    }

    @Override
    public List<TransportationRequestDTO> getAllTransportationRequestsForOrder(Long orderId, Pageable page) {
        Page<TransportationRequestModel> requests =
                transportationRequestRepository.findTransportationRequestModelsByOrderId(orderId, page);
        return requests.stream()
                .map(request -> transportationRequestMapper.toTransportationRequestDTO(request, context))
                .collect(Collectors.toList());
    }

    @Override
    public List<TransportationRequestDTO> getFilteredRequests(TransportationRequestFilterDTO filter, Pageable page) {
        List<TransportationRequestModel> requests =
                transportationRequestRepository.findAllBySpecification(filter, page).toList();
        return requests.stream()
                .map(model -> transportationRequestMapper.toTransportationRequestDTO(model, context)).collect(Collectors.toList());
    }
}
