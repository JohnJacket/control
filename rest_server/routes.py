from mouse_handlers import MousePosition, MouseMove

routes = [
    ('GET', '/mouse/position', MousePosition, 'mouse_position'),
    ('POST', '/mouse/move', MouseMove, 'mouse_move'),
]
