package org.example;

import java.util.*;

enum AccountType {
	REGULAR,
	CONTRIBUTOR,
	ADMIN
}

class Credentials {

	String email;
	private String password;

	public Credentials(String email, String password) {
		this.email = email;
		this.password = password;
	}
}

public abstract class User <T extends Comparable<T>> {

	static class Information {
		private Credentials credentials;
		String name;
		String country;
		String age;
		Character gender;

		private  Information(InformationBuilder builder) {
			credentials = builder.credentials;
			name = builder.name;
			country = builder.country;
			age = builder.age;
			gender = builder.gender;
		}

		static class InformationBuilder {
			private Credentials credentials;
			String name;
			String country;
			String age;
			Character gender;

			public InformationBuilder(Credentials credentials, String name) {
				this.credentials = credentials;
				this.name = name;
			}

			public InformationBuilder country(String country) {
				this.country = country;
				return this;
			}

			public InformationBuilder age(String age) {
				this.age = age;
				return this;
			}

			public InformationBuilder gender(Character gender) {
				this.gender = gender;
				return this;
			}

			public Information build() {
				return new Information(this);
			}
		}
	}

	Information info;
	AccountType accountType;
	String username;
	Integer experience;
	List<String> notifications;
	SortedSet<T> favourites;

}

class UserFactory {
	public static User<?> createUser(AccountType type) {
		switch (type) {
			case REGULAR:
				return new Regular<>();
			case CONTRIBUTOR:
				return new Contributor<>();
			case ADMIN:
				return new Admin<>();
			default:
				return null;
		}
	}
}

class Regular <T extends Comparable<T>> extends User<T> implements RequestsManager {

	@Override
	public void createRequest(Request r) {
		IMDB.getInstance().requests.add(r);

		if (r.solverUsername.equals("ADMIN")) {
			Admin.RequestHolder.addRequest(r);

			return;
		}

		for (User<?> user : IMDB.getInstance().users) {
			if (user.username.equals(r.solverUsername)) {
				((Staff<?>)user).requests.add(r);
			}
		}
	}

	@Override
	public void removeRequest(Request r) {
		IMDB.getInstance().requests.remove(IMDB.getInstance().requests.indexOf(r));

		if (r.solverUsername.equals("ADMIN")) {
			Admin.RequestHolder.removeRequest(r);

			return;
		}

		for (User<?> user: IMDB.getInstance().users) {
			if (user.username.equals(r.solverUsername)) {
				((Staff<?>)user).requests.remove(((Staff<?>)user).requests.indexOf(r));
			}
		}

	}

	public void addReview(Integer grade, String comment, Production p) {
		IMDB.getInstance().productions.get(IMDB.getInstance().
		productions.indexOf(p)).ratings.add(new Rating(username, grade, comment));
	}

}

abstract class Staff <T extends Comparable<T>> extends User<T> implements StaffInterface {

	List<Request> requests;
	SortedSet<T> contributions;

	public Staff() {
		requests = new LinkedList<>();
		contributions = new TreeSet<>();
	}

	@Override
	public void addProductionSystem(Production p) {
		IMDB.getInstance().productions.add(p);
	}

	@Override
	public void addActorSystem(Actor a) {
		IMDB.getInstance().actors.add(a);
	}

	@Override
	public void removeProductionSystem(String name) {
		IMDB.getInstance().productions.remove(IMDB.getInstance().productions.indexOf(getProduction(name)));
	}

	@Override
	public void removeActorSystem(String name) {
		IMDB.getInstance().actors.remove(IMDB.getInstance().actors.indexOf(getActor(name)));
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

	@Override
	public void createRequest(Request r) {
		IMDB.getInstance().requests.add(r);
	}

	@Override
	public void removeRequest(Request r) {
		IMDB.getInstance().requests.remove(IMDB.getInstance().requests.indexOf(r));
	}
	
}

class Admin <T extends Comparable<T>> extends Staff<T>  {

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

}
