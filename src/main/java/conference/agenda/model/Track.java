package conference.agenda.model;

import lombok.Data;

import java.util.List;

@Data
public class Track {
    private List<Conference> morningSession;
    private List<Conference> afternoonSession;
}
