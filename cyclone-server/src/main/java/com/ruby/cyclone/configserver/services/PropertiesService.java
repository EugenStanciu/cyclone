package com.ruby.cyclone.configserver.services;

import com.ruby.cyclone.configserver.models.api.request.AddNewPropertyRequest;
import com.ruby.cyclone.configserver.models.api.request.PropertyLocation;
import com.ruby.cyclone.configserver.models.api.request.UpdatePropertyRequest;
import com.ruby.cyclone.configserver.models.business.Country;
import com.ruby.cyclone.configserver.models.business.Namespace;
import com.ruby.cyclone.configserver.models.business.Property;
import com.ruby.cyclone.configserver.models.business.PropertyId;
import com.ruby.cyclone.configserver.repo.mongo.NamespaceRepository;
import com.ruby.cyclone.configserver.repo.mongo.PropertiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.*;

@Service
public class PropertiesService {

    private PropertiesRepository propertiesRepository;
    private NamespaceRepository namespaceRepository;

    @Autowired
    public PropertiesService(PropertiesRepository propertiesRepository, NamespaceRepository namespaceRepository) {
        this.propertiesRepository = propertiesRepository;
        this.namespaceRepository = namespaceRepository;
    }


    public Map<PropertyLocation, List<Property>> searchProperties(String namespace, String business, String keyWord) {
        List<Property> properties = propertiesRepository.searchByKeyAndLocationRegexes(namespace, business, "", keyWord);
        return groupProperties(properties);
    }

    private Map<PropertyLocation, List<Property>> groupProperties(List<Property> properties) {
        if (properties == null || properties.isEmpty()) {
            return Collections.emptyMap();
        }
        Map<PropertyLocation, List<Property>> propertiesMap = new HashMap<>();
        for (Property property : properties) {
            String ns = property.getId().getNamespace();
            String country = property.getId().getCountry();
            PropertyLocation location = PropertyLocation.builder().namespace(ns).country(country).build();
            if (propertiesMap.containsKey(location)) {
                List<Property> propertyList = propertiesMap.get(location);
                propertyList.add(property);
            } else {
                propertiesMap.put(location, new ArrayList<>());
                propertiesMap.get(location).add(property);
            }
        }
        return propertiesMap;
    }

    public void addProperty(AddNewPropertyRequest propertyRequest) {

        List<Namespace> namespaces = this.namespaceRepository.findAll();
        if (namespaces == null || namespaces.isEmpty()) {
            throw new RuntimeException("no namepsaces defined");
        }
        for (Namespace ns : namespaces) {
            addPropertiesInAllNamespaces(propertyRequest, ns);

        }

    }

    private void addPropertiesInAllNamespaces(@NotNull AddNewPropertyRequest propertyRequest, @NotNull Namespace ns) {
        Set<Country> countries = ns.getCountries();
        if (countries == null || countries.isEmpty()) {
            return;
        }
        countries.forEach(country -> {
            if (country == null || country.getId() == null) {
                return;
            }
            addPropertyForOneCountry(propertyRequest, ns, country);
        });
    }

    private void addPropertyForOneCountry(AddNewPropertyRequest propertyRequest, Namespace ns, Country country) {
        PropertyId pId = PropertyId.builder()
                .namespace(ns.getName())
                .country(country.getId())
                .file(propertyRequest.getFile())
                .key(propertyRequest.getKey())
                .build();
        Property property = new Property();
        property.setId(pId);
        property.setValue(propertyRequest.getDefaultValue());
        if (propertiesRepository.existsById(pId)) {
            return;
        }
        propertiesRepository.save(property);
    }

    public void updateProperty(UpdatePropertyRequest propertyRequest) {
        String propertyKey = propertyRequest.getKey();
        Map<PropertyLocation, Object> valuesByLocation = propertyRequest.getValuesByLocation();
        valuesByLocation.keySet().forEach(location -> {
            PropertyId propertyId = PropertyId.builder().country(location.getCountry())
                    .namespace(location.getNamespace())
                    .key(propertyKey)
                    .build();

            Optional<Property> property = propertiesRepository.findById(propertyId);
            property.map(p -> {
                p.setValue(valuesByLocation.get(location));
                return propertiesRepository.save(p);
            }).orElseThrow(() -> new RuntimeException("this property does not exists"));

        });
    }
}
