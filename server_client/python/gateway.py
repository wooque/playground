from multiprocessing import Process, Queue
import requests
import fapws._evwsgi as evwsgi
from fapws import base

q = Queue()

def dlr(queue):
    while True:
        try:
            id, text = queue.get()
            r = requests.get("http://127.0.0.1:8001/")
        except KeyboardInterrupt:
            break
            
def application(environ, start_response):
    status = '200 OK'
    output = 'Pong!'
 
    response_headers = [('Content-type', 'text/plain'),
                        ('Content-Length', str(len(output)))]
    start_response(status, response_headers)
    q.put((id, "text"))
    return [output]

def serv(queue):
    evwsgi.start("127.0.0.1", "8080")
    evwsgi.set_base_module(base)
    evwsgi.wsgi_cb(("/", application))
    evwsgi.set_debug(0)
    evwsgi.run()

if __name__ == '__main__':

    pdlr = []
    for i in range (0, 4):
        pdlr.append(Process(target=dlr, args=(q,)))
        pdlr[i].start()
    
    p = Process(target=serv, args=(q,))
    p.start()
    
    p.join()
    for i in range (0, 4):
        pdlr[i].join()
