package domin;

import com.google.common.base.MoreObjects;

import java.io.Serializable;

public class User implements Serializable {

    private int id;
    private String username;
    private String password;
    private int status;//0-不在线，1-在线
    private String extendInfo;
    private String ip;
    private int port;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getStatus() { return status; }

    public void setStatus(int status) { this.status = status; }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) { this.username = username; }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).
                add("id", id).
                add("username", username).
                add("password", password).
                add("extendInfo", extendInfo).
                toString();
    }
}
