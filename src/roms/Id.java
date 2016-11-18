package roms;

public abstract class Id {
    private String id;

    public Id(String id){
        this.id = id;
    }

    public String getId() {
        return id;
    }
    
    public boolean equals(Id id){
        return this.id.equals(id.getId());
    }
    
}
