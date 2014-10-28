package dnaKeyManagerversion1;

import java.io.Serializable;
import java.security.Key;

public class KeyWrapper implements DnaKey, Serializable {

	DnaKeyGenerator sysKey;
	public KeyWrapper(){
		sysKey = new DnaKeyGenerator(4);
	}

	@Override
	public Key getKey() {
		return this;
	}

	@Override
	public String getDnaKey() {
		// TODO Auto-generated method stub
		return sysKey.getDnaKey();
	}

	@Override
	public String getOtpKey() {
		// TODO Auto-generated method stub
		return sysKey.getOtpKey();
	}

	@Override
	public String getAlgorithm() {
		// TODO Auto-generated method stub
		return sysKey.getAlgorithm();
	}

	@Override
	public String getFormat() {
		// TODO Auto-generated method stub
		return sysKey.getFormat();
	}

	@Override
	public byte[] getEncoded() {
		// TODO Auto-generated method stub
		return sysKey.getEncoded();
	}


}

