package rml.service;

import rml.model.File;

/**
 * Created by Administrator on 2015/10/22 0022.
 */
public interface FileServiceI {
    File getFileByToken(String token);

    int createFile(File file);
}
