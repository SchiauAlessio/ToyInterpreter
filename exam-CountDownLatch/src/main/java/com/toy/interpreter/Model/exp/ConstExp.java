package com.toy.interpreter.Model.exp;
import com.toy.interpreter.Model.adt.IDict;
import com.toy.interpreter.Model.adt.IHeap;
import com.toy.interpreter.Model.types.IType;
import com.toy.interpreter.Model.value.IValue;

public class ConstExp extends Exp{
    IValue element;

    public ConstExp(IValue val) {
        element = val;
    }

    public IValue eval(IDict<String, IValue> symtable, IHeap heap) throws ArithmeticException {
        return element;
    }

    public String toString() {
        return element.toString();
    }

    @Override
    public Exp deepCopy() {
        return new ConstExp(element.deepCopy());
    }

    public IType typeCheck(IDict<String, IType> typeEnv) {
        return element.getType();
    }
}
