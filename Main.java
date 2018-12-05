package org.askarov;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;


public class Main {

	static int MAX_ROW = 1000;
	static int MAX_LEN = 25;
	static int MAX_VAL = 99;
	static int MIN_VAL = 0;
	static int current_max_row = 0x0;
	
	public static void main(String[] args) {
		
		LinkedList<int[]> inVec = null;

		Scanner scanner = new Scanner(System.in);
		System.out.print("Please specify the input file (default = inVec.txt ): ");
		String inPath = scanner.nextLine();

		
		if(inPath.length() == 0) {
			inPath = "inVec.txt";
		}
		
		System.out.print("Please specify the out file (default = outVec.txt ): ");
		String outPath = scanner.nextLine();

		
		if(outPath.length() == 0) {
			outPath = "outVec.txt";
		}
		
		inVec = loadVectors(inPath);
		
		LinkedList<Integer> pointer_list = radixSortInPlace(inVec);

		printResult(pointer_list, inVec, outPath);
		
		
	}
	public static void printResult(LinkedList<Integer> pointer_list, LinkedList<int[]> inVec, String outFile) {

		try {
		    FileWriter fileWriter = new FileWriter(outFile);
		    PrintWriter printWriter = new PrintWriter(fileWriter);
			for(int i = 0; i < inVec.size(); i++) {
				int [] aux = inVec.get(pointer_list.get(i));
				System.out.println(Arrays.toString(aux));
			    printWriter.println(Arrays.toString(aux));

			}
			printWriter.close();
		
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	public static LinkedList<Integer> radixSortInPlace(LinkedList<int[]> a_input){

		int xdx = MAX_LEN - 1;
		LinkedList<LinkedList<Integer>> qCollection = new LinkedList<LinkedList<Integer>>();

		LinkedList<Integer> pointer_list = new LinkedList<Integer>();
		
		/*
		 * initialize queues
		 */

		for(int idx = MIN_VAL; idx <= MAX_VAL; idx++) {
			qCollection.add(new LinkedList<Integer>());
		}
		
		while(xdx >= 0) {
			for(int idx = 0; idx < a_input.size(); idx++) {
				int [] aux;
				if(!pointer_list.isEmpty()) {
					int new_index = pointer_list.get(idx);
					aux = a_input.get(new_index);
				} else {
					aux = a_input.get(idx);
				}
				 
				int index = a_input.indexOf(aux);
				int id = aux[xdx];
				LinkedList qAux = qCollection.get(aux[xdx]);
				qAux.add(index);
			}
			pointer_list.clear();
			for(int idx = MIN_VAL; idx <= MAX_VAL; idx++) {
				LinkedList list = qCollection.get(idx);
				if(!list.isEmpty()) {
					pointer_list.addAll(list);
					list.clear();
				}
			}
			xdx--;
		}
		return pointer_list;
	}
	
	public static LinkedList<int[]> radixSort(LinkedList<int[]> a_input){

		int xdx = MAX_LEN - 1;
		ArrayList<LinkedList> qCollection = new ArrayList<LinkedList>();
		ArrayList<int[]> auxList = new ArrayList<int[]>();

		for(int idx = MIN_VAL; idx <= MAX_VAL; idx++) {
			qCollection.add(new LinkedList());
		}
		
		while(xdx >= 0) {
			for(int idx = 0; idx < MAX_ROW; idx++) {
				int [] aux;
				aux = a_input.get(idx);
				LinkedList<int[]> qAux = qCollection.get(aux[xdx]);
				qAux.add(aux);
			}
			
			for(int idx = MIN_VAL; idx <= MAX_VAL; idx++) {
				LinkedList<int[]> list = qCollection.get(idx);
				if(!list.isEmpty()) {
					auxList.addAll(list);
					list.clear();
				}
			}
			a_input.clear();
			a_input.addAll(auxList);
			auxList.clear();
			xdx--;
		}
		return a_input;
	}
	
	public static LinkedList<int[]> loadVectors(String aPath){

		LinkedList<int[]> inVec = null; 

		try {

			inVec = new LinkedList<int[]>();
			BufferedReader bufferedReader;

			bufferedReader = new BufferedReader(new FileReader(aPath));
			
			String line;
			while((line = bufferedReader.readLine()) != null) {
				String[] asplit = line.split(",");

				int[] rdata = new int[MAX_LEN];

				for(int idx = 0x0; idx < asplit.length && idx < MAX_LEN; idx++) {
					 rdata[idx] = Integer.parseInt(asplit[idx]);
				}
				
				if(asplit.length < MAX_LEN) {
					for(int i = asplit.length; i < MAX_LEN; i++) {
						 rdata[i] = 0;
					}
				}
				inVec.add(rdata);
				current_max_row++;
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
		return inVec;
	}
}
