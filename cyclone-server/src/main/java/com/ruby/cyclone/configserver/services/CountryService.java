package com.ruby.cyclone.configserver.services;

import com.ruby.cyclone.configserver.models.business.Country;
import com.ruby.cyclone.configserver.models.business.Namespace;
import com.ruby.cyclone.configserver.repo.mongo.NamespaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CountryService {

    private NamespaceRepository namespaceRepository;

    @Autowired
    public CountryService(NamespaceRepository namespaceRepository) {
        this.namespaceRepository = namespaceRepository;
    }

    public Set<Country> getBusinesses(String namespace) {
        return namespaceRepository.findById(namespace)
                .map(Namespace::getCountries)
                .orElseThrow(RuntimeException::new);
    }


    public void archive(String namespace, String country) {
        Optional<Namespace> ns = this.namespaceRepository.findById(namespace);
        ns.map(nsDao -> {
            Set<Country> countries = nsDao.getCountries();
            countries.remove(country);
            nsDao.setCountries(countries);
            namespaceRepository.save(nsDao);
            return true;
        }).orElseThrow(() -> new RuntimeException());
    }

    public String addCountry(String namespace, Country country) {
        Optional<Namespace> ns = this.namespaceRepository.findById(namespace);
        return ns.map(nsDao -> {
            Set<Country> countries = nsDao.getCountries();
            if (countries == null) {
                countries = new HashSet<>();
            }
            countries.add(country);
            nsDao.setCountries(countries);
            namespaceRepository.save(nsDao);
            return country.getId();
        }).orElseThrow(() -> new RuntimeException());
    }


}
