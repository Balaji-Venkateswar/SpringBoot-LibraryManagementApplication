package com.project.library.mappers;

public interface Mapper<A,B> {

    A mapTo(B b);

    B mapFrom(A a);
}
