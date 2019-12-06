package com.eq.works;

import java.util.Scanner;

public class PrintSequence {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Please Enter path of the relations file:");
		String relations = scan.nextLine();
		System.out.println("Now please Enter path of the task ID file:");
		String task_id = scan.nextLine();
		System.out.println("Finally, please Enter path of the questions file:");
		String questions = scan.nextLine();
		scan.close();
		Dependencies d = new Dependencies(relations, task_id, questions);
		System.out.println(d.getSequence());
	}
}
