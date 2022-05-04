package com.toy.interpreter.Model.adt;

import com.toy.interpreter.Model.value.IValue;

import java.util.*;
import java.util.stream.Collectors;

public class Heap implements IHeap{
    Map<Integer, IValue> heap;
    int firstEmpty;
    TreeSet<Integer> emptySpaces;

    public Heap() {heap = new HashMap<>(); this.firstEmpty = 1; emptySpaces = new TreeSet<>();}

    @Override
    public int add(IValue value) {
        if (emptySpaces.isEmpty()) {
            this.heap.put(firstEmpty, value);
            firstEmpty++;
            return firstEmpty - 1;
        }
        int pos = emptySpaces.pollFirst();
        this.heap.put(pos, value);
        return pos;
    }

    @Override
    public void update(int addr, IValue newValue) {
        this.heap.put(addr, newValue);
    }

    @Override
    public void setContent(Map<Integer, IValue> collected) {
        this.heap.clear();
        this.heap = collected.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        for (int i = 1; i < this.heap.size(); i++)
            if (!heap.containsKey(i))
                emptySpaces.add(i);
    }

    @Override
    public boolean isDefined(int address) {
        return this.heap.containsKey(address);
    }

    @Override
    public IValue lookup(int addr) {
        return this.heap.get(addr);
    }

    @Override
    public Map<Integer, IValue> getContent(){return this.heap;}

    @Override
    public String toString() {
        String out = "Heap: \n";
        for (int id : this.heap.keySet()){
            out = out + id + " -> ";
            out = out + this.heap.get(id).toString();
            out += "; ";
        }
        return out + "\n";
    }

    @Override
    public String toStringFile() {
        String out = "Heap: \n";
        for (int id : this.heap.keySet()) {
            out = out + id + " -> ";
            out = out + this.heap.get(id).toString();
            out += "\n";
        }
        return out;
    }
}
