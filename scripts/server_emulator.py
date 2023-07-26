from flask import Flask, request
import requests
from urllib.parse import urlencode, unquote_plus
import json
import logging
log = logging.getLogger('werkzeug')

remove_logs = False

if remove_logs:
    log.setLevel(logging.ERROR)

headers = {
    'authority': 'api.loliland.pro',
    'accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7',
    'accept-language': 'ru,uk;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6',
    'cache-control': 'max-age=0',
    'sec-ch-ua': '"Not.A/Brand";v="8", "Chromium";v="114", "Microsoft Edge";v="114"',
    'sec-ch-ua-mobile': '?0',
    'sec-ch-ua-platform': '"Windows"',
    'sec-fetch-dest': 'document',
    'sec-fetch-mode': 'navigate',
    'sec-fetch-site': 'none',
    'sec-fetch-user': '?1',
    'upgrade-insecure-requests': '1',
    'user-agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36 Edg/114.0.1823.51',
}

app = Flask('Emulated Server for LoliLand')

fake_login = True
fake_votes = 5000.0

no_download = True

@app.route('/', defaults={'path': ''})
@app.route('/<path:path>', methods=['GET', 'POST'])
def index(path):
    print('Launcher send request to: ' + path)
    if not len(request.data) == 0:
        data = json.loads(request.data.decode('utf-8'))
        query_params = urlencode(data)

    if request.method == 'GET':
        if 'download' in path and no_download:
            print('Launcher tried to download file')
            return 'lol'

        print('Launcher send GET request')
        if 'launcher.loliland' in str(request.headers):
            print('API Type: Launcher')
            return requests.post('https://launcher.loliland.pro/' + path, headers=headers, cookies=request.cookies).content
        elif 'api.loliland' in str(request.headers):
            print('API Type: Default')
            return requests.post('https://api.loliland.pro/' + path, headers=headers, cookies=request.cookies).content
    
    elif request.method == 'POST':
        if fake_login and "login" in request.data.decode('utf-8'):
            return {'data': {'votes': 5000.0}, 'type': 'success'}
        print('Launcher send POST request')
        if 'launcher.loliland' in str(request.headers):
            print('API Type: Launcher')
            return requests.post('https://launcher.loliland.pro/' + path + '?' + unquote_plus(query_params), headers=headers, cookies=request.cookies).content
        elif 'api.loliland' in str(request.headers):
            print('API Type: Default')
            return requests.post('https://api.loliland.pro/' + path + '?' + unquote_plus(query_params), headers=headers, cookies=request.cookies).content

app.run('0.0.0.0', 80, True)
