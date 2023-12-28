package org.example;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class Menu <T extends Comparable<T>>{

	private static boolean logout = false;

	public static void regularUserMenu(User<?> user) {
		Regular<?> regularUser = (Regular<?>) user;

		System.out.println("Choose action:");

		System.out.println("    1) View productions details");
		System.out.println("    2) View actors details");
		System.out.println("    3) View notifications");
		System.out.println("    4) Search for actor/movie/series");
		System.out.println("    5) Add/Delete actor/movie/series to/from favourites");
		System.out.println("    6) Create/Remove request");
		System.out.println("    7) Add/Delete review for production");
		System.out.println("    8) View favourites list");
		System.out.println("    9) Log out");

		Scanner scanner = new Scanner(System.in);

		int option = Integer.parseInt(scanner.nextLine());

		if (option < 1 || option > 9) {
			System.out.println("Invalid option! Try again!\n");

			return;
		}

		switch (option) {
			case 1:
				IMDB.getInstance().displayProductions();
				
				return;

			case 2:
				IMDB.getInstance().displayActors();

				return;

			case 3:
				regularUser.displayNotifications();

				return;

			case 4:
				System.out.print("Enter the name of the actor/movie/series: ");

				IMDB.getInstance().displayItemInfo(scanner.nextLine());

				return;

			case 5:
				System.out.println("Add or delete actor/movie/series from favourites?\n    1)Add\n    2)Delete");

				option = Integer.parseInt(scanner.nextLine());

				while (option < 1 || option > 2) {
					System.out.println("Invalid option! Try again!");
					option = Integer.parseInt(scanner.nextLine());
				}

				switch (option) {
					case 1:
						System.out.println("Add actor/movie/series to favourites?\n    1)Actor\n    2)Movie/Series");

						option = Integer.parseInt(scanner.nextLine());

						while (option < 1 || option > 2) {
							System.out.println("Invalid option! Try again!");
							option = Integer.parseInt(scanner.nextLine());
						}

						switch (option) {
							case 1:
								System.out.print("Enter the name of the actor: ");

								String actorName = scanner.nextLine();

								if (IMDB.getInstance().isActorInList(actorName)) {
									((SortedSet<String>)regularUser.favourites).add(actorName);
								} else {
									System.out.println("This actor does not exist in system!");
								}

								return;

							case 2:
								System.out.print("Enter the name of the movie/series: ");

								String title = scanner.nextLine();

								if (IMDB.getInstance().isProductionInList(title)) {
									((SortedSet<String>)regularUser.favourites).add(title);
								} else {
									System.out.println("This movie/series does not exist in system!");
								}

								return;

							default:
								break;
						}

						return;

					case 2:
						System.out.println("Delete actor/movie/series from favourites?\n    1)Actor\n    2)Movie/Series");

						option = Integer.parseInt(scanner.nextLine());

						while (option < 1 || option > 2) {
							System.out.println("Invalid option! Try again!");
							option = Integer.parseInt(scanner.nextLine());
						}

						switch (option) {
							case 1:
								System.out.print("Enter the name of the actor: ");

								String actorName = scanner.nextLine();

								if (regularUser.favourites.contains(actorName)) {
									((SortedSet<String>)regularUser.favourites).remove(actorName);
								} else {
									System.out.println("This actor does not exist in your favourites!");
								}

								return;

							case 2:
								System.out.print("Enter the name of the movie/series: ");

								String title = scanner.nextLine();

								if (regularUser.favourites.contains(title)) {
									((SortedSet<String>)regularUser.favourites).remove(title);
								} else {
									System.out.println("This movie/series does not exist in your favourites!");
								}

								return;

							default:
								break;
						}

						return;

					default:
						break;
				}

				return;

			case 6:

				System.out.println("Create or remove request?\n    1)Create\n    2)Remove");

				option = Integer.parseInt(scanner.nextLine());

				while (option < 1 || option > 2) {
					System.out.println("Invalid option! Try again!");
					option = Integer.parseInt(scanner.nextLine());
				}

				if (option == 1) {

					System.out.println("Enter request type:\n    1) Others\n    2) Delete account\n    3) Actor issue\n    4) Production issue");

					option = Integer.parseInt(scanner.nextLine());

					while (option < 1 || option > 4) {
						System.out.println("Invalid option! Try again!");
						option = Integer.parseInt(scanner.nextLine());
					}

					switch (option) {
						case 1:
							System.out.print("Enter description: ");

							String description = scanner.nextLine();

							LocalDateTime date = LocalDateTime.now();

							regularUser.createRequest(new Request(RequestType.OTHERS, date, description, regularUser.username));

							return;

						case 2:
							System.out.print("Enter description: ");

							description = scanner.nextLine();

							date = LocalDateTime.now();

							regularUser.createRequest(new Request(RequestType.DELETE_ACCOUNT, date, description, regularUser.username));

							return;

						case 3:
							System.out.print("Enter actor name: ");

							String actorName = scanner.nextLine();

							System.out.print("Enter description: ");

							description = scanner.nextLine();

							date = LocalDateTime.now();

							regularUser.createRequest(new Request(RequestType.ACTOR_ISSUE, date, description, actorName, regularUser.username,
													IMDB.getInstance().users.get(IMDB.getInstance().getUserOfContributionIndex(actorName)).username));

							return;

						case 4:
							System.out.print("Enter actor name: ");

							String productionName = scanner.nextLine();

							System.out.print("Enter description: ");

							description = scanner.nextLine();

							date = LocalDateTime.now();

							regularUser.createRequest(new Request(RequestType.MOVIE_ISSUE, date, description, productionName, regularUser.username,
													IMDB.getInstance().users.get(IMDB.getInstance().getUserOfContributionIndex(productionName)).username));


							return;
					}
				} else {
					int requestIndex = IMDB.getInstance().getRequestIndex(regularUser.username);

					if (requestIndex != -1) {
						IMDB.getInstance().requests.remove(requestIndex);
					} else {
						System.out.println("You have no requests!");
					}
				}

				return;

			case 7:

				System.out.println("Add or delete review for production?\n    1)Add\n    2)Delete");

				option = Integer.parseInt(scanner.nextLine());

				while (option < 1 || option > 2) {
					System.out.println("Invalid option! Try again!");
					option = Integer.parseInt(scanner.nextLine());
				}

				switch (option) {
					case 1:
						System.out.print("Enter the name of the movie/series: ");

						String title = scanner.nextLine();

						if (IMDB.getInstance().isProductionInList(title)) {
							System.out.print("Enter the grade for the movie/series: ");

							int grade = Integer.parseInt(scanner.nextLine());

							System.out.print("Enter the review: ");

							String review = scanner.nextLine();

							regularUser.addReview(grade, review, IMDB.getInstance().productions.get(IMDB.getInstance().getProductionIndex(title)));

							int xp = Integer.parseInt(regularUser.experience);

							xp += 1;

							regularUser.experience = Integer.toString(xp);
						} else {
							System.out.println("This movie/series does not exist in system!");
						}

						return;

					case 2:
						System.out.print("Enter the name of the movie/series: ");

						String title1 = scanner.nextLine();

						if (IMDB.getInstance().isProductionInList(title1)) {

							IMDB.getInstance().removeReview(regularUser.username, title1);
						} else {
							System.out.println("This movie/series does not exist in system!");
						}

						return;

					default:
						break;
				}

				return;

			case 8:

				if (regularUser.favourites.size() == 0) {
					System.out.println("You have no favourites!");

					return;
				}

				System.out.println("Your favourites:\n\t" + regularUser.favourites);

				return;

			case 9:
				System.out.println("Log out or exit?\n    1)Log out\n    2)Exit");

				option = Integer.parseInt(scanner.nextLine());

				while (option < 1 || option > 2) {
					System.out.println("Invalid option! Try again!");
					option = Integer.parseInt(scanner.nextLine());
				}

				switch (option) {
					case 1:
						logout = true;	

						return;

					case 2:
						System.exit(0);

					default:
						break;
				}

				return;

			default:

				break;
		}

	}

	public static void contributorMenu(User<?> user) {
		Contributor<?> contributorUser = (Contributor<?>) user;

		System.out.println("Choose action:");

		System.out.println("    1) View productions details");
		System.out.println("    2) View actors details");
		System.out.println("    3) View notifications");
		System.out.println("    4) Search for actor/movie/series");
		System.out.println("    5) Add/Delete actor/movie/series to/from favourites");
		System.out.println("    6) Create/Remove request");
		System.out.println("    7) Add/Delete actor/production to/from system");
		System.out.println("    8) View/Solve requests");
		System.out.println("    9) Update informations about actor/production");
		System.out.println("   10) Log out");

		Scanner scanner = new Scanner(System.in);

		int option = Integer.parseInt(scanner.nextLine());

		if (option < 1 || option > 10) {
			System.out.println("Invalid option! Try again!\n");

			return;
		}

		switch (option) {
			case 1:
				IMDB.getInstance().displayProductions();
				
				return;

			case 2:
				IMDB.getInstance().displayActors();

				return;

			case 3:
				contributorUser.displayNotifications();

				return;

			case 4:
				System.out.print("Enter the name of the actor/movie/series: ");

				IMDB.getInstance().displayItemInfo(scanner.nextLine());

				return;

			case 5:
				System.out.println("Add or delete actor/movie/series from favourites?\n    1)Add\n    2)Delete");

				option = Integer.parseInt(scanner.nextLine());

				while (option < 1 || option > 2) {
					System.out.println("Invalid option! Try again!");
					option = Integer.parseInt(scanner.nextLine());
				}

				switch (option) {
					case 1:
						System.out.println("Add actor/movie/series to favourites?\n    1)Actor\n    2)Movie/Series");

						option = Integer.parseInt(scanner.nextLine());

						while (option < 1 || option > 2) {
							System.out.println("Invalid option! Try again!");
							option = Integer.parseInt(scanner.nextLine());
						}

						switch (option) {
							case 1:
								System.out.print("Enter the name of the actor: ");

								String actorName = scanner.nextLine();

								if (IMDB.getInstance().isActorInList(actorName)) {
									((SortedSet<String>)contributorUser.favourites).add(actorName);
								} else {
									System.out.println("This actor does not exist in system!");
								}

								return;

							case 2:
								System.out.print("Enter the name of the movie/series: ");

								String title = scanner.nextLine();

								if (IMDB.getInstance().isProductionInList(title)) {
									((SortedSet<String>)contributorUser.favourites).add(title);
								} else {
									System.out.println("This movie/series does not exist in system!");
								}

								return;

							default:
								break;
						}

						return;

					case 2:
						System.out.println("Delete actor/movie/series from favourites?\n    1)Actor\n    2)Movie/Series");

						option = Integer.parseInt(scanner.nextLine());

						while (option < 1 || option > 2) {
							System.out.println("Invalid option! Try again!");
							option = Integer.parseInt(scanner.nextLine());
						}

						switch (option) {
							case 1:
								System.out.print("Enter the name of the actor: ");

								String actorName = scanner.nextLine();

								if (contributorUser.favourites.contains(actorName)) {
									((SortedSet<String>)contributorUser.favourites).remove(actorName);
								} else {
									System.out.println("This actor does not exist in your favourites!");
								}

								return;

							case 2:
								System.out.print("Enter the name of the movie/series: ");

								String title = scanner.nextLine();

								if (contributorUser.favourites.contains(title)) {
									((SortedSet<String>)contributorUser.favourites).remove(title);
								} else {
									System.out.println("This movie/series does not exist in your favourites!");
								}

								return;

							default:
								break;
						}

						return;

					default:
						break;
				}

				return;

			case 6:

				System.out.println("Create or remove request?\n    1)Create\n    2)Remove");

				option = Integer.parseInt(scanner.nextLine());

				while (option < 1 || option > 2) {
					System.out.println("Invalid option! Try again!");
					option = Integer.parseInt(scanner.nextLine());
				}

				if (option == 1) {

					System.out.println("Enter request type:\n    1) Others\n    2) Delete account\n    3) Actor issue\n    4) Production issue");

					option = Integer.parseInt(scanner.nextLine());

					while (option < 1 || option > 4) {
						System.out.println("Invalid option! Try again!");
						option = Integer.parseInt(scanner.nextLine());
					}

					switch (option) {
						case 1:
							System.out.print("Enter description: ");

							String description = scanner.nextLine();

							LocalDateTime date = LocalDateTime.now();

							contributorUser.createRequest(new Request(RequestType.OTHERS, date, description, contributorUser.username));

							return;

						case 2:
							System.out.print("Enter description: ");

							description = scanner.nextLine();

							date = LocalDateTime.now();

							contributorUser.createRequest(new Request(RequestType.DELETE_ACCOUNT, date, description, contributorUser.username));

							return;

						case 3:
							System.out.print("Enter actor name: ");

							String actorName = scanner.nextLine();

							System.out.print("Enter description: ");

							description = scanner.nextLine();

							date = LocalDateTime.now();

							contributorUser.createRequest(new Request(RequestType.ACTOR_ISSUE, date, description, actorName, contributorUser.username,
													IMDB.getInstance().users.get(IMDB.getInstance().getUserOfContributionIndex(actorName)).username));

							return;

						case 4:
							System.out.print("Enter actor name: ");

							String productionName = scanner.nextLine();

							System.out.print("Enter description: ");

							description = scanner.nextLine();

							date = LocalDateTime.now();

							contributorUser.createRequest(new Request(RequestType.MOVIE_ISSUE, date, description, productionName, contributorUser.username,
													IMDB.getInstance().users.get(IMDB.getInstance().getUserOfContributionIndex(productionName)).username));


							return;
					}
				} else {
					int requestIndex = IMDB.getInstance().getRequestIndex(contributorUser.username);

					if (requestIndex != -1) {
						IMDB.getInstance().requests.remove(requestIndex);
					} else {
						System.out.println("You have no requests!");
					}
				}

				return;

			case 7:

				System.out.println("Add or delete actor/production to/from system?\n    1) Add\n    2) Delete");

				option = Integer.parseInt(scanner.nextLine());

				while (option < 1 || option > 2) {
					System.out.println("Invalid option! Try again!");
					option = Integer.parseInt(scanner.nextLine());
				}

				switch (option) {
					case 1:
						System.out.println("Add actor/production to system?\n    1) Actor\n    2) Production");

						option = Integer.parseInt(scanner.nextLine());

						while (option < 1 || option > 2) {
							System.out.println("Invalid option! Try again!");
							option = Integer.parseInt(scanner.nextLine());
						}

						switch (option) {
							case 1:

								System.out.print("Enter actor name: ");

								String actorName = scanner.nextLine();

								System.out.println("Enter actor biography: ");

								String biography = scanner.nextLine();

								contributorUser.addActorSystem(new Actor(actorName, biography));

								return;

							case 2:

								System.out.print("Enter production title: ");

								String title = scanner.nextLine();

								System.out.println("Enter production plot: ");

								String plot = scanner.nextLine();

								System.out.println("Enter production type:\n    1) Movie\n    2) Series");

								option = Integer.parseInt(scanner.nextLine());

								while (option < 1 || option > 2) {
									System.out.println("Invalid option! Try again!");
									option = Integer.parseInt(scanner.nextLine());
								}

								switch (option) {
									case 1:

										System.out.print("Enter production duration: ");

										String duration = scanner.nextLine();

										System.out.print("Enter production release year: ");

										Integer releaseYear = Integer.parseInt(scanner.nextLine());

										contributorUser.addProductionSystem(new Movie(title, plot, null, duration, releaseYear));

										return;

									case 2:

										System.out.print("Enter production release year: ");

										releaseYear = Integer.parseInt(scanner.nextLine());

										System.out.print("Enter production number of seasons: ");

										Integer numberOfSeasons = Integer.parseInt(scanner.nextLine());

										Series newSeries = new Series(title, plot, null, releaseYear, numberOfSeasons);

										for (int i = 0; i < numberOfSeasons; i++) {
											List<Episode> episodes = new LinkedList<>();

											System.out.print("Enter season " + i + " number of episodes: ");

											Integer numberOfEpisodes = Integer.parseInt(scanner.nextLine());

											for (int j = 0; j < numberOfEpisodes; j++) {
												System.out.print("Enter episode " + j + " name: ");

												String episodeName = scanner.nextLine();

												System.out.print("Enter episode " + j + " duration: ");

												String episodeDuration = scanner.nextLine();

												episodes.add(new Episode(episodeName, episodeDuration));
											}

											newSeries.addSeason("Season " + i, episodes);
										}

										return;

									default:
										break;
								}

								return;

							default:
								break;
						}

						return;

					case 2:
						System.out.print("Enter the name of the actor/production: ");

						String name = scanner.nextLine();

						if (IMDB.getInstance().isProductionInList(name)) {

							contributorUser.removeProductionSystem(name);
						} else if (IMDB.getInstance().isActorInList(name)) {
							contributorUser.removeActorSystem(name);
						} else {
							System.out.println("This actor/production does not exist in system!");
						}

						return;

					default:
						break;
				}

				return;

			case 8:

				if (contributorUser.requests.size() == 0) {
					System.out.println("You have no requests!");

					return;
				} else {
					contributorUser.displayRequests();

					System.out.println("Solve requests?\n    1) Yes\n    2) No");

					option = Integer.parseInt(scanner.nextLine());

					while (option < 1 || option > 2) {
						System.out.println("Invalid option! Try again!");
						option = Integer.parseInt(scanner.nextLine());
					}

					switch (option) {
						case 1:

							System.out.println("Enter request index: ");

							int requestIndex = Integer.parseInt(scanner.nextLine());

							while (requestIndex < 1 || requestIndex > contributorUser.requests.size()) {
								System.out.println("Invalid index! Try again!");
								requestIndex = Integer.parseInt(scanner.nextLine());
							}

							Request r = contributorUser.requests.get(requestIndex - 1);

							if (r.getRequestType().equals(RequestType.ACTOR_ISSUE)) {

								System.out.print("Enter actor name: ");

								String actorName = scanner.nextLine();

								String biography = new String();

								List<ProductionPair> performances = new LinkedList<>();

								if (IMDB.getInstance().isActorInList(actorName)) {

									System.out.println("Update actor biography/performances?\n    1) Biography\n    2) Performances");

									option = Integer.parseInt(scanner.nextLine());

									while (option < 1 || option > 2) {
										System.out.println("Invalid option! Try again!");
										option = Integer.parseInt(scanner.nextLine());
									}

									switch (option) {
										case 1:

											System.out.print("Enter actor biography: ");

											biography = scanner.nextLine();

											break;

										case 2:

											performances.addAll(IMDB.getInstance().actors.get(IMDB.getInstance().getActorIndex(actorName)).performances);

											System.out.println("Add or delete performances?\n    1) Add\n    2) Delete");

											option = Integer.parseInt(scanner.nextLine());

											while (option < 1 || option > 2) {
												System.out.println("Invalid option! Try again!");
												option = Integer.parseInt(scanner.nextLine());
											}

											switch (option) {
												case 1:

													System.out.println("How many performances do you want to add?");

													int numberOfPerformances = Integer.parseInt(scanner.nextLine());

													for (int i = 0; i < numberOfPerformances; i++) {
														System.out.print("Enter performance " + i + " title: ");

														String title = scanner.nextLine();

														System.out.print("Enter performance " + i + " type: ");

														String type = scanner.nextLine();

														performances.add(new ProductionPair(title, type));
													}

													break;

												case 2:

													System.out.println("How many performances do you want to delete?");

													numberOfPerformances = Integer.parseInt(scanner.nextLine());

													for (int i = 0; i < numberOfPerformances; i++) {
														System.out.print("Enter performance " + i + " title: ");

														String title = scanner.nextLine();

														System.out.print("Enter performance " + i + " type: ");

														String type = scanner.nextLine();

														ProductionPair pair = new ProductionPair(title, type);

														if (performances.contains(pair)) {
															performances.remove(pair);
														} else {
															System.out.println("This performance does not exist in system!");
														}
													}
											}

											break;

										default:
											break;
									}

									contributorUser.updateActor(new Actor(actorName, biography, performances));

								} else {
									System.out.println("This actor does not exist in system!");
								}

								return;
								
							} else if (r.getRequestType().equals(RequestType.MOVIE_ISSUE)) {
								System.out.print("Enter production title: ");

								String title = scanner.nextLine();

								String plot = new String();

								List<String> directors = new LinkedList<>();
								List<String> actors = new LinkedList<>();
								List<Production.Genre> genres = new LinkedList<>();

								if(IMDB.getInstance().isProductionInList(title)) {

									System.out.println("Update production plot/directors/actors/genres?\n    1) Plot\n    2) Directors\n    3) Actors\n    4) Genres");

									option = Integer.parseInt(scanner.nextLine());

									while (option < 1 || option > 4) {
										System.out.println("Invalid option! Try again!");
										option = Integer.parseInt(scanner.nextLine());
									}

									switch (option) {
										case 1:
											
											System.out.print("Enter production plot: ");

											plot = scanner.nextLine();

											break;

										case 2:

											directors.addAll(IMDB.getInstance().productions.get(IMDB.getInstance().getProductionIndex(title)).directors);

											System.out.println("Add or delete directors?\n    1) Add\n    2) Delete");

											option = Integer.parseInt(scanner.nextLine());

											while (option < 1 || option > 2) {
												System.out.println("Invalid option! Try again!");
												option = Integer.parseInt(scanner.nextLine());
											}

											switch (option) {
												case 1:

													System.out.println("How many directors do you want to add?");

													int numberOfDirectors = Integer.parseInt(scanner.nextLine());

													for (int i = 0; i < numberOfDirectors; i++) {
														System.out.print("Enter director " + i + " name: ");

														String directorName = scanner.nextLine();

														directors.add(directorName);
													}

													break;

												case 2:

													System.out.println("How many directors do you want to delete?");

													numberOfDirectors = Integer.parseInt(scanner.nextLine());

													for (int i = 0; i < numberOfDirectors; i++) {
														System.out.print("Enter director " + i + " name: ");

														String directorName = scanner.nextLine();

														if (directors.contains(directorName)) {
															directors.remove(directorName);
														} else {
															System.out.println("This director does not exist in system!");
														}
													}

													break;

												default:
													break;
											}

											break;

										case 3:

											actors.addAll(IMDB.getInstance().productions.get(IMDB.getInstance().getProductionIndex(title)).actors);

											System.out.println("Add or delete actors?\n    1) Add\n    2) Delete");

											option = Integer.parseInt(scanner.nextLine());

											while (option < 1 || option > 2) {
												System.out.println("Invalid option! Try again!");
												option = Integer.parseInt(scanner.nextLine());
											}

											switch (option) {
												case 1:

													System.out.println("How many actors do you want to add?");

													int numberOfActors = Integer.parseInt(scanner.nextLine());

													for (int i = 0; i < numberOfActors; i++) {
														System.out.print("Enter actor " + i + " name: ");

														String actorName = scanner.nextLine();

														actors.add(actorName);
													}

													break;

												case 2:

													System.out.println("How many actors do you want to delete?");

													numberOfActors = Integer.parseInt(scanner.nextLine());

													for (int i = 0; i < numberOfActors; i++) {
														System.out.print("Enter actor " + i + " name: ");

														String actorName = scanner.nextLine();

														if (actors.contains(actorName)) {
															actors.remove(actorName);
														} else {
															System.out.println("This actor does not exist in system!");
														}
													}

													break;

												default:
													break;
											}

											break;

										case 4:

											genres.addAll(IMDB.getInstance().productions.get(IMDB.getInstance().getProductionIndex(title)).genres);

											System.out.println("Add or delete genres?\n    1) Add\n    2) Delete");

											option = Integer.parseInt(scanner.nextLine());

											while (option < 1 || option > 2) {
												System.out.println("Invalid option! Try again!");
												option = Integer.parseInt(scanner.nextLine());
											}

											switch (option) {
												case 1:

													System.out.println("How many genres do you want to add?");

													int numberOfGenres = Integer.parseInt(scanner.nextLine());

													for (int i = 0; i < numberOfGenres; i++) {
														System.out.print("Enter genre " + i + " name: ");

														String genreName = scanner.nextLine();

														genres.add(Production.Genre.valueOf(genreName));
													}

													break;

												case 2:

													System.out.println("How many genres do you want to delete?");

													numberOfGenres = Integer.parseInt(scanner.nextLine());

													for (int i = 0; i < numberOfGenres; i++) {
														System.out.print("Enter genre " + i + " name: ");

														String genreName = scanner.nextLine();

														if (genres.contains(Production.Genre.valueOf(genreName))) {
															genres.remove(Production.Genre.valueOf(genreName));
														} else {
															System.out.println("This genre does not exist in system!");
														}
													}

													break;

												default:
													break;
											}

											break;

										default:
											break;
									}

									if (IMDB.getInstance().productions.get(IMDB.getInstance().getProductionIndex(title)).type.equals("Series")) {
										Series s = (Series)IMDB.getInstance().productions.get(IMDB.getInstance().getProductionIndex(title));

										contributorUser.updateProduction(new Series(title, plot, s.avgRating, s.releaseYear, s.seasonsNumber, directors, actors, genres));
									} else if (IMDB.getInstance().productions.get(IMDB.getInstance().getProductionIndex(title)).type.equals("Movie")) {
										Movie m = (Movie)IMDB.getInstance().productions.get(IMDB.getInstance().getProductionIndex(title));

										contributorUser.updateProduction(new Movie(title, plot, m.avgRating, m.duration, m.releaseYear, directors, actors, genres));
									}
								} else {
									System.out.println("This production does not exist in system!");
								}
							}

							break;

						case 2:
							break;

						default:
							break;
					}
				}

				return;

			case 9:

				System.out.println("Update informations about actor/production?\n    1) Actor\n    2) Production");

				option = Integer.parseInt(scanner.nextLine());

				while (option < 1 || option > 2) {
					System.out.println("Invalid option! Try again!");
					option = Integer.parseInt(scanner.nextLine());
				}

				switch (option) {
					case 1:

						System.out.print("Enter actor name: ");

						String actorName = scanner.nextLine();

						String biography = new String();

						List<ProductionPair> performances = new LinkedList<>();

						if (IMDB.getInstance().isActorInList(actorName)) {

							System.out.println("Update actor biography/performances?\n    1) Biography\n    2) Performances");

							option = Integer.parseInt(scanner.nextLine());

							while (option < 1 || option > 2) {
								System.out.println("Invalid option! Try again!");
								option = Integer.parseInt(scanner.nextLine());
							}

							switch (option) {
								case 1:

									System.out.print("Enter actor biography: ");

									biography = scanner.nextLine();

									break;

								case 2:

									performances.addAll(IMDB.getInstance().actors.get(IMDB.getInstance().getActorIndex(actorName)).performances);

									System.out.println("Add or delete performances?\n    1) Add\n    2) Delete");

									option = Integer.parseInt(scanner.nextLine());

									while (option < 1 || option > 2) {
										System.out.println("Invalid option! Try again!");
										option = Integer.parseInt(scanner.nextLine());
									}

									switch (option) {
										case 1:

											System.out.println("How many performances do you want to add?");

											int numberOfPerformances = Integer.parseInt(scanner.nextLine());

											for (int i = 0; i < numberOfPerformances; i++) {
												System.out.print("Enter performance " + i + " title: ");

												String title = scanner.nextLine();

												System.out.print("Enter performance " + i + " type: ");

												String type = scanner.nextLine();

												performances.add(new ProductionPair(title, type));
											}

											break;

										case 2:

											System.out.println("How many performances do you want to delete?");

											numberOfPerformances = Integer.parseInt(scanner.nextLine());

											for (int i = 0; i < numberOfPerformances; i++) {
												System.out.print("Enter performance " + i + " title: ");

												String title = scanner.nextLine();

												System.out.print("Enter performance " + i + " type: ");

												String type = scanner.nextLine();

												ProductionPair pair = new ProductionPair(title, type);

												if (performances.contains(pair)) {
													performances.remove(pair);
												} else {
													System.out.println("This performance does not exist in system!");
												}
											}
									}

									break;

								default:
									break;
							}

							contributorUser.updateActor(new Actor(actorName, biography, performances));

						} else {
							System.out.println("This actor does not exist in system!");
						}

						return;

					case 2:

						System.out.print("Enter production title: ");

						String title = scanner.nextLine();

						String plot = new String();

						List<String> directors = new LinkedList<>();
						List<String> actors = new LinkedList<>();
						List<Production.Genre> genres = new LinkedList<>();

						if(IMDB.getInstance().isProductionInList(title)) {

							System.out.println("Update production plot/directors/actors/genres?\n    1) Plot\n    2) Directors\n    3) Actors\n    4) Genres");

							option = Integer.parseInt(scanner.nextLine());

							while (option < 1 || option > 4) {
								System.out.println("Invalid option! Try again!");
								option = Integer.parseInt(scanner.nextLine());
							}

							switch (option) {
								case 1:
									
									System.out.print("Enter production plot: ");

									plot = scanner.nextLine();

									break;

								case 2:

									directors.addAll(IMDB.getInstance().productions.get(IMDB.getInstance().getProductionIndex(title)).directors);

									System.out.println("Add or delete directors?\n    1) Add\n    2) Delete");

									option = Integer.parseInt(scanner.nextLine());

									while (option < 1 || option > 2) {
										System.out.println("Invalid option! Try again!");
										option = Integer.parseInt(scanner.nextLine());
									}

									switch (option) {
										case 1:

											System.out.println("How many directors do you want to add?");

											int numberOfDirectors = Integer.parseInt(scanner.nextLine());

											for (int i = 0; i < numberOfDirectors; i++) {
												System.out.print("Enter director " + i + " name: ");

												String directorName = scanner.nextLine();

												directors.add(directorName);
											}

											break;

										case 2:

											System.out.println("How many directors do you want to delete?");

											numberOfDirectors = Integer.parseInt(scanner.nextLine());

											for (int i = 0; i < numberOfDirectors; i++) {
												System.out.print("Enter director " + i + " name: ");

												String directorName = scanner.nextLine();

												if (directors.contains(directorName)) {
													directors.remove(directorName);
												} else {
													System.out.println("This director does not exist in system!");
												}
											}

											break;

										default:
											break;
									}

									break;

								case 3:

									actors.addAll(IMDB.getInstance().productions.get(IMDB.getInstance().getProductionIndex(title)).actors);

									System.out.println("Add or delete actors?\n    1) Add\n    2) Delete");

									option = Integer.parseInt(scanner.nextLine());

									while (option < 1 || option > 2) {
										System.out.println("Invalid option! Try again!");
										option = Integer.parseInt(scanner.nextLine());
									}

									switch (option) {
										case 1:

											System.out.println("How many actors do you want to add?");

											int numberOfActors = Integer.parseInt(scanner.nextLine());

											for (int i = 0; i < numberOfActors; i++) {
												System.out.print("Enter actor " + i + " name: ");

												actorName = scanner.nextLine();

												actors.add(actorName);
											}

											break;

										case 2:

											System.out.println("How many actors do you want to delete?");

											numberOfActors = Integer.parseInt(scanner.nextLine());

											for (int i = 0; i < numberOfActors; i++) {
												System.out.print("Enter actor " + i + " name: ");

												actorName = scanner.nextLine();

												if (actors.contains(actorName)) {
													actors.remove(actorName);
												} else {
													System.out.println("This actor does not exist in system!");
												}
											}

											break;

										default:
											break;
									}

									break;

								case 4:

									genres.addAll(IMDB.getInstance().productions.get(IMDB.getInstance().getProductionIndex(title)).genres);

									System.out.println("Add or delete genres?\n    1) Add\n    2) Delete");

									option = Integer.parseInt(scanner.nextLine());

									while (option < 1 || option > 2) {
										System.out.println("Invalid option! Try again!");
										option = Integer.parseInt(scanner.nextLine());
									}

									switch (option) {
										case 1:

											System.out.println("How many genres do you want to add?");

											int numberOfGenres = Integer.parseInt(scanner.nextLine());

											for (int i = 0; i < numberOfGenres; i++) {
												System.out.print("Enter genre " + i + " name: ");

												String genreName = scanner.nextLine();

												genres.add(Production.Genre.valueOf(genreName));
											}

											break;

										case 2:

											System.out.println("How many genres do you want to delete?");

											numberOfGenres = Integer.parseInt(scanner.nextLine());

											for (int i = 0; i < numberOfGenres; i++) {
												System.out.print("Enter genre " + i + " name: ");

												String genreName = scanner.nextLine();

												if (genres.contains(Production.Genre.valueOf(genreName))) {
													genres.remove(Production.Genre.valueOf(genreName));
												} else {
													System.out.println("This genre does not exist in system!");
												}
											}

											break;

										default:
											break;
									}

									break;

								default:
									break;
							}

							if (IMDB.getInstance().productions.get(IMDB.getInstance().getProductionIndex(title)).type.equals("Series")) {
								Series s = (Series)IMDB.getInstance().productions.get(IMDB.getInstance().getProductionIndex(title));

								contributorUser.updateProduction(new Series(title, plot, s.avgRating, s.releaseYear, s.seasonsNumber, directors, actors, genres));
							} else if (IMDB.getInstance().productions.get(IMDB.getInstance().getProductionIndex(title)).type.equals("Movie")) {
								Movie m = (Movie)IMDB.getInstance().productions.get(IMDB.getInstance().getProductionIndex(title));

								contributorUser.updateProduction(new Movie(title, plot, m.avgRating, m.duration, m.releaseYear, directors, actors, genres));
							}
						} else {
							System.out.println("This production does not exist in system!");
						}

					default:
						break;
				}

				return;

			case 10:
				System.out.println("Log out or exit?\n    1)Log out\n    2)Exit");

				option = Integer.parseInt(scanner.nextLine());

				while (option < 1 || option > 2) {
					System.out.println("Invalid option! Try again!");
					option = Integer.parseInt(scanner.nextLine());
				}

				switch (option) {
					case 1:
						logout = true;	

						return;

					case 2:
						System.exit(0);

					default:
						break;
				}

				return;

			default:

				break;
		}
	}

	public static void adminMenu(User<?> user) {
		Admin<?> adminUser = (Admin<?>) user;

		System.out.println("Choose action:");

		System.out.println("    1) View productions details");
		System.out.println("    2) View actors details");
		System.out.println("    3) View notifications");
		System.out.println("    4) Search for actor/movie/series");
		System.out.println("    5) Add/Delete actor/movie/series to/from favourites");
		System.out.println("    6) Add/Delete user from system");
		System.out.println("    7) Add/Delete actor/production to/from system");
		System.out.println("    8) View/Solve requests");
		System.out.println("    9) Update informations about actor/production");
		System.out.println("   10) Log out");

		Scanner scanner = new Scanner(System.in);

		int option = Integer.parseInt(scanner.nextLine());

		if (option < 1 || option > 10) {
			System.out.println("Invalid option! Try again!\n");

			return;
		}

		switch (option) {
			case 1:
				IMDB.getInstance().displayProductions();
				
				return;

			case 2:
				IMDB.getInstance().displayActors();

				return;

			case 3:
				adminUser.displayNotifications();

				return;

			case 4:
				System.out.print("Enter the name of the actor/movie/series: ");

				IMDB.getInstance().displayItemInfo(scanner.nextLine());

				return;

			case 5:
				System.out.println("Add or delete actor/movie/series from favourites?\n    1)Add\n    2)Delete");

				option = Integer.parseInt(scanner.nextLine());

				while (option < 1 || option > 2) {
					System.out.println("Invalid option! Try again!");
					option = Integer.parseInt(scanner.nextLine());
				}

				switch (option) {
					case 1:
						System.out.println("Add actor/movie/series to favourites?\n    1)Actor\n    2)Movie/Series");

						option = Integer.parseInt(scanner.nextLine());

						while (option < 1 || option > 2) {
							System.out.println("Invalid option! Try again!");
							option = Integer.parseInt(scanner.nextLine());
						}

						switch (option) {
							case 1:
								System.out.print("Enter the name of the actor: ");

								String actorName = scanner.nextLine();

								if (IMDB.getInstance().isActorInList(actorName)) {
									((SortedSet<String>)adminUser.favourites).add(actorName);
								} else {
									System.out.println("This actor does not exist in system!");
								}

								return;

							case 2:
								System.out.print("Enter the name of the movie/series: ");

								String title = scanner.nextLine();

								if (IMDB.getInstance().isProductionInList(title)) {
									((SortedSet<String>)adminUser.favourites).add(title);
								} else {
									System.out.println("This movie/series does not exist in system!");
								}

								return;

							default:
								break;
						}

						return;

					case 2:
						System.out.println("Delete actor/movie/series from favourites?\n    1)Actor\n    2)Movie/Series");

						option = Integer.parseInt(scanner.nextLine());

						while (option < 1 || option > 2) {
							System.out.println("Invalid option! Try again!");
							option = Integer.parseInt(scanner.nextLine());
						}

						switch (option) {
							case 1:
								System.out.print("Enter the name of the actor: ");

								String actorName = scanner.nextLine();

								if (adminUser.favourites.contains(actorName)) {
									((SortedSet<String>)adminUser.favourites).remove(actorName);
								} else {
									System.out.println("This actor does not exist in your favourites!");
								}

								return;

							case 2:
								System.out.print("Enter the name of the movie/series: ");

								String title = scanner.nextLine();

								if (adminUser.favourites.contains(title)) {
									((SortedSet<String>)adminUser.favourites).remove(title);
								} else {
									System.out.println("This movie/series does not exist in your favourites!");
								}

								return;

							default:
								break;
						}

						return;

					default:
						break;
				}

				return;

			case 6:

				System.out.println("Add or delete user from system?\n    1) Add\n    2) Delete");

				option = Integer.parseInt(scanner.nextLine());

				while (option < 1 || option > 2) {
					System.out.println("Invalid option! Try again!");
					option = Integer.parseInt(scanner.nextLine());
				}

				switch (option) {
					case 1:

						System.out.print("Enter user type:\n    1) Admin\n    2) Contributor\n    3) Regular\n");

						AccountType type = null;

						option = Integer.parseInt(scanner.nextLine());

						while (option < 1 || option > 3) {
							System.out.println("Invalid option! Try again!");
							option = Integer.parseInt(scanner.nextLine());
						}

						switch (option) {
							case 1:
								type = AccountType.Admin;
								break;
						
							case 2:
								type = AccountType.Contributor;
								break;

							case 3:
								type = AccountType.Regular;
								break;

							default:
								break;
						}

						System.out.print("Enter username: ");

						String username = scanner.nextLine();

						System.out.print("Enter email: ");

						String email = scanner.nextLine();

						System.out.print("Enter password: ");

						String password = scanner.nextLine();

						System.out.print("Enter name: ");

						String name = scanner.nextLine();

						System.out.print("Enter country: ");

						String country = scanner.nextLine();

						System.out.print("Enter age: ");

						Integer age = Integer.parseInt(scanner.nextLine());

						while (age < 0) {
							System.out.println("Invalid age! Try again!");
							age = Integer.parseInt(scanner.nextLine());
						}

						System.out.print("Enter gender:\n    1)M\n    2)F\n    3)N\n");

						option = Integer.parseInt(scanner.nextLine());

						String gender = null;

						while (option < 1 || option > 3) {
							System.out.println("Invalid option! Try again!");
							option = Integer.parseInt(scanner.nextLine());
						}

						switch (option) {
							case 1:
								gender = "M";
								break;

							case 2:
								gender = "F";
								break;

							case 3:
								gender = "N";
								break;
						
							default:
								break;
						}

						System.out.print("Enter birthdate (yyyy-mm-dd): ");

						LocalDate birthdate = LocalDate.parse(scanner.nextLine());

						SortedSet<String> favourites = new TreeSet<>();
						SortedSet<String> contributions = new TreeSet<>();
						List<String> notifications = new LinkedList<>();

						adminUser.addUserSystem(UserFactory.createUser(type, 
								  new User.Information.InformationBuilder(new Credentials(email, password), name).
								  country(country).age(age).gender(gender).birthDate(birthdate).build(),
								  username, country, favourites, contributions, notifications));

						System.out.println("User " + username + " added to system!\n");

						System.out.println(IMDB.getInstance().users.get(IMDB.getInstance().getUserIndex(username)));

						break;
				
					case 2:

						System.out.print("Enter the name of the user: ");

						username = scanner.nextLine();

						int userIndex = IMDB.getInstance().getUserIndex(username);

						if (userIndex != -1) {
							adminUser.deleteUserSystem(IMDB.getInstance().users.get(userIndex));

							System.out.println("User " + username + " deleted from system!\n");
						} else {
							System.out.println("This user does not exist in system!");
						}

						return;

					default:
						break;
				}

				return;

			case 7:

				System.out.println("Add or delete actor/production to/from system?\n    1) Add\n    2) Delete");

				option = Integer.parseInt(scanner.nextLine());

				while (option < 1 || option > 2) {
					System.out.println("Invalid option! Try again!");
					option = Integer.parseInt(scanner.nextLine());
				}

				switch (option) {
					case 1:
						System.out.println("Add actor/production to system?\n    1) Actor\n    2) Production");

						option = Integer.parseInt(scanner.nextLine());

						while (option < 1 || option > 2) {
							System.out.println("Invalid option! Try again!");
							option = Integer.parseInt(scanner.nextLine());
						}

						switch (option) {
							case 1:

								System.out.print("Enter actor name: ");

								String actorName = scanner.nextLine();

								System.out.println("Enter actor biography: ");

								String biography = scanner.nextLine();

								adminUser.addActorSystem(new Actor(actorName, biography));

								return;

							case 2:

								System.out.print("Enter production title: ");

								String title = scanner.nextLine();

								System.out.println("Enter production plot: ");

								String plot = scanner.nextLine();

								System.out.println("Enter production type:\n    1) Movie\n    2) Series");

								option = Integer.parseInt(scanner.nextLine());

								while (option < 1 || option > 2) {
									System.out.println("Invalid option! Try again!");
									option = Integer.parseInt(scanner.nextLine());
								}

								switch (option) {
									case 1:

										System.out.print("Enter production duration: ");

										String duration = scanner.nextLine();

										System.out.print("Enter production release year: ");

										Integer releaseYear = Integer.parseInt(scanner.nextLine());

										adminUser.addProductionSystem(new Movie(title, plot, null, duration, releaseYear));

										return;

									case 2:

										System.out.print("Enter production release year: ");

										releaseYear = Integer.parseInt(scanner.nextLine());

										System.out.print("Enter production number of seasons: ");

										Integer numberOfSeasons = Integer.parseInt(scanner.nextLine());

										Series newSeries = new Series(title, plot, null, releaseYear, numberOfSeasons);

										for (int i = 0; i < numberOfSeasons; i++) {
											List<Episode> episodes = new LinkedList<>();

											System.out.print("Enter season " + i + " number of episodes: ");

											Integer numberOfEpisodes = Integer.parseInt(scanner.nextLine());

											for (int j = 0; j < numberOfEpisodes; j++) {
												System.out.print("Enter episode " + j + " name: ");

												String episodeName = scanner.nextLine();

												System.out.print("Enter episode " + j + " duration: ");

												String episodeDuration = scanner.nextLine();

												episodes.add(new Episode(episodeName, episodeDuration));
											}

											newSeries.addSeason("Season " + i, episodes);
										}

										return;

									default:
										break;
								}

								return;

							default:
								break;
						}

						return;

					case 2:
						System.out.print("Enter the name of the actor/production: ");

						String name = scanner.nextLine();

						if (IMDB.getInstance().isProductionInList(name)) {

							adminUser.removeProductionSystem(name);
						} else if (IMDB.getInstance().isActorInList(name)) {
							adminUser.removeActorSystem(name);
						} else {
							System.out.println("This actor/production does not exist in system!");
						}

						return;

					default:
						break;
				}

				return;

			case 8:

				if (adminUser.requests.size() == 0 && Admin.RequestHolder.requests.size() == 0) {
					System.out.println("You have no requests!");

					return;
				} else {
					adminUser.displayRequests();

					System.out.println("Solve personal request or from requests holder?\n    1) Personal\n    2) Requests holder");

					option = Integer.parseInt(scanner.nextLine());

					while (option < 1 || option > 2) {
						System.out.println("Invalid option! Try again!");
						option = Integer.parseInt(scanner.nextLine());
					}

					switch (option) {
						case 1:

							System.out.println("Enter request index: ");

							int requestIndex = Integer.parseInt(scanner.nextLine());

							while (requestIndex < 1 || requestIndex > adminUser.requests.size()) {
								System.out.println("Invalid index! Try again!");
								requestIndex = Integer.parseInt(scanner.nextLine());
							}

							Request r = adminUser.requests.get(requestIndex - 1);

							if (r.getRequestType().equals(RequestType.ACTOR_ISSUE)) {

								System.out.print("Enter actor name: ");

								String actorName = scanner.nextLine();

								String biography = new String();

								List<ProductionPair> performances = new LinkedList<>();

								if (IMDB.getInstance().isActorInList(actorName)) {

									System.out.println("Update actor biography/performances?\n    1) Biography\n    2) Performances");

									option = Integer.parseInt(scanner.nextLine());

									while (option < 1 || option > 2) {
										System.out.println("Invalid option! Try again!");
										option = Integer.parseInt(scanner.nextLine());
									}

									switch (option) {
										case 1:

											System.out.print("Enter actor biography: ");

											biography = scanner.nextLine();

											break;

										case 2:

											performances.addAll(IMDB.getInstance().actors.get(IMDB.getInstance().getActorIndex(actorName)).performances);

											System.out.println("Add or delete performances?\n    1) Add\n    2) Delete");

											option = Integer.parseInt(scanner.nextLine());

											while (option < 1 || option > 2) {
												System.out.println("Invalid option! Try again!");
												option = Integer.parseInt(scanner.nextLine());
											}

											switch (option) {
												case 1:

													System.out.println("How many performances do you want to add?");

													int numberOfPerformances = Integer.parseInt(scanner.nextLine());

													for (int i = 0; i < numberOfPerformances; i++) {
														System.out.print("Enter performance " + i + " title: ");

														String title = scanner.nextLine();

														System.out.print("Enter performance " + i + " type: ");

														String type = scanner.nextLine();

														performances.add(new ProductionPair(title, type));
													}

													break;

												case 2:

													System.out.println("How many performances do you want to delete?");

													numberOfPerformances = Integer.parseInt(scanner.nextLine());

													for (int i = 0; i < numberOfPerformances; i++) {
														System.out.print("Enter performance " + i + " title: ");

														String title = scanner.nextLine();

														System.out.print("Enter performance " + i + " type: ");

														String type = scanner.nextLine();

														ProductionPair pair = new ProductionPair(title, type);

														if (performances.contains(pair)) {
															performances.remove(pair);
														} else {
															System.out.println("This performance does not exist in system!");
														}
													}
											}

											break;

										default:
											break;
									}

									adminUser.updateActor(new Actor(actorName, biography, performances));

								} else {
									System.out.println("This actor does not exist in system!");
								}

								return;
								
							} else if (r.getRequestType().equals(RequestType.MOVIE_ISSUE)) {
								System.out.print("Enter production title: ");

								String title = scanner.nextLine();

								String plot = new String();

								List<String> directors = new LinkedList<>();
								List<String> actors = new LinkedList<>();
								List<Production.Genre> genres = new LinkedList<>();

								if(IMDB.getInstance().isProductionInList(title)) {

									System.out.println("Update production plot/directors/actors/genres?\n    1) Plot\n    2) Directors\n    3) Actors\n    4) Genres");

									option = Integer.parseInt(scanner.nextLine());

									while (option < 1 || option > 4) {
										System.out.println("Invalid option! Try again!");
										option = Integer.parseInt(scanner.nextLine());
									}

									switch (option) {
										case 1:
											
											System.out.print("Enter production plot: ");

											plot = scanner.nextLine();

											break;

										case 2:

											directors.addAll(IMDB.getInstance().productions.get(IMDB.getInstance().getProductionIndex(title)).directors);

											System.out.println("Add or delete directors?\n    1) Add\n    2) Delete");

											option = Integer.parseInt(scanner.nextLine());

											while (option < 1 || option > 2) {
												System.out.println("Invalid option! Try again!");
												option = Integer.parseInt(scanner.nextLine());
											}

											switch (option) {
												case 1:

													System.out.println("How many directors do you want to add?");

													int numberOfDirectors = Integer.parseInt(scanner.nextLine());

													for (int i = 0; i < numberOfDirectors; i++) {
														System.out.print("Enter director " + i + " name: ");

														String directorName = scanner.nextLine();

														directors.add(directorName);
													}

													break;

												case 2:

													System.out.println("How many directors do you want to delete?");

													numberOfDirectors = Integer.parseInt(scanner.nextLine());

													for (int i = 0; i < numberOfDirectors; i++) {
														System.out.print("Enter director " + i + " name: ");

														String directorName = scanner.nextLine();

														if (directors.contains(directorName)) {
															directors.remove(directorName);
														} else {
															System.out.println("This director does not exist in system!");
														}
													}

													break;

												default:
													break;
											}

											break;

										case 3:

											actors.addAll(IMDB.getInstance().productions.get(IMDB.getInstance().getProductionIndex(title)).actors);

											System.out.println("Add or delete actors?\n    1) Add\n    2) Delete");

											option = Integer.parseInt(scanner.nextLine());

											while (option < 1 || option > 2) {
												System.out.println("Invalid option! Try again!");
												option = Integer.parseInt(scanner.nextLine());
											}

											switch (option) {
												case 1:

													System.out.println("How many actors do you want to add?");

													int numberOfActors = Integer.parseInt(scanner.nextLine());

													for (int i = 0; i < numberOfActors; i++) {
														System.out.print("Enter actor " + i + " name: ");

														String actorName = scanner.nextLine();

														actors.add(actorName);
													}

													break;

												case 2:

													System.out.println("How many actors do you want to delete?");

													numberOfActors = Integer.parseInt(scanner.nextLine());

													for (int i = 0; i < numberOfActors; i++) {
														System.out.print("Enter actor " + i + " name: ");

														String actorName = scanner.nextLine();

														if (actors.contains(actorName)) {
															actors.remove(actorName);
														} else {
															System.out.println("This actor does not exist in system!");
														}
													}

													break;

												default:
													break;
											}

											break;

										case 4:

											genres.addAll(IMDB.getInstance().productions.get(IMDB.getInstance().getProductionIndex(title)).genres);

											System.out.println("Add or delete genres?\n    1) Add\n    2) Delete");

											option = Integer.parseInt(scanner.nextLine());

											while (option < 1 || option > 2) {
												System.out.println("Invalid option! Try again!");
												option = Integer.parseInt(scanner.nextLine());
											}

											switch (option) {
												case 1:

													System.out.println("How many genres do you want to add?");

													int numberOfGenres = Integer.parseInt(scanner.nextLine());

													for (int i = 0; i < numberOfGenres; i++) {
														System.out.print("Enter genre " + i + " name: ");

														String genreName = scanner.nextLine();

														genres.add(Production.Genre.valueOf(genreName));
													}

													break;

												case 2:

													System.out.println("How many genres do you want to delete?");

													numberOfGenres = Integer.parseInt(scanner.nextLine());

													for (int i = 0; i < numberOfGenres; i++) {
														System.out.print("Enter genre " + i + " name: ");

														String genreName = scanner.nextLine();

														if (genres.contains(Production.Genre.valueOf(genreName))) {
															genres.remove(Production.Genre.valueOf(genreName));
														} else {
															System.out.println("This genre does not exist in system!");
														}
													}

													break;

												default:
													break;
											}

											break;

										default:
											break;
									}

									if (IMDB.getInstance().productions.get(IMDB.getInstance().getProductionIndex(title)).type.equals("Series")) {
										Series s = (Series)IMDB.getInstance().productions.get(IMDB.getInstance().getProductionIndex(title));

										adminUser.updateProduction(new Series(title, plot, s.avgRating, s.releaseYear, s.seasonsNumber, directors, actors, genres));
									} else if (IMDB.getInstance().productions.get(IMDB.getInstance().getProductionIndex(title)).type.equals("Movie")) {
										Movie m = (Movie)IMDB.getInstance().productions.get(IMDB.getInstance().getProductionIndex(title));

										adminUser.updateProduction(new Movie(title, plot, m.avgRating, m.duration, m.releaseYear, directors, actors, genres));
									}
								} else {
									System.out.println("This production does not exist in system!");
								}
							}

							break;

						case 2:

							/* TODO: add others/delete_account solve */
							break;

						default:
							break;
					}
				}

				return;

			case 9:

				System.out.println("Update informations about actor/production?\n    1) Actor\n    2) Production");

				option = Integer.parseInt(scanner.nextLine());

				while (option < 1 || option > 2) {
					System.out.println("Invalid option! Try again!");
					option = Integer.parseInt(scanner.nextLine());
				}

				switch (option) {
					case 1:

						System.out.print("Enter actor name: ");

						String actorName = scanner.nextLine();

						String biography = new String();

						List<ProductionPair> performances = new LinkedList<>();

						if (IMDB.getInstance().isActorInList(actorName)) {

							System.out.println("Update actor biography/performances?\n    1) Biography\n    2) Performances");

							option = Integer.parseInt(scanner.nextLine());

							while (option < 1 || option > 2) {
								System.out.println("Invalid option! Try again!");
								option = Integer.parseInt(scanner.nextLine());
							}

							switch (option) {
								case 1:

									System.out.print("Enter actor biography: ");

									biography = scanner.nextLine();

									break;

								case 2:

									performances.addAll(IMDB.getInstance().actors.get(IMDB.getInstance().getActorIndex(actorName)).performances);

									System.out.println("Add or delete performances?\n    1) Add\n    2) Delete");

									option = Integer.parseInt(scanner.nextLine());

									while (option < 1 || option > 2) {
										System.out.println("Invalid option! Try again!");
										option = Integer.parseInt(scanner.nextLine());
									}

									switch (option) {
										case 1:

											System.out.println("How many performances do you want to add?");

											int numberOfPerformances = Integer.parseInt(scanner.nextLine());

											for (int i = 0; i < numberOfPerformances; i++) {
												System.out.print("Enter performance " + i + " title: ");

												String title = scanner.nextLine();

												System.out.print("Enter performance " + i + " type: ");

												String type = scanner.nextLine();

												performances.add(new ProductionPair(title, type));
											}

											break;

										case 2:

											System.out.println("How many performances do you want to delete?");

											numberOfPerformances = Integer.parseInt(scanner.nextLine());

											for (int i = 0; i < numberOfPerformances; i++) {
												System.out.print("Enter performance " + i + " title: ");

												String title = scanner.nextLine();

												System.out.print("Enter performance " + i + " type: ");

												String type = scanner.nextLine();

												ProductionPair pair = new ProductionPair(title, type);

												if (performances.contains(pair)) {
													performances.remove(pair);
												} else {
													System.out.println("This performance does not exist in system!");
												}
											}
									}

									break;

								default:
									break;
							}

							adminUser.updateActor(new Actor(actorName, biography, performances));

						} else {
							System.out.println("This actor does not exist in system!");
						}

						return;

					case 2:

						System.out.print("Enter production title: ");

						String title = scanner.nextLine();

						String plot = new String();

						List<String> directors = new LinkedList<>();
						List<String> actors = new LinkedList<>();
						List<Production.Genre> genres = new LinkedList<>();

						if(IMDB.getInstance().isProductionInList(title)) {

							System.out.println("Update production plot/directors/actors/genres?\n    1) Plot\n    2) Directors\n    3) Actors\n    4) Genres");

							option = Integer.parseInt(scanner.nextLine());

							while (option < 1 || option > 4) {
								System.out.println("Invalid option! Try again!");
								option = Integer.parseInt(scanner.nextLine());
							}

							switch (option) {
								case 1:
									
									System.out.print("Enter production plot: ");

									plot = scanner.nextLine();

									break;

								case 2:

									directors.addAll(IMDB.getInstance().productions.get(IMDB.getInstance().getProductionIndex(title)).directors);

									System.out.println("Add or delete directors?\n    1) Add\n    2) Delete");

									option = Integer.parseInt(scanner.nextLine());

									while (option < 1 || option > 2) {
										System.out.println("Invalid option! Try again!");
										option = Integer.parseInt(scanner.nextLine());
									}

									switch (option) {
										case 1:

											System.out.println("How many directors do you want to add?");

											int numberOfDirectors = Integer.parseInt(scanner.nextLine());

											for (int i = 0; i < numberOfDirectors; i++) {
												System.out.print("Enter director " + i + " name: ");

												String directorName = scanner.nextLine();

												directors.add(directorName);
											}

											break;

										case 2:

											System.out.println("How many directors do you want to delete?");

											numberOfDirectors = Integer.parseInt(scanner.nextLine());

											for (int i = 0; i < numberOfDirectors; i++) {
												System.out.print("Enter director " + i + " name: ");

												String directorName = scanner.nextLine();

												if (directors.contains(directorName)) {
													directors.remove(directorName);
												} else {
													System.out.println("This director does not exist in system!");
												}
											}

											break;

										default:
											break;
									}

									break;

								case 3:

									actors.addAll(IMDB.getInstance().productions.get(IMDB.getInstance().getProductionIndex(title)).actors);

									System.out.println("Add or delete actors?\n    1) Add\n    2) Delete");

									option = Integer.parseInt(scanner.nextLine());

									while (option < 1 || option > 2) {
										System.out.println("Invalid option! Try again!");
										option = Integer.parseInt(scanner.nextLine());
									}

									switch (option) {
										case 1:

											System.out.println("How many actors do you want to add?");

											int numberOfActors = Integer.parseInt(scanner.nextLine());

											for (int i = 0; i < numberOfActors; i++) {
												System.out.print("Enter actor " + i + " name: ");

												actorName = scanner.nextLine();

												actors.add(actorName);
											}

											break;

										case 2:

											System.out.println("How many actors do you want to delete?");

											numberOfActors = Integer.parseInt(scanner.nextLine());

											for (int i = 0; i < numberOfActors; i++) {
												System.out.print("Enter actor " + i + " name: ");

												actorName = scanner.nextLine();

												if (actors.contains(actorName)) {
													actors.remove(actorName);
												} else {
													System.out.println("This actor does not exist in system!");
												}
											}

											break;

										default:
											break;
									}

									break;

								case 4:

									genres.addAll(IMDB.getInstance().productions.get(IMDB.getInstance().getProductionIndex(title)).genres);

									System.out.println("Add or delete genres?\n    1) Add\n    2) Delete");

									option = Integer.parseInt(scanner.nextLine());

									while (option < 1 || option > 2) {
										System.out.println("Invalid option! Try again!");
										option = Integer.parseInt(scanner.nextLine());
									}

									switch (option) {
										case 1:

											System.out.println("How many genres do you want to add?");

											int numberOfGenres = Integer.parseInt(scanner.nextLine());

											for (int i = 0; i < numberOfGenres; i++) {
												System.out.print("Enter genre " + i + " name: ");

												String genreName = scanner.nextLine();

												genres.add(Production.Genre.valueOf(genreName));
											}

											break;

										case 2:

											System.out.println("How many genres do you want to delete?");

											numberOfGenres = Integer.parseInt(scanner.nextLine());

											for (int i = 0; i < numberOfGenres; i++) {
												System.out.print("Enter genre " + i + " name: ");

												String genreName = scanner.nextLine();

												if (genres.contains(Production.Genre.valueOf(genreName))) {
													genres.remove(Production.Genre.valueOf(genreName));
												} else {
													System.out.println("This genre does not exist in system!");
												}
											}

											break;

										default:
											break;
									}

									break;

								default:
									break;
							}

							if (IMDB.getInstance().productions.get(IMDB.getInstance().getProductionIndex(title)).type.equals("Series")) {
								Series s = (Series)IMDB.getInstance().productions.get(IMDB.getInstance().getProductionIndex(title));

								adminUser.updateProduction(new Series(title, plot, s.avgRating, s.releaseYear, s.seasonsNumber, directors, actors, genres));
							} else if (IMDB.getInstance().productions.get(IMDB.getInstance().getProductionIndex(title)).type.equals("Movie")) {
								Movie m = (Movie)IMDB.getInstance().productions.get(IMDB.getInstance().getProductionIndex(title));

								adminUser.updateProduction(new Movie(title, plot, m.avgRating, m.duration, m.releaseYear, directors, actors, genres));
							}
						} else {
							System.out.println("This production does not exist in system!");
						}

					default:
						break;
				}

				return;

			case 10:
				System.out.println("Log out or exit?\n    1)Log out\n    2)Exit");

				option = Integer.parseInt(scanner.nextLine());

				while (option < 1 || option > 2) {
					System.out.println("Invalid option! Try again!");
					option = Integer.parseInt(scanner.nextLine());
				}

				switch (option) {
					case 1:
						logout = true;	

						return;

					case 2:
						System.exit(0);

					default:
						break;
				}

				return;

			default:

				break;
		}
	}

	public static void welcomeMenu() {
		System.out.println("Welcome back! Enter your credentials!\n");

		Scanner scanner = new Scanner(System.in);
		boolean validCredentials = false;
		String email = null, password = null;

		while (!validCredentials) {
			System.out.print("    email: ");
			email = scanner.nextLine();

			System.out.print("    password: ");
			password = scanner.nextLine();

			validCredentials = IMDB.getInstance().validateCredentials(email, password);

			if (!validCredentials) {
				System.out.println("\nInvalid credentials! Try again!\n");
			}
		}

		User<?> user = IMDB.getInstance().getUser(email);

		System.out.println("\nWelcome back " + user.username + "!");
		System.out.println("Username " + user.username);

		if (user.experience != null) {
			System.out.println("User experience: " + user.experience);
		} else {
			System.out.println("User experience: -");
		}

		if (user.notifications != null) {
			System.out.println("You have " + user.notifications.size() + " notifications!");
		} else {
			System.out.println("You have 0 notifications!");
		}

		switch (user.accountType) {
			case Regular:

				while (logout == false) {
					regularUserMenu(user);
				}

				logout = false;
				welcomeMenu();

				break;
		
			case Contributor:

				while (logout == false) {
					contributorMenu(user);
				}

				logout = false;
				welcomeMenu();

				break;

			case Admin:
			
				while (logout == false) {
					adminMenu(user);
				}

				logout = false;
				welcomeMenu();

				break;

			default:
				break;
		}
	}
}