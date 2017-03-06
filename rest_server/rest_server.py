from flask import Flask, jsonify, make_response, request, abort
import win32control

app = Flask(__name__)

@app.route('/mouse/position', methods=['GET'])
def get_mouse_posiniton():
    (x, y) = win32control.mouse_position()
    return jsonify({'mouse_position': {'x':x, 'y':y}})

'''
{
	"x":100,
	"y":200,
	"speed":0
}
'''

@app.route('/mouse/move', methods=['POST'])
def mouse_move():
    if not request.json or not 'x' in request.json or not 'y' in request.json:
        abort(400)
    win32control.mouse_move(request.json['x'], request.json['y'], request.json.get('speed'))
    return 'Success', 200


@app.route('/mouse/move/relative', methods=['POST'])
def mouse_move_relative():
    if not request.json or not 'x' in request.json or not 'y' in request.json:
        abort(400)
    win32control.mouse_move_relative(request.get_json()['x'], request.get_json()['y'], request.get_json().get('speed', 0))
    return 'Success', 200

'''
{
	"button":8,
	"repeat":2,
	"delay":12000
}
'''


@app.route('/mouse/click', methods=['POST'])
def mouse_click():
    win32control.mouse_click(request.get_json().get('button', 8), request.get_json().get('repeat', 1), request.get_json().get('delay', 0.012))
    return 'Success', 200

'''
{
	"button":8,
}
'''

@app.route('/mouse/down', methods=['POST'])
def mouse_down():
    win32control.mouse_down(request.get_json().get('button', 8))
    return 'Success', 200


@app.route('/mouse/up', methods=['POST'])
def mouse_up():
    win32control.mouse_up(request.json.get('button'))
    return 'Success', 200

'''
{
	"x1":8,
	"y1":8,
	"x2":8,
	"y2":8,
	"button":8,
	"speed":8,
}
'''

@app.route('/mouse/drag', methods=['POST'])
def mouse_drag():
    if not request.json or not 'x1' in request.json or not 'y1' in request.json or not 'x2' in request.json or not 'y2' in request.json:
        abort(400)
    win32control.mouse_drag(request.json['x1'], request.json['y1'], request.json['x2'], request.json['y2'], request.json.get('button'), request.json.get('speed'))
    return 'Success', 200


@app.route('/mouse/wheel', methods=['POST'])
def mouse_wheel():
    if not request.json or not 'direction' in request.json or not 'amount' in request.json:
        abort(400)
    win32control.mouse_wheel(request.json['direction'], request.json['amount'])
    return 'Success', 200


@app.errorhandler(400)
def bad_request(error):
    return make_response(jsonify({'error': 'Bad Request'}), 400)


@app.errorhandler(404)
def not_found(error):
    return make_response(jsonify({'error': 'Not found'}), 404)


if __name__ == '__main__':
    app.run()
