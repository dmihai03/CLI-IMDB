package org.example;

import java.time.LocalDateTime;
import java.util.*;

enum RequestType {
	DELETE_ACCOUNT,
	ACTOR_ISSUE,
	MOVIE_ISSUE,
	OTHERS
}

public class Request {

	private RequestType requestType;
	private LocalDateTime date;
	String name;
	String description;
	String creatorUsername;
	String solverUsername;

	static class RequestHolder {
		List<Request> requests;

		/* TODO  */
	}
}
