package com.logicea.cards.domain.specification;

import com.logicea.cards.domain.entity.Card;
import com.logicea.cards.domain.enumeration.Status;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class CardSpecification {

    private CardSpecification() {}

    public static Specification<Card> searchCard(final List<Status> status, final LocalDate dateCreated, final String search) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (status != null && !status.isEmpty()) {
                predicates.add(root.get("status").in(status));
            }
            if (dateCreated != null) {
                predicates.add(cb.between(root.get("dateCreated"), dateCreated.atStartOfDay(), dateCreated.plusDays(1).atStartOfDay()));
            }
            if (search != null) {
                predicates.add(cb.or(
                        cb.like(root.get("name"), "%" + search + "%"),
                        cb.like(root.get("description"), "%" + search + "%"),
                        cb.like(root.get("color"), "%" + search + "%")
                ));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
