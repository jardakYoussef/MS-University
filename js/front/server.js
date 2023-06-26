const http = require('http')
const fs = require('fs')
const Eureka = require('eureka-js-client').Eureka;
const port = 3001
 
const server = http.createServer(function(req,res) {
    res.writeHead(200, {'Content-Type':'text/html'})
    fs.readFile('index.html', function(error, data){
        if(error){
            res.writeHead(404)
            res.write('Error: File not found')
        }else{
            res.write(data)
        }
        res.end()
    })
 
})

const eurekaClient = new Eureka({
  eureka: {
    host: 'eureka-server',
    port: 8761,
    servicePath: '/eureka/apps/',
    maxRetries: 10,
    requestRetryDelay: 2000,
  },
  instance: {
    app: 'front',
    instanceId: 'front',
    hostName: 'front-service',
    ipAddr: '127.0.0.1',
    port: {
      '$': port,
      '@enabled': 'true',
    },
    vipAddress: 'front-service',
    dataCenterInfo: {
      '@class': 'com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo',
      name: 'MyOwn',
    },
  },
});
eurekaClient.logger.level('debug')
eurekaClient.start((error) => {
  console.log(error || 'Eureka registration complete');
});

 
server.listen(port, function(error) {
    if (error) {
        console.log('An error has occured', error)
 
    } else {
        console.log('Server is running on port ' + port)
    }
})