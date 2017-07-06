#!/usr/bin/python
#coding=utf8

from acfun.process import *
from base.Ac_Data import *

while True:
    ac_comments = Ac_Data()
    func = process()
    func.work(ac_comments)
    time.sleep(10)