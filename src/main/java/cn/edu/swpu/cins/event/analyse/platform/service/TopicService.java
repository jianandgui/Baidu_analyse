package cn.edu.swpu.cins.event.analyse.platform.service;

import cn.edu.swpu.cins.event.analyse.platform.exception.BaseException;
import cn.edu.swpu.cins.event.analyse.platform.model.persistence.Topic;

import java.util.List;

public interface TopicService {
    /**
     * 添加专题接口
     *
     * @param topic
     * @return
     * @throws BaseException
     */
    int addTopic(Topic topic) throws BaseException;

    /**
     * 获取专题列表接口
     *
     * @return
     * @throws BaseException
     */
    List<Topic> getTopics() throws BaseException;

    /**
     * 批量删除专题接口
     *
     * @param ids
     * @return
     * @throws BaseException
     */
    int deleteTopics(List<Integer> ids) throws BaseException;

}
