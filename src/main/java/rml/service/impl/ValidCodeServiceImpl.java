package rml.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rml.dao.ValidCodeMapper;
import rml.model.ValidCode;
import rml.service.ValidCodeService;

import java.util.Date;

/**
 * Created by Administrator on 2015/9/13.
 */

@Service("validCodeService")
public class ValidCodeServiceImpl implements ValidCodeService {

    @Autowired
    ValidCodeMapper  validCodeMapper;

    @Override
    public int checkValidCode(ValidCode code) {
        ValidCode result = validCodeMapper.checkValidCode(code);
        if(result!=null){
            return 0;
        }
        return 1;
    }

    @Override
    public int insert(ValidCode record) {
        record.setCreateTime(new Date());
        validCodeMapper.insert(record);
        return 0;
    }
}
