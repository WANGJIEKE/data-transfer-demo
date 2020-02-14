import csv
import json
import requests
import sys


def profile_performance(data_count: int, api: str = 'http://127.0.0.1:9000/dataPoints') -> float:
    response = requests.get(f'{api}?count={data_count}')
    try:
        response.json()
    except json.decoder.JSONDecodeError:
        print(f'{response.text=}', file=sys.stderr)
        raise
    else:
        return response.elapsed.total_seconds()


if __name__ == '__main__':
    data_counts = (10000, 50000, 100000, 500000, 1000000, 5000000, 10000000)

    with open('result.csv', 'w') as f:
        writer = csv.writer(f)
        writer.writerow(['Attempt'] + list(map(lambda x: f'Time (count = {x})', data_counts)))

        for attempt_i in range(1, 6):
            delays = [profile_performance(cnt) for cnt in data_counts]
            writer.writerow([str(attempt_i)] + list(map(lambda x: str(x), delays)))
