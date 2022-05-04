package com.toy.interpreter.Model.stmt;

import com.toy.interpreter.exceptions.StatementException;
import com.toy.interpreter.Model.adt.IDict;
import com.toy.interpreter.Model.adt.IHeap;
import com.toy.interpreter.Model.adt.ILatchTable;
import com.toy.interpreter.Model.exp.Exp;
import com.toy.interpreter.Model.PrgState;
import com.toy.interpreter.Model.types.IType;
import com.toy.interpreter.Model.types.IntType;
import com.toy.interpreter.Model.value.IValue;
import com.toy.interpreter.Model.value.IntValue;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewLatchStmt implements IStmt{
    private String var;
    private Exp exp;
    private static Lock lock=new ReentrantLock();

    public NewLatchStmt(String v,Exp e){var=v;exp=e;}

    @Override
    public PrgState execute(PrgState state) throws StatementException {
        lock.lock();
        try{
            IDict<String, IValue> table=state.getSymTable();
            IHeap heap=state.getHeap();
            ILatchTable latch = state.getLatchTable();

            IntValue nr = (IntValue)(exp.eval(table, heap));
            int number = nr.getValue();

            int freeLoc= latch.getFreeAddress();
            latch.put(freeLoc,number);

            if(table.isDefined(var))
                table.update(var,new IntValue(freeLoc));
            else
                table.add(var,new IntValue(freeLoc));

        } catch(Exception e){
            throw new StatementException(e.getMessage());
        }
        lock.unlock();
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new NewLatchStmt(var,exp.deepCopy());
    }

    @Override
    public IDict<String, IType> typeCheck(IDict<String, IType> typeEnv) throws StatementException {
        if(typeEnv.lookup(var).equals(new IntType()))
            if(exp.typeCheck(typeEnv).equals(new IntType()))
                return typeEnv;
            else throw new StatementException("Exp is not int");
        else throw new StatementException("Var is not int");
    }

    @Override
    public String toString() {
        return "newLatch("+var+","+exp.toString()+")";
    }
}