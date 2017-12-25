import requests
from bs4 import BeautifulSoup

from base.PageScraper import PageScraper


class MultiPageScraper(PageScraper):
    def __init__(self, urls, university, department):
        super().__init__(university, department)
        self.urls = urls
        self.site_texts = self.extract_htmls()
        self.soups = self.create_soups()
        self.create_json()
        self.save_to_file()
        self.save_exception()

    def extract_htmls(self):
        htmls = []
        for url in self.urls:
            htmls.append(requests.get(url=url).text)
        return htmls

    def create_soups(self):
        soups = []
        for site_text in self.site_texts:
            soups.append(BeautifulSoup(site_text,
                                       super().parser))
        return soups
