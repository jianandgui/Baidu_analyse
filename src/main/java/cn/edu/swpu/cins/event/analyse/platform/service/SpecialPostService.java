package cn.edu.swpu.cins.event.analyse.platform.service;

import cn.edu.swpu.cins.event.analyse.platform.exception.BaseException;
import cn.edu.swpu.cins.event.analyse.platform.model.persistence.SpecialPost;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;

import java.util.List;

public interface SpecialPostService {

    int addSpecialPost(SpecialPost specialPost) throws BaseException;

    int deleteSpecialPostByIds(List<Integer> ids) throws BaseException;

    List<SpecialPost> querySpecialPosts() throws BaseException;

}
