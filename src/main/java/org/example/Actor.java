package org.example;

import java.util.*;

public class Actor {

	String name;
	String biography;
	List<ProductionPair> performances;

	public Actor(String name) {

		this.name = name;
		performances = new LinkedList<>();
	}

	public Actor(String name, String biography) {

		this(name);
		this.biography = biography;
	}

	public Actor(String name, String biography, List<ProductionPair> performances) {

		this(name, biography);
		this.performances = performances;
	}

	public boolean isFieldCompleted(Object o) {

		return o != null;
	}

	public void displayInfo() {

		if (isFieldCompleted(name)) {
			System.out.println("Actor " + name);
		}
		
		if (isFieldCompleted(biography)) {
			System.out.println("Biography: " + biography);
		}

		if (isFieldCompleted(performances)) {
			System.out.println("Performances: ");

			for (ProductionPair pair : performances) {
			System.out.println("\t" + pair.title + " " + pair.type);
		}
		}
	}
}

class ProductionPair {
	String title;
	String type;

	public ProductionPair(String title, String type) {
		this.title = title;
		this.type = type;
	}
}
