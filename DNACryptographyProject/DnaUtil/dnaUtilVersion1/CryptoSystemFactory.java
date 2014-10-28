package dnaUtilVersion1;

import dnaCryptosystemVersion1.DnaCryptographyWrapper;


public class CryptoSystemFactory {

	public static DnaCryptographyWrapper getCryptoSystemInstance(String version) {
		 try {
			return (DnaCryptographyWrapper) Class.forName(version+".DnaCryptographyWrapper").newInstance();
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
