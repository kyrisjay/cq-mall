var http = require('http');

http.createServer(function (req, res) {
    res.write('<head><meta charset="utf-8"></head>');
    res.write("8888端口服务器");
    res.end();
}).listen(8888);