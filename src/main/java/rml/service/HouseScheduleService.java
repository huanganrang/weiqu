package rml.service;

import rml.model.HouseSchedule;

import java.util.List;

/**
 * Created by edward-echo on 2016/3/31.
 */
public interface HouseScheduleService {
    int createSchedule(HouseSchedule schedule);
    List<HouseSchedule> getHouseSchedules(int houseId);
    void deleteScheduls(int houseId);
}
