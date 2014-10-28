package dnaCryptosystemVersion1;

import java.io.Serializable;
import java.util.List;

public class Data implements Serializable {

	List<Byte[]> data;
	
	private Data(){}
	
	public static Data getInstance(){
		return new Data();
	}

	public List<Byte[]> getData() {
		return data;
	}

	public void setData(List<Byte[]> data) {
		this.data = data;
	}

		
	
}
