package cn.edu.swpu.cins.event.analyse.platform.service;

import cn.edu.swpu.cins.event.analyse.platform.exception.NoEventException;
import cn.edu.swpu.cins.event.analyse.platform.model.persistence.SpecialPostEvent;

import java.util.List;

public interface SpecialPostEventService {

    List<SpecialPostEvent> queryAll(List<Integer> ids) throws NoEventException;
}
