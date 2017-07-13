package com.dels.service.index;

import com.dels.model.index.Accomment;

import java.util.List;

/**
 * Created by MaytoMarry on 2017/7/12.
 */
public interface IAccommentService {

    List<Accomment> getDelAccBAcId(String acid);

}
