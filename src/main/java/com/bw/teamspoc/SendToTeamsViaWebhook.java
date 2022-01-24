/*
 * ---Begin Copyright Notice---
 *
 * NOTICE
 *
 * THIS SOFTWARE IS THE PROPERTY OF AND CONTAINS CONFIDENTIAL INFORMATION OF
 * INFOR AND/OR ITS AFFILIATES OR SUBSIDIARIES AND SHALL NOT BE DISCLOSED
 * WITHOUT PRIOR WRITTEN PERMISSION. LICENSED CUSTOMERS MAY COPY AND ADAPT
 * THIS SOFTWARE FOR THEIR OWN USE IN ACCORDANCE WITH THE TERMS OF THEIR
 * SOFTWARE LICENSE AGREEMENT. ALL OTHER RIGHTS RESERVED.
 *
 * (c) COPYRIGHT 2021 INFOR. ALL RIGHTS RESERVED. THE WORD AND DESIGN MARKS
 * SET FORTH HEREIN ARE TRADEMARKS AND/OR REGISTERED TRADEMARKS OF INFOR
 * AND/OR ITS AFFILIATES AND SUBSIDIARIES. ALL RIGHTS RESERVED. ALL OTHER
 * TRADEMARKS LISTED HEREIN ARE THE PROPERTY OF THEIR RESPECTIVE OWNERS.
 *
 * ---End Copyright Notice---
 */
package com.bw.teamspoc;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class SendToTeamsViaWebhook {
	public static void sendJsonString(final String jsonString) {
//		final String webhookUrl = ReadFiles.readWebhookUrl("teamswebhookurl.txt");
		final String pocWebhookUrl = "https://inforonline.webhook.office.com/webhookb2/8cf0ceef-c64e-44d8-9509-c1ba2f487bac@457d5685-0467-4d05-b23b-8f817adda47c/IncomingWebhook/8e57c533c8ab4954a2c89782b8ce5d71/ad997e79-bb46-4e12-adb1-000497a03a62";
		final String demoWebhookUrl = "https://inforonline.webhook.office.com/webhookb2/8cf0ceef-c64e-44d8-9509-c1ba2f487bac@457d5685-0467-4d05-b23b-8f817adda47c/IncomingWebhook/1acfbeabc9d14ae4b8940593c56aa98b/ad997e79-bb46-4e12-adb1-000497a03a62";
		final CloseableHttpClient client = HttpClients.createDefault();
		final HttpPost httpPost = new HttpPost(pocWebhookUrl);

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
}