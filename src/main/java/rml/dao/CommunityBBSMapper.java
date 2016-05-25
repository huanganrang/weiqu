package rml.dao;

import rml.model.CommunityBBS;

import java.util.List;

/**
 * Created by Administrator on 2015/10/11 0011.
 */
public interface CommunityBBSMapper {

  List<CommunityBBS> getCommunityBlogs(CommunityBBS communityBBS);

  List<CommunityBBS> getBlogs(CommunityBBS communityBBS);

  List<CommunityBBS> getCommunityBlogsTotal(CommunityBBS communityBBS);

  int createBlog(CommunityBBS communityBBS);

  CommunityBBS getCommunityBbs(String token);

  int updateCommunityBlogs(CommunityBBS communityBBS);

  CommunityBBS getBbsByToken(String token);

  int top(CommunityBBS communityBBS);

  int topCancel(CommunityBBS communityBBS);



}
