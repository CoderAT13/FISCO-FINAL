# FISCO-FINAL

## 零、基本信息

|  |  |
|:---------:|:---------:|
|姓名     |张涵健         |
|学号     |17343147         |
|日期     |2019-12-12         |


## 一、 整体测试流程

> 如有流程问题请看演示视频

0. 查看初始区块链状态
1. 部署合约
2. 配置合约地址
3. get_accouts.sh准备3个用户
4. 启动JavaApp
5. 浏览器打开 localhost:8081
6. 使用核心企业pem登录
7. 注册应收账款 1000
8. 用两个下游企业用户登录注册
9. 用核心企业向（下游企业1）签发应收账款 500、并查询
10. 用（下游企业1）向（下游企业2）转让 296、并查询（1、2）
11. 核心企业支付后，查询（1、2）
12. 查看链端变化

## 二、编译及运行

### 前端

已将静态资源打包到了后端Spring Boot框架里分发

开发者模式：
```
$ npm install
$ npm start
```

### 后端

IDEA打开项目，运行Spring Boot的Application

在`./code/fiscoserver/src/main/resources/contract.properties`中配置部署好的合约的区块地址

启动后到`localhost:8081`查看用户界面

### 链端

1. 完全按照[https://fisco-bcos-documentation.readthedocs.io/zh_CN/latest/docs/installation.html](https://fisco-bcos-documentation.readthedocs.io/zh_CN/latest/docs/installation.html)中搭建节点（端口、IP等）
2. 控制台部署`./code/chain/Asset.sol`合约，得到合约地址
3. 配置`./code/fiscoserver/src/main/resources/contract.properties`