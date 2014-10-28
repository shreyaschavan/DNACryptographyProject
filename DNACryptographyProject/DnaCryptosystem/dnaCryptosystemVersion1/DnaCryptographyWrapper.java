package dnaCryptosystemVersion1;

import java.security.InvalidKeyException;
import java.security.Key;

import exceptions.InvalidPlaintextException;

public class DnaCryptographyWrapper implements CryptoSystem {

	DnaCryptography dnaCryptography;

	public DnaCryptographyWrapper() {
		dnaCryptography = new DnaCryptography();
	}
	@Override
	public Data doEncrypt(Key k, Data plainText) throws InvalidKeyException, InvalidPlaintextException {
		return dnaCryptography.doEncrypt(k, plainText);
	}

	@Override
	public Data doDecrypt(Key k, Data cipherText) throws InvalidKeyException {
		// TODO Auto-generated method stub
		return dnaCryptography.doDecrypt(k, cipherText);
	}

}
