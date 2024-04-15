package ru.danyabereg.booking.mapper;

public interface Mapper<F, T> {

    T mapToEntity(F dto);

    F mapToDto(T entity);
}
