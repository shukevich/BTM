package com.issoft.btm.repository.specification;

import com.issoft.btm.dto.filter.OrderFilterDTO;
import com.issoft.btm.model.DonorModel_;
import com.issoft.btm.model.OrderModel;
import com.issoft.btm.model.OrderModel_;
import com.issoft.btm.model.PatientModel_;
import com.issoft.btm.model.dictionaries.OrderStatusModel_;
import com.issoft.btm.model.dictionaries.OrderTypeModel_;
import com.issoft.btm.model.dictionaries.enums.OrderStatusEnum;
import com.issoft.btm.model.dictionaries.enums.OrderTypeEnum;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.time.LocalDate;


public class OrderSpecification {

    public static Specification<OrderModel> findAllByFilter(OrderFilterDTO filterDTO) {
        return ((root, query, builder)
                -> findOrdersByFilter(root, builder,
                filterDTO.getOrderStatus(),
                filterDTO.getOrderType(),
                filterDTO.getPatientID(),
                filterDTO.getDonorID(),
                filterDTO.getEntryDateBefore(),
                filterDTO.getEntryDateAfter()
        ));
    }

    private static Predicate findAllByOrderType(Path<OrderModel> root,
                                                CriteriaBuilder builder,
                                                OrderTypeEnum orderType) {
        if (orderType == null) return builder.conjunction();
        return builder.equal(root.get(OrderModel_.orderType).get(OrderTypeModel_.ID), orderType.getId());
    }

    private static Predicate findAllByStatus(Path<OrderModel> root,
                                             CriteriaBuilder builder,
                                             OrderStatusEnum orderStatus) {
        if (orderStatus == null) return builder.conjunction();
        return builder.equal(root.get(OrderModel_.orderStatus).get(OrderStatusModel_.ID), orderStatus.getId());
    }

    private static Predicate findAllByDonorId(Path<OrderModel> root,
                                              CriteriaBuilder builder,
                                              Long donorId) {
        if (donorId == null) return builder.conjunction();
        return builder.equal(root.get(OrderModel_.donor).get(DonorModel_.ID), donorId);
    }

    private static Predicate findAllByPatientId(Path<OrderModel> root,
                                                CriteriaBuilder builder,
                                                Long patientId) {
        if (patientId == null) return builder.conjunction();
        return builder.equal(root.get(OrderModel_.patient).get(PatientModel_.ID), patientId);
    }

    private static Predicate findAllByEntryDatesInterval(Path<OrderModel> root,
                                                         CriteriaBuilder builder,
                                                         LocalDate entryDateBefore,
                                                         LocalDate entryDateAfter) {
        if (entryDateBefore == null || entryDateAfter == null) return builder.conjunction();
        return builder.and(builder.greaterThan(root.get(OrderModel_.entryDate), entryDateAfter),
                builder.lessThan(root.get(OrderModel_.entryDate), entryDateBefore));
    }

    private static Predicate findOrdersByFilter(Path<OrderModel> root,
                                                CriteriaBuilder builder,
                                                OrderStatusEnum orderStatus,
                                                OrderTypeEnum orderType,
                                                Long patientId,
                                                Long donorId,
                                                LocalDate entryDateBefore,
                                                LocalDate entryDateAfter) {
        return builder.and(
                findAllByOrderType(root, builder, orderType),
                findAllByStatus(root, builder, orderStatus),
                findAllByDonorId(root, builder, donorId),
                findAllByPatientId(root, builder, patientId),
                findAllByEntryDatesInterval(root, builder, entryDateBefore, entryDateAfter)
        );
    }
}
