// 모듈을 추출합니다.
const express = require('express');
// 서버를 생성합니다.
const app = express();
app.use(express.static('public'));
// request 이벤트 리스너를 설정합니다.
app.get('/hong.html', (request, response) => {
    response.redirect('https://www.naver.com');
});
app.get('/hong2.html',(request,response) => {
   response.redirect('https://ktis.kookmin.ac.kr'); 
});
app.get('*', (request, response) => {
    response.send(404);
    response.send('해당 경로에는 아무것도 없습니다.');
});
// 서버를 실행합니다.
app.listen(8080, () => {
    console.log('Server running at http://127.0.0.1:8080');
});
