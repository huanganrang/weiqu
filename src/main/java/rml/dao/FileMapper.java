package rml.dao;

import rml.model.File;

/**
 * Created by Administrator on 2015/10/22 0022.
 */
public interface FileMapper {
     File getFileByToken(String token);

    int createFile(File file);
}
