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

package fileReOrg.beans;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FileDetailBean {
    StringProperty fileName = new SimpleStringProperty();
    StringProperty fileSourcePath = new SimpleStringProperty();
    StringProperty fileDestinationPath = new SimpleStringProperty();
    StringProperty fileType = new SimpleStringProperty();
    StringProperty mimeType = new SimpleStringProperty();

    public FileDetailBean() {
    }

    public FileDetailBean(String fileName, String fileSourcePath, String fileDestinationPath, String fileType, String mimeType) {
        this.fileName.set(fileName);
        this.fileSourcePath.set(fileSourcePath);
        this.fileDestinationPath.set(fileDestinationPath);
        this.fileType.set(fileType);
        this.mimeType.set(mimeType);
    }

    public String getFileName() {
        return fileName.get();
    }

    public void setFileName(String value) {
        this.fileName.set(value);
    }

    public StringProperty fileNameProperty() {
        return fileName;
    }

    public String getFileSourcePath() {
        return fileSourcePath.get();
    }

    public void setFileSourcePath(String fileSourcePath) {
        this.fileSourcePath.set(fileSourcePath);
    }

    public StringProperty fileSourcePathProperty() {
        return fileSourcePath;
    }

    public String getFileDestinationPath() {
        return fileDestinationPath.get();
    }

    public void setFileDestinationPath(String fileDestinationPath) {
        this.fileDestinationPath.set(fileDestinationPath);
    }

    public StringProperty fileDestinationPathProperty() {
        return fileDestinationPath;
    }

    public String getFileType() {
        return fileType.get();
    }

    public void setFileType(String fileType) {
        this.fileType.set(fileType);
    }

    public StringProperty fileTypeProperty() {
        return fileType;
    }

    public String getMimeType() {
        return mimeType.get();
    }

    public void setMimeType(String mimeType) {
        this.mimeType.set(mimeType);
    }

    public StringProperty mimeTypeProperty() {
        return mimeType;
    }

    @Override
    public String toString() {
        return "FileBean{" +
                "fileName=" + fileName +
                ", fileSourcePath=" + fileSourcePath +
                ", fileDestinationPath=" + fileDestinationPath +
                ", fileType=" + fileType +
                '}';
    }
}
