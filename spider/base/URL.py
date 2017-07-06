#!/usr/bin/python
#coding=utf8

import socket
import urllib2
import random
from base.Constants import *
import requests
import json


class urlWork(object):
    def __init__(self):
        self.proxys = []
        url = 'http://127.0.0.1:5001/getall'
        content = requests.get(url=url, timeout=30).content.encode("utf-8")
        strs = str(json.loads(content)['data'])
        self.proxys= strs.split(',')

    def getproxy(self):
        i = random.randint(0, len(self.proxys)-1)
        return self.proxys[i]
    '''
这个类是用来完成URL各种操作的。
截取delete so 代码
modify: MaytoMarry
    '''
    def sendGet(self, url):
        '''
          这个类是用来发送URL请求的。
        '''
        content = "";
        timeout = 5;
        #socket.setdefaulttimeout(timeout);
        header = {'Connection': 'keep-alive',
                  'Cache-Control': 'max-age=0',
                  'Upgrade-Insecure-Requests': '1',
                  'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_3) AppleWebKit/537.36 (KHTML, like Gecko)',
                  'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8',
                  'Accept-Encoding': 'gzip, deflate, sdch',
                  'Accept-Language': 'zh-CN,zh;q=0.8',
                  }
        
        try:
            #request = urllib2.Request(url, headers)
            #data = urllib2.urlopen(url);
            #content = data.read().decode("utf-8");
            proxy = self.getproxy()
            proxies = {"https": "https://{proxy}".format(proxy=proxy)}
            content = requests.get(url=url, proxies=proxies, headers=header, timeout=30).content.encode("utf-8")
            print ""
        except Exception as e:
            return URL_EXCEPTION
            
        return content;