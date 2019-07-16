package code.main;

import code.id.IdGenerator;

public class IdGenMain {
    
	public static void main(String args[]){
		
		IdGenerator idObject = IdGenerator.getInstance();
		idObject = IdGenerator.importFromDisk(IdGenerator.getSerialFilePath());

		if (idObject != null) {
			
	        System.out.println(idObject.getIdList().get("default"));
	        System.out.println(idObject.getIdList().get("defaults"));
	        
	        idObject.generateNewRecord("default");
			
			System.out.println(idObject.getNewId("default"));
			System.out.println(idObject.getNewId("default"));
			System.out.println(idObject.getCurrId("first"));
			System.out.println(idObject.getIdList().get("first"));
			
			idObject.saveToDisk(IdGenerator.getSerialFilePath());
		}        
        
	}
    
}