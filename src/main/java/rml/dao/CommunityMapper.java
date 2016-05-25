package rml.dao;

import rml.model.Channel;
import rml.model.Community;

import java.util.List;

/**
 * Created by Administrator on 2015/9/25.
 */
public interface CommunityMapper {

    public int createCommunity(Community community);

    public Community getCommunity(int id);

    public Community getCommunityByToken(String token);

    public int updateCommunity(Community community);

    public List<Community> getCommunities(Community community);

    Community getUserCommunity(int userId);

    public List<Community>  getALLCommunities(Community community);

    Community getCommunityById(int id);

    List<Community> getUserCommunities(Community community);

    List<Community> getUserCommunitiesTotal(Community community);
}
