package com.example.karina_project.byoungchanPage.postingArticle.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@Converter
public class FishInfoConverter implements AttributeConverter<Map<String, Integer>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<String, Integer> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting map to JSON string", e);
        }
    }

    @Override
    public Map<String, Integer> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return Collections.emptyMap();
        }
        try {
            return objectMapper.readValue(dbData, new TypeReference<>() {});
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON string to map", e);
        }
    }

}
