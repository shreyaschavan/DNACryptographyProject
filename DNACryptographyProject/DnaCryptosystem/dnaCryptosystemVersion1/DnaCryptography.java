package dnaCryptosystemVersion1;


//dnautil included in build path for exceptions

import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import dnaKeyManagerversion1.DnaKey;
import exceptions.InvalidPlaintextException;

	//exception for invalid key
	//exception for invalid length of data 


public class DnaCryptography {
	final static int BLOCKSIZE = 960;

	final static int[] MASKS = { 0b00000011 ,//for 1st only mask
		0b00001100,0b00110000,0b11000000};
	//for 4th shift without masking

	final static int A = 0b00000000;
	final static int C = 0b00000001;
	final static int G = 0b00000010;
	final static int T = 0b00000011;

	static ByteBuffer demo;

	private static char complement(Character c) {
		switch (c) {
		case 'A': return 'T';
		case 'T': return 'A';
		case 'G': return 'C';
		case 'C': return 'G';
		}
		return (Character) null;
	}

	private void validateKey(Key k) throws InvalidKeyException {
		if(!(k instanceof DnaKey)){
			throw new InvalidKeyException();
		}
		
	}
	
	private boolean validateCiphertext(Data cipherText) {
		List<Byte[]> pt = cipherText.getData();
		Iterator<Byte[]> it = pt.iterator();
		int i=0;
		while(it.hasNext()){			
			if(it.next().length > 960){
				return false;
				//InvalidPlintextException e = new InvalidPlintextException
				//e.setMessage("invalid ciphertext")
				//throw e;
			}
			i++;
		}

		return true;

	}
	
	private void validatePlaintext(Data plainText) throws InvalidPlaintextException  {
		List<Byte[]> pt = plainText.getData();
		Iterator<Byte[]> it = pt.iterator();
		int i=0;
		while(it.hasNext()){			
			if(it.next().length > 14)
				throw new InvalidPlaintextException("invalid length of text at index "+i+" max alloable len: 14 bytes");
			i++;
		}
	}
	
	public Data doEncrypt(Key k, Data plainText) throws InvalidKeyException, InvalidPlaintextException {
		
		validateKey(k);
		validatePlaintext(plainText);
		
		
		ByteBuffer bb = ByteBuffer.allocate(BLOCKSIZE/8);
		ArrayList<Byte[]> cip = new ArrayList<Byte[]>();
		List<Byte[]> pt = plainText.getData();
		Iterator<Byte[]> it = pt.iterator();

		System.out.println("DNA key :-");
		System.out.print(((DnaKey)k).getDnaKey());

		while(it.hasNext()){

			//create buffer for processing
			Byte[] pt1 = it.next();
			int bbSize = pt1.length;
			bb.put(0,(byte)bbSize);
			for (int i = 0; i < pt1.length; i++) {
				bb.put(i+1, pt1[i]);
			}		
			byte b = 0;
			//initialize remaining to 0
			for(int i=bbSize+1;i<BLOCKSIZE/8;i++)
				bb.put(i,b);


			byte temp = 0;
			int temp2 = 1,j,i;
			StringBuffer dna = new StringBuffer(((DnaKey)k).getDnaKey());

			//DNA hybridization
			System.out.println("cip. DNA:");
			for(i=0;i<bbSize+1;i++){
				temp = bb.get(i);

				for(temp2=1,j=0;j<8;j++,temp2=temp2<<1){
					if((temp & temp2)!=0){
						for(int l=0;l<4;l++){
							dna.setCharAt(i*32+j*4+l, complement(dna.charAt(i*32+j*4+l)));
						}
					}
				}
			}



			//DNA encryption		
			System.out.println("DNA to bin:");
			ByteBuffer ct = ByteBuffer.allocate(BLOCKSIZE);
			for (i=0; i < dna.length()/4; i++) {
				temp2=0;
				for(j=0;j<4;j++){
					switch(dna.charAt(i*4+j)){
					case 'A': temp = 0b00; break;
					case 'C': temp = 0b01; break;
					case 'G': temp = 0b10; break;
					case 'T': temp = 0b11; break;
					}
					temp2 = temp2 | temp;
					if(j!=3)
						temp2 = temp2<<2;	
				}
				ct.put(i, (byte)temp2);
			}		




			//OTP

			byte[] otpKey = ((DnaKey)k).getOtpKey().getBytes();

			System.out.println("OTP key:");
			for (i = 0; i < BLOCKSIZE/8; i++) {
				System.out.print(otpKey[i]);
			}
			System.out.println("\nDNA to Binary:");		
			for (i = 0; i < BLOCKSIZE/8; i++) {
				System.out.print(ct.get(i));
				bb.put(i,(byte)(ct.get(i)^otpKey[i]));
			}

			System.out.println("");
			System.out.println("Final Encrypted String in Bytes:-");
			for (int l = 0; l < bb.array().length; l++) {
				System.out.print(bb.array()[l]);
			}
			System.out.println();
			//demo = bb;
	
			cip.add(ArrayUtils.toObject(bb.array()));
		}

		Data d = Data.getInstance();
		d.setData(cip);
		return d;

	}


	public Data doDecrypt(Key k, Data cipherText) throws InvalidKeyException {
		// TODO Auto-generated method stub
		
		validateKey(k);
		
		//create buffer
		System.out.println("\nReceived text in Bytes");
		System.out.println();
		//		for (int l = 0; l < cipherText.getBytes().length; l++) {
		//			System.out.print(cipherText.getBytes()[l]);
		//		}
		//		System.out.println();
		//		//--------------

		ByteBuffer bb = ByteBuffer.allocate(BLOCKSIZE/8);

		ArrayList<Byte[]> pln = new ArrayList<Byte[]>();

		List<Byte[]> ptxt = cipherText.getData();
		Iterator<Byte[]> it = ptxt.iterator();

		while(it.hasNext()){
			//bb.put(cipherText.getBytes());
			Byte[] tary = it.next();
			for (int i = 0; i < tary.length; i++) {
				bb.put(i,tary[i]);
			}


//			System.out.println("\n\nSize of demo: \n\n"+bb.array().length);
//			System.out.println("\nDec.Before otp");
//			for (int l = 0; l < demo.array().length; l++) {
//				System.out.print(demo.array()[l]);
//			}

			byte b = 0;

			//OTP
			System.out.println("\nafter OTP:");
			byte[] otpKey = ((DnaKey)k).getOtpKey().getBytes();
			for (int i = 0; i <BLOCKSIZE/8; i++) {
				bb.put(i,(byte)(bb.get(i)^otpKey[i]));
				System.out.print(bb.get(i));
			}

			int temp;
			int temp2;
			StringBuffer rcvdDna = new StringBuffer(BLOCKSIZE/2);
			rcvdDna.setLength(BLOCKSIZE/2);
			System.out.println("\nReceived Dna: ");
			for (int i=0; i < bb.capacity(); i++) {
				temp = bb.get(i);
				if(temp<0)
					temp+=256;
				for(int j=3;j>=0;j--){
					temp2 = MASKS[j] & temp;
					temp2 = temp2>>2*j;	
			switch(temp2){
			case A: rcvdDna.setCharAt(i*4+j, 'A'); break;
			case C: rcvdDna.setCharAt(i*4+j, 'C'); break;
			case G: rcvdDna.setCharAt(i*4+j, 'G'); break;
			case T: rcvdDna.setCharAt(i*4+j, 'T'); break;
			}
			System.out.print(rcvdDna.charAt(i*4+j));		
				}
			}

			StringBuffer dnaKey = new StringBuffer(((DnaKey)k).getDnaKey());

			ByteBuffer pt = ByteBuffer.allocate(BLOCKSIZE/8);
			int t = 0;


			System.out.println("");
			for(int i=0;i<15*32;i+=32)
			{
				for(int j=0;j<8;j++){
					if(dnaKey.charAt(i+j*4)!=rcvdDna.charAt(i+j*4+3)){
						t = t | 256;
					}
					t = t>>1;
				}
				pt.put(i/32, (byte)t);
			}


			//return data
			int len;
			len = pt.array()[0];
			Byte []arr = new Byte[len];
			for (int i = 0; i < len; i++) {
				arr[i] = pt.array()[i+1];
			}
			pln.add(arr);
		}
		Data d = Data.getInstance();
		d.setData(pln);
		return d;


	}

}
