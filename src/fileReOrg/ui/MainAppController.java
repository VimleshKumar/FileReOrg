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

import fileReOrg.services.archive.Archive;
import fileReOrg.services.archive.SevenZipArchive;
import fileReOrg.services.archive.ZipArchive;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainAppController {

    public static Image processingImage = new Image(ClassLoader.getSystemResourceAsStream("fileReOrg/res/processing.png"), 20, 20, true, false);
    public static Image allOKImage = new Image(ClassLoader.getSystemResourceAsStream("fileReOrg/res/ok.png"), 20, 20, true, false);
    public static Image windowsClient = new Image(ClassLoader.getSystemResourceAsStream("fileReOrg/res/windows-client.png"), 25, 25, true, false);
    static boolean processRunning;
    ToggleGroup archiveTypeToggleGroup;
    int threadCount = 0;
    int maxThreadCount = 0;

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
    private TreeView<String> treeViewFileExplorer;
    @FXML
    private Button btnFaAddFile;
    @FXML
    private ListView<String> listViewFiles;
    @FXML
    private Button btnFaRemoveAll;
    @FXML
    private Button btnFaRemoveSelected;
    @FXML
    private TextField txtOutputDirectoryArchiver;
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
    private TextField txtOutputDirectoryReorganiser;
    @FXML
    private Button btnRoBrowserOutput;
    @FXML
    private Button btnReorganise;
    @FXML
    private Label footerMessage;
    @FXML
    private ImageView footerLockStatus;
    @FXML
    private Label footerCredit;


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
        assert treeViewFileExplorer != null : "fx:id=\"treeViewFileExplorer\" was not injected: check your FXML file 'mainApp.fxml'.";
        assert btnFaAddFile != null : "fx:id=\"btnFaAddFile\" was not injected: check your FXML file 'mainApp.fxml'.";
        assert listViewFiles != null : "fx:id=\"listViewFiles\" was not injected: check your FXML file 'mainApp.fxml'.";
        assert btnFaRemoveAll != null : "fx:id=\"btnFaRemoveAll\" was not injected: check your FXML file 'mainApp.fxml'.";
        assert btnFaRemoveSelected != null : "fx:id=\"btnFaRemoveSelected\" was not injected: check your FXML file 'mainApp.fxml'.";
        assert txtOutputDirectoryArchiver != null : "fx:id=\"txtOutputDirectoryArchiver\" was not injected: check your FXML file 'mainApp.fxml'.";
        assert btnFaDirectoryBrowse != null : "fx:id=\"btnFaDirectoryBrowse\" was not injected: check your FXML file 'mainApp.fxml'.";
        assert rbFaFormat7z != null : "fx:id=\"rbFaFormat7z\" was not injected: check your FXML file 'mainApp.fxml'.";
        assert rbFaFormatZIP != null : "fx:id=\"rbFaFormatZIP\" was not injected: check your FXML file 'mainApp.fxml'.";
        assert btnFaExtract != null : "fx:id=\"btnFaExtract\" was not injected: check your FXML file 'mainApp.fxml'.";
        assert btnFaArchive != null : "fx:id=\"btnFaArchive\" was not injected: check your FXML file 'mainApp.fxml'.";
        assert ReorganisePane != null : "fx:id=\"ReorganisePane\" was not injected: check your FXML file 'mainApp.fxml'.";
        assert txtSourceDirectory != null : "fx:id=\"txtSourceDirectory\" was not injected: check your FXML file 'mainApp.fxml'.";
        assert btnRoBrowserSource != null : "fx:id=\"btnRoBrowserSource\" was not injected: check your FXML file 'mainApp.fxml'.";
        assert txtOutputDirectoryReorganiser != null : "fx:id=\"txtOutputDirectoryReorganiser\" was not injected: check your FXML file 'mainApp.fxml'.";
        assert btnRoBrowserOutput != null : "fx:id=\"btnRoBrowserOutput\" was not injected: check your FXML file 'mainApp.fxml'.";
        assert btnReorganise != null : "fx:id=\"btnReorganise\" was not injected: check your FXML file 'mainApp.fxml'.";
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
        archiveTypeToggleGroup = new ToggleGroup();
        rbFaFormat7z.setToggleGroup(archiveTypeToggleGroup);
        rbFaFormatZIP.setToggleGroup(archiveTypeToggleGroup);

        //select default radio button
        rbFaFormatZIP.setSelected(true);

        //Archiver button-handler binding
        btnFaAddFile.setOnAction(btnFaAddFileHandler);
        btnFaRemoveSelected.setOnAction(btnFaRemoveSelectedHandler);
        btnFaRemoveAll.setOnAction(btnFaRemoveAllHandler);
        btnFaArchive.setOnAction(btnFaArchiveHandler);
        btnFaExtract.setOnAction(btnFaExtractHandler);
        btnFaDirectoryBrowse.setOnAction(btnFaDirectoryBrowserHandler);

        //Reorganiser button-handler binding //TODO bind all methods


        //populate file browser
        String hostName = null;
        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException x) {
            System.out.println(x.getMessage());
        }
        assert hostName != null : "Unable to get local host name.";
        TreeItem<String> rootNode = new TreeItem<>(hostName, new ImageView(windowsClient));
        Iterable<Path> rootDirectories = FileSystems.getDefault().getRootDirectories();
        for (Path name : rootDirectories) {
            ExplorerTreeItem treeNode = new ExplorerTreeItem(new File(name.toString()));
            rootNode.getChildren().add(treeNode);
        }
        rootNode.setExpanded(true);
        this.treeViewFileExplorer.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        this.treeViewFileExplorer.setRoot(rootNode);

        //set file list to multi-select
        this.listViewFiles.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        //set default output directory
//        this.txtOutputDirectoryArchiver.setText(System.getProperty("user.home"));
//        this.txtOutputDirectoryReorganiser.setText(System.getProperty("user.home"));
        this.txtOutputDirectoryArchiver.setText("E:\\test\\out"); //TODO delete after testing

        //set lock status
        unlock();

        //set footer msg to ready
        footerMessage.setText("Ready.");


    }//End initialize()

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

    //menuItem miAbout Handler
    EventHandler<ActionEvent> miAboutHanlder = (ActionEvent event) -> {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About File ReOrg");
        alert.setHeaderText("File ReOrg");
        alert.setContentText("This is a data organiser tool. It can help in reorganising data and archiving data.");
        alert.show();
    };

    //Close Event
    EventHandler<ActionEvent> closeButtonHandler = (ActionEvent event) -> {
        Stage stage = (Stage) titleBarPane.getScene().getWindow();
        closeApp(stage);
    };


    /**
     * MenuBar Event Handlers
     */
    //menuItem miArchiver Handler
    EventHandler<ActionEvent> miArchiveHandler = (ActionEvent event) -> {
        if (!ArchivePane.isVisible()) {
            ReorganisePane.setVisible(false);
            ArchivePane.setVisible(true);
        }
    };

    //menuItem miReOrganiser Handler
    EventHandler<ActionEvent> miReorganiseHandler = (ActionEvent event) -> {
        if (!ReorganisePane.isVisible()) {
            ArchivePane.setVisible(false);
            ReorganisePane.setVisible(true);
        }
    };


    /**
     * Archiver EventHandler
     */
    //add selected
    EventHandler<ActionEvent> btnFaAddFileHandler = (ActionEvent event) -> {
        ObservableList<Integer> indices = this.treeViewFileExplorer.getSelectionModel().getSelectedIndices();
        int count = 0;
        for (Integer i : indices) {
            int index = i.intValue();
            if (index > 0) {
                ExplorerTreeItem item = (ExplorerTreeItem) this.treeViewFileExplorer.getTreeItem(index);
                if (item.isDirectory()) {
                    footerMessage.setText("Directory can't be added in this release.");
                } else {
                    String absolutePath = item.getAbsolutePath();
                    if (this.listViewFiles.getItems().contains(absolutePath)) {
                        footerMessage.setText("Already added.");
                    } else {
                        this.listViewFiles.getItems().add(absolutePath);
                        count++;
                        footerMessage.setText(count + " file(s) added.");
                    }
                }
            }
        }
    };

    //remove selected
    EventHandler<ActionEvent> btnFaRemoveSelectedHandler = (ActionEvent event) -> {
        ObservableList<Integer> indices = this.listViewFiles.getSelectionModel().getSelectedIndices();
        if (indices.size() > 0) {
            int count = 0;
            for (Integer i : indices) {
                this.listViewFiles.getItems().remove(i.intValue());
                count++;
            }
            footerMessage.setText(count + " files(s) removed.");
        } else {
            footerMessage.setText("No File(s) to remove.");
        }
    };

    //remove all
    EventHandler<ActionEvent> btnFaRemoveAllHandler = (ActionEvent event) -> {
        this.listViewFiles.getItems().clear();
        footerMessage.setText("File list cleared.");
    };

    //BrowseDirectory
    EventHandler<ActionEvent> btnFaDirectoryBrowserHandler = (ActionEvent event) -> {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select output directory");
        Stage stage = (Stage) this.titleBarPane.getScene().getWindow();
        File f = directoryChooser.showDialog(stage);
        if (f != null) {
            this.txtOutputDirectoryArchiver.setText(f.getAbsolutePath());
            footerMessage.setText("Output Directory Selected.");
        }
    };

    //Archive
    EventHandler<ActionEvent> btnFaArchiveHandler = (ActionEvent event) -> {
        ObservableList<String> filesList = this.listViewFiles.getItems();
        if (processRunning) {
            footerMessage.setText("System Busy! Wait till status turns green.");
        } else {
            maxThreadCount = 0;
            threadCount = 0;
            lock();
            if (filesList.size() > 0) {
                String outputFilePath = txtOutputDirectoryArchiver.getText();
                if (new File(outputFilePath).isDirectory()) {
                    Archive archive = null;
                    try {
                        String archiveExtension;
                        String archiveType = ((RadioButton) archiveTypeToggleGroup.getSelectedToggle()).getText();
                        if (archiveType.equalsIgnoreCase("7ZIP")) {
                            archive = new SevenZipArchive();
                            archiveExtension = ".7z";
                        } else if (archiveType.equalsIgnoreCase("ZIP")) {
                            archive = new ZipArchive(ZipArchive.ZipOrJar.ZIP);
                            archiveExtension = ".zip";
                        } else {
                            throw (new Exception("Unsupported Archive Type!"));
                        }
                        for (String file : filesList) {
                            String fileNameWithOutExt = FilenameUtils.removeExtension(new File(file).getName());
                            String archiveFileName = outputFilePath + File.separator + fileNameWithOutExt + "_" + new Date().getTime() + archiveExtension;
                            archiveFile(file, archiveFileName, archive);
                        }
                    } catch (Exception e) {
//                        e.printStackTrace();
                        footerMessage.setText("Unsupported Archive Type!");
                    }
                } else {
                    System.out.println("Something wrong with output folder! Select different folder.");
                }
            } else {
                footerMessage.setText("No Files available to archive.");
            }
        }
    };

    public void archiveFile(String sourceFile, String destFile, Archive archive) {
        try {
            Thread t1 = new Thread(() -> {
                try {
                    lock();
//                    footerMessage.setText("Archiving("+threadCount+"/"+maxThreadCount+")");
                    threadCount++;
                    maxThreadCount++;
                    System.out.println("Actual Thread Count: " + java.lang.Thread.activeCount());
                    System.out.println("Thread Count: " + threadCount);
                    archive.archive(sourceFile, destFile);
                    threadCount--;
                    System.out.println("Archived (" + (maxThreadCount - threadCount) + "/" + maxThreadCount + ")");
                    if (threadCount == 0) {
                        unlock();
//                        this.footerMessage.setText("Archiving Process Completed.");
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            });
            t1.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //Extract
    EventHandler<ActionEvent> btnFaExtractHandler = (ActionEvent event) -> {
    };


    /**
     * Reorganizer EventHandler //TODO add event handlers
     */

    /**
     * Other utility methods
     */

    //Close Application
    void closeApp(Stage stage) {
        if (processRunning) {
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

    //lock process running
    void lock() {
        processRunning = true;
        footerLockStatus.setImage(processingImage);
    }

    //unlock process running
    void unlock() {
        processRunning = false;
        footerLockStatus.setImage(allOKImage);
    }

    //set footer status message
    synchronized void setFooterMessage(String msg) {
        footerMessage.setText(msg);
    }

}
