package code.id;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.HashMap;

import code.id.lookahead.IDLookAheadObjectInputStream;

/**
 * @author Rahul R Nair
 *
 */

public class IdGenerator implements java.io.Serializable {
	
	// static instance of IdGenerator class globally accessible
	//public static IdGenerator idGenInstance = null;
	
	/* private constructor so that class cannot be instantiated from outside this class */
	private IdGenerator(){
		
	}
	
//	  /* synchronized method to control simultaneous access and to return instance of IdGenerator class */
//	  synchronized  public static IdGenerator getInstance() 
//	  {
//	    if (idGenInstance == null) 
//	    {
//	      // if instance is null, initialize
//	    	idGenInstance = new IdGenerator();
//	    }
//	    return idGenInstance;
//	  }
	
	  /* Inner class to provide instance of IdGenerator class */
	  private static class BillPughSingleton
	  {
	    private static final IdGenerator INSTANCE = new IdGenerator();
	  }
	 
	  public static IdGenerator getInstance() 
	  {
	    return BillPughSingleton.INSTANCE;
	  }

	/*
	 * To guarantee a consistent serialVersionUID value across different java
	 * compiler implementations, a serializable class must declare an explicit
	 * serialVersionUID value.
	 */
	private static final long serialVersionUID = 42L;
	
	public final String defaultKey = "defaultSerialKey";
	public static String serialFilePath = "./idList.ser";
	public HashMap<String, Integer> idList = new HashMap<String, Integer>();
	
	/**
	 * @return the defaultKey
	 */
	public String getDefaultKey() {
		return defaultKey;
	}
	
	/**
	 * @return the serialFilePath
	 */
	public static String getSerialFilePath() {
		return serialFilePath;
	}

	/**
	 * @param serialFilePath the serialFilePath to set
	 */
	public static void setSerialFilePath(String serialFilePath) {
		IdGenerator.serialFilePath = serialFilePath;
	}

	/**
	 * @return the idList
	 */
	public HashMap<String, Integer> getIdList() {
		return idList;
	}

	/**
	 * @param idList
	 *            the idList to set
	 */
	public void setIdList(HashMap<String, Integer> idList) {
		this.idList = idList;
	}	

	/**
	 * @param key
	 */
	public int generateNewRecord(String key) {
		int generateStatus = 0;
		if (idList.containsKey(key)) {
			System.out.println("Record already exists!");
		}
		else{
			idList.put(key, 0);
			generateStatus = 1;
		}
		
		return generateStatus;
	}

	/**
	 * @param key -- (the new Id to be obtained for which record book)
	 * @return
	 */
	public String getNewId(String key) {
		String newId = "";
		if (idList.containsKey(key)) {
			idList.replace(key, idList.get(key) + 1);
			newId = key + idList.get(key);
		}
		return newId;
	}
	
	/**
	 * @param key -- (the current Id to be obtained for which record book)
	 * @return
	 */
	public String getCurrId(String key) {
		String newId = "";
		if (idList.containsKey(key)) {
			newId = key + idList.get(key);
		}
		return newId;
	}

	/**
	 * @param savePath
	 */
	public void saveToDisk(String savePath) {
		try {
			FileOutputStream file0 = null;
			ObjectOutputStream oFile = null;
			file0 = new FileOutputStream(savePath);
			oFile = new ObjectOutputStream(file0);

			// Method for serialization of object
			oFile.writeObject(this);
			oFile.close();
			file0.close();
			System.out.println("Object has been serialized!!!");
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	/**
	 * @param filePath
	 */
	public static IdGenerator importFromDisk(String filePath) {
		IdGenerator idG = null;
		
		// Deserialization
		try {
			// Reading the object from a file
			FileInputStream file = new FileInputStream(filePath);
			
			// We use IDLookAheadObjectInputStream instead of InputStream
			ObjectInputStream in = new IDLookAheadObjectInputStream(file);

			// Method for deserialization of object
			 idG = (IdGenerator) in.readObject();
			 BillPughSingleton.INSTANCE.setIdList(idG.getIdList());

			in.close();
			file.close();

			System.out.println("Object has been deserialized ");
		}

		catch (FileNotFoundException ex) {
			System.out.println("FileNotFoundException is caught!");
			System.out.println(ex.getMessage());
		}
		
		catch (EOFException ex) {
			System.out.println("EOFException is caught!");
			System.out.println("Object is not serialized or got corrupted!");
			System.out.println(ex.getMessage());
		}
		
		catch(StreamCorruptedException ex){
			System.out.println("StreamCorruptedException is caught!");
			System.out.println("Object is not serialized properly or got corrupted!");
			System.out.println(ex.getMessage());
		}
		
		catch (IOException ex) {
			System.out.println(ex.getMessage());
		}

		catch (ClassNotFoundException ex) {
			System.out.println("ClassNotFoundException is caught!");
			System.out.println(ex.getMessage());
		}
		
		finally{
			if (null == idG) {
				BillPughSingleton.INSTANCE.generateNewRecord(BillPughSingleton.INSTANCE.getDefaultKey());
				BillPughSingleton.INSTANCE.saveToDisk(filePath);
			}
		}
		return BillPughSingleton.INSTANCE;
	}


}