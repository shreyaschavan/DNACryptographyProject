package dnaKeyManagerversion1;

import java.io.Serializable;

public class DnaKeyGenerator implements Serializable {

	static final int KEYLENGTH = 960;
	/* 
	 * total data = 120 bits
	 * scale = 4
	 * cipher text = 120*scale*2 = 960
	 * OPT key length = 960 bits = 120 bytes
	 * DNA key length = 960/2 = 480 char
	 */
	
	String otpKey;
	String dnaKey;
	int scale;


	DnaKeyGenerator(int scale){
		this.scale = scale;
		setOtpKey();
		setDnaKey();
	}


	private void setDnaKey() {
		char []ssDna = new char[KEYLENGTH/2];
		for(int i=0;i<KEYLENGTH/2;i++)
		{
			double randno= Math.random();
			if(randno<.25)
			{
				ssDna[i] ='A';
				continue;
			}
			else if(randno<.50)
			{
				ssDna[i] ='C';
				continue;
			}
			else if(randno<.75)
			{
				ssDna[i] ='G';
				continue;
			}
			else
			{
				ssDna[i] ='T';
				continue;
			}
		}

		dnaKey = new String(ssDna);
	}


	private void setOtpKey() {
		byte[] key = new byte[KEYLENGTH/8];
		byte tbyte = 0;
		int temp = 0;
		double randno;

		for(int i=0,j=0;i<KEYLENGTH;i++)
		{
			randno = Math.random();
			if(randno <.50)
			{
				temp =1;
				temp = temp << (i%8);
			}
			tbyte = (byte) (tbyte | ((byte)temp));
			temp =0;
			if(i%8 == 0){
				key[j] = (byte)tbyte;
				j++;
				tbyte =0;
			}
		}

		otpKey = new String(key);

		//		for (int i = 0; i < key.length; i++) {
//			System.out.print(" "+key[i]);
//		}
//		System.out.println("\n"+key.length*8);
	}


	public String toString() {
		return "otpKey: " +otpKey +"\n dnaKey: "+ dnaKey;
	}

	public DnaKey getKey() {
		// TODO Auto-generated method stub
		return null;
	}


	public String getDnaKey() {
		// TODO Auto-generated method stub
		return dnaKey;
	}


	public String getOtpKey() {
		// TODO Auto-generated method stub
		return otpKey;
	}


	public String getAlgorithm() {
		// TODO Auto-generated method stub
		return "DNA Cryptography";
	}


	public String getFormat() {
		// TODO Auto-generated method stub
		return null;
	}


	public byte[] getEncoded() {
		// TODO Auto-generated method stub
		return null;
	}

}
