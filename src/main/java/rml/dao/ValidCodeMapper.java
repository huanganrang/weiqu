package rml.dao;

import rml.model.ValidCode;

public interface ValidCodeMapper {

    ValidCode checkValidCode(ValidCode code);

    int insert(ValidCode record);

}