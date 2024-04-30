package persistence;

import org.json.JSONObject;

// This is an interface which our JsonReader and JsonWriter class implements to make other subclasses to
// override the toJson function.
public interface Writable {

    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
