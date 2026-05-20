package gr.york.mobiledev2026.data.remote;

import androidx.annotation.NonNull;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.JsonWriter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;
import gr.york.mobiledev2026.data.model.Artist;
import gr.york.mobiledev2026.data.model.Tag;
import gr.york.mobiledev2026.data.model.Track;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;
import java.util.List;

public class ApiResponseAdapterFactory implements JsonAdapter.Factory {

    public static class ArtistFallbackAdapter {
        @FromJson
        public Artist fromJson(JsonReader reader, JsonAdapter<Artist> delegate) throws IOException {
            if (reader.peek() == JsonReader.Token.STRING) {
                return new Artist(reader.nextString());
            } else if (reader.peek() == JsonReader.Token.BEGIN_OBJECT) {
                return delegate.fromJson(reader);
            } else {
                reader.skipValue();
                return null;
            }
        }

        @ToJson
        public void toJson(JsonWriter writer, Artist value, JsonAdapter<Artist> delegate) throws IOException {
            delegate.toJson(writer, value);
        }
    }

    public static class TagListFallbackAdapter {
        @FromJson
        public List<Tag> fromJson(JsonReader reader, JsonAdapter<List<Tag>> delegate) throws IOException {
            JsonReader.Token token = reader.peek();
            if (token == JsonReader.Token.BEGIN_OBJECT) {
                reader.beginObject();
                List<Tag> tags = null;
                while (reader.hasNext()) {
                    if (reader.nextName().equals("tag")) {
                        if (reader.peek() == JsonReader.Token.BEGIN_ARRAY) {
                            tags = delegate.fromJson(reader);
                        } else if (reader.peek() == JsonReader.Token.BEGIN_OBJECT) {
                            reader.beginObject();
                            String tagName = "";
                            String tagUrl = "";
                            while (reader.hasNext()) {
                                String name = reader.nextName();
                                if (name.equals("name")) {
                                    tagName = reader.nextString();
                                } else if (name.equals("url")) {
                                    tagUrl = reader.nextString();
                                } else {
                                    reader.skipValue();
                                }
                            }
                            reader.endObject();
                            tags = java.util.Collections.singletonList(new Tag(tagName, tagUrl));
                        } else {
                            reader.skipValue();
                        }
                    } else {
                        reader.skipValue();
                    }
                }
                reader.endObject();
                return tags;
            } else if (token == JsonReader.Token.BEGIN_ARRAY) {
                return delegate.fromJson(reader);
            } else {
                reader.skipValue();
                return null;
            }
        }

        @ToJson
        public void toJson(JsonWriter writer, List<Tag> value, JsonAdapter<List<Tag>> delegate) throws IOException {
            delegate.toJson(writer, value);
        }
    }

    public static class TrackListFallbackAdapter {
        @FromJson
        public List<Track> fromJson(JsonReader reader, JsonAdapter<List<Track>> delegate) throws IOException {
            JsonReader.Token token = reader.peek();
            if (token == JsonReader.Token.BEGIN_OBJECT) {
                // Read {"track": [...]} or {"track": {...}}
                reader.beginObject();
                List<Track> tracks = null;
                while (reader.hasNext()) {
                    if (reader.nextName().equals("track")) {
                        if (reader.peek() == JsonReader.Token.BEGIN_ARRAY) {
                            tracks = delegate.fromJson(reader);
                        } else if (reader.peek() == JsonReader.Token.BEGIN_OBJECT) {
                            return delegate.fromJson(reader);
                        } else {
                            reader.skipValue();
                        }
                    } else {
                        reader.skipValue();
                    }
                }
                reader.endObject();
                return tracks;
            } else if (token == JsonReader.Token.BEGIN_ARRAY) {
                return delegate.fromJson(reader);
            } else {
                reader.skipValue();
                return null;
            }
        }

        @ToJson
        public void toJson(JsonWriter writer, List<Track> value, JsonAdapter<List<Track>> delegate) throws IOException {
            delegate.toJson(writer, value);
        }
    }

    private static Object findFirstList(Map<String, Object> map) {
        for (Object value : map.values()) {
            if (value instanceof java.util.List) {
                return value;
            } else if (value instanceof Map) {
                Object found = findFirstList((Map<String, Object>) value);
                if (found != null) return found;
            }
        }
        return null;
    }

    @Override
    public JsonAdapter<?> create(@NonNull Type type, @NonNull Set<? extends Annotation> annotations, @NonNull Moshi moshi) {
        Class<?> rawType = Types.getRawType(type);
        if (rawType != ApiResponse.class) {
            return null;
        }
        if (!(type instanceof ParameterizedType)) {
            return null;
        }

        Type dataType = ((ParameterizedType) type).getActualTypeArguments()[0];
        JsonAdapter<Object> dataAdapter = moshi.adapter(dataType);

        return new JsonAdapter<ApiResponse<?>>() {
            @Override
            public ApiResponse<?> fromJson(@NonNull JsonReader reader) throws IOException {
                ApiResponse<Object> apiResponse = new ApiResponse<>();
                Map<String, Object> jsonMap = (Map<String, Object>) moshi.adapter(Map.class).fromJson(reader);

                if (jsonMap == null) return apiResponse;

                if (jsonMap.containsKey("error")) {
                    apiResponse.setError(String.valueOf(jsonMap.get("message")));
                    Object errCode = jsonMap.get("error");
                    if (errCode instanceof Number) {
                        apiResponse.setCode(((Number) errCode).intValue());
                    }
                    return apiResponse;
                }

                for (String key : jsonMap.keySet()) {
                    if (!key.equals("error")) {
                        Object content = jsonMap.get(key);
                        if (java.util.List.class.isAssignableFrom(Types.getRawType(dataType))) {
                            if (content instanceof Map) {
                                Object foundList = findFirstList((Map<String, Object>) content);
                                if (foundList != null) {
                                    content = foundList;
                                }
                            }
                        }

                        String jsonString = moshi.adapter(Object.class).toJson(content);
                        Object data = dataAdapter.fromJson(jsonString);
                        apiResponse.setData(data);
                        return apiResponse;
                    }
                }

                return apiResponse;
            }

            @Override
            public void toJson(@NonNull JsonWriter writer, ApiResponse<?> value) {
            }
        };
    }
}
