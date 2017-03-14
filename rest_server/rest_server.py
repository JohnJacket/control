import asyncio
from aiohttp import web
from routes import routes
import win32control

def main():
    loop = asyncio.get_event_loop()
    app = web.Application(loop=loop)
    for route in routes:
        app.router.add_route(route[0], route[1], route[2], name=route[3])
    handler = app.make_handler()
    f = loop.create_server(handler, '0.0.0.0', 5000)
    srv = loop.run_until_complete(f)
    print('serving on', srv.sockets[0].getsockname())
    try:
        loop.run_forever()
    except KeyboardInterrupt:
        pass
    finally:
        srv.close()
        loop.run_until_complete(srv.wait_closed())
        loop.run_until_complete(app.shutdown())
        loop.run_until_complete(handler.shutdown(60.0))
        loop.run_until_complete(app.cleanup())
    loop.close()

if __name__ == '__main__':
    main()

'''
app = Flask(__name__)

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


