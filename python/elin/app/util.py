import httplib

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
