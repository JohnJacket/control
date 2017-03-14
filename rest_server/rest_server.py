from tornado import httpserver, gen
from tornado.ioloop import IOLoop
import tornado.web
import json
import win32control


class MousePositionHandler(tornado.web.RequestHandler):
    def data_received(self, chunk):
        pass

    def get(self, *args, **kwargs):
        (x, y) = win32control.mouse_position()
        self.write(json.dumps({'x':x, 'y':y}))


class MouseMoveHandler(tornado.web.RequestHandler):
    def data_received(self, chunk):
        pass

    def post(self):
        data = json.loads(self.request.body.decode('utf-8'))
        if not data or not 'x' in data or not 'y' in data:
            raise tornado.web.HTTPError(400)
        win32control.mouse_move(data['x'], data['y'], data.get('speed'))
        pass


class MouseClickHandler(tornado.web.RequestHandler):
    def data_received(self, chunk):
        pass

    def post(self, *args, **kwargs):
        data = json.loads(self.request.body.decode('utf-8'))
        win32control.mouse_click(data.get('button', 2), data.get('repeat', 1), data.get('delay', 0.012))
        pass


class MouseDownHandler(tornado.web.RequestHandler):
    def data_received(self, chunk):
        pass

    def post(self, *args, **kwargs):
        data = json.loads(self.request.body.decode('utf-8'))
        win32control.mouse_down(data.get('button', 2))
        pass


class MouseUpHandler(tornado.web.RequestHandler):
    def data_received(self, chunk):
        pass

    def post(self, *args, **kwargs):
        data = json.loads(self.request.body.decode('utf-8'))
        win32control.mouse_up(data.get('button'))
        raise tornado.gen.Return()


class MouseWheelHandler(tornado.web.RequestHandler):
    def data_received(self, chunk):
        pass

    def post(self, *args, **kwargs):
        data = json.loads(self.request.body.decode('utf-8'))
        if not data or not 'amount' in data:
            raise tornado.web.HTTPError(400)
        win32control.mouse_wheel(data['amount'])
        pass


class MainHandler(tornado.web.RequestHandler):
    def data_received(self, chunk):
        pass

    def get(self, *args, **kwargs):
        self.write('Hello world!')


class Application(tornado.web.Application):
    def __init__(self):
        handlers = [
            (r"/?", MainHandler),
            (r"/mouse/position", MousePositionHandler),
            (r"/mouse/move", MouseMoveHandler),
            (r"/mouse/click", MouseClickHandler),
            (r"/mouse/down", MouseDownHandler),
            (r"/mouse/up", MouseUpHandler),
            (r"mouse/wheel", MouseWheelHandler)
        ]
        tornado.web.Application.__init__(self, handlers)


def main():
    app = Application()
    sockets = tornado.netutil.bind_sockets(8888)
    tornado.process.fork_processes(0)
    server = tornado.httpserver.HTTPServer(app)
    server.add_sockets(sockets)
    IOLoop.current().start()

'''
    server = tornado.httpserver.HTTPServer(app)
    server.bind(5000)
    server.start(0)
    IOLoop.current().start()
'''

if __name__ == '__main__':
    main()

'''
@app.route('/mouse/position', methods=['GET'])
def get_mouse_posiniton():
    (x, y) = win32control.mouse_position()
    return jsonify({'x':x, 'y':y})


{
	"x":100,
	"y":200,
	"speed":0
}


@app.route('/mouse/move', methods=['POST'])
def mouse_move():
    if not request.json or not 'x' in request.json or not 'y' in request.json:
        abort(400)
    win32control.mouse_move(request.json['x'], request.json['y'], request.json.get('speed'))
    return 'Success', 200


{
	"button":8,
	"repeat":2,
	"delay":12000
}



@app.route('/mouse/click', methods=['POST'])
def mouse_click():
    win32control.mouse_click(request.get_json().get('button', 8), request.get_json().get('repeat', 1), request.get_json().get('delay', 0.012))
    return 'Success', 200


{
	"button":8,
}



@app.route('/mouse/down', methods=['POST'])
def mouse_down():
    win32control.mouse_down(request.get_json().get('button', 8))
    return 'Success', 200


@app.route('/mouse/up', methods=['POST'])
def mouse_up():
    win32control.mouse_up(request.json.get('button'))
    return 'Success', 200


{
"amount":-200.0,
}



@app.route('/mouse/wheel', methods=['POST'])
def mouse_wheel():
    if not request.json or not 'amount' in request.json:
        abort(400)
    win32control.mouse_wheel(request.json['amount'])
    return 'Success', 200


@app.errorhandler(400)
def bad_request(error):
    return make_response(jsonify({'error': 'Bad Request'}), 400)


@app.errorhandler(404)
def not_found(error):
    return make_response(jsonify({'error': 'Not found'}), 404)

'''
