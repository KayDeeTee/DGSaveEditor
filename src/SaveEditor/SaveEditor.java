/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SaveEditor;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author Kristian
 */

//"C:\\Users\\Kristian\\AppData\\Local\\deathsgambit397\\deaths_gambit0\\inventory.sav"
public class SaveEditor extends Application {
    
    public SaveData[] saveData = new SaveData[4];
    
    @Override
    public void start(Stage primaryStage) throws Exception {        
        primaryStage.setTitle("DGSaveEditor");

        saveData[0] = new SaveData(System.getenv("LOCALAPPDATA")+"/deathsgambit397/deaths_gambit0/inventory.sav");
        saveData[1] = new SaveData(System.getenv("LOCALAPPDATA")+"/deathsgambit397/deaths_gambit1/inventory.sav");
        saveData[2] = new SaveData(System.getenv("LOCALAPPDATA")+"/deathsgambit397/deaths_gambit2/inventory.sav");
        saveData[3] = new SaveData(System.getenv("LOCALAPPDATA")+"/deathsgambit397/deaths_gambit3/inventory.sav");
        
        TabPane tabPane = new TabPane();
        
        Tab t0 = new Tab();        
        t0.setText("Slot0");        
        CreateTab(t0, saveData[0]);        
        tabPane.getTabs().add(t0);
        
        Tab t1 = new Tab();        
        t1.setText("Slot1");        
        CreateTab(t1, saveData[1]);        
        tabPane.getTabs().add(t1);
        
        Tab t2 = new Tab();        
        t2.setText("Slot2");        
        CreateTab(t2, saveData[2]);        
        tabPane.getTabs().add(t2);
        
        Tab t3 = new Tab();        
        t3.setText("Slot3");        
        CreateTab(t3, saveData[3]);        
        tabPane.getTabs().add(t3);
        
        
        Scene scene = new Scene(tabPane, 600, 275);      
        
        primaryStage.setScene(scene);
        
        primaryStage.show();
    }
    
    public void CreateTab(Tab t, SaveData s){
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label shardsLabel = new Label("Shards:");
        grid.add(shardsLabel, 0, 0);

        TextField shardsField = new TextField();
        shardsField.setText( Integer.toString(s.shards) );
        grid.add(shardsField, 0, 1);
        
        Label maxTalentLabel = new Label("Max Talent Points:");
        grid.add(maxTalentLabel, 1, 0);

        TextField maxTalentField = new TextField();
        maxTalentField.setText( Integer.toString(s.maxTalents) );
        grid.add(maxTalentField, 1, 1);
        
        Label currentTalentLabel = new Label("Current Talent Points:");
        grid.add(currentTalentLabel, 2, 0);

        TextField currentTalentField = new TextField();
        currentTalentField.setText( Integer.toString(s.talentPoints ) );
        grid.add(currentTalentField, 2, 1);
        
        Label itemSlotLabel = new Label("Item Slot:");
        grid.add(itemSlotLabel, 0, 2);
        
        ComboBox itemSlots = new ComboBox();
        for( int i = 168; i >= 0; i--)
            itemSlots.getItems().add( "Slot " + i );
        
        itemSlots.getSelectionModel().select(0);        
        grid.add(itemSlots, 0, 3);
        
        Label itemTypeLabel = new Label("Item Type:");
        grid.add(itemTypeLabel, 1, 2);

        TextField itemTypeField = new TextField();
        itemTypeField.setText( Integer.toString( s.inv.get(0).id ) );
        grid.add(itemTypeField, 1, 3);
        
        Label quantityLabel = new Label("Quantity:");
        grid.add(quantityLabel, 2, 2);

        TextField quantityField = new TextField();
        quantityField.setText( Integer.toString( s.inv.get(0).quantity ) );
        grid.add(quantityField, 2, 3);
        
        Label qualityLabel = new Label("Quality:");
        grid.add(qualityLabel, 3, 2);

        TextField qualityField = new TextField();
        qualityField.setText( Integer.toString( s.inv.get(0).quality ) );
        grid.add(qualityField, 3, 3); 
        
        Button saveBtn = new Button("Save");
        grid.add(saveBtn, 0, 4);
        
             
        itemSlots.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                s.selectedItemSlot = itemSlots.getSelectionModel().selectedIndexProperty().get();
                itemTypeField.setText( Integer.toString( s.inv.get( s.selectedItemSlot ).id ) );
                quantityField.setText( Integer.toString( s.inv.get( s.selectedItemSlot ).quantity ) );
                qualityField.setText( Integer.toString( s.inv.get( s.selectedItemSlot ).quality ) );
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
        itemTypeField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, 
                String newValue) {
                if (!newValue.matches("\\d*")) {
                    itemTypeField.setText(newValue.replaceAll("[^\\d]", ""));
                }
                s.inv.get( s.selectedItemSlot ).id = Integer.parseInt( itemTypeField.getText() );
            };
        });
        
        quantityField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, 
                String newValue) {
                if (!newValue.matches("\\d*")) {
                    quantityField.setText(newValue.replaceAll("[^\\d]", ""));
                }
                s.inv.get( s.selectedItemSlot ).quantity = Integer.parseInt( quantityField.getText() );
            };
        });
        
        qualityField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, 
                String newValue) {
                if (!newValue.matches("\\d*")) {
                    qualityField.setText(newValue.replaceAll("[^\\d]", ""));
                }
                s.inv.get( s.selectedItemSlot ).quality = Integer.parseInt( qualityField.getText() );
            };
        });
        
        
                
        shardsField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, 
                String newValue) {
                if (!newValue.matches("\\d*")) {
                    shardsField.setText(newValue.replaceAll("[^\\d]", ""));
                }
                s.shards = Integer.parseInt( shardsField.getText() );
            };
        });
        
        maxTalentField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, 
                String newValue) {
                if (!newValue.matches("\\d*")) {
                    maxTalentField.setText(newValue.replaceAll("[^\\d]", ""));
                }
                s.maxTalents = Integer.parseInt( maxTalentField.getText() );
                s.spentTalents = s.maxTalents-s.talentPoints;
            };
        });
        
        currentTalentField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, 
                String newValue) {
                if (!newValue.matches("\\d*")) {
                    currentTalentField.setText(newValue.replaceAll("[^\\d]", ""));
                }
                s.talentPoints = Integer.parseInt( currentTalentField.getText() );
                s.spentTalents = s.maxTalents-s.talentPoints;
            };
        });
        
        saveBtn.setOnAction(new EventHandler<ActionEvent>() {
        @Override public void handle(ActionEvent e) {
            try {
                SaveData.saveSave(s);
            } catch (IOException ex) {
                Logger.getLogger(SaveEditor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
});
        
        t.setContent(grid);
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
