package com.wangfeixixi.logx.ui;

import com.wangfeixixi.logx.LogXConfig;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XixiFileUtils {

    public static List<File> getFiles() {
        return getFiles(LogXConfig.getDirpath());
    }

    /**
     * 读取某个文件夹下的所有文件
     */
    public static List<File> getFiles(String filepath) {
        ArrayList<File> files = new ArrayList<>();
        File file = new File(filepath);
        if (file.isDirectory()) {
            //"文件夹"
            String[] names = file.list();
            for (String name : names) {
                File readfile = new File(filepath + File.separator + name);
                if (readfile.isDirectory()) {
                    files.addAll(getFiles(readfile.getAbsolutePath()));
                } else {
                    files.add(readfile);
                }
            }
        }
        return files;
    }

    public static List<String> getFileContent(File file) {
        List<String> logs = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String line = "";
        try {
            while ((line = br.readLine()) != null) {
                //读取到的内容给line变量
                logs.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return logs;
    }
}
