package org.example;

public class Rating {

	String username;
	Integer grade;
	String comment;

	public Rating(String username, Integer grade, String comment) {

		this.username = username;
		this.grade = grade;
		this.comment = comment;
	}

	public String toString() {
		return username + " rated " + grade + "\n\t\t" + comment;
	}

}
