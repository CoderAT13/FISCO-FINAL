package com.blockchain.fiscoserver.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blockchain.fiscoserver.bocos.AccountConfig;
import com.blockchain.fiscoserver.bocos.AssetClient;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

import org.fisco.bcos.web3j.crypto.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.channel.client.Service;


@RequestMapping("/user")
@RestController
public class HomeController{
	
	@Autowired Web3j web3j;
	@Autowired Service service;
	
	private AccountConfig account;
	
	private AssetClient clientService;
	
	@RequestMapping("/login")
    public Map<String, Integer> login (@RequestPart("file") MultipartFile file) 
            throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException,
            NoSuchProviderException, InvalidKeySpecException, UnrecoverableKeyException ,IOException {
		byte[] bytes = file.getBytes();
		Path path = Paths.get("privateKey.pem");
        Files.write(path,bytes);
        account = new AccountConfig();
        Credentials credentials = account.loadPemAccount(path.toAbsolutePath().toString());
        clientService = new AssetClient();
        try{
        	clientService.initialize(web3j, service);
        	clientService.setCredentials(credentials);
        }catch(Exception e) {
        	System.out.println("init failed");
        }
        Integer status = clientService.senderLogin();
        Map<String, Integer> map = new HashMap<>(3);
        map.put("status", status);
        return map;
    }


    @RequestMapping("/register")
    public Map<String, Integer> register(@RequestParam(value="account",required = true) String name, 
    		@RequestParam(value="amount",required = false) int amount) throws IOException {
    	BigInteger t_amount = new BigInteger(String.valueOf(amount),10);
    	Integer status = clientService.registerAssetAccount(name, t_amount);
    	Map<String, Integer> map = new HashMap<>(3);
        map.put("status", status);
        return map;
    }
    
    @RequestMapping("/transfer")
    public Map<String, Integer> transfer(@RequestParam(value="account",required = true) String name, 
    		@RequestParam(value="amount",required = false) int amount) throws IOException {
    	BigInteger t_amount = new BigInteger(String.valueOf(amount),10);
    	Integer status = clientService.transferAsset(name, t_amount);
    	Map<String, Integer> map = new HashMap<>(3);
        map.put("status", status);
        return map;
    }
    
    @RequestMapping("/search")
    public Map<String, Integer> search(@RequestParam(value="account",required = true) String name)
    		throws IOException{
    	Map<String, Integer> map = clientService.queryAssetAmount(name);
        return map;
    }
    
    @RequestMapping("/pay")
    public Map<String, Integer> pay() throws IOException{
    	Integer status = clientService.payAsset();
    	Map<String, Integer> map = new HashMap<>(3);
        map.put("status", status);
        return map;
    }
}