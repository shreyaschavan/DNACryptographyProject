package dnaKeyUtilVersion1;

public class KeyUtilFactory {

	public static KeyUtility getKeyUtilityInstance(String version) {

		try{
			
			//Read from file if class not found throw exception	
			Class c = Class.forName(version+".KeyUtility");
			return (KeyUtility) c.newInstance();
		}
		catch(ClassNotFoundException e){
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
