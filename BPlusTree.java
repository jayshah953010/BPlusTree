	package BPlusTree;

import java.util.ArrayList;
import java.util.List;
import BPlusTree.Node;

public class BPlusTree {

	static int order; //degree of the tree
	Node nodeObj ;
	static Node rootNode;//to keep track of current root
	static List<Node> stack;//we use this stack for traversal, to keep track of parents during split and merge

	public BPlusTree() {
		//default
		order = 2;
		stack = new ArrayList<Node>();
		nodeObj = new Node();
		rootNode = new Node();
	}

	//initializes order
	public boolean initialize(int inputOrder) {
		//check if order meets minimum requirements for Bplus tree
		if(inputOrder >=2) {
			order = inputOrder;
			rootNode = new LeafNode(null, null);
			return true;
		}
		else {
			return false;
		}
	}

	//insert key value pair
	public boolean insert(double keyVal, String dataVal) {
		stack = new ArrayList<Node>();
		stack.add(0, rootNode);//push root to stack

		if(rootNode.isLeafNode) {
			if(nodeObj.addToLeafNode(keyVal, dataVal, (LeafNode)rootNode)) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			Node sequence=new Node(rootNode);

			//keep traversing till leaf node found
			while(sequence!=null&&sequence.isLeafNode==false)
			{
				boolean flagForTraversal=false;
				int i;

				//find relevant node
				for(i=0;i<sequence.keys.size();i++)
				{
					if(sequence.keys.get(i).keyValue>keyVal)
					{
						flagForTraversal=true;
						sequence=sequence.keys.get(i).left;//greater value found so continue for left child
						break;
					}
				}
				//continue for right most child of the node
				if(!flagForTraversal)
				{
					sequence=sequence.keys.get(sequence.keys.size()-1).right;
				}
				stack.add(0,sequence);

			}
			//add to leaf node in sorted order
			nodeObj.addToLeafNode(keyVal, dataVal, (LeafNode)sequence);
			return true;
		}
	}

	//search data for given key
	public boolean search(double keyVal) {
		LeafNode leafNode;
		stack = new ArrayList<Node>();
		stack.add(0, rootNode);//push root to stack

		if(rootNode.isLeafNode)
		{
			leafNode = (LeafNode)rootNode;
		}

		else {//root not leaf node
			Node sequence=new Node(rootNode);

			//continue till leaf node found
			while(sequence!=null&&!sequence.isLeafNode)
			{
				boolean flag=false;

				for(int i=0;i<sequence.keys.size();i++)
				{
					if(sequence.keys.get(i).keyValue>keyVal)
					{
						flag=true;
						sequence=sequence.keys.get(i).left;
						break;
					}
				}
				if(!flag)
				{
					sequence=sequence.keys.get(sequence.keys.size()-1).right;
				}
				stack.add(0,sequence);// includes first node as LeafTreeNode, we keep pushing the nodes we traverse into the stack


			}
			leafNode = (LeafNode)sequence;
		}
		boolean nodeExists=false;// to check whether value is found or not

		for(int i=0;i<leafNode.leafKeys.size();i++)
		{
			if(leafNode.leafKeys.get(i).keyValue==keyVal)// key found
			{

				for(int j=0;j<leafNode.leafKeys.get(i).leafData.size();j++)
				{
					treesearch.output.add((leafNode.leafKeys.get(i).leafData.get(j) +","));
				}
				//treesearch.output.set(treesearch.output.size()-1, treesearch.output.get(treesearch.output.size()-1).substring(0,treesearch.output.size()-1).length()-1));

				nodeExists=true;
				break;
			}
		}
		if(!nodeExists)// value not found so print Null
		{
			treesearch.output.add("Null");
		}
		treesearch.output.add("NL");//for new line, for new search
		return true;
	}


	//range search
	public boolean search(double keyVal1, double keyVal2) {
		LeafNode node;
		stack = new ArrayList<Node>();
		stack.add(0, rootNode);
		
		if(rootNode.isLeafNode) {
			node = (LeafNode)rootNode;
		}
		else {
			
			Node sequence=new Node(rootNode);

			//keep traversing till leaf node found
			while(sequence!=null&&sequence.isLeafNode==false)
			{
				boolean flag=false;
				int i;

				for(i=0;i<sequence.keys.size();i++)
				{
					if(sequence.keys.get(i).keyValue>keyVal1)
					{
						flag=true;
						sequence=sequence.keys.get(i).left;
						break;
					}
				}
				if(!flag)
				{
					sequence=sequence.keys.get(sequence.keys.size()-1).right;
				}
				stack.add(sequence);
			}
			node = (LeafNode)sequence;

		}
		//booleans to check if both keys present in the tree
		boolean k1Exists=false, k2Exists=false;
		//breaking conditions
		while(!k2Exists && node!=null)
		{
			for (int i = 0; i < node.leafKeys.size(); i++)
			{
				if(k1Exists|| node.leafKeys.get(i).keyValue>=keyVal1)
				{
					if(node.leafKeys.get(i).keyValue>keyVal2)
					{
						k2Exists = true;
						break;
					}
					//adds all data for a key
					for(int j=0; j<node.leafKeys.get(i).getLeafData().size(); j++) {
						treesearch.output.add("(" + node.leafKeys.get(i).keyValue + "," + node.leafKeys.get(i).getLeafData().get(j) + "),");
					}                 
					
					k1Exists=true;
				}
			}
			node=node.rightLeafNode;
		}
		if(k1Exists)// if we had found a key greater than k1
		{
			//treesearch.output.set(treesearch.output.size()-1, treesearch.output.get(treesearch.output.size()-1).substring(0,treesearch.output.size()-1).length()-1));
		}
		else
		{
			treesearch.output.add("Null");
		}
		treesearch.output.add("NL");//for new search command
		return true;
	}
}

