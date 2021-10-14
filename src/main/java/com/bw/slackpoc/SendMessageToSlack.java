package com.bw.slackpoc;

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
