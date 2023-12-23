package org.example;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

enum RequestType {
	DELETE_ACCOUNT,
	ACTOR_ISSUE,
	MOVIE_ISSUE,
	OTHERS
}

public class Request implements Subject {

	List<Observer> observers;

	private RequestType requestType;
	private LocalDateTime date;
	/*
	name: production title / actor name
	*/
	String name;
	String description;
	String creatorUsername;
	String solverUsername;

	/* OTHERS and DELETE_ACCOUNT constructor */
	public Request(RequestType requestType, LocalDateTime date, String description, String creatorUsername) {
		observers = new LinkedList<>();
		this.requestType = requestType;
		this.date = date;
		this.description = description;
		this.creatorUsername = creatorUsername;
		this.solverUsername = "ADMIN";
	}

	/* ACTOR_ISSUE / PRODUCTION_ISSUE constructor */
	public Request(RequestType requestType, LocalDateTime date, String name, String description, String creatorUsername, String solverUsername) {
		observers = new LinkedList<>();
		this.requestType = requestType;
		this.date = date;
		this.name = name;
		this.description = description;
		this.creatorUsername = creatorUsername;
		this.solverUsername = solverUsername;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public RequestType getRequestType() {
		return requestType;
	}

	public void setRequestType(RequestType requestType) {
		this.requestType = requestType;
	}

	public String toString() {
		return	"Request type: " + requestType + "\nDate: " + date + "\nName: " + name +
				"\nDescription: " + description + "\nCreator username: " + creatorUsername +
				"\nSolver username: " + solverUsername + "\n";
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
		if (type.equals("create")) {
			for (Observer observer : observers) {
				observer.update("You have a new request from: " + name);
			}
		}
	}

}
