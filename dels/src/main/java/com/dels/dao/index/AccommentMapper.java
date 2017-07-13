package com.dels.dao.index;

import com.dels.model.index.Accomment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by MaytoMarry on 2017/7/12.
 */
public interface AccommentMapper {

    List<Accomment> selectByAcId(@Param("acid") String acid);
}
