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

package fileReOrg.services.archive;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipArchive implements Archive {
    private final ZipOrJar archiveType;

    public ZipArchive(ZipOrJar archiveType) {
        this.archiveType = archiveType;
    }

    @Override
    public boolean archive(String sourceFileName, String archiveFileName) {
        boolean success = false;
        ZipOutputStream zout = null;
        FileInputStream fin = null;
        try {
            if (this.archiveType == ZipOrJar.JAR) {
                zout = new JarOutputStream(new FileOutputStream(new File(archiveFileName)));
            } else {
                zout = new ZipOutputStream(new FileOutputStream(new File(archiveFileName)));
            }
            zout.setMethod(ZipOutputStream.DEFLATED);
            File inputFile = new File(sourceFileName);
            byte[] buffer = new byte[BUFFER_LIMIT];
            /* generate CRC */
            CRC32 crc = new CRC32();
            fin = new FileInputStream(inputFile);
            int availableBytes = fin.available();
            int length;
            int bytesRead = 0;
            int lastPercentComplete = -1;
            while ((length = fin.read(buffer)) > -1) {
                crc.update(buffer, 0, length);
            }
            fin.close();
            //create zip entry
            ZipEntry entry = new ZipEntry(sourceFileName.substring(sourceFileName.lastIndexOf(File.separator) + File.separator.length()));
            entry.setSize(inputFile.length());
            entry.setTime(inputFile.lastModified());
            entry.setCrc(crc.getValue());
            zout.putNextEntry(entry);
            fin = new FileInputStream(inputFile);
            /* write entry to zip file */
            while ((length = fin.read(buffer)) > -1) {
                zout.write(buffer, 0, length);
            }
            success = true;
        } catch (Exception x) {
            System.out.println(x.getMessage());
        } finally {
            try {
                if (zout != null) {
                    zout.closeEntry();
                    zout.close();
                }
            } catch (Exception x) {
            }
            try {
                if (fin != null) {
                    fin.close();
                }
            } catch (Exception x) {
            }
        }
        return (success);
    }

    @Override
    public boolean extract(String archiveFileName, String destinationFileName) {
        boolean success = false;
        ZipInputStream zin = null;
        FileOutputStream fout = null;
        try {
            if (this.archiveType == ZipOrJar.JAR) {
                zin = new JarInputStream(new FileInputStream(new File(archiveFileName)));
            } else {
                zin = new ZipInputStream(new FileInputStream(new File(archiveFileName)));
            }
            ZipEntry entry = zin.getNextEntry();
            if (entry == null) {
                throw (new Exception("No entry in archive " + archiveFileName));
            } else {
                byte[] inBuffer = new byte[BUFFER_LIMIT];
                int inLength;
                fout = new FileOutputStream(new File(destinationFileName));
                while ((inLength = zin.read(inBuffer)) > -1) {
                    fout.write(inBuffer, 0, inLength);
                }
            }
            success = true;
        } catch (Exception x) {
            System.out.println(x.getMessage());;
        } finally {
            try {
                if (fout != null) {
                    fout.close();
                }
            } catch (Exception x) {
            }
            try {
                if (zin != null) {
                    zin.closeEntry();
                    zin.close();
                }
            } catch (Exception x) {
            }
        }
        return (success);
    }

    public enum ZipOrJar {ZIP, JAR}

}
