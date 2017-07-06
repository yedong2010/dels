#coding=utf8

class ACcommentsPO(object):
    '''
ACcomments表的数据实体
评论搜集表 （个人觉得不需要主键，因为一篇投稿需要对应多个评论）
|
---评论cid
|
---评论内容
|
---评论人用户名
|
---引用评论cid
|
---该评论楼层数
|
---投稿编号
|
---删除标志
|
---时间戳
    '''
    
    def get_cid(self):
        return self.__cid

    def get_content(self):
        return self.__content

    def get_user_name(self):
        return self.__userName

    def get_layer(self):
        return self.__layer

    def get_acid(self):
        return self.__acid

    def get_check_time(self):
        return self.__checkTime

    def get_delete(self):
        return self.__delete

    def set_cid(self, value):
        self.__cid = value

    def set_content(self, value):
        self.__content = value

    def set_user_name(self, value):
        self.__userName = value

    def set_layer(self, value):
        self.__layer = value

    def set_acid(self, value):
        self.__acid = value

    def set_delete(self, value):
        self.__delete = value

    def set_check_time(self, value):
        self.__checkTime = value


    __cid = "";
    __content = "";
    __userName = ""
    __layer = "";
    __acid = "";
    __delete = "";
    __checkTime = "";