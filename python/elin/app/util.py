import httplib
import urllib2
from BeautifulSoup import BeautifulSoup

def http_get(url):
    uri = '/'
    host = url
    sep_idx = url.find('/')
    if sep_idx >= 0:
        host = url[:sep_idx]
        uri = url[sep_idx:]
    c = httplib.HTTPConnection(host)
    c.request("GET", uri)
    response = c.getresponse()
    data = response.read()
    return data

def http_get1(url):
    fp = urllib2.urlopen(url)
    soup = BeautifulSoup(fp)
    return unicode(soup)
