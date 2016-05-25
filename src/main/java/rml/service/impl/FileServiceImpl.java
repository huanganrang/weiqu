package rml.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rml.dao.FileMapper;
import rml.model.File;
import rml.service.FileServiceI;

/**
 * Created by Administrator on 2015/10/22 0022.
 */
@Service("fileService")
public class FileServiceImpl implements FileServiceI {

    @Autowired
    FileMapper fileMapper;

    @Override
    public File getFileByToken(String token) {
        return null;
    }

    @Override
    public int createFile(File file) {
        return fileMapper.createFile(file);
    }
}
