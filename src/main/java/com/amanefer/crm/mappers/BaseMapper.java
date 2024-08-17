package com.amanefer.crm.mappers;

public interface BaseMapper<R, D, E> {

    E fromDtoToEntity(R r);
    D fromEntityToDto(E e);
}
