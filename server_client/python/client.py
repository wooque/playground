import gevent
from gevent import socket
import time

def make_req():
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.connect(('127.0.0.1', 9090))
    s.sendall('Hello, world')
    data = s.recv(1024)
    s.close()

N = 1000
begin = time.clock()

i = 0
reqs = []
while(i < N):
    reqs.append(gevent.spawn(make_req))
    i += 1

gevent.joinall(reqs)

total = time.clock() - begin
print "Throughput:", (N/total), "req/s"
