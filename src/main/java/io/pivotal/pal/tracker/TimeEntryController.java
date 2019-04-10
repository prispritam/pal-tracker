package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class TimeEntryController {

    private TimeEntryRepository timeEntryRepository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {

        this.timeEntryRepository = timeEntryRepository;

    }
    @PostMapping("/time-entries")
    public ResponseEntity create(@RequestBody TimeEntry timeEntryToCreate) {

        ResponseEntity<TimeEntry> entity = new ResponseEntity(timeEntryRepository.create(timeEntryToCreate), HttpStatus.CREATED);
        return entity;
    }

    @GetMapping("/time-entries/{timeEntryId}")
    public ResponseEntity<TimeEntry> read(@PathVariable long timeEntryId) {

        TimeEntry timeEntry = timeEntryRepository.find(timeEntryId);
        if (timeEntry != null) {
            ResponseEntity<TimeEntry> entity = new ResponseEntity(timeEntry, HttpStatus.OK);
            return entity;
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/time-entries")
    public ResponseEntity<List<TimeEntry>> list() {

        ResponseEntity<List<TimeEntry>> entity = new ResponseEntity(timeEntryRepository.list(),HttpStatus.OK);
        return entity;
    }
    @PutMapping("/time-entries/{timeEntryId}")
    public ResponseEntity update(@PathVariable long timeEntryId, @RequestBody TimeEntry expected) {

        TimeEntry timeEntry = timeEntryRepository.update(timeEntryId,expected);
        if (timeEntry != null) {
            ResponseEntity<TimeEntry> entity = new ResponseEntity(timeEntry, HttpStatus.OK);
            return entity;
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

    }
    @DeleteMapping("/time-entries/{timeEntryId}")
    public ResponseEntity<TimeEntry> delete(@PathVariable long timeEntryId) {

        timeEntryRepository.delete(timeEntryId);
        ResponseEntity<TimeEntry> entity = new ResponseEntity(HttpStatus.NO_CONTENT);
        return entity;
    }
}
