import sys
import time
import config
import os
import json

from elin.app.fetch import *

file_path = ''
tag_path = ''


def read_tag():
    since = 0
    try:
        f = open(tag_path)
        since = long(f.readline())
        f.close()
    except Exception:
        pass
    return since

def save_tag(since):
    f = open(tag_path, 'w')
    f.write(str(since))
    f.close()

def save_file(json_dict):
    s = json.dumps(json_dict)
    f = open(file_path, 'a')
    f.write(s)
    f.write('\n')
    f.close()

def main():
    task = sys.argv[1] 
    fetch_func = None
    if task == 'announce':
        fetch_func = get_announce
    elif task == 'user':
        fetch_func = get_user
    else:
        exit(1)
    global file_path
    global tag_path
    file_path = os.path.join(config.DIR, task) 
    tag_path  = os.path.join(config.DIR, task+'_tag')
    since = read_tag()
    print since
    while True:
        data = fetch_func(since, 100)
        for item in data:
            save_file(item)
        sleep = False
        print '%s %s fetched, since %s'%(len(data), task, since)
        if len(data) < 100:
            time.sleep(3600) 
        else:
            item = data[-1]
            since = long(item.get('ndatetime', None) or item.get('ApplyTime'))
            save_tag(since)
            time.sleep(1)
