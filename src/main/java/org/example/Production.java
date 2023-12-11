package org.example;

import java.util.*;

enum Genre {
	ACTION, ADVENTURE, COMEDY, DRAMA, HORROR, SF, FANTASY,
	ROMANCE, MYSTERY, THRILLER, CRIME, BIOGRAPHY, WAR
}

public abstract class Production implements Comparable {

	String title;
	String type;
	List<String> directors;
	List<String> actors;
	List<Genre> genres;
	List<Rating> ratings;
	String plot;
	Double avgRating;

	public Production() {
		title = new String();
		type = new String();
		directors = new LinkedList<>();
		actors = new LinkedList<>();
		genres = new LinkedList<>();
		ratings = new LinkedList<>();
		plot = new String();
	}

	@Override
	public int compareTo(Object object) {

		if (object == null) {
			throw new NullPointerException();
		}

		if (!(object instanceof Production)) {
			throw new ClassCastException("Cannot compare classes!");
		}

		return title.compareTo(((Production)object).title);
	}

	public double calculateAvgRating() {
		double avg = 0;

		for (Rating rating : ratings) {
			avg += rating.grade;
		}

		return avg / ratings.size();
	}

	public abstract void displayInfo();

	/* verifies that a field is completed */
	public boolean isFieldCompleted(Object object) {

		return object != null;
	}
}

class Movie extends Production {

	String duration;
	Integer releaseYear;

	public Movie(String duration, Integer releaseYear) {
		this.duration = duration;
		this.releaseYear = releaseYear;
	}

	@Override
	public void displayInfo() {

		if (isFieldCompleted(title)) {
			System.out.print("Title:\n\t" + title);
		}

		if (isFieldCompleted(duration)) {
			System.out.print("Duration:\n\t" + duration);
		}

		if (isFieldCompleted(releaseYear)) {
			System.out.print("Released in:\n\t" + releaseYear);
		}

		if (isFieldCompleted(plot)) {
			System.out.print("Plot:\n\t" + plot);
		}

		if (isFieldCompleted(avgRating)) {
			System.out.print("Grade:\n\t" + avgRating);
		}

		if (isFieldCompleted(directors)) {
			System.out.println("Directors:");

			for (String director : directors) {
				System.out.println("\t" + director);
			}
		}

		if (isFieldCompleted(actors)) {
			System.out.println("Actors:");

			for (String actor : actors) {
				System.out.println("\t" + actor);
			}
		}

		if (isFieldCompleted(genres)) {
			System.out.println("Genres");

			for (Genre genre : genres) {
				System.out.println("\t" + genre);
			}
		}

		if (isFieldCompleted(ratings)) {
			System.out.println("Ratings:");

			for (Rating rating : ratings) {
				System.out.println("\t" + rating);
			}
		}

	}

}

class Episode {

	String name;
	String duration;

	public Episode(String name, String duration) {
		this.name = name;
		this.duration = duration;
	}

	public String toString() {
		return "Title: " + name + ", duration:" + duration;
	}

}

class Series extends Production {

	Integer releaseYear;
	Integer seasonsNumber;
	private Map<String, List<Episode>> seasons;

	public Series(Integer releaseYear, Integer seasonsNumber) {
			this.releaseYear = releaseYear;
			this.seasonsNumber = seasonsNumber;
		}

	@Override
	public void displayInfo() {
		
		if (isFieldCompleted(title)) {
			System.out.print("Title:\n\t" + title);
		}

		if (isFieldCompleted(seasonsNumber)) {
			System.out.print("Seasons number:\n\t" + seasonsNumber);
		}

		if (isFieldCompleted(releaseYear)) {
			System.out.print("Released in:\n\t" + releaseYear);
		}

		if (isFieldCompleted(plot)) {
			System.out.print("Plot:\n\t" + plot);
		}

		if (isFieldCompleted(avgRating)) {
			System.out.print("Grade:\n\t" + avgRating);
		}

		if (isFieldCompleted(seasons)) {
			System.out.println("Seasons:");

			for (Map.Entry<String, List<Episode>> entry : seasons.entrySet()) {
				System.out.println("\t" + entry.getKey());

				for (Episode episode : entry.getValue()) {
					System.out.println("\t\t" + episode);
				}
			}
		}

		if (isFieldCompleted(directors)) {
			System.out.println("Directors:");

			for (String director : directors) {
				System.out.println("\t" + director);
			}
		}

		if (isFieldCompleted(actors)) {
			System.out.println("Actors:");

			for (String actor : actors) {
				System.out.println("\t" + actor);
			}
		}

		if (isFieldCompleted(genres)) {
			System.out.println("Genres");

			for (Genre genre : genres) {
				System.out.println("\t" + genre);
			}
		}

		if (isFieldCompleted(ratings)) {
			System.out.println("Ratings:");

			for (Rating rating : ratings) {
				System.out.println("\t" + rating);
			}
		}

	}

}
