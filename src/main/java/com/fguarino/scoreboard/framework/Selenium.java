
package com.fguarino.scoreboard.framework;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Selenium {
    static WebDriver driver;
    static int synced = 1;

    void start() {

        System.setProperty("webdriver.chrome.driver", "chromedriver\\chromedriver107.exe");
        driver = new ChromeDriver();

        driver.manage().deleteAllCookies();

        driver.get("https://lizenz.dsv.de/Live.aspx");

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // WebElement match = driver.findElement(By.id("2021_190__P_19"));
        // match.click();
        // WebElement table = driver.findElement(By.id("leftTable"));
        // System.out.println("\n\n" + table.getAttribute("innerHTML") + "\n\n");

        refresh();

    }

    void refresh() {
        WebElement table = driver.findElement(By.id("leftTable"));
        String tableHtml = table.getAttribute("innerHTML");
        String[] tableBody = tableHtml.split("<tbody>");
        tableBody = tableBody[1].split("</tbody>");

        String[] rowText = tableBody[0].split("</tr>");

        for (String string : rowText) {
            rowText = string.split("<td>");

            int id = -1;
            int quater = -1;
            String time = "";
            int homePlayer = -1;
            int guestPlayer = -1;
            String event = "";
            String name = "";

            int cnt = 0;
            for (String cellText : rowText) {
                if (!(cnt == 0)) {

                    cellText = cellText.substring(0, cellText.length() - 5);

                    switch (cnt) {
                        case 1:
                            id = Integer.valueOf(cellText);

                            break;

                        case 2:
                            quater = Integer.valueOf(cellText);

                            break;

                        case 3:
                            time = cellText;

                            break;

                        case 4:
                            try {
                                homePlayer = Integer.valueOf(cellText);

                            } catch (Exception e) {
                                homePlayer = 0;
                            }
                            break;

                        case 5:

                            try {
                                guestPlayer = Integer.valueOf(cellText);
                            } catch (Exception e) {
                                guestPlayer = 0;

                            }
                            break;
                        case 6:
                            name = cellText;

                            break;

                        case 7:
                            event = cellText;

                            break;

                        default:
                            break;
                    }

                }

                cnt++;
            }
            // System.out.println("ID: " + id + "\tH: " + homePlayer + "\tG: " + guestPlayer
            // + "\tEvent: " + event);
            synchronizeToScoreBoard(id, homePlayer, guestPlayer, event);

            // System.out.println();

        }
    }

    void synchronizeToScoreBoard(int id, int homePlayer, int guestPlayer, String e) {

        if (id == synced) {

            if (homePlayer > 0)

            {

                switch (e) {
                    case "Ausschluss mit Ersatz":
                        System.out.println("AmE H:" + homePlayer);
                        Globals.homePlayers.get(homePlayer - 1).addPenalty();
                        Globals.homePlayers.get(homePlayer - 1).addPenalty();
                        Globals.homePlayers.get(homePlayer - 1).addPenalty();
                        break;

                    case "Ausschlussf.":
                        System.out.println("A H:" + homePlayer);
                        Globals.homePlayers.get(homePlayer - 1).addPenalty();
                        break;
                    case "Strafwurff.":
                        System.out.println("S H:" + homePlayer);
                        Globals.homePlayers.get(homePlayer - 1).addPenalty();
                        break;
                    case "Tor":
                        System.out.println("T H:" + homePlayer);
                        Globals.homePlayers.get(homePlayer - 1).addGoal();

                        break;

                    default:
                        break;
                }

            } else {
                switch (e) {
                    case "Ausschluss mit Ersatz":
                        System.out.println("AmE G:" + guestPlayer);
                        Globals.guestPlayers.get(guestPlayer - 1).addPenalty();
                        Globals.guestPlayers.get(guestPlayer - 1).addPenalty();
                        Globals.guestPlayers.get(guestPlayer - 1).addPenalty();
                        break;

                    case "Ausschlussf.":
                        System.out.println("A G:" + guestPlayer);
                        Globals.guestPlayers.get(guestPlayer - 1).addPenalty();
                        break;
                    case "Strafwurff.":
                        System.out.println("S G:" + guestPlayer);
                        Globals.guestPlayers.get(guestPlayer - 1).addPenalty();
                        break;
                    case "Tor":
                        System.out.println("T G:" + guestPlayer);
                        Globals.guestPlayers.get(guestPlayer - 1).addGoal();

                        break;

                    default:
                        break;
                }
            }
            synced++;
            System.out.println("synced: " + synced);
        }
    }
}
