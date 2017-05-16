from flask import Flask, jsonify, make_response, request, abort
import platform
import win32control
import common_control

app = Flask(__name__)


@app.route('/system-info', methods=['GET'])
def get_system_info():
    return jsonify({'OS': platform.system(), 'Release': platform.release(), 'Machine': platform.machine(), 'Platform': platform.platform(), 'Uname': platform.uname()})


@app.route('/mouse/position', methods=['GET'])
def get_mouse_position():
    (x, y) = win32control.mouse_position()
    return jsonify({'x': x, 'y': y})


@app.route('/mouse/move', methods=['POST'])
def mouse_move():
    if not ((request.json and 'x' in request.json) and 'y' in request.json):
        abort(400)
    win32control.mouse_move(request.json['x'], request.json['y'], request.json.get('speed'))
    return 'Success', 200


@app.route('/mouse/click', methods=['POST'])
def mouse_click():
    win32control.mouse_click(request.get_json().get('button', 8), request.get_json().get('repeat', 1), request.get_json().get('delay', 0.012))
    return 'Success', 200


@app.route('/mouse/down', methods=['POST'])
def mouse_down():
    win32control.mouse_down(request.get_json().get('button', 8))
    return 'Success', 200


@app.route('/mouse/up', methods=['POST'])
def mouse_up():
    win32control.mouse_up(request.json.get('button'))
    return 'Success', 200


@app.route('/mouse/wheel', methods=['POST'])
def mouse_wheel():
    if not (request.json and 'amount' in request.json):
        abort(400)
    win32control.mouse_wheel(request.json['amount'])
    return 'Success', 200


@app.route('/keyboard/write', methods=['POST'])
def kbd_write():
    if not (request.json and 'text' in request.json):
        abort(400)
    if not common_control.kbd_write(request.json['text']):
        abort(400)
    return 'Success', 200


@app.route('/keyboard/<key>/<action>', methods=['GET'])
def kbd_key_action(key, action = 'click'):
    if not common_control.kbd_key_action(key, action):
        abort(400)
    return 'Success', 200


@app.errorhandler(400)
def bad_request(error):
    return make_response(jsonify({'error': 'Bad Request'}), 400)


@app.errorhandler(404)
def not_found(error):
    return make_response(jsonify({'error': 'Not found'}), 404)


if __name__ == '__main__':
    app.run(host='0.0.0.0')
