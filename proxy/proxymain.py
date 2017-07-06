#!/usr/bin/python
#coding=utf8

from multiprocessing import Process
from Api.ProxyApi import run as ApiRun
from Schedule.ProxyGetSchedule import run as proxyGetRun
from Schedule.ProxyValidateSchedule import run as proxyValidataRun


def run():
    p_list = list()
    p1 = Process(target=ApiRun, name='ProxyApiRun')
    p_list.append(p1)
    p2 = Process(target=proxyGetRun, name='proxyGetRun')
    p_list.append(p2)
    p3 = Process(target=proxyValidataRun, name='proxyValidataRun')
    p_list.append(p3)
    for p in p_list:
        p.start()
    for p in p_list:
        p.join()

if __name__ == '__main__':
    run()


