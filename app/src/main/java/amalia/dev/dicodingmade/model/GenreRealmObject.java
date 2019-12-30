package amalia.dev.dicodingmade.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class GenreRealmObject extends RealmObject {
    @PrimaryKey
    private int id;
    private String name;

    public GenreRealmObject(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public GenreRealmObject(){}

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
