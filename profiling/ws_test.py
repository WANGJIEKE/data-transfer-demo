import asyncio
import struct
import sys
import time
import websockets


async def get_points(count: int) -> float:
    uri = "ws://localhost:9000/ws"
    async with websockets.connect(uri) as websocket:
        await websocket.send(count.to_bytes(4, byteorder='big'))  # Java int is 4 byte
        t1 = time.time()
        response = await websocket.recv()
        t2 = time.time()
        try:
            struct.unpack(f'>{"dd" * count}', response)
        except struct.error:
            print(f'{response=}', file=sys.stderr)
            raise
        return t2 - t1

asyncio.get_event_loop().run_until_complete(get_points(10000))
