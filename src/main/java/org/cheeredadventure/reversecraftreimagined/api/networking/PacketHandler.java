package org.cheeredadventure.reversecraftreimagined.api.networking;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.cheeredadventure.reversecraftreimagined.ReverseCraftReimagined;

@RequiredArgsConstructor
@Getter
public class PacketHandler {

  public static final SimpleChannel INSTANCE;
  private static final String PROTOCOL_VERSION = "1";
  private static int packetId = 0;

  static {
    INSTANCE = NetworkRegistry.ChannelBuilder
      .named(ResourceLocation.tryBuild(ReverseCraftReimagined.MODID, "main"))
      .networkProtocolVersion(() -> PROTOCOL_VERSION)
      .clientAcceptedVersions(PROTOCOL_VERSION::equals)
      .serverAcceptedVersions(PROTOCOL_VERSION::equals)
      .simpleChannel();
  }

  public static <T> void registerPacket(Class<T> messageType, IPacketHandler<T> handler) {
    INSTANCE.registerMessage(packetId++, messageType, handler::encode, handler::decode,
      handler::handle);
  }
}
