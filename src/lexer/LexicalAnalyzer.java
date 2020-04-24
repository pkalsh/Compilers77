package lexer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;

public class LexicalAnalyzer {
	private ArrayList<SimpleEntry<String, String> > token_list;
	private File file;
	private FileReader file_reader;
	private String filepath;
	public LexicalAnalyzer(String filename) {
		this.token_list = new ArrayList<SimpleEntry<String, String> >();
		this.filepath = "./data/" + filename;
	}
	public void run() {
		int linenum = 1;
		String[] path_token = filepath.split("/");
		String filename = path_token[2].split("\\.")[0];
		TransitionGraph graph = new TransitionGraph(filename);
		//System.out.println("Complete the graph");
		try{
			String path = LexicalAnalyzer.class.getResource("").getPath();
	        file = new File(path + "\\data\\" + path_token[2]);
	        file_reader = new FileReader(file);
	        int singleCh = 0;
	 
	        while((singleCh = file_reader.read()) != -1){
	        	//
	        	//System.out.println((char)singleCh);
	            graph.recognizeTokens((char)singleCh, linenum);
	        	if((char)singleCh == '\n') linenum++;
	        }
	        file_reader.close();
	     } catch (FileNotFoundException e) {
	            e.getStackTrace();
	     } catch(IOException e){
	            e.getStackTrace();
	     } finally {
	    	 try {
	    		 if(file_reader != null) file_reader.close();
	    	 } catch(IOException e) {
	    		 e.printStackTrace();
	    	 }
	     }
		
		if(graph.isError()) {
			System.out.println("The error is detected!! Exit the program..");
			System.exit(0);
		}
		
		 token_list = graph.getTokenList();	
	}
	
	public ArrayList<SimpleEntry<String, String> > getTokenList() {
		return token_list;
	}
}
