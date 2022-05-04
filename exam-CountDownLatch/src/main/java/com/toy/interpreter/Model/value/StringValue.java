package com.toy.interpreter.Model.value;

import com.toy.interpreter.Model.types.IType;
import com.toy.interpreter.Model.types.StringType;

public class StringValue implements IValue{
    String value;

    public StringValue() {this.value = "";}

    public StringValue(String s) {this.value = s;}

    @Override
    public boolean equals(Object o) {
        if (o == null || o.getClass() != this.getClass())
            return false;
        StringValue s_value = (StringValue) o;
        return s_value.value.equals(this.value);
    }

    public String getValue() {return this.value;}

    @Override
    public String toString() {return this.value;}

    public IType getType() {return new StringType();}

    @Override
    public IValue deepCopy() {return new StringValue(this.value);}
}
