package com.toy.interpreter.Model.value;

import com.toy.interpreter.Model.types.IType;

public interface IValue {
    IType getType();
    IValue deepCopy();
}
