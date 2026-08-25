import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private static final Path DATA_FILE = Path.of("data", "tasks.txt");

    public void save(TaskList taskList) throws DogeException {
        try {
            Files.createDirectories(DATA_FILE.getParent());

            List<String> lines = new ArrayList<>();

            for (Task task : taskList.getTasks()) {
                lines.add(convertTaskToLine(task));
            }
        } catch (IOException e) {
            throw new DogeException("Could not save tasks.");
        }
    }

    private String convertTaskToLine(Task task) {
        String status = task.isDone() ? "1" : "0";

    }
}
