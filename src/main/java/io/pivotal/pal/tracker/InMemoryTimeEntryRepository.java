package io.pivotal.pal.tracker;

import java.util.*;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    private Map<Long, TimeEntry> timeEntryMap = new LinkedHashMap<>();
    private long count = 1;

    public TimeEntry create(TimeEntry timeEntry) {

        timeEntry.setId(count++);
        timeEntryMap.put(timeEntry.getId(), timeEntry);
        return timeEntry;
    }

    public TimeEntry find(long id) {

        return timeEntryMap.get(id);
    }

    public List list() {
        List<TimeEntry> entryList = new ArrayList();
        timeEntryMap.forEach((key, value) -> entryList.add(value));
        return entryList;
    }

    public TimeEntry update(long id, TimeEntry timeEntry) {

        if (timeEntryMap.get(id) != null) {
            timeEntry.setId(id);
            timeEntryMap.put(id, timeEntry);
            return timeEntry;
        } else {
            return null;
        }

    }

    public void delete(long id) {

        timeEntryMap.remove(id);

    }

}
