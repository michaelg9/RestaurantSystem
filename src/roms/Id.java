package roms;

import java.util.logging.Logger;

public abstract class Id {
    private String id;
    protected static final Logger logger = Logger.getLogger("roms");
    
    public Id(String id){
        logger.fine("Entry");
        this.id = id;
    }

    public String getId() {
        logger.fine("Entry");
        return id;
    }
    
    public boolean equals(Id id){
        logger.fine("Entry");
        return this.id.equals(id.getId());
    }
    
}
