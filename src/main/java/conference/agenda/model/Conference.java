package conference.agenda.model;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class Conference {
    private String title;
    private int time;
}
