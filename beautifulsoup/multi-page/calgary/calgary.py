
from base.Field import Field
from base.MultiPageScrapper import MultiPageScraper
from base.PageScraper import current_time_millis


class Calgary(MultiPageScraper):
    def __init__(self, urls, university, department):
        super().__init__(urls, university, department)

    def create_json(self):
        for soup in self.soups:
            try:
                r = [i.text.strip() for i in soup.select('div#unitis-profile-block-profileblock_0 p')]
                if len(r) == 0:
                    r = [i.text.strip() for i in soup.select('div#unitis-profile-block-profileblock_401 p')]
                f = Field(
                          soup.select('div.wrapper h1')[0].text.strip() + ', ' +
                          soup.select('div#unitis-profile-block-roles-positions-list li')[0].text.strip(),
                          soup.select('div.unitis-website-list a')[0]['href'].strip(),
                          r,
                          soup.select('div.unitis-email-list a')[0]['href'].strip())
                self.res.append(f)
            except Exception as e:
                self.exception += str(e).strip()


def extract_faculty_urls(base_url, uni_url):
    import requests
    from bs4 import BeautifulSoup

    t = requests.get(base_url).text
    soup = BeautifulSoup(t, 'lxml')
    temp = soup.select('table#uofc-table-7 tr td a')
    faculty_urls = []
    for i in range(0, len(temp), 2):
        faculty_urls.append(uni_url + temp[i]['href'].strip())
    return faculty_urls


if __name__ == '__main__':
    t1 = current_time_millis()

    urls = extract_faculty_urls('http://www.ucalgary.ca/cpsc_info/contact-us',
                                uni_url='http://www.ucalgary.ca')
    university = 'calgary'
    department = 'CS'
    Calgary(urls, university, department)

    print('Time: {}'.format(current_time_millis() - t1))
