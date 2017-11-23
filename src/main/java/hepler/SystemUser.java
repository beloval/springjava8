package hepler;

/**
 * Created by abelov on 22/11/17.
 */
public class SystemUser {
    private final String name;
    private final String fullname;

    public SystemUser(String name, String fullname) {
        this.name = name;
        this.fullname= fullname;
    }

    public String getName() {
        return name;
    }

    public String getFullname() {
        return fullname;
    }
}
