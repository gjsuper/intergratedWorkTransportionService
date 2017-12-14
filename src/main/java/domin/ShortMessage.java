package domin;

import java.sql.Timestamp;

public class ShortMessage {

    private String receiver;
    private String sender;
    private Timestamp recvTime;
    private int status;//0-已读，1-未读
    private int transType;
    private String extendInfo;
    private String message;
    private int dstPort;
    private int srcPort;

    public int getDstPort() {
        return dstPort;
    }

    public void setDstPort(int dstPort) {
        this.dstPort = dstPort;
    }

    public int getSrcPort() {
        return srcPort;
    }

    public void setSrcPort(int srcPort) {
        this.srcPort = srcPort;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Timestamp getRecvTime() {
        return recvTime;
    }

    public void setRecvTime(Timestamp recvTime) {
        this.recvTime = recvTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTransType() {
        return transType;
    }

    public void setTransType(int transType) {
        this.transType = transType;
    }

    public String getExtendInfo() {
        return extendInfo;
    }

    public void setExtendInfo(String extendInfo) {
        this.extendInfo = extendInfo;
    }
}
