package com.ruby.cyclone.configserver.controllers;

import com.ruby.cyclone.configserver.models.api.request.AddNamespaceRequest;
import com.ruby.cyclone.configserver.models.business.Namespace;
import com.ruby.cyclone.configserver.services.NamespaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/namespaces")
public class NamespacesController {


    private NamespaceService namespaceService;

    @Autowired
    public NamespacesController(NamespaceService namespaceService) {
        this.namespaceService = namespaceService;
    }

    @GetMapping
    public List<String> getNamespaces() {
        return namespaceService.getNamespaces();
    }

    @PostMapping
    public String addNamespace(@RequestBody Namespace namespace) {
        return namespaceService.addNamespace(namespace);
    }

    @DeleteMapping("/{namespace}/archive")
    public void archive(@PathVariable String namespace) {
        namespaceService.archive(namespace);
    }

}
