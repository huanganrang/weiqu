package rml.dao;

import rml.model.CommunityMember;

/**
 * Created by edward on 2016/1/10 0010.
 */
public interface CommunityMemberMapper {
    CommunityMember getCommunityMembers(int communityId);

    int createMember(CommunityMember communityMember);

    CommunityMember getCommunityMember(CommunityMember communityMember);
}
