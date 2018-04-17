package udc.secretary.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import udc.Model;

import java.net.URL;
import java.util.ResourceBundle;

public class WalkInControl implements Initializable {
    @FXML
    private JFXButton btnApprove, btnDeny;

    @FXML
    private JFXListView listWalkIn;

    private Model model;
//    private Node secWalkInNode;
//    private ObservableList<Node> nodeComponents;
//    private JFXListView listWalkIn;
//    private JFXButton btnApprove, btnDeny;
//
//    public WalkInControl(){
//        secWalkInNode = loadWalkInView();
//        nodeComponents = ((AnchorPane) secWalkInNode).getChildren();
//        initComponents();
//    }
//
//    private Node loadWalkInView(){
//        Node node = null;
//        try{
//            node = FXMLLoader.load(getClass().getResource("../FXMLFiles/SecWalkInView.fxml"));
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return node;
//    }
//
//    public Node getSecWalkInNode(){
//        return secWalkInNode;
//    }
//
//    private void initComponents(){
//        for (int i = 0; i < nodeComponents.size(); i++){
//            Node node = nodeComponents.get(i);
//            if (node.getId().equals("listWalkIn"))
//                listWalkIn = (JFXListView) node;
//            else if (node.getId().equals("btnApprove"))
//                btnApprove = (JFXButton) node;
//            else if (node.getId().equals("btnDeny"))
//                btnDeny = (JFXButton) node;
//        }
//        initActions();
//    }
//
//    private void initActions(){
//        btnApprove.setOnAction(event -> {
//            System.out.println("Approved");
//        });
//
//        btnDeny.setOnAction(event -> {
//
//        });
//    }

    public WalkInControl(Model model){
        this.model = model;
        ObservableList<String> observableList = FXCollections.observableArrayList();
    }

    public void resetList(){
        listWalkIn.setItems(FXCollections.observableArrayList(""));
    }

    public void setListItems(){
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnApprove.setOnAction(event -> {
            System.out.println("Approved");
        });

        btnDeny.setOnAction(event ->{
            System.out.println("Denied");
        });
    }
}
