package dnaKeyManagerversion1;

import java.security.Key;


public interface DnaKey extends java.security.Key {
	Key getKey();
	String getDnaKey();
	String getOtpKey();
}
