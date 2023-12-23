package org.example;

import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.json.simple.*;
import org.json.simple.parser.*;

public class Parser <T extends Comparable<T>> {

	public static void parseProductions() {
		
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

					Movie newMovie;

					if (releaseYear != null) {
						newMovie = new Movie((String)productionObject.get("title"),
											 (String)productionObject.get("plot"),
											 (Double)productionObject.get("averageRating"),
											 (String)productionObject.get("duration"),
											 releaseYear.intValue());

					} else {
						newMovie = new Movie((String)productionObject.get("title"),
											 (String)productionObject.get("plot"),
											 (Double)productionObject.get("averageRating"),
											 (String)productionObject.get("duration"), null);
					}

					JSONArray directorsArray = (JSONArray)productionObject.get("directors");

					for (Object o : directorsArray) {
						String directorName = (String)o;
						newMovie.directors.add(directorName);
					}

					JSONArray actorsArray = (JSONArray)productionObject.get("actors");

					for (Object o : actorsArray) {
						String actorName = (String)o;

						if (IMDB.getInstance().isActorInList(actorName) &&
							!IMDB.getInstance().isProductionInActorList(newMovie.title)) {

							IMDB.getInstance().actors.get(IMDB.getInstance().getActorIndex(actorName)).
							performances.add(new ProductionPair(newMovie.title, "Movie"));

						} else if (!IMDB.getInstance().isActorInList(actorName)){
							Actor actor = new Actor(actorName);

							actor.performances.add(new ProductionPair(newMovie.title, "Movie"));

							IMDB.getInstance().actors.add(actor);
						}

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
					
					IMDB.getInstance().productions.add(newMovie);
				}

				if (type.equals("Series")) {

					Long releaseYear = (Long)productionObject.get("releaseYear");
					Long seasonsNr = (Long)productionObject.get("numSeasons");

					Series newSeries;

					newSeries = new Series((String)productionObject.get("title"),
										   (String)productionObject.get("plot"),
										   (Double)productionObject.get("averageRating"),
										   releaseYear.intValue(), seasonsNr.intValue());

					JSONArray directorsArray = (JSONArray)productionObject.get("directors");

					for (Object o : directorsArray) {
						String directorName = (String)o;
						newSeries.directors.add(directorName);
					}

					JSONArray actorsArray = (JSONArray)productionObject.get("actors");

					for (Object o : actorsArray) {
						String actorName = (String)o;

						Actor actor = new Actor(actorName);

						if (IMDB.getInstance().isActorInList(actorName) &&
							!IMDB.getInstance().isProductionInActorList(newSeries.title)) {

							IMDB.getInstance().actors.get(IMDB.getInstance().getActorIndex(actorName)).
							performances.add(new ProductionPair(newSeries.title, "Series"));

						} else if (!IMDB.getInstance().isActorInList(actorName)){

							actor.performances.add(new ProductionPair(newSeries.title, "Series"));

							IMDB.getInstance().actors.add(actor);
						}

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

					StringBuffer seasonName = new StringBuffer("Season x");

					for (Integer i = 1; i <= newSeries.seasonsNumber; i++) {
						seasonName.setCharAt(seasonName.length() - 1, i.toString().charAt(0));

						JSONArray episodes = (JSONArray)seasonsObject.get(seasonName.toString());

						List<Episode> episodesOfCurrentSeason = new LinkedList<>();

						for (Object o : episodes) {
							JSONObject episodeInfo = (JSONObject)o;

							episodesOfCurrentSeason.add(new Episode((String)episodeInfo.get("episodeName"),
															 (String)episodeInfo.get("duration")));
						}

						newSeries.addSeason(seasonName.toString(), episodesOfCurrentSeason);

						IMDB.getInstance().productions.add(newSeries);
					}
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


	public static void parseActors() {
		JSONParser parser = new JSONParser();

		FileReader reader = null;

		try {

			reader = new FileReader("../resources/input/actors.json");

			JSONArray actorsArray = (JSONArray)parser.parse(reader);

			for (Object obj : actorsArray) {

				JSONObject actorObject = (JSONObject)obj;

				Actor newActor = new Actor((String)actorObject.get("name"),
											(String)actorObject.get("biography"));

				JSONArray performancesArray = (JSONArray)actorObject.get("performances");

				for (Object o : performancesArray) {
					JSONObject performanceObject = (JSONObject)o;

					newActor.performances.add(new ProductionPair((String)performanceObject.get("title"),
																 (String)performanceObject.get("type")));
				}

				IMDB.getInstance().actors.add(newActor);
			}

		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
			System.out.println("Could not find actors.json");

		} catch (ParseException e) {

			e.printStackTrace();
			System.out.println("Could not parse json file");

		} catch (IOException e) {

			e.printStackTrace();

		}
	}

	public static void parseRequests() {

		JSONParser parser = new JSONParser();

		FileReader reader = null;

		try {

			reader = new FileReader("../resources/input/requests.json");

			JSONArray requestsArray = (JSONArray)parser.parse(reader);

			for (Object obj : requestsArray) {

				JSONObject requestObject = (JSONObject)obj;

				RequestType type = RequestType.valueOf((String)requestObject.get("type"));

				Request newRequest;

				switch (type) {
					case DELETE_ACCOUNT:

						newRequest = new Request(type,
												 LocalDateTime.parse((String)requestObject.get("createdDate")),
												 (String)requestObject.get("description"),
												 (String)requestObject.get("username"));

						IMDB.getInstance().requests.add(newRequest);

						Admin.RequestHolder.addRequest(newRequest);

						break;

					case ACTOR_ISSUE:

						newRequest = new Request(type,
												 LocalDateTime.parse((String)requestObject.get("createdDate")),
												 (String)requestObject.get("actorName"),
												 (String)requestObject.get("description"),
												 (String)requestObject.get("username"),
												 (String)requestObject.get("to"));

						IMDB.getInstance().requests.add(newRequest);

						IMDB.getInstance().addRequestToUser(newRequest.solverUsername, newRequest);

						break;

					case MOVIE_ISSUE:

						newRequest = new Request(type,
												 LocalDateTime.parse((String)requestObject.get("createdDate")),
												 (String)requestObject.get("movieTitle"),
												 (String)requestObject.get("description"),
												 (String)requestObject.get("username"),
												 (String)requestObject.get("to"));

						IMDB.getInstance().requests.add(newRequest);

						IMDB.getInstance().addRequestToUser(newRequest.solverUsername, newRequest);

						break;

					case OTHERS:

						newRequest = new Request(type,
												 LocalDateTime.parse((String)requestObject.get("createdDate")),
												 (String)requestObject.get("description"),
												 (String)requestObject.get("username"));

						IMDB.getInstance().requests.add(newRequest);

						Admin.RequestHolder.addRequest(newRequest);

						break;
					default:
						break;
				}
			}

		} catch (FileNotFoundException e) {

			e.printStackTrace();
			System.out.println("Could not find requests.json");

		} catch (ParseException e) {

			e.printStackTrace();
			System.out.println("Could not parse json file");

		} catch (IOException e) {

			e.printStackTrace();

		}
	}

	public static void parseAccounts() {

		JSONParser parser = new JSONParser();

		FileReader reader = null;

		try {

			reader = new FileReader("../resources/input/accounts.json");

			JSONArray accountsArray = (JSONArray)parser.parse(reader);

			for (Object obj : accountsArray) {

				JSONObject accountObject = (JSONObject)obj;

				AccountType type = AccountType.valueOf((String)accountObject.get("userType"));

				JSONObject informationObject = (JSONObject)accountObject.get("information");

				JSONObject credentialsObject = (JSONObject)informationObject.get("credentials");

				JSONArray favoriteActorsArray = (JSONArray)accountObject.get("favoriteActors");

				JSONArray favoriteProductionsArray = (JSONArray)accountObject.get("favoriteProductions");

				SortedSet<String> favorites = new TreeSet<>();

				if (favoriteActorsArray != null) {
					for (Object o : favoriteActorsArray) {
						favorites.add((String)o);
					}
				}

				if (favoriteProductionsArray != null) {
					for (Object o : favoriteProductionsArray) {
						favorites.add((String)o);
					}
				}

				JSONArray productionsContributionArray = (JSONArray)accountObject.get("productionsContribution");

				JSONArray actorsContributionArray = (JSONArray)accountObject.get("actorsContribution");

				SortedSet<String> contributions = new TreeSet<>();

				if (productionsContributionArray != null) {
					for (Object o : productionsContributionArray) {
						contributions.add((String)o);
					}
				}

				if (actorsContributionArray != null) {
					for (Object o : actorsContributionArray) {
						contributions.add((String)o);
					}
				}

				JSONArray notificationsArray = (JSONArray)accountObject.get("notifications");
				List<String> notifications = null;

				if (notificationsArray != null) {
					notifications = new LinkedList<>();

					for (Object o : notificationsArray) {
						notifications.add((String)o);
					}
				}

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

				User<?> newUser = UserFactory.createUser(type,
									new User.Information.InformationBuilder(new Credentials((String)credentialsObject.get("email"),
																							 (String)credentialsObject.get("password")),
																		   (String)informationObject.get("name")).
																		   country((String)informationObject.get("country")).
																		   age((((Long)informationObject.get("age")).intValue())).
																		   gender((String)informationObject.get("gender")).
																		   birthDate(LocalDate.parse((String)informationObject.get("birthDate"), formatter)).
																		   build(),
																		   (String)accountObject.get("username"),
																		   (String)accountObject.get("experience"),
																		   favorites, contributions, notifications);

				IMDB.getInstance().users.add(newUser);
			}

		} catch (FileNotFoundException e) {

			e.printStackTrace();
			System.out.println("Could not find accounts.json");

		} catch (ParseException e) {

			e.printStackTrace();
			System.out.println("Could not parse json file");

		} catch (IOException e) {

			e.printStackTrace();

		}
	}

}
