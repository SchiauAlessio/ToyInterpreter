package com.toy.interpreter.Model.exp;

import com.toy.interpreter.Model.adt.IDict;
import com.toy.interpreter.Model.adt.IHeap;
import com.toy.interpreter.Model.types.IType;
import com.toy.interpreter.Model.value.IValue;

public class VarExp extends Exp {
    String id;

    public VarExp(String id) {
        this.id = id;
    }

    public IValue eval(IDict<String, IValue> symTable, IHeap heap) throws ArithmeticException {
        return symTable.lookup(id);
    }

    public String toString() {
        return id;
    }

    @Override
    public Exp deepCopy() {
        return new VarExp(id);
    }

    public IType typeCheck(IDict<String, IType> typeEnv) {
        return typeEnv.lookup(id);
    }
}
