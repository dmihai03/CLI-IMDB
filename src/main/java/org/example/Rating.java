package org.example;

import java.util.LinkedList;
import java.util.List;

public class Rating implements Subject {

	List<Observer> observers;

	String username;
	Integer grade;
	String comment;

	public Rating(String username, Integer grade, String comment) {

		this.username = username;
		this.grade = grade;
		this.comment = comment;

		observers = new LinkedList<>();
	}

	public String toString() {
		return username + " rated " + grade + "\n\t\t" + comment;
	}

	@Override
	public void subscribe(Observer observer) {
		observers.add(observer);
	}

	@Override
	public void unsubscribe(Observer observer) {
		observers.remove(observer);
	}

	@Override
	public void notifyObserver(String type) {
		for (Observer observer : observers) {
			observer.update(toString());
		}
	}

}
