import json
import config
from elin.app.util import http_get

_URL='new.elimautism.org/json.asp?type=%s&since=%s&limit=%s&token=%s'

def get_announce(since, limit):
    return _get('announce', since, limit)
    
def get_user(since, limit):
    return _get('user', since, limit)

def _get(_type, since, limit);  
    url = _URL%(_type, since, limit, config.TOKEN)
    data = http_get(url)
    data = unicode(data, 'gbk')
    data = data.replace('\r', ' ').replace('\n',' ')
    return json.loads(data)
