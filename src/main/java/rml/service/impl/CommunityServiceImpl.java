package rml.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rml.dao.ChannelMapper;
import rml.dao.CommunityMapper;
import rml.model.Channel;
import rml.model.Community;
import rml.service.ChannelServiceI;
import rml.service.CommunityServiceI;
import rml.util.RandomGUID;

import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2015/9/25.
 */
@Service("communityService")
public class CommunityServiceImpl implements CommunityServiceI {

    private String BaseUrl = "http://www.weiqu168.com/community/";
    @Autowired
    CommunityMapper communityMapper;

    @Override
    public Community createCommunity(Community community) {
        community.setToken(UUID.randomUUID().toString());
        communityMapper.createCommunity(community);
        return community;
    }

    @Override
    public Community getCommunityByToken(String token) {
        return communityMapper.getCommunityByToken(token);
    }

    @Override
    public List<Community> getCommunities(Community community) {
        return communityMapper.getCommunities(community);
    }

    @Override
    public Community getUserCommunity(int userId) {
        return communityMapper.getUserCommunity(userId );
    }

    @Override
    public List<Community> getALLCommunities(Community community) {
        return communityMapper.getALLCommunities(community);
    }

    @Override
    public Community getCommunityById(int id) {
        return communityMapper.getCommunityById(id);
    }

    @Override
    public List<Community> getUserCommunities(Community community) {
        return communityMapper.getUserCommunities(community);
    }

    @Override
    public List<Community> getUserCommunitiesTotal(Community community) {
        return communityMapper.getUserCommunitiesTotal(community);
    }

}
