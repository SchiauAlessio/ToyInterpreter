package com.toy.interpreter.Controller;

import com.toy.interpreter.Model.PrgState;
import com.toy.interpreter.Model.adt.IDict;
import com.toy.interpreter.Model.adt.IHeap;
import com.toy.interpreter.Model.value.IValue;
import com.toy.interpreter.Model.value.RefValue;

import java.util.*;
import java.util.stream.Collectors;

public class GarbageCollector {

    public Map<Integer, IValue> safeGarbageCollector(List<Integer> tableAddresses, Map<Integer, IValue> heap){
        Map<Integer, IValue> result = new HashMap<>();
        IValue value;
        RefValue rValue;
        for(Integer address : heap.keySet()) {
            value = heap.get(address);
            if (tableAddresses.contains(address)) {
                result.put(address, value);
                while (value instanceof RefValue) {
                    rValue = (RefValue)value;
                    address = rValue.getAddr();
                    value = heap.get(address);
                    result.put(address, value);
                }
            }
        }
        return result;
    }

    public List<Integer> allAddresses(List<PrgState> states) {
        List<Integer> result = new ArrayList<>();
        states.stream()
                .map(PrgState::getSymTable)
                .map(t -> t.getContent().values())
                .map(this::tableAddresses)
                .forEach(list -> list.stream()
                        .filter(add -> !result.contains(add))
                        .forEach(result::add));
        return result;
    }

    private List<Integer> tableAddresses(Collection<IValue> tableValues) {
        return tableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {RefValue v1 = (RefValue)v; return v1.getAddr();})
                .collect(Collectors.toList());
    }

    private List<Integer> getAddrFromSymTable(IDict<String, IValue> symTable) {
        return symTable.getAllValues().stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> ((RefValue) v).getAddr())
                .collect(Collectors.toList());
    }

    private List<Integer> getAddrFromHeapTable(IHeap heap) {
        return heap
                .getContent()
                .values()
                .stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> ((RefValue) v).getAddr())
                .collect(Collectors.toList());
    }

    private List<Integer> getReferencedAddr(IDict<String, IValue> symTable, IHeap heap) {
        List<Integer> symTableAddr = getAddrFromSymTable(symTable);
        List<Integer> heapTableAddr = getAddrFromHeapTable(heap);
        List<Integer> allAddr = new Vector<>();
        allAddr.addAll(symTableAddr);
        allAddr.addAll(heapTableAddr);
        return allAddr;
    }
    public void conservativeGarbageCollector(List<PrgState> programStates) {
        List<Integer> referencedAddresses = getReferencedAddr(programStates.get(0).getSymTable(), programStates.get(0).getHeap());
        programStates.get(0).getHeap().setContent(safeGarbageCollector(referencedAddresses, programStates.get(0).getHeap().getContent()));
    }

}
