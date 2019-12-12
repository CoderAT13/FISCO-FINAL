pragma solidity ^0.4.24;

import "./Table.sol";

contract Asset {
    // event
    event RegisterEvent(int256 ret, string account, uint256 asset_value);
    event TransferEvent(int256 ret, string from_account, string to_account, uint256 amount);
    event PayEvent(int256 ret);

    address constant master = 0xbAF9163Da71F944b2B26a8CBe6484563cbE3EB47;
    string constant lastdate = "2019-12-31";

    string[] accountList;
    mapping(string=>address) name2addr;
    mapping(address=>string) addr2name;

    constructor() public {
        createTable();
    }

    function createTable() private {
        TableFactory tf = TableFactory(0x1001);
        tf.createTable("t_asset", "account", "asset_value");
    }

    function openTable() private view returns(Table) {
        TableFactory tf = TableFactory(0x1001);
        Table table = tf.openTable("t_asset");
        return table;
    }

    /*
    描述 : 处理用户登录

    返回值：
            3:已注册的、核心企业master身份
            2:未注册的、核心企业master身份
            1:已注册的、普通企业身份
            0:未注册的、普通企业身份
    */

    function login() public view returns(int){
        if (name2addr[addr2name[msg.sender]] != address(0)){
            if (msg.sender == master){
                return 3;
            }
            return 1;
        }
        if (msg.sender == master){
            return 2;
        }
        return 0;
    }

    /*
    描述 : 根据资产账户查询资产金额
    参数 ：
            account : 资产账户

    返回值：
            参数一： 成功返回0, 账户不存在返回-1
            参数二： 第一个参数为0时有效，资产金额
    */


    function select(string memory account) public view returns(int256, uint256) {
        // 打开表
        Table table = openTable();
        // 查询
        Entries entries = table.select(account, table.newCondition());
        uint256 asset_value = 0;
        if (0 == uint256(entries.size())) {
            return (-1, asset_value);
        } else {
            Entry entry = entries.get(0);
            return (0, uint256(entry.getInt("asset_value")));
        }
    }

    /*
    描述 : 资产注册
    参数 ：
            account : 资产账户
            amount  : 资产金额
    返回值：
            0  资产注册成功
            1  管理员资产注册成功
            -1 资产账户已存在
            -2 其他错误
    */
    function register(string memory account, uint256 asset_value) public returns(int256){
        int256 ret_code = 0;
        int256 ret = 0;
        uint256 temp_asset_value = 0;
        // 查询账户是否存在
        (ret, temp_asset_value) = select(account);
        if(ret != 0) {
            name2addr[account] = msg.sender;
            addr2name[msg.sender] = account;
            accountList.push(account);
            Table table = openTable();

            Entry entry = table.newEntry();
            entry.set("account", account);
            if(msg.sender == master){
                entry.set("asset_value", int256(asset_value));
            }else{
                entry.set("asset_value", int256(0));
            }
            // 插入
            int count = table.insert(account, entry);
            if (count == 1) {
                // 成功
                ret_code = 0;
                if (msg.sender == master){
                    ret_code = 1;
                }
            } else {
                // 失败? 无权限或者其他错误
                ret_code = -2;
            }
        } else {
            // 账户已存在
            ret_code = -1;
        }

        emit RegisterEvent(ret_code, account, asset_value);

        return ret_code;
    }

    /*
    描述 : 资产转移
    参数 ：
            from_account : 转移资产账户
            to_account ： 接收资产账户
            amount ： 转移金额
    返回值：
            0  资产转移成功
            -1 转移资产账户不存在
            -2 接收资产账户不存在
            -3 金额不足
            -4 金额溢出
            -5 其他错误
    */
    function transfer(string memory to_account, uint256 amount) public returns(int256) {
        // 查询转移资产账户信息
        string memory from_account = addr2name[msg.sender];
        int ret_code = 0;
        int256 ret = 0;
        uint256 from_asset_value = 0;
        uint256 to_asset_value = 0;
        
        // 转移账户是否存在?
        (ret, from_asset_value) = select(from_account);
        if(ret != 0) {
            ret_code = -1;
            // 转移账户不存在
            emit TransferEvent(ret_code, from_account, to_account, amount);
            return ret_code;

        }

        // 接受账户是否存在?
        (ret, to_asset_value) = select(to_account);
        if(ret != 0) {
            ret_code = -2;
            // 接收资产的账户不存在
            emit TransferEvent(ret_code, from_account, to_account, amount);
            return ret_code;
        }

        if(from_asset_value < amount) {
            ret_code = -3;
            // 转移资产的账户金额不足
            emit TransferEvent(ret_code, from_account, to_account, amount);
            return ret_code;
        }

        if (to_asset_value + amount < to_asset_value) {
            ret_code = -4;
            // 接收账户金额溢出
            emit TransferEvent(ret_code, from_account, to_account, amount);
            return ret_code;
        }

        Table table = openTable();

        Entry entry0 = table.newEntry();
        entry0.set("account", from_account);
        entry0.set("asset_value", int256(from_asset_value - amount));
        // 更新转账账户
        int count = table.update(from_account, entry0, table.newCondition());
        if(count != 1) {
            ret_code = -5;
            // 失败? 无权限或者其他错误?
            emit TransferEvent(ret_code, from_account, to_account, amount);
            return ret_code;
        }

        Entry entry1 = table.newEntry();
        entry1.set("account", to_account);
        entry1.set("asset_value", int256(to_asset_value + amount));
        // 更新接收账户
        table.update(to_account, entry1, table.newCondition());

        emit TransferEvent(ret_code, from_account, to_account, amount);

        return ret_code;
    }

    function pay() public returns(int256){
        Table table = openTable();
        for(uint i = 0; i < accountList.length; i++){
            Entry entry = table.newEntry();
            entry.set("account", accountList[i]);
            entry.set("asset_value", int256(0));
            table.update(accountList[i], entry, table.newCondition());
        }

        emit PayEvent(0);
        return 0;
    }


}