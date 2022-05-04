package com.toy.interpreter.Model.adt;

import java.util.Vector;


public class List<T> implements IList<T> {
    Vector<T> vector;

    public List() {
        this.vector = new Vector<>();
    }

    @Override
    public void add(T e) {
        vector.add(e);
    }

    @Override
    public java.util.List<T> getContent() {
        return vector;
    }

//    @Override
//    public void clear() {
//        vector.clear();
//    }

//    @Override
//    public boolean isEmpty() {
//        return vector.isEmpty();
//    }

//    @Override
//    public void remove(Object o) {
//        vector.remove(o);
//    }

    public String toString() {
        StringBuilder strbld = new StringBuilder();
        for (T e : vector)
            strbld.append(e + " ");
        return strbld.toString()+"\n";
    }

    @Override
    public String toStringFile() {
        String out = "Out:\n";
        for (T elem : this.vector) {
            out = out + elem.toString();
            out += "\n";
        }
        return out;
    }
}
