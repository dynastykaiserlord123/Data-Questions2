package com.eq.works;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class Dependencies {
	// We will store all of the dependencies in a hash map.
	// Thus if task 102 depends on tasks 97, 94, and 56, we will hash the key as 102
	// and a list of values of 97, 94, and 56
	private HashMap<Integer, ArrayList<Integer>> dependencies = new HashMap<Integer, ArrayList<Integer>>();
	
	private Integer goal;
	private Integer start;
	
	// Since the order of the dependencies matter, we will use a linked hash set to
	// maintain order
	private Set<String> sequence = new LinkedHashSet<String>();
	private Set<String> startDependencies = new HashSet<String>();
	
	// Keeps track of all valid task ids. Thus if the user inputs an id that doesn't exist,
	// the program will let the user know that the task id they input was invalid
	private Set<String> tasks;

	/**
	 * Default constructor for the class. Both the start and end tasks MUST be
	 * within the list of task ids
	 */
	public Dependencies() {
		initialize();
	}

	/**
	 * This initialization method will read all of the dependencies from an input
	 * file and store all of them in a hash map. Thus if we want to find the
	 * prerequisites for a particular task, we can obtain all of them by getting the
	 * list of tasks associated with the key for the particular task
	 */
	private void initialize() {
		BufferedReader br;
		try {
			Integer key;
			Integer value;
			br = new BufferedReader(new FileReader("relations.txt"));
			String dependency = br.readLine();
			while (dependency != null && dependency.length() > 0) {
				key = Integer.parseInt(dependency.split("->")[1]);
				value = Integer.parseInt(dependency.split("->")[0]);
				ArrayList<Integer> values = dependencies.get(key);
				if (values == null) {
					values = new ArrayList<Integer>();
					values.add(value);
					dependencies.put(key, values);
				} else if (!values.contains(value)) {
					values.add(value);
					dependencies.put(key, values);
				}
				dependency = br.readLine();
			}
			br.close();
			br = new BufferedReader(new FileReader("task_ids.txt"));
			tasks = new HashSet<String>(Arrays.asList(br.readLine().split(",")));
			br.close();
			br = new BufferedReader(new FileReader("question.txt"));
			Integer inputStart = Integer.parseInt(br.readLine().split(":")[1].trim());
			Integer inputGoal = Integer.parseInt(br.readLine().split(":")[1].trim());
			if (tasks.contains(inputGoal.toString()) && tasks.contains(inputStart.toString())) {
				setGoal(inputGoal);
				setStart(inputStart);
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getSequence() {
		if (start != null || goal != null) {
			getStartDependencies(start);
			getSequence(goal);
			return sequence.toString();
		}
		System.out.println("Start and/or goal task ids cannot be found within list of valid tasks");
		return null;
	}

	/**
	 * This is to keep a tab on all of the dependencies associated with the starting
	 * task. For instance if task 73 is a given start but 73 depends on both tasks
	 * 39 and 41, then 39 and 41 should also both be assumed even if another task
	 * depends on them
	 * 
	 * @param start
	 *            The given start task whose dependencies we will look for
	 */
	private void getStartDependencies(Integer start) {
		ArrayList<Integer> starts = dependencies.get(start);
		if (starts != null) {
			for (Integer x : starts) {
				if (!startDependencies.contains(x.toString())) {
					getStartDependencies(x);
				}
			}
		}
		if (this.start != start) {
			startDependencies.add(start.toString());
		}
	}

	/**
	 * This method will recursively add up all of the prerequisite tasks associated
	 * with the goal while excluding all prerequisites of the start task. As the
	 * order of the tasks is very important, this method will start by adding from
	 * the lowest tier tasks which have the fewest prerequisites and end with the
	 * goal task. However if the goal task is actually a prerequisite of the start task,
	 * this method will return an empty set as the goal task would be assumed to already 
	 * have been completed prior to the start task anyway
	 * 
	 * @param goal
	 *            The end goal task whose dependencies we want to print out
	 */
	private void getSequence(Integer goal) {
		ArrayList<Integer> starts = dependencies.get(goal);
		if (starts == null || start == goal) {
			if (!startDependencies.contains(goal.toString())) {
				sequence.add(goal.toString());
			}
		} else {
			if (start != goal) {
				for (Integer x : starts) {
					if (!sequence.contains(x.toString())) {
						getSequence(x);
					}
				}
				if (!startDependencies.contains(goal.toString())) {
					sequence.add(goal.toString());
				}

			}
		}
	}

	public int getGoal() {
		return goal;
	}

	public void setGoal(int goal) {
		this.goal = goal;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}
}
