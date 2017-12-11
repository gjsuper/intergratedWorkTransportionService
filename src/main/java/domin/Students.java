package domin;

import com.google.common.base.MoreObjects;

public class Students {
    private int id;
    private String name;
    private String password;
    private String extendInfo;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getExtendInfo() {
        return extendInfo;
    }

    public void setExtendInfo(String extendInfo) {
        this.extendInfo = extendInfo;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public Students() {
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).
                add("name", name).add("password", password).
                add("extendInfo", extendInfo).toString();
    }

}
