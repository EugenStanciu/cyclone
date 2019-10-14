package com.ruby.cyclone.configserver.services;

import com.ruby.cyclone.configserver.models.api.request.AddNamespaceRequest;
import com.ruby.cyclone.configserver.models.business.Namespace;
import com.ruby.cyclone.configserver.repo.mongo.NamespaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.ruby.cyclone.configserver.controllers.DummyValues.DUMMY_NS;

@Service
public class NamespaceService {

    private NamespaceRepository namespaceRepository;

    @Autowired
    public NamespaceService(NamespaceRepository namespaceRepository) {
        this.namespaceRepository = namespaceRepository;
    }

    public List<String> getNamespaces() {
       return this.namespaceRepository.findAll()
                .stream()
                .map(Namespace::getName)
                .collect(Collectors.toList());
    }

    @Transactional
    public String addNamespace(Namespace namespace) {
        if (namespaceRepository.existsById(namespace.getName())) {
            throw new RuntimeException("Namespace already exists");
        }
        namespace.setCountries(Collections.emptySet());
        return namespaceRepository.save(namespace).getName();
    }

    @Transactional
    public void archive(String namespace) {
        //todo make backup collection
        namespaceRepository.deleteById(namespace);
    }



}
