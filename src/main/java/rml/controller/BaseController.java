package rml.controller;

import rml.util.ReturnJson;

/**
 * Created by john on 16/5/26.
 */
public class BaseController implements Action {
    protected ReturnJson newFailure() {
        ReturnJson returnJson = new ReturnJson();
        returnJson.setServerStatus(1);
        return returnJson;
    }

    protected ReturnJson newFailure(String msg) {
        ReturnJson returnJson = new ReturnJson();
        returnJson.setServerStatus(1);
        returnJson.setReturnMessage(msg);
        return returnJson;
    }

    protected ReturnJson newSuccess() {
        ReturnJson returnJson = new ReturnJson();
        return returnJson;
    }
}
