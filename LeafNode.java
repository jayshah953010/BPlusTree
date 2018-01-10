package BPlusTree;

import java.util.ArrayList;
import java.util.List;
import BPlusTree.Node;
import BPlusTree.LeafKey;

public class LeafNode extends Node {

	List<LeafKey> leafKeys;
	LeafNode leftLeafNode;//used for range retrieval
	LeafNode rightLeafNode;

	public LeafNode() {
		leftLeafNode = new LeafNode();
		rightLeafNode = new LeafNode();
	}

	public LeafNode(LeafNode left, LeafNode right) {
		super();
		isLeafNode = true;
		leafKeys = new ArrayList<LeafKey>();
		leftLeafNode = left;
		rightLeafNode = right;
	}

	//adds key value pair in an ordered way to the invoking leaf node
	public boolean addKeyValToLeafNode(double key, String val) {

		//detects if key exists already
		boolean isDuplicate = false;
		int i;
		//get position to insert
		for(i=0; i<leafKeys.size(); i++) {
			if(leafKeys.get(i).keyValue == key) {
				//duplicate key
				isDuplicate = true;
				break;
			}
			else if(leafKeys.get(i).keyValue > key) {
				//breaks when greater value encountered. no point in looping then
				break;
			}
		}
		//duplicate, just add data. unsorted
		if(isDuplicate) {
			leafKeys.get(i).leafData.add(val);
			return true;
		}
		//add new key value pair
		else {
			leafKeys.add(i, new LeafKey(key, val));
			return true;
		}
	}

}