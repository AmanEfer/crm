package com.amanefer.crm.mappers;

public interface BaseMapper<R, D, E> {

    E toEntity(R r);

    D toDto(E e);
}
