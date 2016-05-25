package rml.service;

import rml.model.HouseChat;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2015/9/29.
 */
public interface HouseChatServiceI {

    public void pushChat(HouseChat chat);

    public List<HouseChat> pullChat(HouseChat chat);

}
