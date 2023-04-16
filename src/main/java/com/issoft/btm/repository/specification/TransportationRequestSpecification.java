package com.issoft.btm.repository.specification;

import com.issoft.btm.dto.filter.TransportationRequestFilterDTO;
import com.issoft.btm.model.AddressModel_;
import com.issoft.btm.model.TransportationRequestModel;
import com.issoft.btm.model.TransportationRequestModel_;
import com.issoft.btm.model.dictionaries.TransportationRequestStatusModel_;
import com.issoft.btm.model.dictionaries.enums.TransportationRequestStatusEnum;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

public class TransportationRequestSpecification {

    public static Specification<TransportationRequestModel> findAllByFilter(TransportationRequestFilterDTO filterDTO) {
        return ((root, query, builder)
                -> findRequestsByFilter(root, builder,
                filterDTO.getTransportationStatus(),
                filterDTO.getDeliveryCountry(),
                filterDTO.getPickupCountry(),
                filterDTO.getTransportPartner()
        ));
    }

    private static Predicate findAllByRequestStatus(Path<TransportationRequestModel> root,
                                                    CriteriaBuilder builder,
                                                    TransportationRequestStatusEnum requestStatusEnum) {
        if (requestStatusEnum == null) return builder.conjunction();
        return builder.equal(root.get(TransportationRequestModel_.status).get(TransportationRequestStatusModel_.ID), requestStatusEnum.getId());
    }

    private static Predicate findAllByTransportPartner(Path<TransportationRequestModel> root,
                                                       CriteriaBuilder builder,
                                                       String transportPartner) {
        if (transportPartner == null) return builder.conjunction();
        return builder.equal(root.get(TransportationRequestModel_.transportPartner), transportPartner);
    }

    private static Predicate findAllByPickupCountry(Path<TransportationRequestModel> root,
                                                    CriteriaBuilder builder,
                                                    String pickupCountry) {
        if (pickupCountry == null) return builder.conjunction();
        return builder.like(root.get(TransportationRequestModel_.pickupAddress).get(AddressModel_.country), pickupCountry);
    }

    private static Predicate findAllByDeliveryCountry(Path<TransportationRequestModel> root,
                                                      CriteriaBuilder builder,
                                                      String deliveryCountry) {
        if (deliveryCountry == null) return builder.conjunction();
        return builder.like(root.get(TransportationRequestModel_.deliverAddress).get(AddressModel_.country), deliveryCountry);
    }


    private static Predicate findRequestsByFilter(Path<TransportationRequestModel> root,
                                                  CriteriaBuilder builder,
                                                  TransportationRequestStatusEnum requestStatusEnum,
                                                  String deliveryCountry,
                                                  String pickupCountry,
                                                  String transportPartner) {
        return builder.and(
                findAllByRequestStatus(root, builder, requestStatusEnum),
                findAllByDeliveryCountry(root, builder, deliveryCountry),
                findAllByPickupCountry(root, builder, pickupCountry),
                findAllByTransportPartner(root, builder, transportPartner)
        );
    }


}
