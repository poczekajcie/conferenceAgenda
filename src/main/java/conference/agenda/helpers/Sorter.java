package conference.agenda.helpers;

import conference.agenda.model.Conference;
import conference.agenda.model.Track;

import java.util.*;
import java.util.stream.Collectors;

public class Sorter {
    private static final int MORNING_SESSION_TIME = 180;
    private static final int AFTERNOON_SESSION_TIME = 240;

    private List<Conference> conferences;

    public Sorter(List<Conference> conferences) {
        conferences.sort(Comparator.comparing(Conference::getTime));
        this.conferences = conferences;
    }

    public List<Track> setupTracks() {
        Track track1 = new Track();
        Track track2 = new Track();

        track1.setMorningSession(getConferencesForSession(getSessionTimes(MORNING_SESSION_TIME)));
        track2.setMorningSession(getConferencesForSession(getSessionTimes(MORNING_SESSION_TIME)));
        track1.setAfternoonSession(getConferencesForSession(getSessionTimes(AFTERNOON_SESSION_TIME)));
        track2.setAfternoonSession(getConferencesForSession(getSessionTimes(AFTERNOON_SESSION_TIME)));

        return List.of(track1, track2);

    }

    private List<Integer> getSessionTimes(int sessionTime) {
        int availableTime = sessionTime;
        List<Integer> elements = new ArrayList<>();
        List<Integer> availableElementsTime = conferences.stream().map(Conference::getTime).collect(Collectors.toList());
        while (availableTime >= 0 && elements.stream().mapToInt(a -> a).sum() <= sessionTime && !availableElementsTime.isEmpty()) {
            int longestAvailableConferenceTime = availableElementsTime.get(availableElementsTime.size() - 1);
            elements.add(longestAvailableConferenceTime);
            availableTime = availableTime - longestAvailableConferenceTime;
            availableElementsTime.remove(availableElementsTime.size() - 1);
        }
        if (elements.stream().mapToInt(a -> a).sum() > sessionTime) {
            elements.remove(elements.size() - 1);
        }
        return elements;
    }

    private List<Conference> getConferencesForSession(List<Integer> times) {
        List<Conference> sessionConferences = new ArrayList<>();
        times.forEach(time -> {
            Optional<Conference> conference = conferences
                    .stream()
                    .filter(item -> item.getTime() == time)
                    .findFirst();
            if (conference.isPresent()) {
                sessionConferences.add(conference.get());
                conferences.remove(conference.get());
            }
        });

        return sessionConferences;
    }
}
