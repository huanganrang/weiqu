package rml.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rml.dao.HouseScheduleMapper;
import rml.model.HouseSchedule;
import rml.service.HouseScheduleService;

import java.util.List;

/**
 * Created by edward-echo on 2016/3/31.
 */
@Service("houseScheduleService")
public class HouseScheduleServiceImpl implements HouseScheduleService {

    @Autowired
    private HouseScheduleMapper houseScheduleMapper;

    @Override
    public int createSchedule(HouseSchedule schedule) {
        return houseScheduleMapper.createSchedule(schedule);
    }

    @Override
    public List<HouseSchedule> getHouseSchedules(int houseId) {
        return houseScheduleMapper.getHouseSchedules(houseId);
    }

    @Override
    public void deleteScheduls(int houseId) {
        houseScheduleMapper.deleteScheduls(houseId);
    }
}
