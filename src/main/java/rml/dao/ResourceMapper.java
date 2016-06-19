package rml.dao;

import rml.model.Resource;

import java.util.List;

/**
 * Created by Jianghui on 2016/6/19.
 */
public interface ResourceMapper {
    List<Resource> findResourcesByHouseToken(String token);
    int insert(Resource resource);
    int delete(String id);
    int update(Resource resource);
 }
