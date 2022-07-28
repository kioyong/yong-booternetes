package com.baeldung.controller;

import java.util.Objects;

import com.baeldung.controller.repository.AddressRepository;
import com.baeldung.controller.repository.UserRepository;
import com.baeldung.entity.Address;
import com.baeldung.entity.User;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.springdoc.api.annotations.ParameterObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@Api(value = "queryController")
public class QueryController {

    @Autowired
    private UserRepository personRepository;
    @Autowired
    private AddressRepository addressRepository;

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<User> queryOverUser(@ParameterObject @QuerydslPredicate(root = User.class,bindings = UserRepository.class) Predicate predicate,
        @RequestParam MultiValueMap<String, String> parameters) throws Exception {
        final BooleanBuilder builder = new BooleanBuilder();

        int size = Integer.parseInt(Objects.requireNonNull(parameters.getFirst("$limit")));
        int offset = Integer.parseInt(Objects.requireNonNull(parameters.getFirst("$offset")));
        int page = offset / size;
        Pageable pageable = PageRequest.of(page, size);
        String operator = parameters.getFirst("operator");
        Page<User> result;

        result = personRepository.findAll(builder.or(predicate), pageable);

        result.getContent().forEach(s -> s.setEmail("xxx@gmail.com"));
        return result;
    }

    @GetMapping(value = "/addresses", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Address> queryOverAddress(@QuerydslPredicate(root = Address.class) Predicate predicate) {
        final BooleanBuilder builder = new BooleanBuilder();
        return addressRepository.findAll(builder.and(predicate));
    }
}
