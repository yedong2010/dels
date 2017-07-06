#coding=utf8

class ACcommentsInfoPO(object):
    '''
ACcommentsInfo表的数据实体
投稿信息表
|
---投稿编号（主键）
|
---投稿类型
|
---投稿标题
|
---时间戳
|
---投稿URL
    '''

    
    def get_type(self):
        return self.__type

    
    def get_id(self):
        return self.__id


    def get_url(self):
        return self.__url


    def get_title(self):
        return self.__title



    def get_check_time(self):
        return self.__checkTime


    def set_url(self, value):
        self.__url = value


    def set_title(self, value):
        self.__title = value



    def set_check_time(self, value):
        self.__checkTime = value


    def set_id(self, value):
        self.__id = value
    
    
    def set_type(self, value):
        self.__type = value


    __id = "";
    __url = "";
    __type = "";
    __title = "";
    __checkTime = "";
    