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
			reader = new FileReader("src/main/resources/input/production.json");

			JSONArray productionsArr = (JSONArray)parser.parse(reader);

			for (Object obj : productionsArr) {

				JSONObject productionObject = (JSONObject)obj;

				String type = (String)productionObject.get("type");

				if (type.equals("Movie")) {

					Movie newMovie = new Movie((String)productionObject.get("duration"),
											   (Integer)productionObject.get("releaseYear"));

					newMovie.title = (String)productionObject.get("title");
					newMovie.plot = (String)productionObject.get("plot");
					newMovie.avgRating = (Double)productionObject.get("averageRating");

					
					
				}

				if (type.equals("Series")) {

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
	}

	public static void main(String[] args) {

		getInstance().run();
	}

}
