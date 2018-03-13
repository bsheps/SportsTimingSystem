import java.io.IOException;
import java.time.LocalTime;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * @author bjf
 * 
 *         This class tells Gson how to properly parse a Racer object into Json.
 *         All it does is define the values that are interesting to us. I've
 *         also included a read method to create a Racer instance from Json
 *         data.
 *
 */
public class RacerAdapter extends TypeAdapter<Racer> {
	public Racer read(JsonReader reader) throws IOException {
		if (reader.peek() == JsonToken.NULL) {
			reader.nextNull();
			return null;
		}
		String nse = reader.nextString();
		String[] parts = nse.split(",");
		String name = parts[0];
		LocalTime start = LocalTime.parse(parts[1]);
		LocalTime end = LocalTime.parse(parts[2]);
		return new Racer(name, start, end);
	}

	public void write(JsonWriter writer, Racer value) throws IOException {
		if (value == null) {
			writer.nullValue();
			return;
		}
		String nse = value.getBibNum() + "," + value.getStartTime() + "," + value.getEndTime();
		writer.value(nse);
	}
}