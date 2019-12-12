pragma solidity ^0.4.24;

contract TableFactory {
    function openTable(string memory) public view returns (Table); //open table
    function createTable(string memory,string memory,string memory) public returns(int); //create table
}

//select condition
contract Condition {
    function EQ(string memory, int) public;
    function EQ(string memory, string memory) public;

    function NE(string memory, int) public;
    function NE(string memory, string memory)  public;

    function GT(string memory, int) public;
    function GE(string memory, int) public;

    function LT(string memory, int) public;
    function LE(string memory, int) public;

    function limit(int) public;
    function limit(int, int) public;
}

//one record 
contract Entry {
    function getInt(string memory) public view returns(int);
    function getAddress(string memory) public view returns(address);
    function getBytes64(string memory) public view returns(byte[64] memory);
    function getBytes32(string memory) public view returns(bytes32);
    function getString(string memory) public view returns(string memory);
    
    function set(string memory, int) public;
    function set(string memory, string memory) public;
}

//record sets
contract Entries {
    function get(int) public view returns(Entry);
    function size() public view returns(int);
}

//Table main contract
contract Table {
    //select api
    function select(string memory, Condition) public view returns(Entries);
    //insert api
    function insert(string memory, Entry) public returns(int);
    //update api
    function update(string memory, Entry, Condition) public returns(int);
    //remove api
    function remove(string memory, Condition) public returns(int);
    
    function newEntry() public view returns(Entry);
    function newCondition() public view returns(Condition);
}
