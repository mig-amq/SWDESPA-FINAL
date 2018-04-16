package udc.secretary.Controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

public class WalkInControl {
    private Node secWalkInNode;
    private ObservableList<Node> nodeComponents;

    public WalkInControl(){
        secWalkInNode = loadWalkInView();
    }

    private Node loadWalkInView(){
        Node node = null;
        try{
            node = FXMLLoader.load(getClass().getResource("../FXMLFiles/SecWalkInView.fxml"));
        }catch (Exception e){
            e.printStackTrace();
        }
        return node;
    }

}
