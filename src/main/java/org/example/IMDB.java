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

	public int getProductionIndex(String title) {
		for (int i = 0; i < productions.size(); i++) {
			if (productions.get(i).title.equals(title)) {
				return i;
			}
		}

		return -1;
	}

	public boolean isProductionInList(String title) {
		for (Production production : productions) {
			if (production.title.equals(title)) {
				return true;
			}
		}

		return false;
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
			if (users.get(i) instanceof Staff<?>) {
				if (((Staff<?>)users.get(i)).contributions.contains(contribution)) {
					return i;
				}
			}
		}

		return -1;
	}

	public void addRequestToUser(String username, Request request) {
		((Staff<?>)users.get(getUserIndex(username))).requests.add(request);
	}

	public void displayAllProductions() {

		for (Production prod : productions) {
			prod.displayInfo();
			System.out.println("----------------------------------------------------------------------------------------------------------------------------");
			System.out.println("----------------------------------------------------------------------------------------------------------------------------");
		}
	}

	public void displayProductions() {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter the genre (Action, Adventure, Comedy, Drama, Horror, SF, Cooking, Fantasy, Romance, Mystery, Thriller, Crime, Biography, War): ");
		boolean isInvalidGenre = true;
		Production.Genre genre = null;

		while (isInvalidGenre) {
			try {
				genre = Production.Genre.valueOf(scanner.nextLine());
				isInvalidGenre = false;
			} catch (IllegalArgumentException e) {
				System.out.println("Invalid genre! Please type again!");
				isInvalidGenre = true;
			}
		}

		System.out.print("Enter the minimum rating: ");

		double rating = scanner.nextDouble();

		while (rating < 0 || rating > 10) {
			System.out.println("Invalid rating!");
			System.out.print("Enter the minimum rating: ");
			rating = scanner.nextDouble();
		}

		int count = 0;

		for (Production p : productions) {
			if (p.genres.contains(genre) && p.avgRating >= rating) {
				count++;
				p.displayInfo();
				System.out.println("----------------------------------------------------------------------------------------------------------------------------");
				System.out.println("----------------------------------------------------------------------------------------------------------------------------");
			}
		}

		if (count == 0) {
			System.out.println("No productions with this filters found!");
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

	public User<?> getUser(String email) {
		for (User<?> user : users) {
			if (user.info.getEmail().equals(email)) {
				return user;
			}
		}

		return null;
	}

	public boolean validateCredentials(String email, String password) {
		User<?> user = getUser(email);

		if (user == null) {
			return false;
		}

		return user.info.checkPassword(password);
	}

	public void displayItemInfo(String name) {
		if (isActorInList(name)) {
			actors.get(getActorIndex(name)).displayInfo();
		} else if (isProductionInList(name)) {
			productions.get(getProductionIndex(name)).displayInfo();
		} else {
			System.out.println("No such item!");
		}
	}

	public void removeReview(String username, String title) {
		Production production = productions.get(getProductionIndex(title));

		for (Rating review : production.ratings) {
			if (review.username.equals(username)) {
				production.ratings.remove(review);
			}
		}
	}

	public int getRequestIndex(String name) {
		for (int i = 0; i < requests.size(); i++) {
			if (requests.get(i).creatorUsername.equals(name)) {
				return i;
			}
		}

		return -1;
	}

	public void run() {
		Parser.parseActors();

		Parser.parseProductions();

		Parser.parseAccounts();

		Parser.parseRequests();

		Menu.welcomeMenu();

	}

	public static void main(String[] args) {

		getInstance().run();
	}

}
