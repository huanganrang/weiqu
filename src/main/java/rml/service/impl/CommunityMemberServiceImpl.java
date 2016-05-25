package rml.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rml.dao.CommunityMemberMapper;
import rml.model.CommunityMember;
import rml.service.CommunityMemberService;

/**
 * Created by edward on 2016/1/10 0010.
 */
@Service("communityMemberService")
public class CommunityMemberServiceImpl implements CommunityMemberService{

    @Autowired
    CommunityMemberMapper communityMemberMapper;

    @Override
    public CommunityMember getCommunityMembers(int communityId) {
        return communityMemberMapper.getCommunityMembers(communityId);
    }

    @Override
    public int createMember(CommunityMember communityMember) {
        return communityMemberMapper.createMember(communityMember);
    }

    @Override
    public CommunityMember getCommunityMember(CommunityMember communityMember) {
        return communityMemberMapper.getCommunityMember(communityMember);
    }
}
