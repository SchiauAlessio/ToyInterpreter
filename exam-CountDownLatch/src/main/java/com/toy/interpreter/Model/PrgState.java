package com.toy.interpreter.Model;

import com.toy.interpreter.Model.stmt.IStmt;
import com.toy.interpreter.Model.value.IValue;
import com.toy.interpreter.Model.value.StringValue;
import com.toy.interpreter.exceptions.ControllerException;
import com.toy.interpreter.Model.adt.*;

import java.io.BufferedReader;
import java.util.concurrent.atomic.AtomicInteger;

public class PrgState {

    static AtomicInteger nextID = new AtomicInteger(1);
    int id;
    IStack<IStmt> exeStack;
    IDict<String, IValue> symTable;
    IList<IValue> out;
    IDict<StringValue, BufferedReader> fileTable;
    IHeap heap;
    IStmt originalProgram; //optional field, but good to have
    ILatchTable latchTable;

    public PrgState(IStmt stmt) {
        this.exeStack = new MyStack<>();
        this.exeStack.push(stmt);
        this.symTable = new Dict<>();
        this.out = new List<>();
        //this.originalProgram = deepCopy(prg);
        this.fileTable = new Dict<>();
        this.heap=new Heap();
        this.id = this.getID();
        this.latchTable=new myLatchTable();
    }

    public int getID() { return nextID.incrementAndGet() - 1; }

    public PrgState oneStep() throws Exception {
        if (exeStack.isEmpty())
            throw new ControllerException("Execution stack is empty");

        IStmt stmt = exeStack.pop();
        //System.out.format(this.toString());
        return stmt.execute(this);
    }

    public boolean isNotCompleted() {return !exeStack.isEmpty();}

    public void setExeStack(IStack<IStmt> news){this.exeStack = news;}

    public void setSymTable(IDict<String, IValue> news){this.symTable = news;}

    public void setOutput(IList<IValue> newl) {this.out = newl;}

    public void setFileTable(IDict<StringValue, BufferedReader> files) {this.fileTable = files;}

    public void setHeap(IHeap hp) {this.heap = hp;}

    public void setLatchTable(ILatchTable latchtb){
        this.latchTable=latchtb;
    }

    public String toString() {
        return id + "\n"+"ExeStack: " + this.exeStack.toString() + "\nSymTable: " + this.symTable.toString() + "\nOut: " + out.toString()+heap.toString()+"\nLatch Table:" +latchTable.toString()+ "\n\n";
    }

    public IDict<String, IValue> getSymTable() {
        return this.symTable;
    }

    public IStack<IStmt> getStk() {
        return this.exeStack;
    }

    public IList<IValue> getOut() {
        return this.out;
    }

    public IHeap getHeap() {return this.heap;}

    private IStmt deepCopy(IStmt prg) {
        return prg;
    }

    public IDict<StringValue, BufferedReader> getFileTable() {return this.fileTable;}

    public String toStringFile() {return id+"\n"+exeStack.toStringFile() + symTable.toStringFile() + out.toStringFile()+heap.toStringFile()+latchTable.toStringFile()+"-----------\n";}

    public int getcurrentid(){return id;}

    public ILatchTable getLatchTable() {
        return latchTable;
    }
}
