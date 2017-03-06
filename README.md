# Remote control
Server and client for control PC's mouse, keyboard and do some quick operations from another device.

### Current progress
* REST server written on Python 3.6
* Mouse control support by server
* Android client skeleton for mouse control

### In development
* Async REST server
* Android client with mouse control support
* Simplification of installation process
* Keyboard control support
* Server on Linux

## Server
Written on python 3.6 with Flask and pywin32.
Supports mouse control operations

### Supported platforms:
* Windows.

### Server mouse operations
	MouseMove (int x, int y, int speed = 0)
	MouseMoveRelative (int x, int y, int speed = 0)
	MouseClick (int button = left, int repeat = 1, double delay = 0.012)
	MouseDown (int button = left)
	MouseUp (int button = left)
	MouseDrag (int x1, int y1, int x2, int y2, int button = left, int speed = 0)
		Drag&Drop operation from (x1, y1) to (x2, y2) with button up and speed.
	MousePosition ()
	MouseWheel (int direction, unsigned int amount)

## Client
Android client on Java.
	
### Supported platforms:
* Android 4.0.3 and higher.
	

