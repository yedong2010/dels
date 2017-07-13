package com.dels.service.impl.index;

import com.dels.dao.index.AccommentMapper;
import com.dels.model.index.Accomment;
import com.dels.service.index.IAccommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by MaytoMarry on 2017/7/12.
 */
@Service
public class AccommentService  implements IAccommentService{

    @Autowired
    AccommentMapper acDao;

    @Override
    public List<Accomment> getDelAccBAcId(String acid) {
        return acDao.selectByAcId(acid);
    }
}
