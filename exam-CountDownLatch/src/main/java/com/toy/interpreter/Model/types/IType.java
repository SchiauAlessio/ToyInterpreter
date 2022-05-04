package com.toy.interpreter.Model.types;

import com.toy.interpreter.Model.value.IValue;

public interface IType {
    IValue defaultValue();
    IType deepCopy();
}
