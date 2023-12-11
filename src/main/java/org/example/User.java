package org.example;

import java.util.*;

enum AccountType {
	REGULAR,
	CONTRIBUTOR,
	ADMIN
}

public abstract class User {

	class Information {
		private Credentials credentials;
		String name;
		String country;
		String age;
		Character gender;
	}

	Information info;
	AccountType accountType;
	String username;
	Integer experience;
	List<String> notifications;
	SortedSet favourites;

}

class Regular extends User implements RequestsManager {

	@Override
	public void createRequest(Request r) {
		
	}

	@Override
	public void removeRequest(Request r) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'removeRequest'");
	}

	public void addReview(Integer rating, String comment) {
		/* TODO add review method */
	}

}

abstract class Staff extends User implements StaffInterface {

	List<Request> requests;
	SortedSet addedItems;

	@Override
	public void addProductionSystem(Production p) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'addProductionSystem'");
	}

	@Override
	public void addActorSystem(Actor a) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'addActorSystem'");
	}

	@Override
	public void removeProductionSystem(String name) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'removeProductionSystem'");
	}

	@Override
	public void removeActorSystem(String name) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'removeActorSystem'");
	}

	@Override
	public void updateProduction(Production p) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'updateProduction'");
	}

	@Override
	public void updateActor(Actor a) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'updateActor'");
	}
}

class Contributor extends Staff implements RequestsManager {

	@Override
	public void createRequest(Request r) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'createRequest'");
	}

	@Override
	public void removeRequest(Request r) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'removeRequest'");
	}
	
}

class Admin extends Staff {

	public void addUserSystem(User user) {
		/* TODO */
	}

	public void deleteUserSystem(User user) {
		/* TODO */
	}

}
