from base.Field import Field
from base.SinglePageScraper import SinglePageScraper
from base.PageScraper import current_time_millis


class Alberta(SinglePageScraper):
    def __init__(self, url, university, department):
        super().__init__(url, university, department)

    def create_json(self):
        for row in self.soup.select('table tbody tr'):
            try:
                f = Field(
                          row.select('td a')[0].text.strip(),
                          row.select('td a')[0]['href'].strip(),
                          row.select('td')[2].text.strip().split(','),
                          row.select('td a')[1]['href'].strip())
                self.res.append(f)
            except Exception as e:
                self.exception += str(e).strip()


if __name__ == '__main__':
    t1 = current_time_millis()

    url = 'https://www.ualberta.ca/computing-science/faculty-and-staff/faculty'
    university = 'alberta'
    department = 'CS'
    Alberta(url, university, department)

    print('Time: {}'.format(current_time_millis() - t1))
