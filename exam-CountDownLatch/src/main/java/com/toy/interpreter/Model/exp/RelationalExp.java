package com.toy.interpreter.Model.exp;

import com.toy.interpreter.Model.adt.IDict;
import com.toy.interpreter.Model.adt.IHeap;
import com.toy.interpreter.Model.types.BoolType;
import com.toy.interpreter.Model.types.IType;
import com.toy.interpreter.exceptions.RelationalException;
import com.toy.interpreter.Model.types.IntType;
import com.toy.interpreter.Model.value.BoolValue;
import com.toy.interpreter.Model.value.IValue;
import com.toy.interpreter.Model.value.IntValue;

public class RelationalExp extends Exp{
    String op;
    Exp e1, e2;

    public RelationalExp(String o, Exp exp1, Exp exp2){
        op = o;
        e1 = exp1;
        e2 = exp2;
    }

    public IValue eval(IDict<String, IValue> symTable, IHeap heap) {
        IValue v1, v2;
        BoolValue result;
        v1 = e1.eval(symTable,heap);
        v2 = e2.eval(symTable,heap);
        if (!v1.getType().equals(new IntType()) || !v2.getType().equals(new IntType()))
            throw new RelationalException("Types are not int");
        int intv1 = ((IntValue)v1).getValue();
        int intv2 = ((IntValue)v2).getValue();
        switch (op) {
            case "<" ->
                    result = new BoolValue(intv1 < intv2);
            case "<=" ->
                    result = new BoolValue(intv1 <= intv2);
            case "==" ->
                    result = new BoolValue(intv1 == intv2);
            case "!=" ->
                    result = new BoolValue(intv1 != intv2);
            case ">" ->
                    result = new BoolValue(intv1 > intv2);
            case ">=" ->
                    result = new BoolValue(intv1 >= intv2);
            default -> result = new BoolValue();
        }
        return result;
    }

    public String toString() {
        return e1.toString() + " " + op + " " + e2.toString();
    }

    @Override
    public Exp deepCopy() {
        return new RelationalExp(op,e1.deepCopy(),e2.deepCopy());
    }

    public IType typeCheck(IDict<String, IType> typeEnv) {
        IType t1, t2;
        t1 = e1.typeCheck(typeEnv);
        t2 = e2.typeCheck(typeEnv);
        if (t1.equals(new IntType())) {
            if (t2.equals(new IntType()))
                return new BoolType();
            else throw new ArithmeticException("Second operand is not an integer");
        } else throw new ArithmeticException("First operand is not an integer");
    }

}
