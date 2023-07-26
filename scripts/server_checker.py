try: 
    from mcstatus import JavaServer
except ImportError:
    import pip
    pip.main(['install', 'mcstatus'])

import requests

api = {}

youtube_api = True
dev_api = False
main_api = False

if main_api:
    api['main'] = {'api': 'api.loliland.ru', 'api_launcher': 'launcher.loliland.ru'}

if youtube_api:
    api['yt'] = {'api': 'ytapi.loliland.ru', 'api_launcher': 'ytlauncher.loliland.ru'}

if dev_api:
    api['dev'] = {'api': 'devapi.loliland.ru', 'api_launcher': 'devlauncher.loliland.ru'}

servers = {}

def get_input(text: str):
    question = input(text + ' (y,n): ') 
    if question.lower() == 'y':
        return True
    elif question.lower() == 'n':
        return False

def check_servers():
    api_servers = {}

    for a in api.items():
        print('')
        print('Checking ' + a[0].capitalize() + ' API')

        server_request = requests.get('https://' + a[1]['api'] + '/launcher.address')
        if server_request.status_code == 200:
            print(a[1]['api'] + ' 200 (Work)')
            api_servers[a[0]] = server_request.json()
        else:
            print(a[1]['api'] + f' {server_request.status_code} (error)')

    if get_input('\nCheck servers online'):
        online_data = {}
        for aserver in api_servers.items():
            print('Checking servers api: ' + str(aserver[0]).capitalize())

            for server_raw in aserver[1]['data']:
                server_name = ' '.join(word.capitalize() for word in str(server_raw).replace('_', ' ').split())
                print('Checking server: ' + server_name + '\n')
                online_data[server_raw] = {}

                for s in aserver[1]['data'][server_raw]:
                    print('Ping server: #' + str(s) + ' of ' + server_name, end='')
                    try: server_info = JavaServer.lookup(aserver[1]['data'][server_raw][s]['address'] + ':' + str(aserver[1]['data'][server_raw][s]['port'])).status().raw
                    except: 
                        print('\nServer died')
                        continue

                    online_data[server_raw][s] = server_info['players']['online']
                    print(f'''
Online players: {server_info['players']['online']}
Version: {server_info['version']['name']}, protocol: {server_info['version']['protocol']}
''')        
                            
        if get_input('Get online'):
            for server in online_data.items():
                print('Online of server: ' + ' '.join(word.capitalize() for word in str(server[0]).replace('_', ' ').split()))
                for s in online_data[server[0]].items():
                    print('Server #' + s[0] + ', online: ' + str(s[1]))

check_servers()