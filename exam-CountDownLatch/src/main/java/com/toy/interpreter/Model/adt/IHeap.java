package com.toy.interpreter.Model.adt;

import com.toy.interpreter.Model.value.IValue;

import java.util.Map;

public interface IHeap {
    int add(IValue value);
    void update(int addr, IValue newValue);
    void setContent(Map<Integer, IValue> collected);
    IValue lookup(int addr);
    String toStringFile();
    boolean isDefined(int addr);
    Map<Integer, IValue> getContent();

}
