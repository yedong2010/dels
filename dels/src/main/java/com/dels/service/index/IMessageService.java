package com.dels.service.index;

import com.dels.model.index.Accomment;
import com.dels.model.index.Message;

import java.util.List;

/**
 * Created by MaytoMarry on 2017/7/12.
 */
public interface IMessageService {

    List<Message> getMessages();

    String insertMessage(String content);

}
