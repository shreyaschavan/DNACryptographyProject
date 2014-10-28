package dnaKeyUtilVersion1;

import dnaKeyManagerversion1.KeyWrapper;

public class KeyUtility {
	KeyUtility(){
	}

	public java.security.Key getNewKey() {
		return new KeyWrapper().getKey();
	}
}
