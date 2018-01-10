package BPlusTree;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import BPlusTree.ReadWriteFromFile;
import BPlusTree.BPlusTree;

public class treesearch {
	//other class object initialization
	public static ReadWriteFromFile readWriteFromFile = new ReadWriteFromFile();
	public static BPlusTree bPlusTree = new BPlusTree();

	public static String fileName;
	public static String outputFileName = "output.txt";
	public static List<String> output;

	//Command parameters
	public static List<String> commands = new ArrayList<String>();

	public static void main(String[] args) throws IOException{
		//Read the commands from file
		fileName = args[0];
		//get the input as a list of commands
		commands = readWriteFromFile.readFile(fileName);
		output = new ArrayList<String>();

		boolean flag = false;
		//Make sure first command is to initialize, so only has digits
		if(Pattern.matches("\\d+", commands.get(0))){
			//call method to set the order, check if it was successful
			if(bPlusTree.initialize(Integer.parseInt(commands.get(0)))) {

			}


			for(int i=1; i<commands.size(); i++) {

				//					//initialize
				if((Pattern.matches("\\d+", commands.get(i)))) {
					flag = bPlusTree.initialize(Integer.parseInt(commands.get(i)));
				}

				//insert
				else if(commands.get(i).substring(0, 6).equals("Insert")||commands.get(i).substring(0, 6).equals("INSERT")||commands.get(i).substring(0, 6).equals("insert")) {
					int a = commands.get(i).indexOf("(") + 1;
					int b = commands.get(i).indexOf(",") + 1;
					int c = commands.get(i).indexOf(")");
					//							
					flag = bPlusTree.insert(Double.parseDouble(commands.get(i).substring(a, b-1)), commands.get(i).substring(b, c));
				}
				//search
				else {
					int a = commands.get(i).indexOf("(") + 1;
					int b = commands.get(i).indexOf(",") + 1;
					int c = commands.get(i).indexOf(")");
					//range search
					if(commands.get(i).indexOf(',')>=0) {
						flag = bPlusTree.search(Double.parseDouble(commands.get(i).substring(a, b-1)), Double.parseDouble(commands.get(i).substring(b,c)));
					}
					else {
						flag = bPlusTree.search(Double.parseDouble(commands.get(i).substring(a,c)));
					}
				}
			}
			if(!flag) {
				
			}
		}
		//print output
		if(!readWriteFromFile.WriteToFile(outputFileName, output)) {

		}

	}
}