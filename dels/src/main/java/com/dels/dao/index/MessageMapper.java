package com.dels.dao.index;

import com.dels.model.index.Message;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by MaytoMarry on 2017/7/12.
 */
public interface MessageMapper {

    List<Message> selectContents();

    void insertMessage(@Param("content") String content);
}
