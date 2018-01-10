package BPlusTree;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ReadWriteFromFile {

	//File parameters
	FileReader fileReader;
	BufferedReader bufferedReader;

	FileWriter fileWriter;
	BufferedWriter bufferedWriter;

	List<String> input;
	public static String line = null;

	//receives file name to read from. outputs Array List with list of commands received as input
	public List<String> readFile(String fName) {
		input = new ArrayList<String>();
		try {

			fileReader = new FileReader(fName);
			bufferedReader = new BufferedReader(fileReader);

			//reads one line at a time. Appends input Array List
			while((line = bufferedReader.readLine()) != null) {
				input.add(line);
			}
			bufferedReader.close();
			fileReader.close();
			return input;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	//receives filename and list of output strings as input
	public boolean WriteToFile(String fName, List<String> output) {
		File outputFile = new File(fName);

		try {
			//check if file exists already
			if(!outputFile.createNewFile()) {
				PrintWriter printWriter = new PrintWriter(outputFile);
				//rewrite the data in the file so we can start from the beginning
				printWriter.print("");
				printWriter.close();
			}
			fileWriter = new FileWriter(outputFile);
			bufferedWriter = new BufferedWriter(fileWriter);
			for(int i=0; i<output.size(); i++) {
				if(output.get(i)!="NL") {
					bufferedWriter.write(output.get(i));
				}
				else {
					bufferedWriter.newLine();
				}
			}
			bufferedWriter.close();
			fileWriter.close();
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
