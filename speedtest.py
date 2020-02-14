import json
import requests
import sys


API = 'http://127.0.0.1:9000/dataPoints'


def get_data(data_count: int):
    response = requests.get(f'{API}?count={data_count}')
    try:
        response.json()
    except json.decoder.JSONDecodeError:
        print(f'received invalid JSON response\n{response.text=}', file=sys.stderr)
    else:
        print(f'{data_count=},{response.elapsed.total_seconds()=}')


for c in (10000, 50000, 100000, 500000, 1000000, 5000000):
    get_data(c)

