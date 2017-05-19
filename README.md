# Remote control
Server and client for control PC's mouse, keyboard and do some quick operations from another device.

### Current progress
* REST server written on Python 3.6
* Mouse control support by server
* Android client with mouse and keyboard control support
* Keyboard control support by server

### In development
* Service discovery
* PC's control panel
* Server as service with some sort of settings
* Fullscreen keyboard in landscape mode
* Server on Linux and Mac

## Server
Written on python 3.6 with Flask and pywin32.
Supports mouse and keyboard control operations

### Supported platforms:
* Windows.

### Server common operations
	SystemInfo() : '/system-info' [GET]
	
### Server mouse operations
	MouseMove (int x, int y, int speed = 0) : '/mouse/move' [POST]
	MouseClick (int button = left, int repeat = 1, double delay = 0.012) : '/mouse/click' [POST]
	MouseDown (int button = left) : '/mouse/down' [POST]
	MouseUp (int button = left) : '/mouse/up' [POST]
	MousePosition () : '/mouse/position' [GET]
	MouseWheel (double amount) : '/mouse/wheel' [POST]
	
### Server keyboard operations
	KeyboardWrite(string text) : '/keyboard/write' [POST]
	KeyboardKeyAction(string key, string action = click) : '/keyboard/<key>/<action>' [GET]
		<action> - click, down, up
	
## Client
Android client on Java.
	
### Supported platforms:
* Android 4.0.3 and higher.
	

