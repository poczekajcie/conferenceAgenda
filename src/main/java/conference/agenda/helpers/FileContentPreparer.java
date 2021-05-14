package conference.agenda.helpers;

import conference.agenda.model.Conference;
import conference.agenda.model.Track;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FileContentPreparer {
    private static final String LIGHTNING_NAME = "lightning";

    public List<Conference> readFromLines(List<String> lines) {
        List<Conference> list = new LinkedList<>();
        lines.forEach(line -> {
            if (line.contains(LIGHTNING_NAME)) {
                line = line.replace(LIGHTNING_NAME, "5min");
            }
            String numbers = getNumbers(line);
            int timePositionInString = line.lastIndexOf(numbers);
            list.add(new Conference(line.substring(0, timePositionInString).trim(), Integer.parseInt(numbers)));
        });
        return list;
    }

    private String getNumbers(String line) {
        String result = line.replaceAll("\\D+","");
        if (result.length() > 2) {
            result =result.substring(2);
        }
        return result;
    }

    public String parseConferencesToText(List<Track> tracks) {
        List<String> lines = new ArrayList<>();
        parseTrack("Track 1:", tracks.get(0), lines);
        parseTrack("Track 2:", tracks.get(1), lines);
        StringBuilder outputText = new StringBuilder();
        for (String line : lines) {
            outputText.append(line).append("\n");
        }
        return outputText.toString();
    }

    private void parseTrack(String title, Track track, List<String> lines) {
        lines.add(title);
        parseSession(LocalTime.parse("09:00:00", DateTimeFormatter.ISO_TIME), track.getMorningSession(), lines);
        lines.add(DateTimeFormatter.ofPattern("hh:mm a").format(LocalTime.parse("12:00:00", DateTimeFormatter.ISO_TIME)) + " Lunch");
        parseSession(LocalTime.parse("13:00:00", DateTimeFormatter.ISO_TIME), track.getAfternoonSession(), lines);
    }

    private void parseSession(LocalTime startingHour, List<Conference> sessionConferences, List<String> lines) {
        for (Conference conference : sessionConferences) {
            lines.add(DateTimeFormatter.ofPattern("hh:mm a").format(startingHour) + " " + conference.getTitle() + conference.getTime() + "min");
            startingHour = startingHour.plusMinutes(conference.getTime());
        }
    }
}
