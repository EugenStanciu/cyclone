package com.ruby.cyclone.configserver.models.business;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Document(collection = "namespaces")
@Data
@Builder
@AllArgsConstructor

public class Namespace {

    @Id
    private String name;

    private String description;

    @UniqueElements
    private Set<Country> countries = new HashSet<>();
}
