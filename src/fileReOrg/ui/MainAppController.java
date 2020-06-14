/*
 * Copyright (c) 2020.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fileReOrg.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainAppController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private StackPane titleBarPane;

    @FXML
    private Button titleBarBtnMin;

    @FXML
    private Button titleBarBtnMax;

    @FXML
    private Button titleBarBtnClose;

    @FXML
    private MenuBar menuBar;

    @FXML
    private MenuItem miExit;

    @FXML
    private MenuItem miArchiver;

    @FXML
    private MenuItem miReorganiser;

    @FXML
    private MenuItem miAbout;

    @FXML
    private AnchorPane appSpace;

    @FXML
    private VBox ArchivePane;

    @FXML
    private Button btnFaAddFile;

    @FXML
    private Button btnFaRemoveAll;

    @FXML
    private Button btnFaRemoveSelected;

    @FXML
    private TextField txtOutputDirectory;

    @FXML
    private Button btnFaDirectoryBrowse;

    @FXML
    private RadioButton rbFaFormat7z;

    @FXML
    private RadioButton rbFaFormatZIP;

    @FXML
    private Button btnFaExtract;

    @FXML
    private Button btnFaArchive;

    @FXML
    private VBox ReorganisePane;

    @FXML
    private TextField txtSourceDirectory;

    @FXML
    private Button btnRoBrowserSource;

    @FXML
    private Button btnRoBrowserOutput;

    @FXML
    private Label footerMessage;

    @FXML
    private ImageView footerLockStatus;

    @FXML
    private Label footerCredit;

    static boolean processRunning = false;


    /**
     * Title Bar Control Handlers
     */

    //Minimize Event
    EventHandler<ActionEvent> minimizeButtonHandler = (ActionEvent event) -> {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        if (stage.isMaximized()) {
            stage.setMaximized(false);
            titleBarBtnMax.setDisable(false);
        } else {
            stage.setIconified(true);
        }
    };
    //Maximize Event
    EventHandler<ActionEvent> maximizeButtonHandler = (ActionEvent event) -> {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setMaximized(true);
        titleBarBtnMax.setDisable(true);
    };
    //Close Event
    EventHandler<ActionEvent> closeButtonHandler = (ActionEvent event) -> {
        Stage stage = (Stage) titleBarPane.getScene().getWindow();
        closeApp(stage);
    };
    //To drag window using mouse
    //MousePressed Event : to get the current location of the mouse
    double x = 0.0;
    double y = 0.0;
    EventHandler<MouseEvent> titleBarMousePressedEventHandler = (MouseEvent event) -> {
        x = event.getX();
        y = event.getY();
    };
    EventHandler<MouseEvent> titleBarDraggedEventHandler = (MouseEvent event) -> {
        Node node = (Node) event.getSource();
        Scene scene = node.getScene();
        Stage stage = (Stage) (scene.getWindow());
        if (!stage.isMaximized()) {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        }
    };

    //Close Application
    //Method to close app
    void closeApp(Stage stage) {
        if(processRunning) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("All the running processes (if any) will be stopped. Do you want to close?");
            alert.setTitle("Closing Application");
            alert.getButtonTypes().remove(0, 2);
            alert.getButtonTypes().add(0, ButtonType.YES);
            alert.getButtonTypes().add(1, ButtonType.NO);
            Optional<ButtonType> confirmationResponse = alert.showAndWait();
            if (confirmationResponse.get() == ButtonType.YES) {
                if (stage != null) {
                    stage.close();
                }
                System.exit(0);
            }
        } else {
            if (stage != null) {
                stage.close();
            }
            System.exit(0);
        }
    }

    /**
     * MenuBar Event Handlers
     */

    //menuItem miArchiver Handler
    EventHandler<ActionEvent> miArchiveHandler = (ActionEvent event) -> {
        if(!ArchivePane.isVisible()){
            ReorganisePane.setVisible(false);
            ArchivePane.setVisible(true);
        }
    };

    //menuItem miReOrganiser Handler
    EventHandler<ActionEvent> miReorganiseHandler = (ActionEvent event) -> {
        if(!ReorganisePane.isVisible()){
            ArchivePane.setVisible(false);
            ReorganisePane.setVisible(true);
        }
    };

    //menuItem miAbout Handler
    EventHandler<ActionEvent> miAboutHanlder = (ActionEvent event) -> {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About File ReOrg");
        alert.setHeaderText("File ReOrg");
        alert.setContentText("This is a data organiser tool. It can help in reorganising data and archiving data.");
        alert.show();
    };

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert titleBarPane != null : "fx:id=\"titleBarPane\" was not injected: check your FXML file 'mainApp.fxml'.";
        assert titleBarBtnMin != null : "fx:id=\"titleBarBtnMin\" was not injected: check your FXML file 'mainApp.fxml'.";
        assert titleBarBtnMax != null : "fx:id=\"titleBarBtnMax\" was not injected: check your FXML file 'mainApp.fxml'.";
        assert titleBarBtnClose != null : "fx:id=\"titleBarBtnClose\" was not injected: check your FXML file 'mainApp.fxml'.";
        assert menuBar != null : "fx:id=\"menuBar\" was not injected: check your FXML file 'mainApp.fxml'.";
        assert miExit != null : "fx:id=\"miExit\" was not injected: check your FXML file 'mainApp.fxml'.";
        assert miArchiver != null : "fx:id=\"miArchiver\" was not injected: check your FXML file 'mainApp.fxml'.";
        assert miReorganiser != null : "fx:id=\"miReorganiser\" was not injected: check your FXML file 'mainApp.fxml'.";
        assert miAbout != null : "fx:id=\"miAbout\" was not injected: check your FXML file 'mainApp.fxml'.";
        assert appSpace != null : "fx:id=\"appSpace\" was not injected: check your FXML file 'mainApp.fxml'.";
        assert ArchivePane != null : "fx:id=\"ArchivePane\" was not injected: check your FXML file 'mainApp.fxml'.";
        assert btnFaAddFile != null : "fx:id=\"btnFaAddFile\" was not injected: check your FXML file 'mainApp.fxml'.";
        assert btnFaRemoveAll != null : "fx:id=\"btnFaRemoveAll\" was not injected: check your FXML file 'mainApp.fxml'.";
        assert btnFaRemoveSelected != null : "fx:id=\"btnFaRemoveSelected\" was not injected: check your FXML file 'mainApp.fxml'.";
        assert txtOutputDirectory != null : "fx:id=\"txtOutputDirectory\" was not injected: check your FXML file 'mainApp.fxml'.";
        assert btnFaDirectoryBrowse != null : "fx:id=\"btnFaDirectoryBrowse\" was not injected: check your FXML file 'mainApp.fxml'.";
        assert rbFaFormat7z != null : "fx:id=\"rbFaFormat7z\" was not injected: check your FXML file 'mainApp.fxml'.";
        assert rbFaFormatZIP != null : "fx:id=\"rbFaFormatZIP\" was not injected: check your FXML file 'mainApp.fxml'.";
        assert btnFaExtract != null : "fx:id=\"btnFaExtract\" was not injected: check your FXML file 'mainApp.fxml'.";
        assert btnFaArchive != null : "fx:id=\"btnFaArchive\" was not injected: check your FXML file 'mainApp.fxml'.";
        assert ReorganisePane != null : "fx:id=\"ReorganisePane\" was not injected: check your FXML file 'mainApp.fxml'.";
        assert txtSourceDirectory != null : "fx:id=\"txtSourceDirectory\" was not injected: check your FXML file 'mainApp.fxml'.";
        assert btnRoBrowserSource != null : "fx:id=\"btnRoBrowserSource\" was not injected: check your FXML file 'mainApp.fxml'.";
        assert btnRoBrowserOutput != null : "fx:id=\"btnRoBrowserOutput\" was not injected: check your FXML file 'mainApp.fxml'.";
        assert footerMessage != null : "fx:id=\"footerMessage\" was not injected: check your FXML file 'mainApp.fxml'.";
        assert footerLockStatus != null : "fx:id=\"footerLockStatus\" was not injected: check your FXML file 'mainApp.fxml'.";
        assert footerCredit != null : "fx:id=\"footerCredit\" was not injected: check your FXML file 'mainApp.fxml'.";

        //load File Archiver window
        ArchivePane.setVisible(true);
        ReorganisePane.setVisible(false);

        //bind title bar button handler
        titleBarBtnClose.setOnAction(closeButtonHandler);
        titleBarBtnMax.setOnAction(maximizeButtonHandler);
        titleBarBtnMin.setOnAction(minimizeButtonHandler);

        //enable drag on title bar
        titleBarPane.setOnMousePressed(titleBarMousePressedEventHandler);
        titleBarPane.setOnMouseDragged(titleBarDraggedEventHandler);

        //bind MenuBar button handlers
        miAbout.setOnAction(miAboutHanlder);
        miArchiver.setOnAction(miArchiveHandler);
        miReorganiser.setOnAction(miReorganiseHandler);
        miExit.setOnAction(closeButtonHandler);

        //toggle group radio buttons
        ToggleGroup archiveFormatGroup = new ToggleGroup();
        rbFaFormat7z.setToggleGroup(archiveFormatGroup);
        rbFaFormatZIP.setToggleGroup(archiveFormatGroup);

    }

}
