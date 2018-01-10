package BPlusTree;

import java.util.ArrayList;
import java.util.List;
import BPlusTree.Key;

public class LeafKey extends Key {

	List<String> leafData = new ArrayList<String>();

	//useful for printing output
	public List<String> getLeafData() {
		return leafData;
	}

	//for new key value pair in leaf node
	public LeafKey(double keyNew, String dataNew) {

		super(keyNew, null, null);

		leafData.add(dataNew);

	}

	public LeafKey() {
		//set default key value
		leafData = new ArrayList<String>();
	}	
}
