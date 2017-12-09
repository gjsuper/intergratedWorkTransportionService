package domin;


import java.io.Serializable;

public class KafkaDataStruct implements Serializable {

    private byte[] data;
    private int len;

    public KafkaDataStruct(byte[] data, int len) {

        this.data = new byte[len];

        System.arraycopy(data, 0, this.data, 0, len);

        this.len = len;
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
        StringBuilder sb = new StringBuilder("len:").append(len).append(", data:[");

        for(byte b : data) {
            sb.append(Integer.toHexString(b & 0xFF)).append(" ");
        }

        sb.append("]");

        return sb.toString();
    }
}
