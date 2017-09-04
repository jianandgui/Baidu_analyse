package cn.edu.swpu.cins.event.analyse.platform.service.impl;

import cn.edu.swpu.cins.event.analyse.platform.dao.SpecialPostDao;
import cn.edu.swpu.cins.event.analyse.platform.exception.BaseException;
import cn.edu.swpu.cins.event.analyse.platform.exception.NoEventException;
import cn.edu.swpu.cins.event.analyse.platform.exception.OperationFailureException;
import cn.edu.swpu.cins.event.analyse.platform.model.persistence.SpecialPost;
import cn.edu.swpu.cins.event.analyse.platform.service.SpecialPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecialPostServiceImpl implements SpecialPostService {

    @Autowired
    private SpecialPostDao specialPostDao;

    @Override
    public int addSpecialPost(SpecialPost specialPost) throws BaseException {
            String name = specialPost.getName();
            List<String> url = specialPost.getUrl();
            if(name ==null ||name== " "||url.isEmpty() ){
                throw new IllegalArgumentException();
            }

            try{
                int num = specialPostDao.insertSpecialPost(specialPost);
                if(num != 1){
                    throw new OperationFailureException();
                }
                return num;
            }catch (Exception e){
                throw e;
            }


    }

    @Override
    public int deleteSpecialPostByIds(List<Integer> ids) throws BaseException {
        try{
            if(ids.isEmpty()){
                throw new IllegalArgumentException();
            }
            int num = ids.size();
            if(num!=specialPostDao.deleteByIds(ids)){
                throw new OperationFailureException();
            }
            return num;
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public List<SpecialPost> querySpecialPosts() throws BaseException {
        try{
            List<SpecialPost> specialPosts = specialPostDao.selectAllSpecialPost();
            if(specialPosts.isEmpty()){
                throw new NoEventException();
            }
            return specialPosts;
        }catch (Exception e){
            throw e;
        }
    }
}
