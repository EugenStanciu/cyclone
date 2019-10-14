package com.ruby.cyclone.configserver.models.business;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@Document(collection = "files")
public class FileName {
    @Id
    String name;

}
