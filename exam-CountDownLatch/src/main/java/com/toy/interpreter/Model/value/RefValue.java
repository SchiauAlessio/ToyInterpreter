package com.toy.interpreter.Model.value;

import com.toy.interpreter.Model.types.IType;
import com.toy.interpreter.Model.types.RefType;

public class RefValue implements IValue{
    int address;
    IType locationType;

    public RefValue(int addr,IType location){
        this.address=addr;
        this.locationType=location;
    }

    public boolean equals(Object o){
        if(o==null||o.getClass()!=this.getClass())
            return false;
        RefValue ref_value=(RefValue) o;
        return address==ref_value.getAddr();
    }

    public int getAddr(){return this.address;}

    public IType getLocationType() {return locationType;}

    @Override
    public IType getType(){return new RefType(locationType);}

    @Override
    public IValue deepCopy() {
        return new RefValue(address,locationType.deepCopy());
    }

    public String toString(){
        return "(" + address + "," + locationType.toString() + ")";
    }
}
