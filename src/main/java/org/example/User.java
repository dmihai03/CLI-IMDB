package org.example;

import java.time.LocalDate;
import java.util.*;

enum AccountType {
	Regular,
	Contributor,
	Admin
}

class Credentials {

	String email;
	private String password;

	public Credentials(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public boolean checkPassword(String password) {
		return password.equals(password);
	}
}

public abstract class User <T extends Comparable<T>> implements Observer {

	Subject subject;

	Information info;
	AccountType accountType;
	String username;
	String experience;
	List<String> notifications;
	SortedSet<T> favourites;

	public User(Information info, AccountType accountType, String username,
				String experience, SortedSet<T> favourites, List<String> notifications) {
		this.info = info;
		this.accountType = accountType;
		this.username = username;
		this.experience = experience;
		this.favourites = favourites;
		this.notifications = notifications;
	}

	static class Information {
		private Credentials credentials;
		String name;
		String country;
		Integer age;
		Character gender;
		private LocalDate birthDate;

		private Information(InformationBuilder builder) {
			credentials = builder.credentials;
			name = builder.name;
			country = builder.country;
			age = builder.age;
			gender = builder.gender;
			birthDate = builder.birthDate;
		}

		static class InformationBuilder {
			private Credentials credentials;
			String name;
			String country;
			Integer age;
			Character gender;
			private LocalDate birthDate;

			public InformationBuilder(Credentials credentials, String name) {
				this.credentials = credentials;
				this.name = name;
			}

			public InformationBuilder country(String country) {
				this.country = country;
				return this;
			}

			public InformationBuilder age(Integer age) {
				this.age = age;
				return this;
			}

			public InformationBuilder gender(String gender) {
				this.gender = gender.charAt(0);
				return this;
			}

			public InformationBuilder birthDate(LocalDate birthDate) {
				this.birthDate = birthDate;
				return this;
			}

			public Information build() {
				return new Information(this);
			}
		}
	}

	public String toString() {
		return	"Username: " + username + "\n" +
				"Email: " + info.credentials.email + "\n" +
				"Account type: " + accountType + "\n" +
				"Experience: " + experience + "\n" +
				"Name: " + info.name + "\n" +
				"Country: " + info.country + "\n" +
				"Age: " + info.age + "\n" +
				"Gender: " + info.gender + "\n" +
				"Birth date: " + info.birthDate + "\n" +
				"Favourites: " + favourites + "\n" +
				"Notifications: " + notifications + "\n";

	}
}

class UserFactory {
	public static <T extends Comparable<T>> User<T> createUser
						(AccountType type, User.Information info, String username,
						String experience, SortedSet<T> favourites, SortedSet<T> contributions, List<String> notifications) {
		switch (type) {

			case Regular:
				return new Regular<T>(info, type, username, experience, favourites, notifications);

			case Contributor:
				return new Contributor<T>(info, type, username, experience, favourites, contributions, notifications);

			case Admin:
				return new Admin<T>(info, type, username, experience, favourites, contributions, notifications);

			default:
				return null;

		}
	}
}

class Regular <T extends Comparable<T>> extends User<T> implements RequestsManager {

	public Regular(Information info, AccountType accountType, String username,
				   String experience, SortedSet<T> favourites, List<String> notifications) {
		super(info, accountType, username, experience, favourites, notifications);
	}

	@Override
	public void createRequest(Request r) {
		IMDB.getInstance().requests.add(r);

		if (r.solverUsername.equals("ADMIN")) {
			Admin.RequestHolder.addRequest(r);

			for (User<?> user : IMDB.getInstance().users) {
				if (user.accountType.equals(AccountType.Admin)) {
					user.update("You have a new request from " + r.creatorUsername);
				}
			}

			return;
		}

		for (User<?> user : IMDB.getInstance().users) {
			if (user.username.equals(r.solverUsername)) {
				((Staff<?>)user).requests.add(r);

				user.update("You have a new request from " + r.creatorUsername + "!");
			}
		}
	}

	@Override
	public void removeRequest(Request r) {
		IMDB.getInstance().requests.remove(IMDB.getInstance().requests.indexOf(r));

		if (r.solverUsername.equals("ADMIN")) {
			Admin.RequestHolder.removeRequest(r);

			for (User<?> user : IMDB.getInstance().users) {
				if (user.accountType.equals(AccountType.Admin)) {
					user.update(r.creatorUsername + " removed their request!");
				}
			}

			return;
		}

		for (User<?> user: IMDB.getInstance().users) {
			if (user.username.equals(r.solverUsername)) {
				((Staff<?>)user).requests.remove(((Staff<?>)user).requests.indexOf(r));

				user.update(r.creatorUsername + " removed their request!");
			}
		}
	}

	public void addReview(Integer grade, String comment, Production p) {
		IMDB.getInstance().productions.get(IMDB.getInstance().
		productions.indexOf(p)).ratings.add(new Rating(username, grade, comment));

		for (Rating r : IMDB.getInstance().productions.get(IMDB.getInstance().productions.indexOf(p)).ratings) {
			if (!r.username.equals(username)) {
				IMDB.getInstance().users.get(IMDB.getInstance().getUserIndex(r.username)).
				update(username + " added a review to a production that you rated: " + p.title + "!");
			}
		}

		IMDB.getInstance().users.get(IMDB.getInstance().getUserOfContributionIndex(p.title)).
		update(username + " added a review to a production that you added: " + p.title + "!");

	}

	@Override
	public void update(String message) {
		notifications.add(message);
	}

	@Override
	public void setSubject(Subject subject) {
		this.subject = subject;
	}

}

abstract class Staff <T extends Comparable<T>> extends User<T> implements StaffInterface {

	List<Request> requests;
	SortedSet<T> contributions;

	public Staff(Information info, AccountType accountType, String username,
				 String experience, SortedSet<T> favourites, SortedSet<T> contributions, List<String> notifications) {
		super(info, accountType, username, experience, favourites, notifications);

		requests = new LinkedList<>();
		this.contributions = contributions;
	}

	@Override
	public void addProductionSystem(Production p) {
		IMDB.getInstance().productions.add(p);

		contributions.add((T)p.title);
	}

	@Override
	public void addActorSystem(Actor a) {
		IMDB.getInstance().actors.add(a);

		contributions.add((T)a.name);
	}

	public int getProductionIndex(String title) {
		List<T> contributionsList = new ArrayList<>(contributions);

		for (int i = 0; i < contributionsList.size(); i++) {
			if (contributionsList.get(i).equals(title)) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public void removeProductionSystem(String name) {
		IMDB.getInstance().productions.remove(IMDB.getInstance().productions.indexOf(getProduction(name)));

		for (User<?> user : IMDB.getInstance().users) {
			user.favourites.remove(getProduction(name));

			if (user.accountType.equals(AccountType.Contributor) || user.accountType.equals(AccountType.Admin)) {
				((Staff<?>)user).contributions.remove(getProductionIndex(name));
			}
		}
	}

	public int getActorIndex(String name) {
		List<T> contributionsList = new ArrayList<>(contributions);

		for (int i = 0; i < contributionsList.size(); i++) {
			if (contributionsList.get(i).equals(name)) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public void removeActorSystem(String name) {
		IMDB.getInstance().actors.remove(IMDB.getInstance().actors.indexOf(getActor(name)));

		for (User<?> user : IMDB.getInstance().users) {
			user.favourites.remove(getProduction(name));

			if (user.accountType.equals(AccountType.Contributor) || user.accountType.equals(AccountType.Admin)) {
				((Staff<?>)user).contributions.remove(getProductionIndex(name));
			}
		}
	}

	@Override
	public void updateProduction(Production p) {
		removeProductionSystem(p.title);

		IMDB.getInstance().productions.add(p);
	}

	@Override
	public void updateActor(Actor a) {
		removeActorSystem(a.name);

		IMDB.getInstance().actors.add(a);
	}

	public Production getProduction(String name) {
		for (Production prod : IMDB.getInstance().productions) {
			if (prod.title.equals(name)) {
				return prod;
			}
		}

		return null;
	}

	public String toString() {
		return super.toString() + "Contributions: " + contributions + "\n";
	}

	public Actor getActor(String name) {
		for (Actor actor : IMDB.getInstance().actors) {
			if (actor.name.equals(name)) {
				return actor;
			}
		}

		return null;
	}
}

class Contributor <T extends Comparable<T>> extends Staff<T> implements RequestsManager {

	public Contributor(Information info, AccountType accountType, String username, String experience,
					   SortedSet<T> favourites, SortedSet<T> contributions, List<String> notifications) {
		super(info, accountType, username, experience, favourites, contributions, notifications);
	}

	@Override
	public void createRequest(Request r) {
		IMDB.getInstance().requests.add(r);

		if (r.solverUsername.equals("ADMIN")) {
			Admin.RequestHolder.addRequest(r);

			for (User<?> user : IMDB.getInstance().users) {
				if (user.accountType.equals(AccountType.Admin)) {
					user.update("You have a new request from " + r.creatorUsername);
				}
			}

			return;
		}

		for (User<?> user : IMDB.getInstance().users) {
			if (user.username.equals(r.solverUsername)) {
				((Staff<?>)user).requests.add(r);

				user.update("You have a new request from " + r.creatorUsername + "!");
			}
		}
	}

	@Override
	public void removeRequest(Request r) {
		IMDB.getInstance().requests.remove(IMDB.getInstance().requests.indexOf(r));

		if (r.solverUsername.equals("ADMIN")) {
			Admin.RequestHolder.removeRequest(r);

			for (User<?> user : IMDB.getInstance().users) {
				if (user.accountType.equals(AccountType.Admin)) {
					user.update(r.creatorUsername + " removed their request!");
				}
			}

			return;
		}

		for (User<?> user: IMDB.getInstance().users) {
			if (user.username.equals(r.solverUsername)) {
				((Staff<?>)user).requests.remove(((Staff<?>)user).requests.indexOf(r));

				user.update(r.creatorUsername + " removed their request!");
			}
		}
	}

	@Override
	public void update(String message) {
		notifications.add(message);
	}

	@Override
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	
}

class Admin <T extends Comparable<T>> extends Staff<T>  {

	public Admin(Information info, AccountType accountType, String username, String experience,
				 SortedSet<T> favourites, SortedSet<T> contributions, List<String> notifications) {
		super(info, accountType, username, experience, favourites, contributions, notifications);
	}

	static class RequestHolder {
		static List<Request> requests;

		public static void addRequest(Request r) {
			if (requests == null) {
				requests = new LinkedList<>();
			}

			requests.add(r);
		}

		public static void removeRequest(Request r) {
			if (requests != null) {
				requests.remove(r);
			}
		}
	}

	public void addUserSystem(User<?> user) {
		IMDB.getInstance().users.add(user);
	}

	public void deleteUserSystem(User<?> user) {
		IMDB.getInstance().users.remove(IMDB.getInstance().users.indexOf(user));
	}

	@Override
	public void update(String message) {
		notifications.add(message);
	}

	@Override
	public void setSubject(Subject subject) {
		this.subject = subject;
	}

}
