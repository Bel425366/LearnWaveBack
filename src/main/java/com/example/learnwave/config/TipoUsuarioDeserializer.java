package com.example.learnwave.config;

import com.example.learnwave.enums.TipoUsuario;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class TipoUsuarioDeserializer extends JsonDeserializer<TipoUsuario> {
    @Override
    public TipoUsuario deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getValueAsString();
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        
        try {
            return TipoUsuario.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return TipoUsuario.fromString(value);
        }
    }
}