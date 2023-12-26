package org.example;

public interface Subject {

	public void subscribe(Observer observer);

	public void unsubscribe(Observer observer);

	public void notifyObserver(NotifyType type);

}
