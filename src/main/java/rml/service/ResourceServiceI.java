package rml.service;

import rml.model.Resource;

import java.util.List;

/**
 * Created by Jianghui on 2016/6/19.
 */
public interface ResourceServiceI {
    List<Resource> findResourcesByHouseToken(String token);
}
