package com.toy.interpreter.Model.adt;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface IDict<T1, T2> {
    T2 lookup(T1 k);

    boolean isDefined(T1 k);

    void remove(T1 v1);

    void update(T1 k, T2 val);

    boolean isEmpty();

    void add(T1 k, T2 v);

    String toStringFile();

    Map<T1, T2> getContent();

    Set<Map.Entry<T1, T2>> entrySet();

    Collection<T2> getAllValues();
}
