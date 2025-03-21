package org.cheeredadventure.reversecraftreimagined.api;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import java.util.Optional;

public class OptionalTypeAdapterFactory implements TypeAdapterFactory {

  @Override
  @SuppressWarnings("unchecked")
  public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
    if (type.getRawType() == Optional.class) {
      TypeToken<?> innerType = TypeToken.get(type.getRawType());
      TypeAdapter<?> delegate = gson.getAdapter(innerType);
      return (TypeAdapter<T>) OptionalTypeAdapter.of(delegate);
    }
    return null;
  }
}
