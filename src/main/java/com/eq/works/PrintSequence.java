package com.eq.works;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class PrintSequence {

	public static void main(String[] args) {
		String start;
		String goal;
		try {
			BufferedReader br = new BufferedReader(new FileReader("question.txt"));
			start = br.readLine().split(":")[1].trim();
			goal = br.readLine().split(":")[1].trim();
			br.close();
			Dependencies d = new Dependencies(Integer.parseInt(goal), Integer.parseInt(start));
			System.out.println(d.getSequence());			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
