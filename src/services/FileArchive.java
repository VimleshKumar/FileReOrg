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

package services;

import java.io.File;
import java.util.List;

/**
 * <p>
 * {@link FileArchive}
 * <p>
 * provides methods for file compression and decompression.<br>
 * Use appropriate library wherever it needs.<br>
 * Should be able to support zip, 7z and rar formats.
 *
 * @version 1.0.0
 * @see ui.FROAppController
 */

public class FileArchive {


    /**
     *
     * @param inFilesToCompress List of files to compress.
     * @param outCompressFile After compression zip file saved to this location.
     * @return status message of method call
     */
    public String compressFilesToZIP(List<File> inFilesToCompress, File outCompressFile) {
        String status = "Something went wrong.";
        //list files
        //verify
        //compress
        //verify compressed file
        //return
        return status;
    }

    /**
     *
     * @param inCompressedZipFile Zip File to decompress.
     * @param outDecompressInDirectory Directory to save decompressed files.
     * @return
     */
    public File deCompressFilesToZIP(File inCompressedZipFile, File outDecompressInDirectory) {

        return null;
    }

}
