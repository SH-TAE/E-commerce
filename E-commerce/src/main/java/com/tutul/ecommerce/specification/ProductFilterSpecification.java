package com.tutul.ecommerce.specification;

import com.tutul.ecommerce.entities.Product;
import io.micrometer.common.util.StringUtils;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;


import java.util.ArrayList;
import java.util.List;

public class ProductFilterSpecification {
    public static Specification<Product> getFilteredProduct(ProductFilterParams params) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.isNotBlank(params.getTitle())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + params.getTitle().toLowerCase() + "%"));
            }

            if (StringUtils.isNotBlank(params.getDescription())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + params.getDescription().toLowerCase() + "%"));
            }

            if (params.getCategory() != null && !params.getCategory().isEmpty()) {
                predicates.add(root.get("category").get("name").in(params.getCategory()));

            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

    }


}
