package rml.service;

import rml.model.Channel;
import rml.model.Community;

import java.util.List;

/**
 * Created by Administrator on 2015/9/25.
 */
public interface ChannelServiceI {
    public Channel createChannel(Channel channel);

    int updateChannelUser(int id);

    int minusChannelUser(int id);

    List<Channel> getChannelPage(Channel channel);

    int count();

    Channel getChannel(String channelToken);

    List<Channel> getUserChannel(Channel channel);

    int deleteUser(Integer id);

    List<Channel> getUserChannelTotal(Channel channel);
}
