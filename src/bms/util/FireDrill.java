package bms.util;

import bms.exceptions.FireDrillException;
import bms.room.RoomType;


/**
 * Denotes a class containing a routine to carry out fire drills on rooms
 * of a given type.
 */
public interface FireDrill {

     /**
      * Denotes a class containing a routine to carry out fire drills on rooms
      * of a given type.
      *
      * @param roomType Room type Enum
      * @throws FireDrillException
      */
     void fireDrill(RoomType roomType) throws FireDrillException;
}
