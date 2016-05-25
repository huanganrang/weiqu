package rml.service;

import rml.model.RecordClient;

import java.util.List;

/**
 * Created by Administrator on 2015/10/6 0006.
 */
public interface RecordClientServiceI {

    public void recordClient(RecordClient recordClient);

    public List<RecordClient> getAllClients(RecordClient recordClient);


}
