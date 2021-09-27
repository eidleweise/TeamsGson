package com.infor.teams;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.infor.teams.entities.HeroImage;
import com.infor.teams.entities.MessageCard;
import com.infor.teams.entities.Section;

import java.util.ArrayList;
import java.util.List;

public class TeamGson {

    public static void main(String[] args) {
        HeroImage image = HeroImage.builder().image("https://hddesktopwallpapers.in/wp-content/uploads/2015/08/duck-cute-wallpaper.jpg").build();
        Section heroSection = Section.builder().heroImage(image).build();
        List<Section> sections = new ArrayList<Section>();
        sections.add(heroSection);

        MessageCard messageCard = MessageCard.builder()
                .type("MessageCard")
                .context("http://schema.org/extensions")
                .title("I am a title")
                .themeColor("ffff00")
                .text("@ben.woodward@infor.com I am some message text **with** bold and _italics_" + "<br>" +
                        "Will this make a new line?" + "<br>" +
                        "![Tux, the Linux mascot](https://d33wubrfki0l68.cloudfront.net/e7ed9fe4bafe46e275c807d63591f85f9ab246ba/e2d28/assets/images/tux.png)" + "<br>" +
                        "My favorite search engine is [Duck Duck Go](https://duckduckgo.com).")
                .moreText("Some more text")
                .sections(sections)
                .build();

        System.out.println("messageCard = " + messageCard);

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        String jsonString = gson.toJson(messageCard);
        System.out.println("jsonString = " + jsonString);
        SendToTeamsViaWebhook.sendJsonString(jsonString);
        System.out.println("Sent to Teams");

    }

}
