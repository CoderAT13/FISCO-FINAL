# License Generate System
# 0.3.0
## Date
2019.4.17

## New
1. 增添了管理页面
2. 对历史记录搜素进行了UI优化
3. 证书生成页面增加了剩余次数的判断、当前选择设备的显示


# 0.3.0
1. 增添了管理页面
2. 对历史记录搜素进行了UI优化
3. 证书生成页面增加了剩余次数的判断、当前选择设备的显示

# 0.2.4
1. 把永久有效和设置日期部分换成了Switch
2. 修复了zone和passengerStay的选择bug，新增了（zone、ps）row里的已选说明
3. 增加了对下载证书的response的判断处理以及snackbar的弹出
4. 修改了页面标题，RCS => LGS

# 0.2.3
1. 新增了模拟后台上传zip压缩文件，以gzip文件的方式保存能正常解压缩
2. 对模拟后台的文件能下载并正常解压

# 0.2.2
1. 完成了历史页面所有功能操作
2. 所有url查询参数没有则空，例如搜索条件有Device就“gethistory/15?page=1&Device=1001”，没有就是“gethistory/15?page=1”
3. 缺少下载license.tar.gz的功能，需要与后端对好文件流格式

# 0.2.1
1. 修复了有效日期无法设置永久有效的bug
2. 生成证书时whitelist转为license的一个字符串属性ssn

# 0.2.0
## Contributer
CoderAt

## 说明
1. 登录后直接进入了证书生成页面
2. 操作步骤：选设备型号=>选择功能=>上传设备序列号校验=>生成证书

## API （Request url说明）
1. 历史记录获取: /user/gethistory/15?page=1&Time=20190318&Type=100x&Device=1001w     

查询条件无，例如没有Time，就写Time=empty，用关键字搜索
2. 历史记录格式：{"total": Number, "data" : Array[Objects{ Time: String, Type: String, Device: String, Num: Number}]}
3. 证书生成报问：{method: 'POST',headers: { ...authHeader(), 'Content-Type': 'application/json' }, license: JSON.stringify(Object),
whiteList: JSON.stringify(Array)}
4. licenseObject: {FunctionName: "0"|"1", …… ,ExpireTime: String}