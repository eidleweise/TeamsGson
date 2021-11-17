package com.bw.graphtutorial;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.Scanner;

import com.microsoft.graph.models.DateTimeTimeZone;
import com.microsoft.graph.models.Event;
import com.microsoft.graph.models.User;

public class GraphTutorialApp {
		public static void main(final String[] args) {
			System.out.println("Java Graph Tutorial");
			System.out.println();

			// Load OAuth settings
			final Properties oAuthProperties = new Properties();
			try {
				oAuthProperties.load(GraphTutorialApp.class.getResourceAsStream("oAuth.properties"));
			} catch (IOException e) {
				System.out.println("Unable to read OAuth configuration. Make sure you have a properly formatted oAuth.properties file. See README for details.");
				return;
			}

			final String appId = oAuthProperties.getProperty("app.id");
			final List<String> appScopes = Arrays.asList(oAuthProperties.getProperty("app.scopes").split(","));
			System.out.println("appScopes = " + appScopes);

			// Initialize Graph with auth settings
			Graph.initializeGraphAuth(appId, appScopes);
			final String accessToken = Graph.getUserAccessToken();

			System.out.println("Access token: " + accessToken);
			if (accessToken != null) {
				// Greet the user
				User user = Graph.getUser();
				System.out.println("Welcome " + user.displayName);
				assert user.mailboxSettings != null;
				System.out.println("Time zone: " + user.mailboxSettings.timeZone);
				System.out.println();

				Scanner input = new Scanner(System.in);

				int choice = -1;

				while (choice != 0) {
					System.out.println("Please choose one of the following options:");
					System.out.println("0. Exit");
					System.out.println("1. Display access token");
					System.out.println("2. View this week's calendar");
					System.out.println("3. Add an event");

					try {
						choice = input.nextInt();
					} catch (InputMismatchException ex) {
						// Skip over non-integer input
					}

					input.nextLine();

					// Process user choice
					switch(choice) {
					case 0:
						// Exit the program
						System.out.println("Goodbye...");
						break;
					case 1:
						// Display access token
						break;
					case 2:
						// List the calendar
						listCalendarEvents(user.mailboxSettings.timeZone);
						break;
					case 3:
						// Create a new event
						break;
					default:
						System.out.println("Invalid choice");
					}
				}

				input.close();
			}
		}

	private static void listCalendarEvents(final String timeZone) {
		ZoneId tzId = GraphToIana.getZoneIdFromWindows("Pacific Standard Time");

		// Get midnight of the first day of the week (assumed Sunday)
		// in the user's timezone, then convert to UTC
		ZonedDateTime startOfWeek = ZonedDateTime.now(tzId)
				.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY))
				.truncatedTo(ChronoUnit.DAYS)
				.withZoneSameInstant(ZoneId.of("UTC"));

		// Add 7 days to get the end of the week
		ZonedDateTime endOfWeek = startOfWeek.plusDays(7);

		// Get the user's events
		List<Event> events = Graph.getCalendarView(
				startOfWeek, endOfWeek, timeZone);

		System.out.println("Events:");

		for (Event event : events) {
			System.out.println("Subject: " + event.subject);
			assert Objects.requireNonNull(event.organizer).emailAddress != null;
			assert event.organizer.emailAddress != null;
			assert event.start != null;
			assert event.end != null;

			System.out.println("  Organizer: " + event.organizer.emailAddress.name);
			System.out.println("  Start: " + formatDateTimeTimeZone(event.start));
			System.out.println("  End: " + formatDateTimeTimeZone(event.end));
		}
		System.out.println();
	}


	private static String formatDateTimeTimeZone(final DateTimeTimeZone date) {
		assert date.dateTime != null;
		LocalDateTime dateTime = LocalDateTime.parse(date.dateTime);

		return dateTime.format(
				DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)) +
				" (" + date.timeZone + ")";
	}

}
