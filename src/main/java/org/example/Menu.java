package org.example;

import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.SortedSet;

public class Menu {

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
		
			default:
				break;
		}
	}
}