package com.eq.works;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class Dependencies {
	private static HashMap<Integer, ArrayList<Integer>> dependencies = new HashMap<Integer, ArrayList<Integer>>();
	private int goal;
	private int start;
	private Set<String> sequence = new LinkedHashSet<String>();
	private Set<String> startDependencies = new HashSet<String>();

	public Dependencies(int goal, int start) {
		this.setGoal(goal);
		this.start = start;
		initialize();
	}

	private void initialize() {
		try {
			Integer key;
			Integer value;
			BufferedReader br = new BufferedReader(new FileReader("relations.txt"));
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
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getGoal() {
		return goal;
	}

	public void setGoal(int goal) {
		this.goal = goal;
	}

	public String getSequence() {
		getStartDependencies(start);
		getSequence(goal);
		return sequence.toString();
	}

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
}
