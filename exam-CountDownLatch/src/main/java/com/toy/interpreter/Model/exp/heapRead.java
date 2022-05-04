package com.toy.interpreter.Model.exp;

import com.toy.interpreter.Model.adt.IDict;
import com.toy.interpreter.Model.adt.IHeap;
import com.toy.interpreter.Model.types.IType;
import com.toy.interpreter.Model.types.RefType;
import com.toy.interpreter.exceptions.HeapException;
import com.toy.interpreter.exceptions.StatementException;
import com.toy.interpreter.Model.value.IValue;
import com.toy.interpreter.Model.value.RefValue;

public class heapRead extends Exp{
    Exp expression;

    public heapRead(Exp e) {this.expression = e;}

    @Override
    public IValue eval(IDict<String, IValue> symTable, IHeap heap) {
        IValue value = expression.eval(symTable, heap);

        if (!(value instanceof RefValue))
            throw new StatementException("Expression is not a RefValue");

        RefValue rValue = (RefValue)value;
        int address = rValue.getAddr();

        if (!heap.isDefined(address))
            throw new StatementException("Value is not on the heap");

        return heap.lookup(address);
    }

    @Override
    public String toString() {
        return "rH(" + expression.toString() + ")";
    }

    @Override
    public Exp deepCopy() {
        return new heapRead(expression.deepCopy());
    }

    @Override
    public IType typeCheck(IDict<String, IType> typeEnv) {
        IType type = expression.typeCheck(typeEnv);
        if (type instanceof RefType) {
            RefType refType = (RefType) type;
            return refType.getInner();
        } else throw new HeapException("Argument is not a refType");
    }
}
