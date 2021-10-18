package com.bw.slackpoc;

import com.slack.api.bolt.App;
import com.slack.api.bolt.jetty.SlackAppServer;


/**
 * https://api.slack.com/start/building/bolt-java#start
 */
public class BoltApp {
    String SLACK_BOT_TOKEN="xoxb-2627369211936-2616380258913-6H0PuwKszjBL5we1wt0K077B";//
    String SLACK_SIGNING_SECRET="8997fa1f37dc5e7ce8bb7139c6b88400";

    public static void main(String[] args) throws Exception {
        // App expects env variables (SLACK_BOT_TOKEN, SLACK_SIGNING_SECRET)
        App app = new App();

        app.command("/hello", (req, ctx) -> ctx.ack(":wave: Hello!"));

        SlackAppServer server = new SlackAppServer(app);
        server.start(); // http://localhost:3000/slack/events
    }
}
