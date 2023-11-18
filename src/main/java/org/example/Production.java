package org.example;

import java.util.*;

enum Genre {
	ACTION, ADVENTURE, COMEDY, DRAMA, HORROR, SF, FANTASY,
	ROMANCE, MYSTERY, THRILLER, CRIME, BIOGRAPHY, WAR
}

public abstract class Production implements Comparable {

	String type;
	List<String> directors;
	List<String> actors;
	List<Genre> genres;
	List<Rating> ratings;
	String plot;
	Double avgRating;

	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'compareTo'");
	}
}

class Movie extends Production {

	Integer duration;
	Integer releaseYear;

}

class Episode {

	String name;
	Integer duration;

}

class Series extends Production {

	Integer releaseYear;
	Integer seasonsNumber;
	private Map<String, List<Episode>> seasons;

}
