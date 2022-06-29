package bms.util;

import java.util.ArrayList;
import java.util.List;

/**Singleton class which manages all the timed items.
 * All classes that implement TimedItem must be registered with this manager,
 * which will allow their elapseOneMinute() method to be called at regular
 * time intervals.
 *
 * Once a class is registered with the timed item manager by calling
 * registerTimedItem(TimedItem) ()} and passing itself, the manager will
 * ensure that its elapseOneMinute() method is called at regular intervals.
 */
public class TimedItemManager implements TimedItem {

    /** Singleton instance of the TimeItemManager.*/
    private static TimedItemManager timedItemManager = null;

    /**Stores all registered TimedItems in a building.*/
    private List<TimedItem> registeredTimedItems = new ArrayList<TimedItem>();

    private TimedItemManager() {}

    /**
     * Returns the singleton instance of the timed item manager.
     *
     * @return singleton instance of TimedItemManager Class
     */
    public static TimedItemManager getInstance() {
        if (timedItemManager == null) {
            timedItemManager = new TimedItemManager();
        }
        return timedItemManager;
    }


    /**
     * Registers a timed item with the manager.
     * After calling this method, the manager will call the given timed item's
     * elapseOneMinute() method at regular intervals.
     *
     * @param timedItem a timed item to register with the manager.
     */
    public void registerTimedItem(TimedItem timedItem) {
        registeredTimedItems.add(timedItem);
    }

    /**
     * Calls elapseOneMinute() on each registered timed item.
     */
    public void elapseOneMinute() {
        for (TimedItem registeredTimedItem : registeredTimedItems) {
            registeredTimedItem.elapseOneMinute();
        }
    }
}
