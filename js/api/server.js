require('dotenv').config()
const express = require('express');
const bodyParser = require('body-parser');
const mongoose = require('mongoose');
const Eureka = require('eureka-js-client').Eureka;
const helmet = require('helmet') 
const path = require('path')
const classController = require("./api/controllers/classController")
const classControllerInst = new classController()

mongoose.connect('mongodb://' + process.env.DB_HOST + ':' + process.env.DB_PORT + '/' + process.env.DB_NAME, {
  useNewUrlParser: true,
  serverSelectionTimeoutMS: 3000, // Increase the timeout limit
  socketTimeoutMS: 45000,
});

mongoose.Promise = global.Promise;

const app = express();

app.set('view engine', 'ejs'); // Specify a default view engine
app.set('views', path.join(__dirname, 'views'));
app.engine('html', require('ejs').renderFile);

app.use(function (req, res, next) {
  res.header('Access-Control-Allow-Origin', '*');
  res.header('Access-Control-Allow-Headers', 'Origin, X-Requested-With, Content-Type, Accept');
  next();
});

app.use(helmet());
app.use(bodyParser.json());
app.get('/class', (req, res) => {
  classControllerInst.fetchAll(req,res)
 })
 app.post('/class', (req, res) => {
  classControllerInst.add(req,res)
 })

 app.post('/class/assign', (req, res) => {
  classControllerInst.assignClass(req, res);
});


app.use(function (err, req, res, next) {
  console.log(err);
  if (err.name === 'UnauthorizedError') {
    return res.status(err.status).send({ code: err.status, message: 'you dont have permission to access bro'})
  }
  res.status(err.status || 500);
  res.render('error.html'); // Specify an extension for the view
});
const eurekaClient = new Eureka({
  eureka: {
    host: 'eureka-server',
    port: 8761,
    servicePath: '/eureka/apps/',
    maxRetries: 10,
    requestRetryDelay: 2000,
  },
  instance: {
    app: 'class',
    instanceId: 'class',
    hostName: 'class-service',
    ipAddr: '127.0.0.1',
    port: {
      '$': 3000,
      '@enabled': 'true',
    },
    vipAddress: 'class-service',
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

const db = mongoose.connection;
db.on('error', console.error.bind(console, 'MongoDB connection error:'));
db.once('open', () => {
  console.log('MongoDB connected!');
  app.listen(process.env.PORT, () => console.log(`Server running on port ${process.env.PORT}`));
});

