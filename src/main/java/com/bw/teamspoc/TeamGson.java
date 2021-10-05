package com.bw.teamspoc;

import com.bw.teamspoc.entities.Button;
import com.bw.teamspoc.entities.HeroCard;
import com.bw.teamspoc.entities.HeroCardContent;
import com.bw.teamspoc.entities.Section;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.bw.teamspoc.entities.HeroImage;
import com.bw.teamspoc.entities.MessageCard;

import java.util.ArrayList;
import java.util.List;

public class TeamGson {

    /**
     * {
     *     "contentType": "application/vnd.microsoft.card.hero",
     *     "content": {
     *         "title": "Hero Card",
     *         "subtitle": "Subtitle",
     *         "text": "Text",
     *         "images": [
     *             {
     *                 "url": "https://i.imgur.com/cfQv5Jy.jpeg"
     *             }
     *         ],
     *         "buttons": [
     *             {
     *                 "type": "openUrl",
     *                 "title": "Button 1",
     *                 "value": "https://www.microsoft.com"
     *             },
     *             {
     *                 "type": "openUrl",
     *                 "title": "Button 2",
     *                 "value": "https://www.azure.com"
     *             }
     *         ]
     *     }
     * }
     */

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

        HeroImage heroImage  = HeroImage.builder()
                .image("https://d33wubrfki0l68.cloudfront.net/e7ed9fe4bafe46e275c807d63591f85f9ab246ba/e2d28/assets/images/tux.png")
                .text("Image Text")
                .build();
        List<HeroImage> heroImages = new ArrayList<>();
        heroImages.add(heroImage);

        Button button1 = Button.builder()
                .type("openUrl")
                .title("Button 1")
                .text("Button 1T")
                .value("http://google.com")
                .build();
        Button button2 = Button.builder()
                .type("openUrl")
                .title("Button 2")
                .text("Button 2T")
                .value("http://google.com")
                .build();
        List<Button> buttons = new ArrayList<>();
        buttons.add(button1);
        buttons.add(button2);

        HeroCardContent heroCardContent = HeroCardContent.builder()
                .title("Ben's trying a hero image card")
                .subtitle("This is a subtitle")
                .text("And here we have some text")
                .images(heroImages)
                .buttons(buttons)
                .build();
        HeroCard hereoMessageCard = HeroCard.builder()
                .contentType("application/vnd.microsoft.card.hero")
                .content(heroCardContent)
                .text("Hero Card Text")
                .build();


        System.out.println("messageCard = " + hereoMessageCard);

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        String jsonString = gson.toJson(hereoMessageCard);
        System.out.println("jsonString = " + jsonString);
        SendToTeamsViaWebhook.sendJsonString(jsonString);
        System.out.println("Sent to Teams");

    }

}
