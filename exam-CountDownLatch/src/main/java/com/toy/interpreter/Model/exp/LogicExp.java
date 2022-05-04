package com.toy.interpreter.Model.exp;

import com.toy.interpreter.Model.adt.IHeap;
import com.toy.interpreter.Model.types.IType;
import com.toy.interpreter.exceptions.LogicalException;
import com.toy.interpreter.Model.adt.IDict;
import com.toy.interpreter.Model.types.BoolType;
import com.toy.interpreter.Model.value.BoolValue;
import com.toy.interpreter.Model.value.IValue;

public class LogicExp extends Exp {
    Exp e1;
    Exp e2;
    char op;

    public LogicExp(char operator, Exp exp1, Exp exp2) {
        e1 = exp1;
        op = operator;
        e2 = exp2;
    }

    public IValue eval(IDict<String, IValue> symtable, IHeap heap) throws LogicalException {
        if (op != '&' && op != '|' && op != '^')
            throw new LogicalException("Invalid operator!");
        IValue v1, v2;
        v1 = e1.eval(symtable,heap);
        v2 = e2.eval(symtable,heap);
        if (!v1.getType().equals(new BoolType()))
            throw new LogicalException("First operand is not of type bool!");
        if (!v2.getType().equals(new BoolType()))
            throw new LogicalException("Second operand is not of type bool!");
        BoolValue b1 = (BoolValue) v1;
        BoolValue b2 = (BoolValue) v2;
        boolean bl1, bl2;
        bl1 = b1.getValue();
        bl2 = b2.getValue();
        if (op == '&') return new BoolValue(bl1 && bl2);
        else return new BoolValue(bl1 || bl2);
    }

    public String toString() {
        return e1 + "" + op + op + "" + e2;
    }

    @Override
    public Exp deepCopy() {
        return new LogicExp(op, e1.deepCopy(), e2.deepCopy());
    }

    @Override
    public IType typeCheck(IDict<String, IType> typeEnv) {
        IType t1, t2;
        t1 = e1.typeCheck(typeEnv);
        t2 = e2.typeCheck(typeEnv);
        if (t1.equals(new BoolType())) {
            if (t2.equals(new BoolType()))
                return new BoolType();
            else throw new LogicalException("First operand is not boolean");
        } else throw new LogicalException("Second operand is not boolean");
    }
}
