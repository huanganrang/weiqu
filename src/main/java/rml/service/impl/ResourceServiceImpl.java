package rml.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rml.dao.ResourceMapper;
import rml.model.Resource;
import rml.service.ResourceServiceI;

import java.util.List;

/**
 * Created by Jianghui on 2016/6/19.
 */
@Service("resourceService")
public class ResourceServiceImpl implements ResourceServiceI {
    @Autowired
    ResourceMapper resourceMapper;
    @Override
    public List<Resource> findResourcesByHouseToken(String token) {
        return resourceMapper.findResourcesByHouseToken(token);
    }
}
