// 웹 서버를 생성하고 실행합니다.
const express = require('express');
const app = express();
const server = require('http').Server(app);
app.use(express.static(`${__dirname}/public`));
server.listen(52273);
console.log('type in Browser URL: http://localhost:52273');

// 소켓 서버를 생성하고 실행합니다.
const io = require('socket.io')(server); 
io.on('connection', (socket) => {
    socket.on('line', (data) => {
        io.sockets.emit('line', data);
    });
});
