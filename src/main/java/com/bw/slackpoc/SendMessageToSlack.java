package com.bw.slackpoc;


/**
 * Some notes -
 * My Apps - https://api.slack.com/apps
 * Messaging API - https://api.slack.com/messaging/managing
 * Tutorial - https://www.woolha.com/tutorials/java-sending-message-to-slack-webhook
 * Bolt - Maybe what we want to use? https://api.slack.com/tools/bolt
 */
public class SendMessageToSlack {
    public static void main(String[] args) {
        SlackMessage slackMessage = SlackMessage.builder()
                .username("Ben App")
                .text("just testing")
                .icon_emoji(":twice:")
                .build();
        SlackUtils.sendMessage(slackMessage);
    }
}
