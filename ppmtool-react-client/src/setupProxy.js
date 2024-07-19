const { createProxyMiddleware } = require('http-proxy-middleware');

console.log("in setuppuuuu");

module.exports = function(app) {
  console.log("in setupproxy")
  app.use(
    '/api',
    createProxyMiddleware({
      target: 'http://localhost:8080',
      changeOrigin: true,
      onProxyReq: (proxyReq, req, res) => {
        console.log('Proxying request:', req.url);
      },
      onError: (err, req, res) => {
        console.log('Proxy error:', err);
      },
    })
  );
};