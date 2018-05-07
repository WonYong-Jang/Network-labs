// 모듈을 추출합니다.
const express = require('express');
// 서버를 생성합니다.
const app = express();
app.use(express.static('public'));
// request 이벤트 리스너를 설정합니다.

id=3333;
id2=4444;
ad="https://ktis.kookmin.ac.kr";
ad2="https://ecampus.kookmin.ac.kr";
eval("addr = { id" + id + " : '" + ad + "' };");
eval("addr.id" + id2 + " = ad2;");

app.get('/hong.html', (request, response) => {
    response.redirect('http://naver.com');
});
app.get('/hong2.html', (request, response) => {
    eval("response.redirect(addr.id" + id + ");");
});
app.get('/hong3.html', (request, response) => {
    eval("response.redirect(addr.id" + id2 + ");");
});
app.get('/hong4.html', (request, response) => {
    response.send('<h1> Hello Hong</h1>');
});
app.get('*', (request, response) => {
    response.send(404);
    response.send('해당 경로에는 아무것도 없습니다.');
});
// 서버를 실행합니다.
app.listen(8080, () => {
    console.log('Server running at http://127.0.0.1:8080');
});
