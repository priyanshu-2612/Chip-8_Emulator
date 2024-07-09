package main.java;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Launch extends Application {
    protected static final File loadingScreen = new File("C:/CHip-8_Emulator/src/main/resources/1-chip8-logo.ch8");
    protected static Stage mainStage;
    private static Clip clip;
    protected static Display display;
    private static Memory memory;
    private static Keyboard keyboard;
    private static InstructionSet instructionSet;
    private static Decode decoder;
    protected static CPU cpu;
    protected static Timeline gameLoop;
    protected static Scene mainScene;
    protected static AnchorPane root;
    protected static ImageView bckgr;

    public static void initialize(){
            display = new Display();
            memory = new Memory();
            keyboard = new Keyboard();
            instructionSet = new InstructionSet(memory,display,keyboard);
            decoder = new Decode(instructionSet);
            cpu = new CPU(decoder , memory);
            gameLoop = new Timeline();

        File beepSound = new File("C:/Users/prash/Downloads/ping_pong_8bit_beeep.wav");

        AudioInputStream audioStream = null;
        try {
            audioStream = AudioSystem.getAudioInputStream(beepSound);
        } catch (UnsupportedAudioFileException | IOException e) {
            throw new RuntimeException(e);
        }

        clip = null;
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }

        try {
            clip.open(audioStream);
        } catch (LineUnavailableException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void launchgame(){

        root.getChildren().add(display);

        //mainScene = new Scene(root);
        mainScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                keyboard.setKeyPressed(e.getCode());
            }
        });
        mainScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                keyboard.setKeyReleased(e.getCode());
            }
        });

        //mainStage.setScene(mainScene);
        mainStage.setMaxWidth(64*12.5);
        mainStage.setMaxHeight(32*13);
        mainStage.setMinWidth(64*12.5);
        mainStage.setMinHeight(32*13);
        mainStage.setResizable(false);


        gameLoop.setCycleCount(Timeline.INDEFINITE);

        KeyFrame kf = new KeyFrame(
                Duration.seconds(0.002),
                actionEvent -> {
                    try {
                        cpu.cycle();
                    } catch (RuntimeException e) {
                        System.out.println("GAME OVER");
                        gameLoop.stop();
                    }

                    if (Byte.toUnsignedInt(memory.delayTimer) > 0) memory.delayTimer--;
                    if (Byte.toUnsignedInt(memory.soundTimer) > 0) {
                        clip.start();
                        clip.setMicrosecondPosition(0);
                        memory.soundTimer--;
                    }
                });


        gameLoop.getKeyFrames().add(kf);

        gameLoop.play();
        mainStage.show();

    }
    @Override
    public void start(Stage stage) throws Exception {
        root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        initialize();
        stage.setTitle("Chip-8");
        mainStage = stage;
        mainScene = new Scene(root,64*12,32*13);
        mainStage.setResizable(false);
        mainStage.setScene(mainScene);
        Image bck = new Image("file:src/main/resources/Ocean.png");
        bckgr = new ImageView(bck);
        bckgr.setFitWidth(64*12.5);
        bckgr.setFitHeight(32*13);
        //root.getChildren().addAll(bckgr,Ui_Elements.comboBox,Ui_Elements.playbutton,Ui_Elements.loadbutton);
        root.getChildren().add(bckgr);
        root.getChildren().add(Ui_Elements.comboBox);
        root.getChildren().add(Ui_Elements.playbutton);
        root.getChildren().add(Ui_Elements.loadbutton);
        mainStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
