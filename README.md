# Network-labs

### Lab1 - HTTP 프로토콜 : 클라이언트(Chrome브라우저), 서버-(node.js&javascript)
 - lab1.js 완성하기
'''
// 모듈을 추출합니다.
const express = require('express');
// 서버를 생성합니다.
const app = express();
app.use(express.static('public'));
// request 이벤트 리스너를 설정합니다. 정적파일(js,css,image) 다루기위해

app.get('/hong.html',(request,response)=> {
    response.redirect('http://www.naver.com');
});
app.get('/hong2.html',(request,response)=> {
    response.redirect('http://ktis.kookmin.ac.kr');
});
app.get('*',(request,response)=> {
    response.send(404);
    response.send('해당 경로에는 아무것도 없습니다.');
});
//서버를 실행합니다.
app.listen(8080, ()=> {
    console.log('Server running at http://192.168.33.197:8080');
});
'''