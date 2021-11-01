package com.bw.graphtutorial;

import java.net.URL;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

import com.azure.identity.DeviceCodeCredential;
import com.azure.identity.DeviceCodeCredentialBuilder;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.logger.DefaultLogger;
import com.microsoft.graph.logger.LoggerLevel;
import com.microsoft.graph.models.Event;
import com.microsoft.graph.models.User;
import com.microsoft.graph.options.HeaderOption;
import com.microsoft.graph.options.Option;
import com.microsoft.graph.options.QueryOption;
import com.microsoft.graph.requests.EventCollectionPage;
import com.microsoft.graph.requests.EventCollectionRequestBuilder;
import com.microsoft.graph.requests.GraphServiceClient;

public class Graph {

	private static GraphServiceClient<okhttp3.Request> graphClient = null;
	private static TokenCredentialAuthProvider authProvider = null;

	public static void initializeGraphAuth(final String applicationId, final List<String> scopes) {
		// Create the auth provider
		final DeviceCodeCredential credential = new DeviceCodeCredentialBuilder()
				.clientId(applicationId)
				.challengeConsumer(challenge -> System.out.println(challenge.getMessage()))
				.build();

		authProvider = new TokenCredentialAuthProvider(scopes, credential);

		// Create default logger to only log errors
		DefaultLogger logger = new DefaultLogger();
		logger.setLoggingLevel(LoggerLevel.ERROR);

		// Build a Graph client
		graphClient = GraphServiceClient.builder()
				.authenticationProvider(authProvider)
				.logger(logger)
				.buildClient();
	}

	public static String getUserAccessToken()
	{
		try {
			URL meUrl = new URL("https://graph.microsoft.com/v1.0/me");
			return authProvider.getAuthorizationTokenAsync(meUrl).get();
		} catch(Exception ex) {
			return null;
		}
	}

	public static User getUser() {
		if (graphClient == null) throw new NullPointerException(
				"Graph client has not been initialized. Call initializeGraphAuth before calling this method");

		// GET /me to get authenticated user

		return graphClient
				.me()
				.buildRequest()
				.select("displayName,mailboxSettings")
				.get();
	}

	public static List<Event> getCalendarView(
			final ZonedDateTime viewStart, final ZonedDateTime viewEnd, final String timeZone) {
		if (graphClient == null) throw new NullPointerException(
				"Graph client has not been initialized. Call initializeGraphAuth before calling this method");

		List<Option> options = new LinkedList<>();
		options.add(new QueryOption("startDateTime", viewStart.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)));
		options.add(new QueryOption("endDateTime", viewEnd.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)));
		// Sort results by start time
		options.add(new QueryOption("$orderby", "start/dateTime"));

		// Start and end times adjusted to user's time zone
		options.add(new HeaderOption("Prefer", "outlook.timezone=\"" + timeZone + "\""));

		// GET /me/events
		EventCollectionPage eventPage = graphClient
				.me()
				.calendarView()
				.buildRequest(options)
				.select("subject,organizer,start,end")
				.top(25)
				.get();

		List<Event> allEvents = new LinkedList<>();

		// Create a separate list of options for the paging requests
		// paging request should not include the query parameters from the initial
		// request, but should include the headers.
		List<Option> pagingOptions = new LinkedList<>();
		pagingOptions.add(new HeaderOption("Prefer", "outlook.timezone=\"" + timeZone + "\""));

		while (eventPage != null) {
			allEvents.addAll(eventPage.getCurrentPage());

			EventCollectionRequestBuilder nextPage =
					eventPage.getNextPage();

			if (nextPage == null) {
				break;
			} else {
				eventPage = nextPage
						.buildRequest(pagingOptions)
						.get();
			}
		}

		return allEvents;
	}
}