import json
import config
import string

from elin.app.util import http_get
from elin.app.util import http_get1

_URL='http://new.elimautism.org/json.asp?type=%s&since=%s&limit=%s&token=%s'
trantab = string.maketrans('\x00\x01\x02\x03\x04\x05\x06\x07\x08\t\n\x0b\x0c\r\x0e\x0f\x10\x11\x12\x13\x14\x15\x16\x17\x18\x19\x1a\x1b\x1c\x1d\x1e\x1f', '                                ')

def get_announce(since, limit):
    return _get('announce', since, limit)
    
def get_user(since, limit):
    return _get('user', since, limit)

def _get(_type, since, limit):
    url = _URL%(_type, since, limit, config.TOKEN)
    data = http_get1(url)
    data = data.replace('\n', '%')
    ret = None
    try:
        ret = json.loads(data)
    except Exception as e:
        import pdb;pdb.set_trace()
        raise e
    return ret
