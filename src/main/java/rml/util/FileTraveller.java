package rml.util;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by edward-echo on 2016/5/5.
 */
public class FileTraveller {
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String path ="I:\\doc";
        List<File> files =traverseFolder(path);
        for(File file:files){
            System.err.println(file.getAbsolutePath());
        }
    }
    public static List<File> traverseFolder(String path) {
        int fileNum = 0, folderNum = 0;
        File file = new File(path);
        LinkedList<File> list = new LinkedList<File>();
        if (file.exists()) {
            File[] files = file.listFiles();
            for (File file2 : files) {
                if (file2.isDirectory()) {
                    fileNum++;
                } else {
                    list.add(file2);
                }
            }
        } else {
        }
        return list;
    }
}
