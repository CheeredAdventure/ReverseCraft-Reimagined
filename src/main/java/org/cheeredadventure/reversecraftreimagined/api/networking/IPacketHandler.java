package org.cheeredadventure.reversecraftreimagined.api.networking;

import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

public interface IPacketHandler<T> {

  void encode(T msg, FriendlyByteBuf buffer);

  T decode(FriendlyByteBuf buffer);

  void handle(T msg, Supplier<Context> ctx);

}
