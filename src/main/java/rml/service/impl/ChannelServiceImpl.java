package rml.service.impl;

import org.codehaus.jackson.map.deser.ValueInstantiators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rml.dao.ChannelMapper;
import rml.model.Channel;
import rml.service.ChannelServiceI;
import rml.util.RandomGUID;

import java.util.List;

/**
 * Created by Administrator on 2015/9/25.
 */
@Service("channelService")
public class ChannelServiceImpl implements ChannelServiceI {

    private String BaseUrl = "http://www.weiqu168.com/channel/";
    @Autowired
    ChannelMapper channelMapper;

    @Override
    public Channel createChannel(Channel channel) {
        RandomGUID guid = new RandomGUID(true);
        channel.setToken(guid.valueAfterMD5);
        channelMapper.createChannel(channel);
        Channel channel1 = channelMapper.getChannel(channel.getToken());
        channel1.setUrl(BaseUrl + new Integer(channel1.getId()).toString());
        channelMapper.updateChannel(channel1);
        return channel1;
    }

    public int updateChannelUser(int id){
      return  channelMapper.updateChannelUser(id);
    }

    @Override
    public int minusChannelUser(int id) {
        return channelMapper.minusChannelUser(id);
    }

    @Override
    public List<Channel> getChannelPage(Channel channel) {
        return channelMapper.getChannelPage(channel);
    }

    @Override
    public int count() {
        return channelMapper.count();
    }

    @Override
    public Channel getChannel(String channelToken) {
        return channelMapper.getChannel(channelToken);
    }

    @Override
    public List<Channel> getUserChannel(Channel channel) {
        return channelMapper.getUserChannel(channel);
    }

    @Override
    public int deleteUser(Integer id) {
        return channelMapper.deleteUser(id);
    }

    @Override
    public List<Channel> getUserChannelTotal(Channel channel) {
        return channelMapper.getUserChannelTotal(channel);
    }

    @Override
    public int updateChannel(Channel channel) {
        return channelMapper.updateChannelMode(channel);
    }

}
