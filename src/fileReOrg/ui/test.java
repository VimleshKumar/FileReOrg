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

import fileReOrg.beans.FileDetailBean;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class test {
    public static void main(String[] args) {
//        File file = new File("E:\\test");
//        List<File> files = listFiles(file);
//        for (File f: files){
//            System.out.println(f.getAbsolutePath());
//        }
//    }
//
//    static List<File> listFiles(File file){
//        List<File> files = new ArrayList<>();
//        if(file.isFile()){
//            files.add(file);
//        } else if(file.isDirectory()){
//            File[] fs = file.listFiles();
//            for (File f: fs){
//                files.addAll(listFiles(f));
//            }
//        }
//        return files;
//        try {
//            File f = new File("E:\\mt.txt");
////            FileReader fileReader = new FileReader(f.getAbsoluteFile());
//            FileWriter fileWriter = new FileWriter("E:\\mt1.txt");
//            List<String> lines = Files.readAllLines(f.toPath());
//            for(String l : lines){
//                String s = l.substring(0,l.indexOf("="));
//                String ty = l.substring(l.indexOf("=") + 1, l.length());
//                System.out.println(ty + "=" + s);
//                fileWriter.append("\n"+ty + "=" + s);
//            }
//            fileWriter.close();
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Scanner scanner = new Scanner();
//        System.out.println("@application/epub+zip");
        String suffix = null;
        suffix = suffix.replaceAll("[^a-zA-Z0-9]", ""); //special chars cleanup
    }
}
