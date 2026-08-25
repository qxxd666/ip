package doge.storage;

import doge.exception.DogeException;
import doge.model.Deadline;
import doge.model.Event;
import doge.model.Task;
import doge.model.TaskList;
import doge.model.Todo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;


/** Reads and writes tasks using the application's local data file. */
public class Storage {
    private static final Path DATA_FILE = Path.of("data", "tasks.txt");

    /** Saves all tasks to disk, replacing the existing data file. */
    public void save(TaskList taskList) throws DogeException {
        try {
            Files.createDirectories(DATA_FILE.getParent());
            List<String> lines = new ArrayList<>();

            for (Task task : taskList.getTasks()) {
                lines.add(task.toStorageString());
            }

            Files.write(DATA_FILE, lines, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new DogeException("Could not save tasks.");
        }
    }

    /** Loads tasks from disk, returning an empty list when no data file exists. */
    public TaskList load() throws DogeException {
        TaskList taskList = new TaskList();

        if (!Files.exists(DATA_FILE)) {
            return taskList;
        }

        try {
            List<String> lines = Files.readAllLines(DATA_FILE, StandardCharsets.UTF_8);

            for (String line : lines) {
                if (!line.isBlank()) {
                    taskList.add(convertLineToTask(line));
                }
            }

            return taskList;
        } catch (IOException e) {
            throw new DogeException("Could not load tasks.");
        }
    }

    /** Converts one persisted task line into a task object with its saved status. */
    private Task convertLineToTask(String line) throws DogeException {
        String[] parts = line.split("\\s*\\|\\s*", -1);
        if (parts.length < 3) {
            throw new DogeException("Invalid task data: " + line);
        }

        String type = parts[0];
        String status = parts[1];
        if (!status.equals("0") && !status.equals("1")) {
            throw new DogeException("Invalid task data: " + line);
        }

        boolean isDone = status.equals("1");
        Task task;

        switch (type) {
        case "T" -> {
            if (parts.length != 3) {
                throw new DogeException("Invalid todo data: " + line);
            }
            task = new Todo(parts[2]);
        }
        case "D" -> {
            if (parts.length != 4) {
                throw new DogeException("Invalid deadline data: " + line);
            }
            try {
                LocalDateTime deadline = LocalDateTime.parse(
                        parts[3], DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                task = new Deadline(parts[2], deadline);
            } catch (DateTimeParseException e) {
                throw new DogeException("Invalid deadline data: " + line);
            }
        }
        case "E" -> {
            if (parts.length != 5) {
                throw new DogeException("Invalid event data: " + line);
            }
            try {
                LocalDateTime start = LocalDateTime.parse(
                        parts[3], DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                LocalDateTime end = LocalDateTime.parse(
                        parts[4], DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                task = new Event(parts[2], start, end);
            } catch (DateTimeParseException e) {
                throw new DogeException("Invalid event data: " + line);
            }
        }
        default -> throw new DogeException("Unknown task type: " + type);
        }

        if (isDone) {
            task.markDone();
        }
        return task;
    }
}
