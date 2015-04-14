import fapws._evwsgi as evwsgi
from fapws import base

def application(environ, start_response):
    status = '200 OK'
    output = 'Pong!'
 
    response_headers = [('Content-type', 'text/plain'),
                        ('Content-Length', str(len(output)))]
    start_response(status, response_headers)
    return [output]
 
def start():
    evwsgi.start("0.0.0.0", "8080")
    evwsgi.set_base_module(base)
 
    evwsgi.wsgi_cb(("/", application))
 
    evwsgi.set_debug(0)
    evwsgi.run()
 
if __name__=="__main__":
    start()
