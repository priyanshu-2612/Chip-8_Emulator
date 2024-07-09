package main.java;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML

    public ComboBox<String> comboBox;
    public Button playbutton;
    public Button loadbutton;
    private HashMap<String,String> colorCombos;
    public AnchorPane background;
    public Label choose;

    private String[] themes = {"Soft Purple" , "Sky Blue" , "Sunset" , "Forest" , "Ocean" , "Sunshine" , "Berry" , "Minty Fresh" , "Peach" , "Pastel"};

    public void play(ActionEvent e){
        if(!Launch.cpu.isRomLoaded()){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("No ROM Loaded");
            a.setResizable(false);
            a.show();
        }
        else Launch.launchgame();
    }
    public void loadRom(ActionEvent e){
        FileChooser fil_chooser = new FileChooser();
        File file = fil_chooser.showOpenDialog(Launch.mainStage);
        try{
            Launch.cpu.load_rom(file);
        }
        catch (RuntimeException exc){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText(exc.getMessage());
            a.setResizable(false);
            a.show();
        }
    }

    public void changeTheme(ActionEvent e){
        String theme = comboBox.getValue();
        choose.setText(theme);
        Launch.bckgr.setImage(new Image("file:src/main/resources/"+theme+".png"));
        String[] OnOff = colorCombos.get(theme).split("-");
        Launch.display.On = Color.valueOf(OnOff[0]);
        Launch.display.Off = Color.valueOf(OnOff[1]);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Ui_Elements.comboBox = comboBox;
        Ui_Elements.loadbutton = loadbutton;
        Ui_Elements.playbutton = playbutton;
        comboBox.getItems().addAll(themes);
        colorCombos = new HashMap<>();
        colorCombos.put("Soft Purple", "#9B72AA-#3C1E5F");
        colorCombos.put("Sky Blue", "#87CEEB-#191970");
        colorCombos.put("Sunset", "#FFB6C1-#8B0000");
        colorCombos.put("Forest", "#98FF98-#006400");
        colorCombos.put("Ocean", "#00FFFF-#000080");
        colorCombos.put("Sunshine", "#FFFFE0-#DAA520");
        colorCombos.put("Berry", "#F08080-#8B008B");
        colorCombos.put("Pastel", "#E6E6FA-#6A5ACD");
        colorCombos.put("Minty Fresh", "#AFEEEE-#008080");
        colorCombos.put("Peach", "#FFDAB9-#FF8C00");

        comboBox.setOnAction(this::changeTheme);
    }
}
