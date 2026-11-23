package com.taxi.data;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

public final class TaxiRepository {

    public static final String DEFAULT_FILE = "taxi.json";

    private TaxiRepository() {
    }

    public static TaxiData load() {
        return load(DEFAULT_FILE);
    }

    public static TaxiData load(String file) {
        ObjectMapper mapper = new ObjectMapper()
                .setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        try (InputStream in = TaxiRepository.class.getClassLoader().getResourceAsStream(file)) {
            if (in == null) {
                throw new IllegalStateException("Не найден файл данных: " + file);
            }
            return mapper.readValue(in, TaxiData.class);
        } catch (IOException e) {
            throw new UncheckedIOException("Ошибка чтения " + file, e);
        }
    }
}
