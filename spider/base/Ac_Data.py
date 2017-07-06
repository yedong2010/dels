#encoding: utf-8
'''
Created on 2015-05-28 19:25

author: Linzertorte https://github.com/Linzertorte/LeetCode-in-Python/edit/master/LRUCache.py
modify: YuZhenchuan
email: yuzhenchuan@delete.so

last modify: maytomarry
email: script_ye@foxmail.com
'''

from base.db_proc import db_proc
from base.PO.ACcommentsPO import *
from multiprocessing.dummy import Pool as ThreadPool 


class Ac_Data():
    def __init__(self):
        self.db_proc = db_proc()

    def save(self, nodes):
        # self.db_proc.ACComments.clear()
        # 存入
        try:
            self.db_proc.ACComments.insert(nodes)
        except Exception as e:
            # 在多线程并发的时候，容易出现头结点被同时操作的情况，先跑跑看，以后再看是不是需要加锁
            pass
    
    def insert(self, data):
        if not data:
            # 有的投稿什么都没有
            return
        self.save(data)


