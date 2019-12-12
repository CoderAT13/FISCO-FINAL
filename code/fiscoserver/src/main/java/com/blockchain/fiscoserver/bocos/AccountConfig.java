package com.blockchain.fiscoserver.bocos;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;


import org.fisco.bcos.channel.client.PEMManager;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.ECKeyPair;
import org.fisco.bcos.web3j.crypto.EncryptType;
import org.fisco.bcos.web3j.crypto.gm.GenCredential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "accounts")
public class AccountConfig {

    private String pemFile;
    private String p12File;
    private String password;
    private Credentials credential;

    public Credentials getCredentials(){
        return credential;
        // return loadP12Account();
    }

    // load pem account file
    public Credentials loadPemAccount(String path)
            throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException,
                    NoSuchProviderException, InvalidKeySpecException, UnrecoverableKeyException {
    	File pemFile = new File(path);
        InputStream input = new FileInputStream(pemFile);
        PEMManager pem = new PEMManager();
        pem.load(input);
        ECKeyPair keyPair = pem.getECKeyPair();
        Credentials credentials = GenCredential.create(keyPair.getPrivateKey().toString(16));
        System.out.println(credentials.getAddress());
        this.credential = credentials;
        return credentials;
    }

    /**
     * @return the pemFile
     */
    public String getPemFile() {
        return pemFile;
    }

    /**
     * @param pemFile the pemFile to set
     */
    public void setPemFile(String pemFile) {
        this.pemFile = pemFile;
    }

    /**
     * @return the p12File
     */
    public String getP12File() {
        return p12File;
    }

    /**
     * @param p12File the p12File to set
     */
    public void setP12File(String p12File) {
        this.p12File = p12File;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    
}
