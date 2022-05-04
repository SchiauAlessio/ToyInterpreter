package com.toy.interpreter.View;

import com.toy.interpreter.Controller.Controller;
import com.toy.interpreter.Model.PrgState;
import com.toy.interpreter.Model.stmt.IStmt;
import com.toy.interpreter.Model.value.IValue;
import com.toy.interpreter.Model.adt.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class RunPrgController implements Initializable {

    private Controller controller;

    @FXML
    private TextField nrPrgStatesTextField;

    @FXML
    private ListView outListView;

    @FXML
    private ListView fileTableListView;

    @FXML
    private TableView heapTableView;

    @FXML
    private TableColumn addressHeapColumn;

    @FXML
    private TableColumn valueHeapColumn;

    @FXML
    private ListView prgStateIdentifierListView;

    @FXML
    private TableView symTableView;

    @FXML
    private TableColumn varnameSymColumn;

    @FXML
    private TableColumn valueSymColumn;

    @FXML
    private ListView exeStackListView;

    @FXML
    private Button runButton;

    @FXML
    private TableView<Map.Entry<Integer, Integer>> latchTableView=new TableView<>();

    @FXML
    private TableColumn<Map.Entry<Integer, Integer>, Integer> valueLatchTableColumn=new TableColumn<>();

    @FXML
    private TableColumn<Map.Entry<Integer, Integer>, Integer> locationLatchTableColumn=new TableColumn<>();

    public static PrgState createPrgState(IStmt stmt) {
        IStack<IStmt> stk = new MyStack<>();
        IDict<String, IValue> symtable = new Dict<>();
        IList<IValue> ot = new com.toy.interpreter.Model.adt.List<>();
        return new PrgState(stmt);
    }

    public void setController(Controller controller) {
        this.controller = controller;
        populatePrgStateIdentifiers();
        populateExecutionStack(controller.getRepo().getPrgList().get(0));
        prgStateIdentifierListView.getSelectionModel().select(0);
    }

    public List<Integer> getPrgStatesIds(List<PrgState> prgStates) {
        return prgStates
                .stream()
                .map(PrgState::getcurrentid)
                .collect(Collectors.toList());
    }

    public void populatePrgStateIdentifiers() {
        List<PrgState> prgStates = controller.getRepo().getPrgList();
        for (int i = 0; i < prgStates.size(); ++i) {
            if (prgStates.get(i).getStk().isEmpty()) {
                prgStates.remove(i);
            }
        }
        prgStateIdentifierListView.setItems(FXCollections.observableList(getPrgStatesIds(prgStates)));
        nrPrgStatesTextField.setText(Integer.toString(prgStates.size()));
    }

    public void populateOutList(PrgState prgState) {
        if (prgState != null) {
            ObservableList<IValue> observableList = FXCollections.observableList(prgState.getOut().getContent());
            outListView.setItems(observableList);
            outListView.refresh();
        }
    }

    public void populateFileTable(PrgState prgState) {
        if (prgState != null) {
            var fileTable = prgState.getFileTable();
            List<String> fileTableList = new ArrayList<>();
            for (var entry : fileTable.entrySet()) {
                fileTableList.add("" + entry.getKey() + entry.getValue());
            }
            ObservableList<String> observableList = FXCollections.observableArrayList(fileTableList);
            this.fileTableListView.setItems(observableList);
            this.fileTableListView.refresh();
        }
    }

    public void populateSymbolTable(PrgState prgState) {
        if (prgState != null) {
            var symbolTable = prgState.getSymTable();
            List<Map.Entry<String, IValue>> symbolTableList = new ArrayList<>(symbolTable.entrySet());
            this.symTableView.setItems(FXCollections.observableArrayList(symbolTableList));
            this.symTableView.refresh();
        }
    }

    public void populateHeapTable(PrgState prgState) {
        if (prgState != null) {
            var heap = prgState.getHeap();
            List<Map.Entry<Integer, IValue>> heapList = new ArrayList<>(heap.getContent().entrySet());
            this.heapTableView.setItems(FXCollections.observableArrayList(heapList));
            this.heapTableView.refresh();
        }
    }

    public void populateExecutionStack(PrgState prgState) {
        if (prgState != null) {
            var stack = prgState.getStk();
            List<String> executionStackList = new ArrayList<>();
            for (var statement : stack.getContent()) {
                executionStackList.add(statement.toString());
            }
            this.exeStackListView.setItems(FXCollections.observableArrayList(executionStackList));
            this.exeStackListView.refresh();
        }
    }

    public void populateLatchTable(PrgState prgState){
        if (prgState != null){
            ILatchTable latchTable = prgState.getLatchTable();
            List<Map.Entry<Integer, Integer>> latchList = new ArrayList<>();
            for(Map.Entry<Integer, Integer> entry : latchTable.getLatchTable().entrySet())
                latchList.add(entry);

            latchTableView.setItems(FXCollections.observableList(latchList));
            latchTableView.refresh();
        }
    }

    private void populateAll(PrgState currentPrgState) {
        populateFileTable(currentPrgState);
        populateOutList(currentPrgState);
        populateHeapTable(currentPrgState);
        populateSymbolTable(currentPrgState);
        populateExecutionStack(currentPrgState);
        populateLatchTable(currentPrgState);
    }

    private PrgState getCurrentPrgState() {
        int index = prgStateIdentifierListView.getSelectionModel().getSelectedIndex();
        if (index == -1) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No program was selected!", ButtonType.OK);
            alert.show();
            return null;
        }
        int selectedId = (int) prgStateIdentifierListView.getSelectionModel().getSelectedItem();
        return controller.getRepo().getPrgList()
                .stream()
                .filter(a -> a.getcurrentid() == selectedId)
                .collect(Collectors.toList())
                .get(0);
    }

    private void oneStepExec() {
        int nrPrgStatesLeft = controller.getRepo().getPrgList().size();
        if (nrPrgStatesLeft == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Finished execution!", ButtonType.OK);
            alert.show();
            return;
        }
        try {
            controller.oneStep();
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.show();
        }
        nrPrgStatesLeft = controller.getRepo().getPrgList().size();
        if (nrPrgStatesLeft == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Finished execution!", ButtonType.OK);
            alert.show();
        } else {
            populateAll(getCurrentPrgState());
        }
        populatePrgStateIdentifiers();
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle){
        addressHeapColumn.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Map.Entry<Integer, IValue>, Integer>, ObservableValue<Integer>>) p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        valueHeapColumn.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Map.Entry<Integer, IValue>, IValue>, ObservableValue<IValue>>) p -> new SimpleObjectProperty<>(p.getValue().getValue()));

        valueSymColumn.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Map.Entry<String, IValue>, IValue>, ObservableValue<IValue>>) p -> new SimpleObjectProperty<>(p.getValue().getValue()));
        varnameSymColumn.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Map.Entry<String, IValue>, IValue>, ObservableValue<String>>) p -> new SimpleStringProperty(p.getValue().getKey()));

        prgStateIdentifierListView.setOnMouseClicked(mouseEvent -> populateAll(getCurrentPrgState()));

        runButton.setOnAction(actionEvent -> oneStepExec());

        locationLatchTableColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        valueLatchTableColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getValue()).asObject());
    }
}
