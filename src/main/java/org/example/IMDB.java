package org.example;

import java.io.*;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class IMDB {

	private static IMDB instance = null;

	List<User> users;
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

	public void parseProductions() {
		
		JSONParser parser = new JSONParser();

		FileReader reader = null;

		try {
			reader = new FileReader("../resources/input/production.json");

			JSONArray productionsArr = (JSONArray)parser.parse(reader);

			for (Object obj : productionsArr) {

				JSONObject productionObject = (JSONObject)obj;

				String type = (String)productionObject.get("type");

				if (type.equals("Movie")) {

					Long releaseYear = (Long)productionObject.get("releaseYear");

					Production newMovie;

					if (releaseYear != null) {
						newMovie = new Movie((String)productionObject.get("duration"),
											  releaseYear.intValue());

					} else {
						newMovie = new Movie((String)productionObject.get("duration"), null);
					}

					newMovie.title = (String)productionObject.get("title");
					newMovie.plot = (String)productionObject.get("plot");
					newMovie.avgRating = (Double)productionObject.get("averageRating");

					JSONArray directorsArray = (JSONArray)productionObject.get("directors");

					for (Object o : directorsArray) {
						String directorName = (String)o;
						newMovie.directors.add(directorName);
					}

					JSONArray actorsArray = (JSONArray)productionObject.get("actors");

					for (Object o : actorsArray) {
						String actorName = (String)o;
						newMovie.actors.add(actorName);
					}

					JSONArray genresArray = (JSONArray)productionObject.get("genres");

					for (Object o : genresArray) {
						Production.Genre genre = Production.Genre.valueOf((String)o);
						newMovie.genres.add(genre);
					}

					JSONArray ratingsArray = (JSONArray)productionObject.get("ratings");

					for (Object o : ratingsArray) {
						JSONObject ratingObject = (JSONObject)o;

						Long rating = (Long)ratingObject.get("rating");

						Rating newRating = new Rating((String)ratingObject.get("username"),
													  rating.intValue(),
													  (String)ratingObject.get("comment"));

						newMovie.ratings.add(newRating);
					}
					
					productions.add(newMovie);
				}

				if (type.equals("Series")) {

					Long releaseYear = (Long)productionObject.get("releaseYear");
					Long seasonsNr = (Long)productionObject.get("numSeasons");

					Production newSeries;

					newSeries = new Series(releaseYear.intValue(), seasonsNr.intValue());

					newSeries.title = (String)productionObject.get("title");
					newSeries.plot = (String)productionObject.get("plot");
					newSeries.avgRating = (Double)productionObject.get("averageRating");

					JSONArray directorsArray = (JSONArray)productionObject.get("directors");

					for (Object o : directorsArray) {
						String directorName = (String)o;
						newSeries.directors.add(directorName);
					}

					JSONArray actorsArray = (JSONArray)productionObject.get("actors");

					for (Object o : actorsArray) {
						String actorName = (String)o;
						newSeries.actors.add(actorName);
					}

					JSONArray genresArray = (JSONArray)productionObject.get("genres");

					for (Object o : genresArray) {
						Production.Genre genre = Production.Genre.valueOf((String)o);
						newSeries.genres.add(genre);
					}

					JSONArray ratingsArray = (JSONArray)productionObject.get("ratings");

					for (Object o : ratingsArray) {
						JSONObject ratingObject = (JSONObject)o;

						Long rating = (Long)ratingObject.get("rating");

						Rating newRating = new Rating((String)ratingObject.get("username"),
													  rating.intValue(),
													  (String)ratingObject.get("comment"));

						newSeries.ratings.add(newRating);
					}

					JSONObject seasonsObject = (JSONObject)productionObject.get("seasons");

					
				}
			}

			reader.close();

		} catch (FileNotFoundException e) {

			e.printStackTrace();
			System.out.println("Could not find production.json");

		} catch (ParseException e) {

			e.printStackTrace();
			System.out.println("Could not parse json file");

		} catch (IOException e) {

			e.printStackTrace();

		}
	}

	public void run() {

		parseProductions();

		for (Production prod : productions) {
			prod.displayInfo();
			System.out.println("----------------------------------------------------------------------------------------------------------------------------");
			System.out.println("----------------------------------------------------------------------------------------------------------------------------");
		}
	}

	public static void main(String[] args) {

		getInstance().run();
	}

}
