# -*- coding: utf-8 -*-
# !/usr/bin/env python

import sys

from flask import Flask, jsonify

from Manager.ProxyManager import ProxyManager

app = Flask(__name__)


api_list = {
    'get': u'get list usable proxy',
    'refresh': u'refresh proxy pool',
    'get_all': u'get all proxy from proxy pool',
    'delete?proxy=127.0.0.1:8080': u'delete an unable proxy',
}


@app.route('/')
def index():
    return jsonify(api_list)


@app.route('/get', methods=['GET'])
def get():
    proxy = ProxyManager().get()
    return jsonify(proxy)


@app.route('/refresh/')
def refresh():
    pass
    return 'success'


@app.route('/getall/', methods=['GET'])
def getAll():
    proxy = ProxyManager().getall()
    data = {'data': str(','.join(proxy))}
    return jsonify(data)


@app.route('/delete/', methods=['GET'])
def delete():
    # proxy = request.args.get('proxy')
    # ProxyManager().delete(proxy)
    return 'success'


@app.route('/get_status/')
def get_status():
    # status = ProxyManager().get_status()
    # return jsonify(status)
    return 'success'


def run():
    app.run(host='127.0.0.1', port=5001, debug=True)

if __name__ == '__main__':
    run()
