#coding=utf8

from pymysql.err import *
import pymysql
from DB.MysqlPool import *

class Proxy(object):
    '''
代理表各项操作
    '''

    __dbinfo = "";

    def __init__(self):
        self.__conn = Mysql.getCoon()
        
    def insert(self, data):
        self.__conn.end()
        self.__conn.close()

    def clear(self):
        return ""
