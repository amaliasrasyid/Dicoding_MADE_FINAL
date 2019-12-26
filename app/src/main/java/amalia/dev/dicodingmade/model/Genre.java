package amalia.dev.dicodingmade.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Genre  extends RealmObject {
    @PrimaryKey
    private int id;
    private String name;

    public Genre(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public Genre(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
