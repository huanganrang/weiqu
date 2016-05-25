package rml.dao;

import org.springframework.stereotype.Component;
import rml.model.ChannelCategory;

import java.util.List;

/**
 * Created by Administrator on 2015/9/24.
 */
@Component
public interface CategoryMapper {
    public List<ChannelCategory> getChannelCategory();
}
