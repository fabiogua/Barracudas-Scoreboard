
package com.fguarino.scoreboard.framework;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Selenium {
    static WebDriver driver;
    static ArrayList<Integer> syncedValueList = new ArrayList<>();
    Matcher matcher;

    void start() {

        System.setProperty("webdriver.chrome.driver", "chromedriver\\chromedriver109.exe");
        driver = new ChromeDriver();

        driver.manage().deleteAllCookies();

        driver.get("https://lizenz.dsv.de/Live.aspx");
    }

    void refresh() {
        /*
         * <th>Ab</th>
         * <th>Zeit</th>
         * <th>Heim</th>
         * <th>Gast</th>
         * <th>Spieler</th>
         * <th>Ereignis</th>
         * <th>Tore</th>
         */

        WebElement table = driver.findElement(By.id("leftTable"));
        String input = table.getAttribute("innerHTML").split("tbody>")[1];

        /*
         * REGGEX101:
         * 
         * "<tr.+?((strikeplay).+)?<td>(\d+?)</td><td>(\d+?)</td><td>(\d+?):(\d+?)</td><td>(\d+?)?</td><td>(\d+?)?</td><td>((.+?),(.+?))?</td><td>(.+?)?</td><td>((\d+?):(\d+?))?</td></tr>"gm
         * 
         */
        int id = -1, homePlayer = -1, guestPlayer = -1;
        String name = "", event = "";
        Boolean strike = false;

        Pattern pattern = Pattern.compile(
                "<tr.+?(strikeplay\">)?<td>(\\d+?)</td><td>\\d+?</td><td>\\d+?:\\d+?</td><td>(\\d+?)?</td><td>(\\d+?)?</td><td>(.+?)?</td><td>(.+?)?</td><td>(\\d+?:\\d+?)?</td></tr>");
        matcher = pattern.matcher(input);
        while (matcher.find()) {
            for (int i = 1; i < matcher.groupCount(); i++) {
                /*
                 * group 1: strikeplay
                 * group 2: Ereignis Nr.
                 * group 3: Heim Nr.
                 * group 4: Gast Nr.
                 * group 5: Nachname, V.
                 * group 6: Ereignis
                 * 
                 */

                id = Integer.valueOf(matcher.group(2));
                strike = matcher.group(1) != null;

                try {
                    homePlayer = Integer.valueOf(matcher.group(3));
                } catch (Exception e) {
                    if (matcher.group(3) == null) {
                        homePlayer = -1;
                    } else if (matcher.group(3).equals("X")) {
                        homePlayer = 0;
                    }
                }

                try {
                    guestPlayer = Integer.valueOf(matcher.group(4));
                } catch (Exception e) {

                    if (matcher.group(4) == null) {
                        guestPlayer = -1;

                    } else if (matcher.group(4).equals("X")) {
                        guestPlayer = 0;
                    }
                }

                name = matcher.group(5);
                event = matcher.group(6);

            }

            synchronizeToScoreBoard(id, strike, homePlayer, guestPlayer, event);
        }
        System.out.println();
        System.out.println();
        System.out.println();

    }

    void synchronizeToScoreBoard(int id, Boolean strike, int homePlayer, int guestPlayer, String e) {

        if (!syncedValueList.contains(id) && !strike) {
            syncedValueList.add(id);

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
        } else if (syncedValueList.contains(id) && strike) {
            if (homePlayer > 0) {
                switch (e) {
                    case "Ausschluss mit Ersatz":
                        System.out.println("AmE H:" + homePlayer);
                        // kp weil alle 3 wegmachen ist blöd?
                        break;

                    case "Ausschlussf.":
                        System.out.println("A H:" + homePlayer);
                        Globals.homePlayers.get(homePlayer - 1).subPenalty();
                        break;
                    case "Strafwurff.":
                        System.out.println("S H:" + homePlayer);
                        Globals.homePlayers.get(homePlayer - 1).subPenalty();
                        break;
                    case "Tor":
                        System.out.println("T H:" + homePlayer);
                        Globals.homePlayers.get(homePlayer - 1).subGoal();

                        break;

                    default:
                        break;
                }

            } else {
                switch (e) {
                    case "Ausschluss mit Ersatz":
                        System.out.println("AmE G:" + guestPlayer);
                        break;

                    case "Ausschlussf.":
                        System.out.println("A G:" + guestPlayer);
                        Globals.guestPlayers.get(guestPlayer - 1).subPenalty();
                        break;
                    case "Strafwurff.":
                        System.out.println("S G:" + guestPlayer);
                        Globals.guestPlayers.get(guestPlayer - 1).subPenalty();
                        break;
                    case "Tor":
                        System.out.println("T G:" + guestPlayer);
                        Globals.guestPlayers.get(guestPlayer - 1).subGoal();

                        break;

                    default:
                        break;
                }
            }
        }
    }
}
