/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SaveEditor;

/**
 *
 * @author Kristian
 */
public class Item {
	
	public int index;
	public int id;
	public int quantity;
	public int quality;
	
	public Item( String data ){
		String sIndex = data.split("=")[0].substring(4);
		data = data.split("=")[1];
		data = data.substring(1, data.length()-1);
		String sID = data.split(":")[0];
		String sQuantity = data.split(":")[1];
		
		index = Integer.parseInt(sIndex);
		id = Integer.parseInt(sID);
		quality = id / 1000;
		id = id % 1000;
		quantity = Integer.parseInt(sQuantity);		
	}
	
	public String output(){
		return String.format("slot%d=\"%d:%d:\"\n", index, (quality * 1000) + id, quantity);
	}

}

