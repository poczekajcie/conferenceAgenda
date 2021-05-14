import conference.agenda.model.Conference;
import conference.agenda.helpers.FileContentPreparer;
import conference.agenda.model.Track;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FileContentPreparerTest {

    private List<String> lines;
    private FileContentPreparer fileContentPreparer;

    @BeforeAll
    void beforeAll() {
        lines = List.of("Title 15min", "Another title lightning", "Next one 30min");
        fileContentPreparer = new FileContentPreparer();
    }

    @Test
    public void shouldReplaceLightning() {
        List<Integer> result = fileContentPreparer.readFromLines(lines).stream().map(Conference::getTime).collect(Collectors.toList());
        assertThat(result.stream().anyMatch(time -> time == 5)).isTrue();
    }

    @Test
    public void shouldExtractNumbers() {
        List<Integer> result = fileContentPreparer.readFromLines(lines).stream().map(Conference::getTime).collect(Collectors.toList());
        assertThat(result.containsAll(List.of(15, 5, 30))).isTrue();
    }

    @Test
    public void shouldExtractTitles() {
        List<String> titles = fileContentPreparer.readFromLines(lines).stream().map(Conference::getTitle).collect(Collectors.toList());
        assertThat(titles.containsAll(List.of("Title", "Another title", "Next one"))).isTrue();
    }

    @Test
    public void shouldParseTracks() {
        Track track1 = new Track();
        Track track2 = new Track();
        Conference conference = new Conference("title ", 30);
        track1.setMorningSession(List.of(conference));
        track1.setAfternoonSession(List.of(conference));
        track2.setMorningSession(List.of(conference));
        track2.setAfternoonSession(List.of(conference));

        String expectedText = "Track 1:\n09:00 AM title 30min\n12:00 PM Lunch\n01:00 PM title 30min\nTrack 2:\n09:00 AM title 30min\n12:00 PM Lunch\n01:00 PM title 30min\n";
        assertThat(fileContentPreparer.parseConferencesToText(List.of(track1, track2))).isEqualTo(expectedText);
    }

    @Test
    public void shouldIgnoreNumberInConferenceTitle() {
        List<String> testLines = List.of("Title 15min", "Another 15 title lightning", "Next one 30min");
        List<Conference> conferences = fileContentPreparer.readFromLines(testLines);
        assertThat(conferences).contains(new Conference("Another 15 title", 5));
    }
}
