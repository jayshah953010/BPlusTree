package BPlusTree;

import java.util.ArrayList;
import java.util.List;
import BPlusTree.LeafNode;
import BPlusTree.BPlusTree;
import BPlusTree.Key;
import BPlusTree.LeafKey;

public class Node {

	boolean isLeafNode;//differentiates internal nodes from leaf nodes
	List<Key> keys;//stores keys for each instance

	public Node() {
		isLeafNode = false;//true only when new leaf node created
		keys = new ArrayList<Key>();
	}

	public Node(Node nodeInput) {
		isLeafNode = false;
		keys = nodeInput.keys;
	}

	//add key value pair to corresponding leaf node, check if node needs to be split
	public boolean addToLeafNode(double key, String val, LeafNode leafNodeToInsertInto) {

		leafNodeToInsertInto.addKeyValToLeafNode(key, val);//adds in a sorted manner
		if(leafNodeToInsertInto.leafKeys.size() >= BPlusTree.order) {
			//split
			int leftSize = ((int) (Math.ceil(((double)BPlusTree.order)/(double)2))) - 1;//size of left subtree
			int rightSize = leafNodeToInsertInto.leafKeys.size() - leftSize;//size for right subtree. we are splitting leaf keys hence all keys would be included

			//new right node with original node's right subtree as left tree
			LeafNode rightLeafNode = new LeafNode(leafNodeToInsertInto, leafNodeToInsertInto.rightLeafNode);
			leafNodeToInsertInto.rightLeafNode = rightLeafNode;//setting the original tree's right node to newly created node

			List<LeafKey> leftNodeLeafKeys = new ArrayList<LeafKey>();//leaf keys of left node
			List<LeafKey> rightNodeLeafKeys = new ArrayList<LeafKey>();//leaf keys of right node;

			//Add keys to both subtrees
			for(int i=0; i<(leftSize + rightSize); i++) {
				if(i<leftSize) {
					leftNodeLeafKeys.add(leafNodeToInsertInto.leafKeys.get(i));
				}
				else {
					rightNodeLeafKeys.add(leafNodeToInsertInto.leafKeys.get(i));
				}
			}

			LeafNode stackLastNode = (LeafNode)BPlusTree.stack.get(0);
			stackLastNode.leafKeys = leftNodeLeafKeys;//update leaf keys of last node on stack
			rightLeafNode.leafKeys = rightNodeLeafKeys;

			Key keyToMerge = new Key(rightNodeLeafKeys.get(0).keyValue, null, rightLeafNode);//key to send up
			Node newNodeAfterSplit = new Node();
			newNodeAfterSplit.keys.add(keyToMerge);

			adjustOrder(newNodeAfterSplit);//handles merge
		}

		return true;
	}

	//calls merge
	public void adjustOrder(Node nodeToMerge) {

		//last node from stack.
		Node nodeFromStack = BPlusTree.stack.get(0);

		BPlusTree.stack.remove(0); //pop the stack
		if(BPlusTree.stack.size() == 0) //stack is empty hence we popped the root
		{
			nodeToMerge.keys.get(0).left = nodeFromStack;//adjust pointers
			BPlusTree.rootNode = nodeToMerge;
		}
		else {
			//merge
			Node nodeAfterMerge = merge(nodeToMerge);

			//check if to be split
			if(nodeAfterMerge.keys.size() >= BPlusTree.order) {
				Node nodeToMergeWithParent = new Node();

				int nodeAfterMergeKeySize = nodeAfterMerge.keys.size();
				List<Key> leftKeys = new ArrayList<Key>(nodeAfterMerge.keys.subList(0, (int)Math.ceil((double)(nodeAfterMergeKeySize)/(double)2)-1));
				List<Key> rightKeys= new ArrayList<Key>(nodeAfterMerge.keys.subList((int) (Math.ceil((double)(nodeAfterMergeKeySize)/(double)2)),nodeAfterMergeKeySize));

				Key keyToMoveUp = nodeAfterMerge.keys.get((int) (Math.ceil((double)nodeAfterMergeKeySize/(double)2)-1));

				nodeToMergeWithParent.keys.add(keyToMoveUp);
				Node rightNode=new Node();
				//adjust left and right pointers
				rightNode.keys=rightKeys;
				nodeAfterMerge.keys=leftKeys;
				keyToMoveUp.left=null;
				keyToMoveUp.right=rightNode;
				//adjust before merging again
				adjustOrder(nodeToMergeWithParent);

			}
		}

	}

	//merges nodeToMerge received as parameter with the invoking instance
	public Node merge(Node nodeToMerge) {

		//left most key of nodeToMerge
		Key keyToCompare=nodeToMerge.keys.get(0);

		//check order and add
		for(int i=0;i<BPlusTree.stack.get(0).keys.size();i++)
		{
			if(BPlusTree.stack.get(0).keys.get(i).keyValue>keyToCompare.keyValue)
			{
				//set left right pointers and add
				keyToCompare.left=BPlusTree.stack.get(0).keys.get(i).left;
				BPlusTree.stack.get(0).keys.get(i).left=keyToCompare.right;
				BPlusTree.stack.get(0).keys.add(i,keyToCompare);
				return BPlusTree.stack.get(0);
			}
		}
		//nodeToMerge largest to be added
		BPlusTree.stack.get(0).keys.add(keyToCompare);
		//adjust left pointer
		keyToCompare.left=BPlusTree.stack.get(0).keys.get(BPlusTree.stack.get(0).keys.size()-2).right;
		return BPlusTree.stack.get(0);
	}

}
