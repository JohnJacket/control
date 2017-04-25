import platform
import win32control


def kbd_write(text):
    if platform.system() == 'Windows':
        return win32control.kbd_write(text)
    else:
        return False


def kbd_keyid_action(id, action):
    if 0 > id > 255 or (action != 'click' and action != 'down' and action != 'up'):
        return False
    if platform.system() == 'Windows':
        return win32control.kbd_keyid_action(id, action)
    else:
        return False


def kbd_key_action(key, action):
    if key != 'backspace' and key != 'enter':
        return False
    if action != 'click' and action != 'down' and action != 'up':
        return False
    if platform.system() == 'Windows':
        return win32control.kbd_key_action(key, action)
    else:
        return False
