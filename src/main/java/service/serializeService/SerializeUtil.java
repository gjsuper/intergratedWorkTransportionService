package service.serializeService;

import java.io.*;

public class SerializeUtil {

    public static Object deserialize(byte[] data) {
        Object readObject = null;
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream inputStream;

        try {
            inputStream = new ObjectInputStream(in);
            readObject = inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return readObject;
    }

    public static byte[] serialize(Object data) {
        byte[] bb;
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        ObjectOutputStream outputStream;

        try {
            outputStream = new ObjectOutputStream(byteArray);
            outputStream.writeObject(data);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        bb = byteArray.toByteArray();
        return bb;
    }
}
