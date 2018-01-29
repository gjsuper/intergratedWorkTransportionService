package domin;


import java.io.Serializable;

public class KafkaDataStruct implements Serializable {

    private byte[] data;
    private int len;
    private String ip;
    private int port;

    public KafkaDataStruct(byte[] data, int len, String ip, int port) {

        this.data = new byte[len];

        System.arraycopy(data, 0, this.data, 0, len);

        this.len = len;

        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() { return port; }

    public void setPort(int port) {
        this.port = port;
    }



    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("len:").append(len).append(", ip:").
                append(ip).append(", post:").append(port).append(", data:[");

        for(byte b : data) {
            sb.append(Integer.toHexString(b & 0xFF)).append(" ");
        }

        sb.append("]");

        return sb.toString();
    }
}
