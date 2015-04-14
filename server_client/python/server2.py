import gevent
from gevent import socket
 
def handle_socket(sock):
    data = sock.recv(1024)
    sock.sendall("HTTP/1.1 200 OK\r\n\r\n")
    sock.shutdown(socket.SHUT_RDWR)
    sock.close()
 
server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server.bind(('localhost', 8001))
server.listen(500)

while True:
    try:
        new_sock, address = server.accept()
    except KeyboardInterrupt:
        break
    # handle every new connection with a new co=routine
    gevent.spawn(handle_socket, new_sock)
