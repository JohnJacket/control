# Remote control
Server and client for control PC's mouse, keyboard and do some quick operations from another device.

### Current progress
* REST server written on Python 3.6
* Mouse control support by server
* Android client with mouse control support.

### In development
* Service discovery
* Async REST server
* Keyboard control support
* Fullscreen keyboard
* PC's control panel
* Server on Linux and Mac

## Server
Written on python 3.6 with Flask and pywin32.
Supports mouse control operations

### Supported platforms:
* Windows.

### Server mouse operations
	MouseMove (int x, int y, int speed = 0)
	MouseClick (int button = left, int repeat = 1, double delay = 0.012)
	MouseDown (int button = left)
	MouseUp (int button = left)
	MousePosition ()
	MouseWheel (double amount)

## Client
Android client on Java.
	
### Supported platforms:
* Android 4.0.3 and higher.
	

