package com.toy.interpreter.Model.types;

import com.toy.interpreter.Model.value.IValue;
import com.toy.interpreter.Model.value.IntValue;

public class IntType implements IType{
    @Override
    public IValue defaultValue() {
        return new IntValue(0);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || o.getClass() != this.getClass())
            return false;
        return true;
    }

    @Override
    public IType deepCopy() {
        return new IntType();
    }

    @Override
    public String toString(){
        return "int";
    }
}
