package com.toy.interpreter.Model.exp;

import com.toy.interpreter.Model.adt.IDict;
import com.toy.interpreter.Model.adt.IHeap;
import com.toy.interpreter.Model.types.IType;
import com.toy.interpreter.Model.value.IValue;

public abstract class Exp {

    public abstract IValue eval(IDict<String,IValue> symTable, IHeap heap);

    public abstract String toString();

    public abstract Exp deepCopy();

    public abstract IType typeCheck(IDict<String, IType> typeEnv);
}
