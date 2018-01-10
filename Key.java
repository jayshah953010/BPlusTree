package BPlusTree;

import BPlusTree.Node;

public class Key {
	
	double keyValue;
	Node left, right;
	
	public Key() {
		//left right pointers to nodes. Useful for internal nodes. Not used for leaf node keys.
		left = new Node();
		right = new Node();
	}

	//initialize the parameters for invoking node to the parameters passed as arguments
	public Key(double keyVal, Node leftNode, Node rightNode) {
		keyValue = keyVal;
		left = leftNode;
		right = rightNode;
	}

}
