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

import SevenZip.Compression.LZMA.Decoder;
import SevenZip.Compression.LZMA.Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class SevenZipArchive implements Archive {

    /**
     *
     * @param sourceFilePath
     * @param archiveFileName
     * @return
     * @throws IOException
     */
    @Override
    public boolean archive(String sourceFilePath, String archiveFileName) throws IOException {
        boolean success = false;
        File inputFile = new File(sourceFilePath);
        FileInputStream fin = null;
        FileOutputStream szout = null;
        try {
            fin = new FileInputStream(new File(sourceFilePath));
            szout = new FileOutputStream(new File(archiveFileName));
            Encoder encoder = new Encoder();
            encoder.WriteCoderProperties(szout);
            long fileSize;
            boolean endOfStream = false;
            if (endOfStream) {
                fileSize = -1;
            } else {
                fileSize = inputFile.length();
            }
            for (int i = 0; i < 8; i++) {
                szout.write((int) (fileSize >>> (8 * i)) & 0xFF);
            }
            encoder.Code(fin, szout, -1, -1, null);
            szout.flush();
            success = true;
        } catch (Exception x) {
            throw (x);
        } finally {
            try {
                if (szout != null) {
                    szout.close();
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


    /**
     *
     * @param archiveFileName
     * @param destinationFilePath
     * @return
     * @throws IOException
     */
    @Override
    public boolean extract(String archiveFileName, String destinationFilePath) throws IOException {
        boolean success = false;
        FileInputStream szin = null;
        FileOutputStream fout = null;
        try {
            int propertiesSize = 5;
            byte[] properties = new byte[propertiesSize];
            szin = new FileInputStream(new File(archiveFileName));
            fout = new FileOutputStream(new File(destinationFilePath));
            if (szin.read(properties, 0, propertiesSize) != propertiesSize) {
                throw (new Exception("input .lzma file is too short"));
            }
            Decoder decoder = new Decoder();
            if (!decoder.SetDecoderProperties(properties)) {
                throw (new Exception("Incorrect stream properties"));
            }
            long outSize = 0;
            for (int i = 0; i < 8; i++) {
                int v = szin.read();
                if (v < 0) {
                    throw (new Exception("Can't read stream size"));
                }
                outSize |= ((long) v) << (8 * i);
            }
            if (!decoder.Code(szin, fout, outSize)) {
                throw new Exception("Error in data stream");
            }
            success = true;
        } catch (Exception x) {
            try {
                throw (x);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            try {
                if (fout != null) {
                    fout.close();
                }
            } catch (Exception x) {
            }
            try {
                if (szin != null) {
                    szin.close();
                }
            } catch (Exception x) {
            }
        }
        return (success);
    }
}
