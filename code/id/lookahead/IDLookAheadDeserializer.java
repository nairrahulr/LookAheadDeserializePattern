package code.id.lookahead;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * A simple Java program to demonstrate how to perform input validation on
 * serialized binary buffers. Specifically, we only want to allow instances of
 * the IDGenerator class to be deserialized.
 * 
 * @author Rahul R Nair
 * 
 */
public class IDLookAheadDeserializer {

	private static byte[] serialize(Object obj) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(obj);
		byte[] buffer = baos.toByteArray();
		oos.close();
		baos.close();
		return buffer;
	}

	private static Object deserialize(byte[] buffer) throws IOException,
			ClassNotFoundException {
		ByteArrayInputStream bais = new ByteArrayInputStream(buffer);

		// We use IDLookAheadObjectInputStream instead of InputStream
		ObjectInputStream ois = new IDLookAheadObjectInputStream(bais);

		Object obj = ois.readObject();
		ois.close();
		bais.close();
		return obj;
	}
}