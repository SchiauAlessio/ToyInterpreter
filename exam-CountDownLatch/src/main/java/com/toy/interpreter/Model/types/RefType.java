package com.toy.interpreter.Model.types;

import com.toy.interpreter.Model.value.IValue;
import com.toy.interpreter.Model.value.RefValue;

public class RefType implements IType{
    IType inner;

    public RefType(IType inner){this.inner=inner;}

    public IType getInner(){return this.inner;}

    public boolean equals(Object another){
        if(another instanceof RefType)
            return inner.equals(((RefType) another).getInner());
        else return false;
    }

    @Override
    public String toString(){
        return "Ref("+inner.toString()+")";
    }

    @Override
    public IValue defaultValue() {
        return new RefValue(0,inner);
    }

    @Override
    public IType deepCopy() {
        return new RefType(this.inner);
    }
}
