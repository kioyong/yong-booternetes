package com.baeldung.controller.repository;

import com.baeldung.entity.QUser;
import com.baeldung.entity.User;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.bitbucket.gt_tech.spring.data.querydsl.value.operators.ExpressionProviderFactory;

public interface UserRepository
        extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User>, QuerydslBinderCustomizer<QUser> {
    @Override
    default void customize(final QuerydslBindings bindings, final QUser root) {
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::eq);
        bindings.bind(root.address.country).first((StringPath path, String value) -> {
            if (value.contains(",")) {
                return path.in(value.split(","));
            }
            return path.eq(value);
        });
        bindings.bind(root.name).all(ExpressionProviderFactory::getPredicate);
        bindings.excluding(root.email);
    }
}
