import requests
from bs4 import BeautifulSoup

from base.PageScraper import PageScraper


class SinglePageScraper(PageScraper):
    def __init__(self, url, university, department):
        super().__init__(university, department)
        self.url = url
        self.site_text = self.extract_html()
        self.soup = self.create_soup()
        self.create_json()
        self.save_to_file()
        self.save_exception()

    def extract_html(self):
        return requests.get(url=self.url).text

    def create_soup(self):
        return BeautifulSoup(self.site_text,
                             super().parser)
