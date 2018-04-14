package udc.customfx.drawerpanel;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Collection;

public class DrawerPanel extends VBox{

    private boolean equalHeights = false;
    private double drawerHeight, drawerWidth;
    private Collection<Pane> panels = new ArrayList<>();

    public DrawerPanel(double width, double height, boolean equalHeights) {
        this.setFillWidth(true);
        this.setPrefSize(width, height);
        this.equalHeights = equalHeights;
    }

    public DrawerPanel(double width, double height, Collection<Pane> panels) {
        this(width, height, true);
        this.addAll(panels);
    }

    public AnchorPane SPACER (double height) {
        AnchorPane temp = new AnchorPane();
        temp.setPrefHeight(height);
        temp.setMinHeight(height);

        return temp;
    }

    public void addAll(Collection<Pane> panels0) {
        for (Pane panel : panels0) {
            if (equalHeights)
                panel.setPrefHeight(drawerHeight / ((panels0.size() > 0) ? panels0.size() : 1));

            panels.add(panel);
        }

        this.getChildren().clear();
        this.getChildren().addAll(panels);
    }

    public void add(Pane panel) {
        if (equalHeights)
            panel.setPrefHeight(drawerHeight / ((panels.size() > 0) ? panels.size() : 1));

        panels.add(panel);

        this.getChildren().clear();
        this.getChildren().addAll(panels);
    }

    public void setPanels(Collection<Pane> panels) {
        this.panels = panels;
    }

    public Collection<Pane> getPanels() {
        return panels;
    }

    public double getDrawerHeight() {
        return drawerHeight;
    }

    public double getDrawerWidth() {
        return drawerWidth;
    }

    public void setDrawerHeight(double drawerHeight0) {
        drawerHeight = drawerHeight0;
    }

    public void setDrawerWidth(double drawerWidth0) {
        drawerWidth = drawerWidth0;
    }

    @Override
    public void setPrefSize(double prefWidth, double prefHeight) {
        super.setPrefSize(prefWidth, prefHeight);
        super.setMinWidth(prefWidth);
        super.setMinHeight(prefHeight);

        drawerHeight = prefHeight;
        drawerWidth = prefWidth;
    }

    @Override
    protected void setHeight(double value) {
        super.setHeight(value);
        super.setMinHeight(value);

        drawerHeight = value;
    }

    @Override
    protected void setWidth(double value) {
        super.setWidth(value);
        super.setMinWidth(value);

        drawerWidth = value;
    }
}
