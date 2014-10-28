package dnaCryptosystemVersion1;

import java.security.InvalidKeyException;
import java.security.Key;

import exceptions.InvalidPlaintextException;

public interface CryptoSystem {

	Data doEncrypt(Key k,Data plainText) throws InvalidKeyException, InvalidPlaintextException;
	Data doDecrypt(Key k,Data cipherText) throws InvalidKeyException;
}
