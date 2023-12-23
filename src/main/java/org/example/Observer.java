package org.example;

public interface Observer {

	public void update(String message);

	public void setSubject(Subject subject);

}
