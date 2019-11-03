package amalia.dev.dicodingmade;

public class Pemeran {
    private String namePerson;
    private String nameCharacter;

    public Pemeran(){};

    public Pemeran(String namePerson, String nameCharacter) {
        this.namePerson = namePerson;
        this.nameCharacter = nameCharacter;
    }

    public String getNamePerson() {
        return namePerson;
    }

    public void setNamePerson(String namePerson) {
        this.namePerson = namePerson;
    }

    public String getNameCharacter() {
        return nameCharacter;
    }

    public void setNameCharacter(String nameCharacter) {
        this.nameCharacter = nameCharacter;
    }
}
