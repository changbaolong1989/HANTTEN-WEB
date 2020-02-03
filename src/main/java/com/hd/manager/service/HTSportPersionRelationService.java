package com.hd.manager.service;

import com.hd.manager.vo.TbTaskSpotPersonRelationVO;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

public interface HTSportPersionRelationService {

    Map<String, Object> queryAll(TbTaskSpotPersonRelationVO paramVO);
    void add(TbTaskSpotPersonRelationVO tbTaskSpotPersonRelationVO);
    void updateLeaveTime(@RequestBody TbTaskSpotPersonRelationVO paramVO);
}
