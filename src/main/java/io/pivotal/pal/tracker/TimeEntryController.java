package io.pivotal.pal.tracker;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class TimeEntryController {


    private TimeEntryRepository timeEntryRepository;
    private final DistributionSummary timeEntrySummary;
    private final Counter actionCounter;

    public TimeEntryController(TimeEntryRepository timeEntryRepository, MeterRegistry meterRegistry) {

        this.timeEntryRepository = timeEntryRepository;
        timeEntrySummary = meterRegistry.summary("timeEntry.summary");
        actionCounter = meterRegistry.counter("timeEntry.actionCounter");

    }
    @PostMapping("/time-entries")
    public ResponseEntity create(@RequestBody TimeEntry timeEntryToCreate) {

        ResponseEntity<TimeEntry> entity = new ResponseEntity(timeEntryRepository.create(timeEntryToCreate), HttpStatus.CREATED);
        actionCounter.increment();
        timeEntrySummary.record(timeEntryRepository.list().size());
        return entity;
    }

    @GetMapping("/time-entries/{timeEntryId}")
    public ResponseEntity<TimeEntry> read(@PathVariable long timeEntryId) {

        TimeEntry timeEntry = timeEntryRepository.find(timeEntryId);
        if (timeEntry != null) {
            ResponseEntity<TimeEntry> entity = new ResponseEntity(timeEntry, HttpStatus.OK);
            actionCounter.increment();
            return entity;
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/time-entries")
    public ResponseEntity<List<TimeEntry>> list() {

        ResponseEntity<List<TimeEntry>> entity = new ResponseEntity(timeEntryRepository.list(),HttpStatus.OK);
        actionCounter.increment();
        return entity;
    }
    @PutMapping("/time-entries/{timeEntryId}")
    public ResponseEntity update(@PathVariable long timeEntryId, @RequestBody TimeEntry expected) {

        TimeEntry timeEntry = timeEntryRepository.update(timeEntryId,expected);
        if (timeEntry != null) {
            actionCounter.increment();
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
        actionCounter.increment();
        timeEntrySummary.record(timeEntryRepository.list().size());
        return entity;
    }
}
