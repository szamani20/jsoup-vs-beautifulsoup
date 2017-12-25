from base.PageScraper import current_time_millis

from base.Field import Field
from base.SinglePageScraper import SinglePageScraper


class McGill(SinglePageScraper):
    def __init__(self, url, university, department):
        super().__init__(url, university, department)

    def create_json(self):
        for row in self.soup.select('div.panel-body div.row div.col-md-6'):
            try:
                email = row.select('div.collapse a')[-1].text.strip()
                index_email = email.index('Email')
                email = email[(index_email+6):].strip()
                f = Field(
                          row.select('h4')[0].text.strip(),
                          row.select('div.collapse a')[0]['href'],
                          [i.text.strip() for i in row.select('p')],
                          email)
                self.res.append(f)
            except Exception as e:
                self.exception += str(e).strip()


if __name__ == '__main__':
    t1 = current_time_millis()

    url = 'http://www.cs.mcgill.ca/people/faculty'
    university = 'mcgill'
    department = 'CS'
    McGill(url, university, department)

    print('Time: {}'.format(current_time_millis() - t1))
