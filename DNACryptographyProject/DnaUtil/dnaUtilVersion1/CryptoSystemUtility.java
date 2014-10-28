package dnaUtilVersion1;

import java.security.InvalidKeyException;
import java.security.Key;

import dnaCryptosystemVersion1.Data;
import dnaCryptosystemVersion1.DnaCryptographyWrapper;
import exceptions.InvalidPlaintextException;

public class CryptoSystemUtility{

	static String version;
	static DnaCryptographyWrapper algoHolder;

	static{
		version = "dnaCryptosystemVersion1";
	}


	private CryptoSystemUtility(){
			algoHolder = CryptoSystemFactory.getCryptoSystemInstance(version);
	}
	public static String getVersion() {
		return version;
	}

	public static void setVersion(String version1) {
		version = version1;
			algoHolder = CryptoSystemFactory.getCryptoSystemInstance(version);
	}

	public static Data doEncrypt(Key k, Data d) throws InvalidKeyException, InvalidPlaintextException {
		// TODO Auto-generated method stub
		return algoHolder.doEncrypt(k, d);
	}

	public static Data doDecrypt(Key k, Data cipherText) throws InvalidKeyException {
		// TODO Auto-generated method stub
		return algoHolder.doDecrypt(k, cipherText);
	}
}
