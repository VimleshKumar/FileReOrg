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

import java.io.File;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ExplorerTreeItem  extends TreeItem<String> {

    public static Image folderCollapseImage=new Image(ClassLoader.getSystemResourceAsStream("fileReOrg/res/folder.png"),20,20,true,false);
    public static Image folderExpandImage=new Image(ClassLoader.getSystemResourceAsStream("fileReOrg/res/opened-folder.png"),20,20,true,false);
    public static Image fileImage=new Image(ClassLoader.getSystemResourceAsStream("fileReOrg/res/file-simple.png"),20,20,true,false);
    private boolean isLeaf;
    private boolean isFirstTimeChildren=true;
    private boolean isFirstTimeLeaf=true;
    private final File file;
    public File getFile(){return(this.file);}
    private final String absolutePath;
    public String getAbsolutePath(){return(this.absolutePath);}
    private final boolean isDirectory;
    public boolean isDirectory(){return(this.isDirectory);}


    public ExplorerTreeItem(File file){
        super(file.toString());
        this.file=file;
        this.absolutePath=file.getAbsolutePath();
        this.isDirectory=file.isDirectory();
        if(this.isDirectory){
            this.setGraphic(new ImageView(folderCollapseImage));
            //add event handlers
            this.addEventHandler(TreeItem.branchCollapsedEvent(),new EventHandler(){
                @Override
                public void handle(Event e){
                    ExplorerTreeItem source=(ExplorerTreeItem)e.getSource();
                    if(!source.isExpanded()){
                        ImageView iv=(ImageView)source.getGraphic();
                        iv.setImage(folderCollapseImage);
                    }
                }
            } );
            this.addEventHandler(TreeItem.branchExpandedEvent(),new EventHandler(){
                @Override
                public void handle(Event e){
                    ExplorerTreeItem source=(ExplorerTreeItem)e.getSource();
                    if(source.isExpanded()){
                        ImageView iv=(ImageView)source.getGraphic();
                        iv.setImage(folderExpandImage);
                    }
                }
            } );
        }else{
            this.setGraphic(new ImageView(fileImage));
        }
        //set the value (which is what is displayed in the tree)
        String fullPath=file.getAbsolutePath();
        if(!fullPath.endsWith(File.separator)){
            String value=file.toString();
            int indexOf=value.lastIndexOf(File.separator);
            if(indexOf>0){
                this.setValue(value.substring(indexOf+1));
            }else{
                this.setValue(value);
            }
        }
    }

    @Override
    public ObservableList<TreeItem<String>> getChildren(){
        if(isFirstTimeChildren){
            isFirstTimeChildren=false;
            super.getChildren().setAll(buildChildren(this));
        }
        return(super.getChildren());
    }

    @Override
    public boolean isLeaf(){
        if(isFirstTimeLeaf){
            isFirstTimeLeaf=false;
            isLeaf=this.file.isFile();
        }
        return(isLeaf);
    }

    private ObservableList<ExplorerTreeItem> buildChildren(ExplorerTreeItem treeItem){
        File f=treeItem.getFile();
        if((f!=null)&&(f.isDirectory())){
            File[] files=f.listFiles();
            if (files!=null){
                ObservableList<ExplorerTreeItem> children=FXCollections.observableArrayList();
                for(File childFile:files){
                    children.add(new ExplorerTreeItem(childFile));
                }
                return(children);
            }
        }
        return FXCollections.emptyObservableList();
    }
}
