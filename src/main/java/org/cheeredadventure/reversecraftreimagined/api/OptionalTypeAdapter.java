package org.cheeredadventure.reversecraftreimagined.api;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.Optional;

public class OptionalTypeAdapter<T> extends TypeAdapter<Optional<T>> {

  private final TypeAdapter<T> delegate;

  public OptionalTypeAdapter(TypeAdapter<T> delegate) {
    this.delegate = delegate;
  }

  public static <T> TypeAdapter<Optional<T>> of(TypeAdapter<T> delegate) {
    return new OptionalTypeAdapter<>(delegate);
  }

  @Override
  public void write(JsonWriter out, Optional<T> value) throws IOException {
    if (value.isPresent()) {
      delegate.write(out, value.get());
    } else {
      out.nullValue();
    }
  }

  @Override
  public Optional<T> read(JsonReader in) throws IOException {
    if (in.peek() == JsonToken.NULL) {
      in.nextNull();
      return Optional.empty();
    } else {
      return Optional.of(delegate.read(in));
    }
  }
}
