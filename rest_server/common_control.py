import platform
import win32control
import win32dictonary


def kbd_write(text):
    if platform.system() == 'Windows':
        return win32control.kbd_write(text)
    else:
        return False


def kbd_key_action(key, action):
    if platform.system() == 'Windows':
        if win32dictonary.VK_CODE.get(key) == None:
            return False
        return win32control.kbd_key_action(key, action)
    else:
        return False


def system_command(command):
    if platform.system() == 'Windows':
        return win32control.system_command(command)
    else:
        return False
