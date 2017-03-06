import time
import win32api, win32gui, win32con


def mouse_move(x, y, speed = 0):
    if speed is 0:
        #win32api.SetCursorPos(x, y)
        win32api.mouse_event(win32con.MOUSEEVENTF_MOVE, x, y, 0, 0)
    else:
        (actualX, actualY) = mouse_position()
        aimX = x
        aimY = y

        while True:
            if actualX > aimX:
                actualX -= speed
            elif actualX < aimX:
                actualX += speed

            if actualY > aimY:
                actualY -= speed
            elif actualY < aimY:
                actualY += speed

            mouse_move(actualX, actualY)

            if actualX > (aimX - speed) and actualX < (aimX + speed):
                if actualY > (aimY - speed) and actualY < (aimY + speed):
                    break

            #if (x, y) is not mouse_position():
                #mouse_move(x, y)


def mouse_move_relative(x, y, speed = 0):
    (cur_x, cur_y) = mouse_position()
    mouse_move(cur_x + x, cur_y + y, speed)


def mouse_click(button = win32con.MOUSEEVENTF_LEFTDOWN, repeat = 1, delay = 0.012):
    if repeat > 1:
        while repeat > 0:
            mouse_click(button)
            time.sleep(delay)
            repeat -= 1
    else:
        win32api.mouse_event(button, 0, 0, 0, 0)
        win32api.mouse_event(button*2, 0, 0, 0, 0)


def mouse_down(button = win32con.MOUSEEVENTF_LEFTDOWN):
    win32api.mouse_event(button, 0, 0, 0, 0)


def mouse_up(button = win32con.MOUSEEVENTF_LEFTDOWN):
    win32api.mouse_event(button*2, 0, 0, 0, 0)


def mouse_drag(x1, y1, x2, y2, button = win32con.MOUSEEVENTF_LEFTDOWN, speed = 0):
    if (x1, y1) is not mouse_position():
        win32api.mouse_event(win32con.MOUSEEVENTF_MOVE, x1, y1, 0, 0)
    win32api.mouse_event(button, 0, 0, 0, 0)
    win32api.mouse_event(win32con.MOUSEEVENTF_MOVE, x2, y2, 0, 0)
    win32api.mouse_event(button*2, 0, 0, 0, 0)


def mouse_wheel(direction, amount):
    win32api.mouse_event(win32con.MOUSEEVENTF_WHEEL, 0, 0, win32con.WHEEL_DELTA*direction*amount, 0)


def mouse_position():
    return win32gui.GetCursorPos()
