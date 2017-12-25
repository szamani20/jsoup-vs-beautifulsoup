
from base.Field import Field
from base.MultiPageScrapper import MultiPageScraper
from base.PageScraper import current_time_millis


class York(MultiPageScraper):
    def __init__(self, urls, university, department):
        super().__init__(urls, university, department)

    def create_json(self):
        for soup in self.soups:
            try:
                f = Field(
                          soup.select('h1.staff-title')[0].text.strip() + ', ' + soup.select('div.post-description')[0].text.strip(),
                          soup.select('div.staff-information a')[1]['href'].strip(),
                          [i.text.strip() for i in soup.select('div.staff-content ul:nth-of-type(1) li')],
                          soup.select('div.staff-information a')[0]['href'].strip())
                self.res.append(f)
            except Exception as e:
                self.exception += str(e).strip()


def extract_faculty_urls(base_url, number_of_pages=10):
    import requests
    from bs4 import BeautifulSoup
    faculty_urls = set()
    for i in range(1, number_of_pages):
        t = requests.get(base_url.format(str(i))).text
        soup = BeautifulSoup(t, 'lxml')
        for row in soup.select('div.img-wrap a'):
            faculty_urls.add(row['href'].strip())
    return faculty_urls


if __name__ == '__main__':
    t1 = current_time_millis()

    urls = extract_faculty_urls('http://eecs.lassonde.yorku.ca/community/faculty-members/page/{}/', 10)
    university = 'york'
    department = 'CS'
    York(urls, university, department)

    print('Time: {}'.format(current_time_millis() - t1))
