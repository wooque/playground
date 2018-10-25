import csv

from util import get_distance, points, plot


def process_salary_data():
    lines = open('orig.txt').readlines()

    csv_file = open('data.csv', 'w')
    writer = csv.writer(csv_file)
    writer.writerow(['City', 'Avg Salary'])

    for l in lines:
        parts = l.split('\t')
        city = parts[0]
        if any(e.lower() in city.lower() for e in ['СРБИЈА', 'регион', 'област']):
            # print('Skiodp', city)
            continue

        city = city.replace('Град ', '').strip()
        net_salaries = [int(p) for p in parts[1::2]]
        avg_salary = sum(net_salaries) / len(net_salaries)

        writer.writerow([city, avg_salary])
        print(city, avg_salary)

    csv_file.close()


def get_and_save_distances():
    reader = csv.DictReader(open('data.csv'))
    data = {e['City']: e['Avg Salary'] for e in reader}

    cities = [c + ',Serbia' for c in data.keys()]
    distances = get_distance(cities, 'Belgrade')

    csv_file = open('data.csv', 'w')
    writer = csv.writer(csv_file)
    writer.writerow(['City', 'Avg Salary', 'Distance'])

    for (city, salary), dist in zip(data.items(), distances):
        writer.writerow([city, salary, dist])

    csv_file.close()


def load_data():
    reader = csv.DictReader(open('data.csv'))
    return [{
        'city': e['City'],
        'salary': float(e['Avg Salary']),
        'distance': int(e['Distance']),
    } for e in reader]


def rank():
    data = load_data()
    salary_points = points([e['salary'] for e in data])
    distance_points = points([e['distance'] for e in data], reverse=True)

    rank_data = []
    for elem, salary, distance in zip(data, salary_points, distance_points):
        r = (salary + distance) / 2
        rank_data.append((elem['city'], r))

    return sorted(rank_data, key=lambda e: e[1], reverse=True)


def save_rank():
    rank_data = rank()
    data = {d['city']: (d['salary'], d['distance']) for d in load_data()}

    csv_file = open('rank.csv', 'w')
    writer = csv.writer(csv_file)
    writer.writerow(['City', 'Rank', 'Salary', 'Distance'])

    for city, city_rank in rank_data:
        writer.writerow([city, city_rank, data[city][0], data[city][1]])

    csv_file.close()


if __name__ == '__main__':
    save_rank()
