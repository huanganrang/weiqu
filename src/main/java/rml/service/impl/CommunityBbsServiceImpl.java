package rml.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rml.dao.CommunityBBSMapper;
import rml.model.CommunityBBS;
import rml.service.CommunityBbsServiceI;
import rml.util.RandomGUID;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


/**
 * Created by Administrator on 2015/10/11 0011.
 */

@Service("communityBbsService")
public class CommunityBbsServiceImpl implements CommunityBbsServiceI {

    @Autowired
    CommunityBBSMapper communityBBSMapper;

    private String baseUrl = "http://www.weiqu168.com/community/bbs/";

    @Override
    public int createBlog(CommunityBBS communityBBS) {
        communityBBS.setToken(UUID.randomUUID().toString());
        communityBBS.setCreateTime(new Date());
        return communityBBSMapper.createBlog(communityBBS);
    }

    @Override
    public int updateCommunityBlogs(CommunityBBS communityBBS) {
        return  communityBBSMapper.updateCommunityBlogs(communityBBS);
    }

    @Override
    public List<CommunityBBS> getCommunityBlogs(CommunityBBS communityBBS) {
        List<CommunityBBS> values = communityBBSMapper.getCommunityBlogs(communityBBS);
        return values;
    }

    @Override
    public List<CommunityBBS> getBlogs(CommunityBBS communityBBS) {
        return communityBBSMapper.getBlogs(communityBBS);
    }

    @Override
    public List<CommunityBBS> getCommunityBlogsTotal(CommunityBBS communityBBS) {
        return communityBBSMapper.getCommunityBlogsTotal(communityBBS);
    }

    @Override
    public CommunityBBS getBbsByToken(String token) {
        CommunityBBS communityBBS =  communityBBSMapper.getCommunityBbs(token);
        return communityBBS;
    }

    @Override
    public int top(CommunityBBS communityBBS) {
        return communityBBSMapper.top(communityBBS);
    }

    @Override
    public int topCancel(CommunityBBS communityBBS) {
        return communityBBSMapper.topCancel(communityBBS);
    }


}
