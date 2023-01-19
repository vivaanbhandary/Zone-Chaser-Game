package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

// Represents a log of alarm system events.
 // We use the Singleton Design Pattern to ensure that there is only
 // one EventLog in the system and that the system has global access
 // to the single instance of the EventLog.

public class EventLog implements Iterable<Event> {
    // the only EventLog in the system (Singleton Design Pattern)
    private static EventLog theLog;
    private Collection<Event> events;

    //Prevent external construction (Singleton Design Pattern)
    private EventLog() {
        events = new ArrayList<Event>();
    }

    //MODIFIES: this
    //EFFECTS: creates instance of this if not done so yet, then returns it
    public static EventLog getInstance() {
        if (theLog == null) {
            theLog = new EventLog();
        }

        return theLog;
    }

    //MODIFIES: this
    //EFFECTS: adds the event to the event log
    public void logEvent(Event e) {
        events.add(e);
    }

    //MODIFIES: this
    //EFFECTS: Clears the event log and logs the event.
    public void clear() {
        events.clear();
        logEvent(new Event("Event log cleared."));
    }

    //EFFECTS: returns the iterator
    @Override
    public Iterator<Event> iterator() {
        return events.iterator();
    }
}
