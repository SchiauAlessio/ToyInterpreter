package com.toy.interpreter.Model.exp;

import com.toy.interpreter.Model.adt.IDict;
import com.toy.interpreter.Model.adt.IHeap;
import com.toy.interpreter.Model.types.IType;
import com.toy.interpreter.Model.types.IntType;
import com.toy.interpreter.Model.value.IValue;
import com.toy.interpreter.Model.value.IntValue;

public class ArithExp extends Exp {
    char op;
    Exp e1, e2;

    public ArithExp(char operator, Exp exp1, Exp exp2) {
        op = operator;
        e1 = exp1;
        e2 = exp2;
    }

    public IValue eval(IDict<String, IValue> symTable, IHeap heap) throws ArithmeticException {
        if (op != '+' && op != '-' && op != '*' && op != '/')
            throw new ArithmeticException("Invalid operator!");
        IValue v1, v2;
        v1 = e1.eval(symTable,heap);
        v2 = e2.eval(symTable,heap);
        if (!v1.getType().equals(new IntType()))
            throw new ArithmeticException("First operand is not of type int!");
        if (!v2.getType().equals(new IntType()))
            throw new ArithmeticException("Second operand is not of type int!");
        IntValue i1 = (IntValue) v1;
        IntValue i2 = (IntValue) v2;
        int n1, n2;
        n1 = i1.getValue();
        n2 = i2.getValue();
        if (op == '+') return new IntValue(n1 + n2);
        if (op == '-') return new IntValue(n1 - n2);
        if (op == '*') return new IntValue(n1 * n2);
        else if (n2 == 0)
            throw new ArithmeticException("Division by 0!");
        else return new IntValue(n1 / n2);
    }

    public char getOp() {
        return this.op;
    }

    public Exp getFirst() {
        return this.e1;
    }

    public Exp getSecond() {
        return this.e2;
    }

    public String toString() {
        return e1.toString() + " " + op + " " + e2.toString();
    }

    @Override
    public Exp deepCopy() {
        return new ArithExp(op, e1.deepCopy(), e2.deepCopy());
    }

    public IType typeCheck(IDict<String, IType> typeEnv) {
        IType t1, t2;
        t1 = e1.typeCheck(typeEnv);
        t2 = e2.typeCheck(typeEnv);
        if (t1.equals(new IntType())) {
            if (t2.equals(new IntType()))
                return new IntType();
            else throw new ArithmeticException("Second operand is not an integer");
        } else throw new ArithmeticException("First operand is not an integer");
    }
}
