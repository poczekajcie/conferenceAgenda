import conference.agenda.helpers.Sorter;
import conference.agenda.model.Conference;
import conference.agenda.model.Track;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SorterTest {

    @Test
    public void shouldSetSessions() {
        ArrayList<Conference> conferences = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            conferences.add(new Conference("Title", 60));
        }
        Sorter sorter = new Sorter(conferences);
        List<Track> tracks = sorter.setupTracks();
        assertThat(tracks.get(0).getMorningSession()).hasSize(3);
        assertThat(tracks.get(0).getAfternoonSession()).hasSize(4);
    }
}
