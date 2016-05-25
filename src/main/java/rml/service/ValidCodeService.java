package rml.service;

import rml.model.ValidCode;

/**
 * Created by Administrator on 2015/9/13.
 */
public interface ValidCodeService {

    int checkValidCode(ValidCode code);

    int insert(ValidCode record);

}
