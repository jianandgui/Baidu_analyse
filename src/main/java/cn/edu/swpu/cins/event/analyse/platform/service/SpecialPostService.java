package cn.edu.swpu.cins.event.analyse.platform.service;

import cn.edu.swpu.cins.event.analyse.platform.exception.BaseException;
import cn.edu.swpu.cins.event.analyse.platform.exception.OperationFailureException;
import cn.edu.swpu.cins.event.analyse.platform.model.persistence.SpecialPost;

import java.util.List;

public interface SpecialPostService {

    int addSpecialPost(SpecialPost specialPost) throws BaseException;

    int deleteSpecialPostByIds(List<Integer> ids) throws BaseException;

    List<SpecialPost> querySpecialPosts() throws BaseException;

    int updateById(Integer id,List<String> urls) throws OperationFailureException;

}
