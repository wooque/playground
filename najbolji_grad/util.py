import matplotlib.pyplot as plt
import requests


def chunks(l, n):
    for i in range(0, len(l), n):
        yield l[i:i + n]


def get_distance(src, dst):
    distances = []
    for chunk in chunks(src, 25):
        url = 'https://maps.googleapis.com/maps/api/distancematrix/json'
        data = requests.get(url, params=dict(origins='|'.join(chunk), destinations=dst))
        results = data.json()['rows']
        for o, r in zip(chunk, results):
            try:
                dist = r['elements'][0]['distance']['value']
            except KeyError:
                print("Cannot calculate distance for {}, error: {}".format(o, r))
                dist = 0
            distances.append(dist)
    return distances


def points(series, reverse=False):
    min_series = min(series)
    max_series = max(series)
    rrange = max_series - min_series
    pts = []
    for s in series:
        if reverse:
            dist = max_series - s
        else:
            dist = s - min_series
        pt = dist / rrange * 100
        pts.append(pt)
    return pts


def plot(data, reverse=False):
    if reverse:
        data.reverse()
    r = range(len(data))
    plt.barh(r, [d[1] for d in data])
    plt.yticks(r, [d[0] for d in data])
    plt.show()
