package com.toy.interpreter.Model.adt;

import java.util.List;

public interface IList<T> {
    void add(T e);

    //void clear();

    boolean equals(Object o);

    //boolean isEmpty();

    //void remove(Object o);

    List<T> getContent();

    String toString();

    String toStringFile();
}
