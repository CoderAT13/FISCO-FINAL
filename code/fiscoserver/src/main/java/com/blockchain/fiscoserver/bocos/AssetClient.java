package com.blockchain.fiscoserver.bocos;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.fisco.bcos.asset.contract.Asset;
import org.fisco.bcos.asset.contract.Asset.PayEventEventResponse;
import org.fisco.bcos.asset.contract.Asset.RegisterEventEventResponse;
import org.fisco.bcos.asset.contract.Asset.TransferEventEventResponse;
import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.Keys;
import org.fisco.bcos.web3j.protocol.Web3j;

import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple2;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class AssetClient {

	static Logger logger = LoggerFactory.getLogger(AssetClient.class);

	private Web3j web3j;

	private Credentials credentials;

	public Web3j getWeb3j() {
		return web3j;
	}

	public void setWeb3j(Web3j web3j) {
		this.web3j = web3j;
	}

	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	public void recordAssetAddr(String address) throws FileNotFoundException, IOException {
		Properties prop = new Properties();
		prop.setProperty("address", address);
		final Resource contractResource = new ClassPathResource("contract.properties");
		FileOutputStream fileOutputStream = new FileOutputStream(contractResource.getFile());
		prop.store(fileOutputStream, "contract address");
	}

	public String loadAssetAddr() throws Exception {
		// load Asset contact address from contract.properties
		Properties prop = new Properties();
		final Resource contractResource = new ClassPathResource("contract.properties");
		prop.load(contractResource.getInputStream());

		String contractAddress = prop.getProperty("address");
		if (contractAddress == null || contractAddress.trim().equals("")) {
			throw new Exception(" load Asset contract address failed, please deploy it first. ");
		}
		logger.info(" load Asset address from contract.properties, address is {}", contractAddress);
		return contractAddress;
	}

	public void initialize(Web3j web3j, Service service) throws Exception {
		
		Credentials credentials = Credentials.create(Keys.createEcKeyPair());

		setCredentials(credentials);
		setWeb3j(web3j);

	}

	private static BigInteger gasPrice = new BigInteger("30000000");
	private static BigInteger gasLimit = new BigInteger("30000000");
	


 	public void deployAssetAndRecordAddr() {

		try {
			Asset asset = Asset.deploy(web3j, credentials, new StaticGasProvider(gasPrice, gasLimit)).send();
			System.out.println(" deploy Asset success, contract address is " + asset.getContractAddress());

			recordAssetAddr(asset.getContractAddress());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println(" deploy Asset contract failed, error message is  " + e.getMessage());
		}
	}
 	
	public Integer senderLogin() {
		BigInteger result = new BigInteger("0");
		try {
			String contractAddress = loadAssetAddr();
			
			Asset asset = Asset.load(contractAddress, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
			result = asset.login().send();
		} catch (Exception e) {
			
		}
		return Integer.valueOf(result.toString());
	}
	

	public Map<String, Integer> queryAssetAmount(String assetAccount) {
		Integer status = 0;
		Integer value = 0;
		try {
			String contractAddress = loadAssetAddr();

			Asset asset = Asset.load(contractAddress, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
			Tuple2<BigInteger, BigInteger> result = asset.select(assetAccount).send();
			status = Integer.valueOf(result.getValue1().toString());
			if (result.getValue1().compareTo(new BigInteger("0")) == 0) {
				value = Integer.valueOf(result.getValue2().toString());
				System.out.printf(" asset account %s, value %s \n", assetAccount, result.getValue2());
			} else {
				System.out.printf(" %s asset account is not exist \n", assetAccount);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			logger.error(" queryAssetAmount exception, error message is {}", e.getMessage());

			System.out.printf(" query asset account failed, error message is %s\n", e.getMessage());
		}
		Map<String, Integer> result = new HashMap<>(3);
		result.put("status", status);
		result.put("amount", value);
		return result;
	}

	public Integer registerAssetAccount(String assetAccount, BigInteger amount) {
		Integer status = 0;
		try {
			String contractAddress = loadAssetAddr();

			Asset asset = Asset.load(contractAddress, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
			TransactionReceipt receipt = asset.register(assetAccount, amount).send();
			List<RegisterEventEventResponse> response = asset.getRegisterEventEvents(receipt);
			if (!response.isEmpty()) {
				if (response.get(0).ret.compareTo(new BigInteger("0")) == 0) {
					System.out.printf(" register asset account success => asset: %s, value: %s \n", assetAccount,
							amount);
				} else {
					System.out.printf(" register asset account failed, ret code is %s \n",
							response.get(0).ret.toString());
				}
				status = Integer.valueOf(response.get(0).ret.toString());
			} else {
				System.out.println(" event log not found, maybe transaction not exec. ");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();

			logger.error(" registerAssetAccount exception, error message is {}", e.getMessage());
			System.out.printf(" register asset account failed, error message is %s\n", e.getMessage());
		}
		return status;
	}

	public Integer transferAsset(String toAssetAccount, BigInteger amount) {
		Integer status = 0;
		try {
			String contractAddress = loadAssetAddr();
			Asset asset = Asset.load(contractAddress, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
			TransactionReceipt receipt = asset.transfer(toAssetAccount, amount).send();
			List<TransferEventEventResponse> response = asset.getTransferEventEvents(receipt);
			if (!response.isEmpty()) {
				if (response.get(0).ret.compareTo(new BigInteger("0")) == 0) {
					System.out.printf(" transfer success =>  to_asset: %s, amount: %s \n",
							 toAssetAccount, amount);
				} else {
					System.out.printf(" transfer asset account failed, ret code is %s \n",
							response.get(0).ret.toString());
				}
				status = Integer.valueOf(response.get(0).ret.toString());
			} else {
				System.out.println(" event log not found, maybe transaction not exec. ");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();

			logger.error(" registerAssetAccount exception, error message is {}", e.getMessage());
			System.out.printf(" register asset account failed, error message is %s\n", e.getMessage());
		}
		return status;
	}
	
	public Integer payAsset() {
		Integer status = 0;
		try {
			String contractAddress = loadAssetAddr();
			Asset asset = Asset.load(contractAddress, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
			TransactionReceipt receipt = asset.pay().send();
			List<PayEventEventResponse> response = asset.getPayEventEvents(receipt);
			if (!response.isEmpty()) {
				if (response.get(0).ret.compareTo(new BigInteger("0")) == 0) {
					
				} else {
					System.out.printf(" transfer asset account failed, ret code is %s \n",
							response.get(0).ret.toString());
				}
				status = Integer.valueOf(response.get(0).ret.toString());
			} else {
				System.out.println(" event log not found, maybe transaction not exec. ");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();

			logger.error(" registerAssetAccount exception, error message is {}", e.getMessage());
			System.out.printf(" register asset account failed, error message is %s\n", e.getMessage());
		}
		return status;
	}
}