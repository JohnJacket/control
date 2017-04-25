import Constant
import time
import win32api
import win32gui
import win32clipboard
import win32con
import win32dictonary


def mouse_move(x, y, speed=0):
    if speed is 0:
        win32api.mouse_event(win32con.MOUSEEVENTF_MOVE, x, y, 0, 0)
    else:
        (actualX, actualY) = mouse_position()
        aim_x = x
        aim_y = y

        while True:
            if actualX > aim_x:
                actualX -= speed
            elif actualX < aim_x:
                actualX += speed

            if actualY > aim_y:
                actualY -= speed
            elif actualY < aim_y:
                actualY += speed

            mouse_move(actualX, actualY)

            if (aim_x - speed) < actualX < (aim_x + speed):
                if (aim_y - speed) < actualY < (aim_y + speed):
                    break


def mouse_click(button=Constant.LEFT_MOUSE_BUTTON, repeat=1, delay=0.012):
    button = translate_to_win32(button)
    if repeat > 1:
        while repeat > 0:
            mouse_click(button)
            time.sleep(delay)
            repeat -= 1
    else:
        win32api.mouse_event(button, 0, 0, 0, 0)
        win32api.mouse_event(button*2, 0, 0, 0, 0)


def mouse_down(button=Constant.LEFT_MOUSE_BUTTON):
    button = translate_to_win32(button)
    win32api.mouse_event(button, 0, 0, 0, 0)


def mouse_up(button=win32con.MOUSEEVENTF_LEFTDOWN):
    button = translate_to_win32(button)
    win32api.mouse_event(button*2, 0, 0, 0, 0)


def mouse_wheel(amount):
    win32api.mouse_event(win32con.MOUSEEVENTF_WHEEL, 0, 0, amount, 0)


def mouse_position():
    return win32gui.GetCursorPos()


def kbd_write(text):
    win32clipboard.OpenClipboard()
    win32clipboard.EmptyClipboard()
    win32clipboard.SetClipboardText(text)
    hwnd = win32gui.GetFocus()
    win32api.SendMessage(hwnd, win32con.WM_PASTE, 0, 0)
    win32clipboard.EmptyClipboard()
    win32clipboard.CloseClipboard()
    return True


def kbd_key_action(key, action):
    if action == 'click':
        return kbd_key_click(key)
    elif action == 'down':
        return kbd_key_down(key)
    elif action == 'up':
        return kbd_key_up(key)
    else:
        return False


def kbd_key_click(key):
    kbd_key_down(key)
    kbd_key_up(key)
    return True


def kbd_key_down(key):
    win32api.keybd_event(win32dictonary.VK_CODE[key], 0, 0, 0)
    return True


def kbd_key_up(key):
    win32api.keybd_event(win32dictonary.VK_CODE[key], 0, win32con.KEYEVENTF_KEYUP, 0)
    return True


def translate_to_win32(button):
    if button == Constant.LEFT_MOUSE_BUTTON:
        return win32con.MOUSEEVENTF_LEFTDOWN
    elif button == Constant.RIGHT_MOUSE_BUTTON:
        return win32con.MOUSEEVENTF_RIGHTDOWN
    elif button == Constant.MIDDLE_MOUSE_BUTTON:
        return win32con.MOUSEEVENTF_MIDDLEDOWN
