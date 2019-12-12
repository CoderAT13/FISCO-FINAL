package org.fisco.bcos.asset.contract;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.fisco.bcos.channel.client.TransactionSucCallback;
import org.fisco.bcos.channel.event.filter.EventLogPushWithDecodeCallback;
import org.fisco.bcos.web3j.abi.EventEncoder;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Event;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.abi.datatypes.Utf8String;
import org.fisco.bcos.web3j.abi.datatypes.generated.Int256;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint256;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
import org.fisco.bcos.web3j.protocol.core.methods.response.Log;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple2;
import org.fisco.bcos.web3j.tx.Contract;
import org.fisco.bcos.web3j.tx.TransactionManager;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.fisco.bcos.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version none.
 */
@SuppressWarnings("unchecked")
public class Asset extends Contract {
    public static String BINARY = "60806040523480156200001157600080fd5b506200002b62000031640100000000026401000000009004565b6200018c565b600061100190508073ffffffffffffffffffffffffffffffffffffffff166356004b6a6040518163ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018060200180602001848103845260078152602001807f745f617373657400000000000000000000000000000000000000000000000000815250602001848103835260078152602001807f6163636f756e74000000000000000000000000000000000000000000000000008152506020018481038252600b8152602001807f61737365745f76616c75650000000000000000000000000000000000000000008152506020019350505050602060405180830381600087803b1580156200014b57600080fd5b505af115801562000160573d6000803e3d6000fd5b505050506040513d60208110156200017757600080fd5b81019080805190602001909291905050505050565b612981806200019c6000396000f30060806040526004361061006d576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806312cb70c7146100725780631b9265b8146100f9578063b34e97e814610124578063ea87152b1461014f578063fcd7e3c1146101d6575b600080fd5b34801561007e57600080fd5b506100e3600480360381019080803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091929192908035906020019092919050505061025a565b6040518082815260200191505060405180910390f35b34801561010557600080fd5b5061010e61146d565b6040518082815260200191505060405180910390f35b34801561013057600080fd5b506101396119e5565b6040518082815260200191505060405180910390f35b34801561015b57600080fd5b506101c0600480360381019080803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929080359060200190929190505050611b9e565b6040518082815260200191505060405180910390f35b3480156101e257600080fd5b5061023d600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050612357565b604051808381526020018281526020019250505060405180910390f35b60006060600080600080600080600080600260003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000208054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561033d5780601f106103125761010080835404028352916020019161033d565b820191906000526020600020905b81548152906001019060200180831161032057829003601f168201915b505050505098506000975060009650600095506000945061035d89612357565b80975081985050506000871415156104b3577fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff97507f8f6b9fa4d4bf04c7c1c3242d4a5c59ba22525b6761cf89e44becb27c606154bd888a8e8e604051808581526020018060200180602001848152602001838103835286818151815260200191508051906020019080838360005b838110156104075780820151818401526020810190506103ec565b50505050905090810190601f1680156104345780820380516001836020036101000a031916815260200191505b50838103825285818151815260200191508051906020019080838360005b8381101561046d578082015181840152602081019050610452565b50505050905090810190601f16801561049a5780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390a187995061145e565b6104bc8c612357565b8096508198505050600087141515610612577ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffe97507f8f6b9fa4d4bf04c7c1c3242d4a5c59ba22525b6761cf89e44becb27c606154bd888a8e8e604051808581526020018060200180602001848152602001838103835286818151815260200191508051906020019080838360005b8381101561056657808201518184015260208101905061054b565b50505050905090810190601f1680156105935780820380516001836020036101000a031916815260200191505b50838103825285818151815260200191508051906020019080838360005b838110156105cc5780820151818401526020810190506105b1565b50505050905090810190601f1680156105f95780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390a187995061145e565b8a86101561075e577ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffd97507f8f6b9fa4d4bf04c7c1c3242d4a5c59ba22525b6761cf89e44becb27c606154bd888a8e8e604051808581526020018060200180602001848152602001838103835286818151815260200191508051906020019080838360005b838110156106b2578082015181840152602081019050610697565b50505050905090810190601f1680156106df5780820380516001836020036101000a031916815260200191505b50838103825285818151815260200191508051906020019080838360005b838110156107185780820151818401526020810190506106fd565b50505050905090810190601f1680156107455780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390a187995061145e565b848b860110156108ac577ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffc97507f8f6b9fa4d4bf04c7c1c3242d4a5c59ba22525b6761cf89e44becb27c606154bd888a8e8e604051808581526020018060200180602001848152602001838103835286818151815260200191508051906020019080838360005b838110156108005780820151818401526020810190506107e5565b50505050905090810190601f16801561082d5780820380516001836020036101000a031916815260200191505b50838103825285818151815260200191508051906020019080838360005b8381101561086657808201518184015260208101905061084b565b50505050905090810190601f1680156108935780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390a187995061145e565b6108b46127c1565b93508373ffffffffffffffffffffffffffffffffffffffff166313db93466040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b15801561091a57600080fd5b505af115801561092e573d6000803e3d6000fd5b505050506040513d602081101561094457600080fd5b810190808051906020019092919050505092508273ffffffffffffffffffffffffffffffffffffffff1663e942b5168a6040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808060200180602001838103835260078152602001807f6163636f756e7400000000000000000000000000000000000000000000000000815250602001838103825284818151815260200191508051906020019080838360005b83811015610a175780820151818401526020810190506109fc565b50505050905090810190601f168015610a445780820380516001836020036101000a031916815260200191505b509350505050600060405180830381600087803b158015610a6457600080fd5b505af1158015610a78573d6000803e3d6000fd5b505050508273ffffffffffffffffffffffffffffffffffffffff16632ef8ba748c88036040518263ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018381526020018281038252600b8152602001807f61737365745f76616c756500000000000000000000000000000000000000000081525060200192505050600060405180830381600087803b158015610b2657600080fd5b505af1158015610b3a573d6000803e3d6000fd5b505050508373ffffffffffffffffffffffffffffffffffffffff1663bf2b70a18a858773ffffffffffffffffffffffffffffffffffffffff16637857d7c96040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b158015610bc057600080fd5b505af1158015610bd4573d6000803e3d6000fd5b505050506040513d6020811015610bea57600080fd5b81019080805190602001909291905050506040518463ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828103825285818151815260200191508051906020019080838360005b83811015610cca578082015181840152602081019050610caf565b50505050905090810190601f168015610cf75780820380516001836020036101000a031916815260200191505b50945050505050602060405180830381600087803b158015610d1857600080fd5b505af1158015610d2c573d6000803e3d6000fd5b505050506040513d6020811015610d4257600080fd5b81019080805190602001909291905050509150600182141515610ea3577ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffb97507f8f6b9fa4d4bf04c7c1c3242d4a5c59ba22525b6761cf89e44becb27c606154bd888a8e8e604051808581526020018060200180602001848152602001838103835286818151815260200191508051906020019080838360005b83811015610df7578082015181840152602081019050610ddc565b50505050905090810190601f168015610e245780820380516001836020036101000a031916815260200191505b50838103825285818151815260200191508051906020019080838360005b83811015610e5d578082015181840152602081019050610e42565b50505050905090810190601f168015610e8a5780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390a187995061145e565b8373ffffffffffffffffffffffffffffffffffffffff166313db93466040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b158015610f0757600080fd5b505af1158015610f1b573d6000803e3d6000fd5b505050506040513d6020811015610f3157600080fd5b810190808051906020019092919050505090508073ffffffffffffffffffffffffffffffffffffffff1663e942b5168d6040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808060200180602001838103835260078152602001807f6163636f756e7400000000000000000000000000000000000000000000000000815250602001838103825284818151815260200191508051906020019080838360005b83811015611004578082015181840152602081019050610fe9565b50505050905090810190601f1680156110315780820380516001836020036101000a031916815260200191505b509350505050600060405180830381600087803b15801561105157600080fd5b505af1158015611065573d6000803e3d6000fd5b505050508073ffffffffffffffffffffffffffffffffffffffff16632ef8ba748c87016040518263ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018381526020018281038252600b8152602001807f61737365745f76616c756500000000000000000000000000000000000000000081525060200192505050600060405180830381600087803b15801561111357600080fd5b505af1158015611127573d6000803e3d6000fd5b505050508373ffffffffffffffffffffffffffffffffffffffff1663bf2b70a18d838773ffffffffffffffffffffffffffffffffffffffff16637857d7c96040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b1580156111ad57600080fd5b505af11580156111c1573d6000803e3d6000fd5b505050506040513d60208110156111d757600080fd5b81019080805190602001909291905050506040518463ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828103825285818151815260200191508051906020019080838360005b838110156112b757808201518184015260208101905061129c565b50505050905090810190601f1680156112e45780820380516001836020036101000a031916815260200191505b50945050505050602060405180830381600087803b15801561130557600080fd5b505af1158015611319573d6000803e3d6000fd5b505050506040513d602081101561132f57600080fd5b8101908080519060200190929190505050507f8f6b9fa4d4bf04c7c1c3242d4a5c59ba22525b6761cf89e44becb27c606154bd888a8e8e604051808581526020018060200180602001848152602001838103835286818151815260200191508051906020019080838360005b838110156113b657808201518184015260208101905061139b565b50505050905090810190601f1680156113e35780820380516001836020036101000a031916815260200191505b50838103825285818151815260200191508051906020019080838360005b8381101561141c578082015181840152602081019050611401565b50505050905090810190601f1680156114495780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390a18799505b50505050505050505092915050565b60008060008061147b6127c1565b9250600091505b6000805490508210156119a3578273ffffffffffffffffffffffffffffffffffffffff166313db93466040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b1580156114f357600080fd5b505af1158015611507573d6000803e3d6000fd5b505050506040513d602081101561151d57600080fd5b810190808051906020019092919050505090508073ffffffffffffffffffffffffffffffffffffffff1663e942b51660008481548110151561155b57fe5b906000526020600020016040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808060200180602001838103835260078152602001807f6163636f756e74000000000000000000000000000000000000000000000000008152506020018381038252848181546001816001161561010002031660029004815260200191508054600181600116156101000203166002900480156116515780601f1061162657610100808354040283529160200191611651565b820191906000526020600020905b81548152906001019060200180831161163457829003601f168201915b50509350505050600060405180830381600087803b15801561167257600080fd5b505af1158015611686573d6000803e3d6000fd5b505050508073ffffffffffffffffffffffffffffffffffffffff16632ef8ba7460006040518263ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018381526020018281038252600b8152602001807f61737365745f76616c756500000000000000000000000000000000000000000081525060200192505050600060405180830381600087803b15801561173357600080fd5b505af1158015611747573d6000803e3d6000fd5b505050508273ffffffffffffffffffffffffffffffffffffffff1663bf2b70a160008481548110151561177657fe5b90600052602060002001838673ffffffffffffffffffffffffffffffffffffffff16637857d7c96040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b1580156117e557600080fd5b505af11580156117f9573d6000803e3d6000fd5b505050506040513d602081101561180f57600080fd5b81019080805190602001909291905050506040518463ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018281038252858181546001816001161561010002031660029004815260200191508054600181600116156101000203166002900480156119385780601f1061190d57610100808354040283529160200191611938565b820191906000526020600020905b81548152906001019060200180831161191b57829003601f168201915b5050945050505050602060405180830381600087803b15801561195a57600080fd5b505af115801561196e573d6000803e3d6000fd5b505050506040513d602081101561198457600080fd5b8101908080519060200190929190505050508180600101925050611482565b7f5f6c131cabf632b3b05d9c2b0c9b4f12a87da287437b507def756671b8638ca260006040518082815260200191505060405180910390a16000935050505090565b60008073ffffffffffffffffffffffffffffffffffffffff166001600260003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206040518082805460018160011615610100020316600290048015611a9a5780601f10611a78576101008083540402835291820191611a9a565b820191906000526020600020905b815481529060010190602001808311611a86575b5050915050908152602001604051809103902060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16141515611b455773baf9163da71f944b2b26a8cbe6484563cbe3eb4773ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415611b3c5760039050611b9b565b60019050611b9b565b73baf9163da71f944b2b26a8cbe6484563cbe3eb4773ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415611b965760029050611b9b565b600090505b90565b60008060008060008060008095506000945060009350611bbd89612357565b8095508196505050600085141515612278573360018a6040518082805190602001908083835b602083101515611c085780518252602082019150602081019050602083039250611be3565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555088600260003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000209080519060200190611ccc9291906128b0565b506000899080600181540180825580915050906001820390600052602060002001600090919290919091509080519060200190611d0a9291906128b0565b5050611d146127c1565b92508273ffffffffffffffffffffffffffffffffffffffff166313db93466040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b158015611d7a57600080fd5b505af1158015611d8e573d6000803e3d6000fd5b505050506040513d6020811015611da457600080fd5b810190808051906020019092919050505091508173ffffffffffffffffffffffffffffffffffffffff1663e942b5168a6040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808060200180602001838103835260078152602001807f6163636f756e7400000000000000000000000000000000000000000000000000815250602001838103825284818151815260200191508051906020019080838360005b83811015611e77578082015181840152602081019050611e5c565b50505050905090810190601f168015611ea45780820380516001836020036101000a031916815260200191505b509350505050600060405180830381600087803b158015611ec457600080fd5b505af1158015611ed8573d6000803e3d6000fd5b5050505073baf9163da71f944b2b26a8cbe6484563cbe3eb4773ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415611fe9578173ffffffffffffffffffffffffffffffffffffffff16632ef8ba74896040518263ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018381526020018281038252600b8152602001807f61737365745f76616c756500000000000000000000000000000000000000000081525060200192505050600060405180830381600087803b158015611fcc57600080fd5b505af1158015611fe0573d6000803e3d6000fd5b505050506120ab565b8173ffffffffffffffffffffffffffffffffffffffff16632ef8ba7460006040518263ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018381526020018281038252600b8152602001807f61737365745f76616c756500000000000000000000000000000000000000000081525060200192505050600060405180830381600087803b15801561209257600080fd5b505af11580156120a6573d6000803e3d6000fd5b505050505b8273ffffffffffffffffffffffffffffffffffffffff166331afac368a846040518363ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828103825284818151815260200191508051906020019080838360005b8381101561216657808201518184015260208101905061214b565b50505050905090810190601f1680156121935780820380516001836020036101000a031916815260200191505b509350505050602060405180830381600087803b1580156121b357600080fd5b505af11580156121c7573d6000803e3d6000fd5b505050506040513d60208110156121dd57600080fd5b81019080805190602001909291905050509050600181141561224f576000955073baf9163da71f944b2b26a8cbe6484563cbe3eb4773ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141561224a57600195505b612273565b7ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffe95505b61229c565b7fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff95505b7f91c95f04198617c60eaf2180fbca88fc192db379657df0e412a9f7dd4ebbe95d868a8a6040518084815260200180602001838152602001828103825284818151815260200191508051906020019080838360005b8381101561230c5780820151818401526020810190506122f1565b50505050905090810190601f1680156123395780820380516001836020036101000a031916815260200191505b5094505050505060405180910390a185965050505050505092915050565b6000806000806000806123686127c1565b93508373ffffffffffffffffffffffffffffffffffffffff1663e8434e39888673ffffffffffffffffffffffffffffffffffffffff16637857d7c96040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b1580156123eb57600080fd5b505af11580156123ff573d6000803e3d6000fd5b505050506040513d602081101561241557600080fd5b81019080805190602001909291905050506040518363ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828103825284818151815260200191508051906020019080838360005b838110156124c35780820151818401526020810190506124a8565b50505050905090810190601f1680156124f05780820380516001836020036101000a031916815260200191505b509350505050602060405180830381600087803b15801561251057600080fd5b505af1158015612524573d6000803e3d6000fd5b505050506040513d602081101561253a57600080fd5b81019080805190602001909291905050509250600091508273ffffffffffffffffffffffffffffffffffffffff1663949d225d6040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b1580156125b557600080fd5b505af11580156125c9573d6000803e3d6000fd5b505050506040513d60208110156125df57600080fd5b810190808051906020019092919050505060001415612626577fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff82819150955095506127b8565b8273ffffffffffffffffffffffffffffffffffffffff1663846719e060006040518263ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180828152602001915050602060405180830381600087803b15801561269657600080fd5b505af11580156126aa573d6000803e3d6000fd5b505050506040513d60208110156126c057600080fd5b8101908080519060200190929190505050905060008173ffffffffffffffffffffffffffffffffffffffff1663fda69fae6040518163ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180806020018281038252600b8152602001807f61737365745f76616c7565000000000000000000000000000000000000000000815250602001915050602060405180830381600087803b15801561277557600080fd5b505af1158015612789573d6000803e3d6000fd5b505050506040513d602081101561279f57600080fd5b8101908080519060200190929190505050819150955095505b50505050915091565b600080600061100191508173ffffffffffffffffffffffffffffffffffffffff1663f23f63c96040518163ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018080602001828103825260078152602001807f745f617373657400000000000000000000000000000000000000000000000000815250602001915050602060405180830381600087803b15801561286b57600080fd5b505af115801561287f573d6000803e3d6000fd5b505050506040513d602081101561289557600080fd5b81019080805190602001909291905050509050809250505090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106128f157805160ff191683800117855561291f565b8280016001018555821561291f579182015b8281111561291e578251825591602001919060010190612903565b5b50905061292c9190612930565b5090565b61295291905b8082111561294e576000816000905550600101612936565b5090565b905600a165627a7a723058203d174c3440a55111847acf7b577e0850a867efa95225349d7ad671b4e259c6880029";

    public static final String ABI = "[{\"constant\":false,\"inputs\":[{\"name\":\"to_account\",\"type\":\"string\"},{\"name\":\"amount\",\"type\":\"uint256\"}],\"name\":\"transfer\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[],\"name\":\"pay\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"login\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"account\",\"type\":\"string\"},{\"name\":\"asset_value\",\"type\":\"uint256\"}],\"name\":\"register\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"account\",\"type\":\"string\"}],\"name\":\"select\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"},{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"ret\",\"type\":\"int256\"},{\"indexed\":false,\"name\":\"account\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"asset_value\",\"type\":\"uint256\"}],\"name\":\"RegisterEvent\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"ret\",\"type\":\"int256\"},{\"indexed\":false,\"name\":\"from_account\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"to_account\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"amount\",\"type\":\"uint256\"}],\"name\":\"TransferEvent\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"ret\",\"type\":\"int256\"}],\"name\":\"PayEvent\",\"type\":\"event\"}]";

    public static final String FUNC_TRANSFER = "transfer";

    public static final String FUNC_PAY = "pay";

    public static final String FUNC_LOGIN = "login";

    public static final String FUNC_REGISTER = "register";

    public static final String FUNC_SELECT = "select";

    public static final Event REGISTEREVENT_EVENT = new Event("RegisterEvent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event TRANSFEREVENT_EVENT = new Event("TransferEvent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event PAYEVENT_EVENT = new Event("PayEvent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));
    ;

    @Deprecated
    protected Asset(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Asset(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Asset(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Asset(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> transfer(String to_account, BigInteger amount) {
        final Function function = new Function(
                FUNC_TRANSFER, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(to_account), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void transfer(String to_account, BigInteger amount, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_TRANSFER, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(to_account), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String transferSeq(String to_account, BigInteger amount) {
        final Function function = new Function(
                FUNC_TRANSFER, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(to_account), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<TransactionReceipt> pay() {
        final Function function = new Function(
                FUNC_PAY, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void pay(TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_PAY, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String paySeq() {
        final Function function = new Function(
                FUNC_PAY, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<BigInteger> login() {
        final Function function = new Function(FUNC_LOGIN, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> register(String account, BigInteger asset_value) {
        final Function function = new Function(
                FUNC_REGISTER, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(account), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(asset_value)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void register(String account, BigInteger asset_value, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_REGISTER, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(account), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(asset_value)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String registerSeq(String account, BigInteger asset_value) {
        final Function function = new Function(
                FUNC_REGISTER, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(account), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(asset_value)), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<Tuple2<BigInteger, BigInteger>> select(String account) {
        final Function function = new Function(FUNC_SELECT, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(account)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteCall<Tuple2<BigInteger, BigInteger>>(
                new Callable<Tuple2<BigInteger, BigInteger>>() {
                    @Override
                    public Tuple2<BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<BigInteger, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue());
                    }
                });
    }

    public List<RegisterEventEventResponse> getRegisterEventEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(REGISTEREVENT_EVENT, transactionReceipt);
        ArrayList<RegisterEventEventResponse> responses = new ArrayList<RegisterEventEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RegisterEventEventResponse typedResponse = new RegisterEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.ret = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.account = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.asset_value = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void registerRegisterEventEventLogFilter(String fromBlock, String toBlock, List<String> otherTopcs, EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(REGISTEREVENT_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,fromBlock,toBlock,otherTopcs,callback);
    }

    public void registerRegisterEventEventLogFilter(EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(REGISTEREVENT_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,callback);
    }

    public List<TransferEventEventResponse> getTransferEventEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TRANSFEREVENT_EVENT, transactionReceipt);
        ArrayList<TransferEventEventResponse> responses = new ArrayList<TransferEventEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TransferEventEventResponse typedResponse = new TransferEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.ret = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.from_account = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.to_account = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void registerTransferEventEventLogFilter(String fromBlock, String toBlock, List<String> otherTopcs, EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(TRANSFEREVENT_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,fromBlock,toBlock,otherTopcs,callback);
    }

    public void registerTransferEventEventLogFilter(EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(TRANSFEREVENT_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,callback);
    }

    public List<PayEventEventResponse> getPayEventEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(PAYEVENT_EVENT, transactionReceipt);
        ArrayList<PayEventEventResponse> responses = new ArrayList<PayEventEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PayEventEventResponse typedResponse = new PayEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.ret = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void registerPayEventEventLogFilter(String fromBlock, String toBlock, List<String> otherTopcs, EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(PAYEVENT_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,fromBlock,toBlock,otherTopcs,callback);
    }

    public void registerPayEventEventLogFilter(EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(PAYEVENT_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,callback);
    }

    @Deprecated
    public static Asset load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Asset(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Asset load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Asset(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Asset load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Asset(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Asset load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Asset(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Asset> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Asset.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<Asset> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Asset.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Asset> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Asset.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Asset> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Asset.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class RegisterEventEventResponse {
        public Log log;

        public BigInteger ret;

        public String account;

        public BigInteger asset_value;
    }

    public static class TransferEventEventResponse {
        public Log log;

        public BigInteger ret;

        public String from_account;

        public String to_account;

        public BigInteger amount;
    }

    public static class PayEventEventResponse {
        public Log log;

        public BigInteger ret;
    }
}
