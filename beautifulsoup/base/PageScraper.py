import json
import time

current_time_millis = lambda: int(round(time.time() * 1000))


class PageScraper:
    parser = 'lxml'

    def __init__(self, university, department):
        self.university = university
        self.department = department
        self.res = []
        self.exception = ''

    def create_json(self):
        pass

    def save_to_file(self):
        with open('{}.html'.format(self.university), 'w') as f:
            f.write(json.dumps(list(map(lambda o: o.__dict__, self.res))))

    def save_exception(self):
        with open('{}_exception.txt'.format(self.university), 'w') as e:
            e.write(self.exception)
