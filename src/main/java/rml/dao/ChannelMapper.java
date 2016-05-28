package rml.dao;

import rml.model.Channel;

import java.util.List;

/**
 * Created by Administrator on 2015/9/25.
 */
public interface ChannelMapper {

    int createChannel(Channel channel);

    Channel getChannel(String token);

    int updateChannel(Channel channel);

    int updateChannelMode(Channel channel);

    List<Channel> getUserChannel(Channel channel);

    List<Channel> getUserChannelTotal(Channel channel);

    int deleteUser(Integer id);

    int updateChannelUser(Integer id);

    int minusChannelUser(Integer id);


    List<Channel> getChannelPage(Channel channel);

    int count();

}
