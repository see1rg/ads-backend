package ru.skypro.homework.dtos;

import lombok.Data;

import java.util.Collection;

/**
 * Класс ResponseWrapper представляет собой обертку для ответа с коллекцией результатов.
 *
 * @param <T> тип элементов в коллекции результатов
 */
@Data
public class ResponseWrapper<T> {
    private final int count;
    private final Collection<T> results;

    public ResponseWrapper(Collection<T> results) {
        this.count = results.size();
        this.results = results;
    }
}
