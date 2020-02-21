import csv
import json
import requests
import struct
import sys


def profile_performance(data_count: int, api: str = 'http://127.0.0.1:9000/dataPoints') -> float:
    response = requests.get(f'{api}?count={data_count}')
    try:
        response.json()
    except json.decoder.JSONDecodeError:
        print(f'{response.text=}', file=sys.stderr)
        raise
    return response.elapsed.total_seconds()


def profile_bin_performance(data_count: int, api: str = 'http://127.0.0.1:9000/binDataPoints') -> float:
    response = requests.get(f'{api}?count={data_count}')
    try:
        struct.unpack(f'>{"dd" * data_count}', response.content)  # big-endian in networking
    except struct.error:
        print(f'{response.content=}', file=sys.stderr)
        raise
    return response.elapsed.total_seconds()


if __name__ == '__main__':
    data_counts = (10000, 50000, 100000, 500000, 1000000)

    with open('json_result.csv', 'w') as f:
        writer = csv.writer(f)
        writer.writerow(['Attempt'] + list(map(lambda x: f'Time (count={x})', data_counts)))

        for attempt_i in range(1, 6):
            delays = [profile_performance(cnt) for cnt in data_counts]
            writer.writerow([str(attempt_i)] + list(map(lambda x: str(x), delays)))

    with open('bin_result.csv', 'w') as f:
        writer = csv.writer(f)
        writer.writerow(['Attempt'] + list(map(lambda x: f'Time (count={x})', data_counts)))

        for attempt_i in range(1, 6):
            delays = [profile_bin_performance(cnt) for cnt in data_counts]
            writer.writerow([str(attempt_i)] + list(map(lambda x: str(x), delays)))
