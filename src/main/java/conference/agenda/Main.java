package conference.agenda;

import conference.agenda.helpers.FileContentPreparer;
import conference.agenda.helpers.FileManager;
import conference.agenda.helpers.Sorter;
import conference.agenda.model.Conference;
import conference.agenda.model.Track;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        String inputFilePath = args[0];
        String outputFilePath = args[1];

	    FileManager fileManager = new FileManager();
        FileContentPreparer fileContentPreparer = new FileContentPreparer();

	    List<String> lines = fileManager.readFile(inputFilePath);
        List<Conference> conferences = fileContentPreparer.readFromLines(lines);
        Sorter sorter = new Sorter(conferences);
        List<Track> tracks = sorter.setupTracks();
        String outputText = fileContentPreparer.parseConferencesToText(tracks);
        fileManager.writeToFile(outputText, outputFilePath);
    }
}
