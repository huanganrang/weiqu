package rml.service;

import rml.model.Community;

import java.util.List;

/**
 * Created by Administrator on 2015/9/25.
 */
public interface CommunityServiceI {
    public Community createCommunity(Community channel);

    public Community getCommunityByToken(String token);

    public List<Community> getCommunities(Community community);

    Community getUserCommunity(int userId);

    public List<Community>  getALLCommunities(Community community);

    Community getCommunityById(int id);

    List<Community> getUserCommunities(Community community);

    List<Community> getUserCommunitiesTotal(Community community);

}
