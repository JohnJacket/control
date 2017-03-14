import json
from aiohttp import web
import win32control


class MousePosition(web.View):
    async def get(self):
        (x, y) = await win32control.mouse_position()
        return web.json_response({'x':x, 'y':y})


class MouseMove(web.View):
    async def post(self):
        data = await self.request.json()
        if not data or not 'x' in data or not 'y' in data:
            raise web.HTTPError(400)
        win32control.mouse_move(data['x'], data['y'], data.get('speed'))
        return web.Response()

