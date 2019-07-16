package code.id.lookahead;

import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

import code.id.IdGenerator;

/**
 * Our ObjectInputStream subclass.
 * 
 * @author Rahul R Nair
 * 
 */
public class IDLookAheadObjectInputStream extends ObjectInputStream {

	public IDLookAheadObjectInputStream(InputStream inputStream)
			throws IOException {
		super(inputStream);
	}

	/**
	 * Only deserialize instances of our expected IDGenerator class
	 */
	@Override
	protected Class<?> resolveClass(ObjectStreamClass oDser) throws IOException,
			ClassNotFoundException {
		if (!oDser.getName().equals(IdGenerator.class.getName())) {
			throw new InvalidClassException(
					"Unauthorized deserialization attempt", oDser.getName());
		}
		return super.resolveClass(oDser);
	}
}
