package rml.controller;

import rml.util.ReturnJson;

/**
 * Created by john on 16/5/26.
 */
public interface Action {
    ReturnJson ERROR = new ReturnJson(500,"服务器异常",2);
    ReturnJson SUCCESS = new ReturnJson(200,"服务器异常",1);
}
