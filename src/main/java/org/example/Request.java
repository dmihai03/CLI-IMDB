package org.example;

import java.time.LocalDateTime;

enum RequestType {
	DELETE_ACCOUNT,
	ACTOR_ISSUE,
	MOVIE_ISSUE,
	OTHERS
}

public class Request {

	private RequestType requestType;
	private LocalDateTime date;
	/*
	name: production title / actor name
	*/
	String name;
	String description;
	String creatorUsername;
	String solverUsername;

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

}
