package com.bw.teamspoc;

import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class SendToTeamsViaWebhook {
	public static void sendJsonString(final String jsonString) {
		final String webhookUrl = readWebhookUrl();
		final CloseableHttpClient client = HttpClients.createDefault();
		final HttpPost httpPost = new HttpPost(webhookUrl);

		try {
			final StringEntity entity = new StringEntity(jsonString);
			httpPost.setEntity(entity);
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");

			final CloseableHttpResponse response = client.execute(httpPost);
			final StatusLine statusLine = response.getStatusLine();
			System.out.println(statusLine.getStatusCode() + " " + statusLine.getReasonPhrase());
			String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
			System.out.println("Response body: " + responseBody);

			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String readWebhookUrl() {
		StringBuilder contentBuilder = new StringBuilder();

		URI filePath;
		try (Stream<String> stream = Files.lines( Paths.get("webhookurl.txt"), StandardCharsets.UTF_8)) {
			stream.forEach(s -> contentBuilder.append(s).append("\n"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return contentBuilder.toString().trim();
	}
}