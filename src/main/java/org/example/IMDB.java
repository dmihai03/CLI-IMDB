package org.example;

import java.util.*;

public class IMDB {

	private static IMDB instance = null;

	List<User<?>> users;
	List<Actor> actors;
	List<Request> requests;
	List<Production> productions;

	private IMDB() {

		users = new LinkedList<>();
		actors = new LinkedList<>();
		requests = new LinkedList<>();
		productions = new LinkedList<>();
	}

	public static IMDB getInstance() {
		if (instance == null) {
			instance = new IMDB();
		}

		return instance;
	}

	public boolean isActorInList(String name) {
		for (Actor actor : actors) {
			if (actor.name.equals(name)) {
				return true;
			}
		}

		return false;
	}

	public int getActorIndex(String name) {
		for (int i = 0; i < actors.size(); i++) {
			if (actors.get(i).name.equals(name)) {
				return i;
			}
		}

		return -1;
	}

	public boolean isProductionInActorList(String title) {
		for (Actor actor : actors) {
			for (ProductionPair pair : actor.performances) {
				if (pair.title.equals(title)) {
					return true;
				}
			}
		}

		return false;
	}

	public int getUserIndex(String username) {
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).username.equals(username)) {
				return i;
			}
		}

		return -1;
	}

	public int getUserOfContributionIndex(String contribution) {
		for (int i = 0; i < users.size(); i++) {
			if (((Staff<?>)users.get(i)).contributions.contains(contribution)) {
				return i;
			}
		}

		return -1;
	}

	public void addRequestToUser(String username, Request request) {
		((Staff<?>)users.get(getUserIndex(username))).requests.add(request);
	}

	public void displayProductions() {

		for (Production prod : productions) {
			prod.displayInfo();
			System.out.println("----------------------------------------------------------------------------------------------------------------------------");
			System.out.println("----------------------------------------------------------------------------------------------------------------------------");
		}

	}

	public void displayActors() {
		
		for (Actor actor : actors) {
			actor.displayInfo();
			System.out.println("----------------------------------------------------------------------------------------------------------------------------");
			System.out.println("----------------------------------------------------------------------------------------------------------------------------");
		}
	}

	public void displayAccounts() {

		for (User<?> user : users) {
			System.out.println(user);
			System.out.println("----------------------------------------------------------------------------------------------------------------------------");
			System.out.println("----------------------------------------------------------------------------------------------------------------------------");
		}
	}

	public void displayRequests() {

		for (Request request : requests) {
			System.out.println(request);
			System.out.println("----------------------------------------------------------------------------------------------------------------------------");
			System.out.println("----------------------------------------------------------------------------------------------------------------------------");
		}
	}

	public void run() {
		Parser.parseActors();

		Parser.parseProductions();

		Parser.parseAccounts();

		Parser.parseRequests();

	}

	public static void main(String[] args) {

		getInstance().run();
	}

}
