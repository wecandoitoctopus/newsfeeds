# -*- coding: utf-8 -*-
import http.server, socketserver, webbrowser, os, contextlib, socket

PORT = 5500
with contextlib.closing(socket.socket(socket.AF_INET, socket.SOCK_STREAM)) as s:
    while True:
        try:
            s.bind(("127.0.0.1", PORT)); break
        except OSError: PORT += 1

os.chdir(os.path.dirname(os.path.abspath(__file__)))
url = f"http://127.0.0.1:{PORT}/swagger.html"
handler = http.server.SimpleHTTPRequestHandler
with socketserver.TCPServer(("127.0.0.1", PORT), handler) as httpd:
    print(f"Serving {os.getcwd()} at {url}")
    webbrowser.open(url)
    httpd.serve_forever()
