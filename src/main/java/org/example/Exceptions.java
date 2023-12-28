package org.example;

class InvalidCommandException extends Exception {

	public InvalidCommandException(String message) {
		super(message);
	}
}

class InformationIncompleteException extends Exception {

	public InformationIncompleteException(String message) {
		super(message);
	}
}
