package io.github.InSearchOfName.InSearchOfNamesRandomAdditions;

import com.google.inject.Injector;
import io.github.InSearchOfName.inSearchOfNamesRandomAdditions.InSearchOfNamesRandomAdditions;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;

/**
 * Lightweight helper to bootstrap MockBukkit + the plugin and provide
 * convenient access to the Guice injector and common test utilities.
 *
 * Usage:
 * <pre>
 *   private TestHelper helper;
 *   private ServerMock server;
 *   private InSearchOfNamesRandomAdditions plugin;
 *
 *   @BeforeEach
 *   void setup() {
 *     helper = TestHelper.start();
 *     server = helper.server();
 *     plugin = helper.plugin();
 *   }
 *
 *   @AfterEach
 *   void tearDown() { helper.close(); }
 * </pre>
 */
public final class TestHelper implements AutoCloseable {
  private final ServerMock server;
  private final InSearchOfNamesRandomAdditions plugin;

  private TestHelper(ServerMock server, InSearchOfNamesRandomAdditions plugin) {
    this.server = server;
    this.plugin = plugin;
  }

  public static TestHelper start() {
    ServerMock server = MockBukkit.mock();
    InSearchOfNamesRandomAdditions plugin = MockBukkit.load(InSearchOfNamesRandomAdditions.class);
    return new TestHelper(server, plugin);
  }

  public ServerMock server() { return server; }
  public InSearchOfNamesRandomAdditions plugin() { return plugin; }
  public Injector injector() { return plugin.getInjector(); }

  public <T> T get(Class<T> type) { return injector().getInstance(type); }

  public PlayerMock newPlayer() { return server.addPlayer(); }
  public PlayerMock newPlayer(String name) { return server.addPlayer(name); }

  public void tick(int ticks) { server.getScheduler().performTicks(ticks); }

  public void openMenu(PlayerMock player) { server.dispatchCommand(player, "Menu"); }

  @Override
  public void close() {
    MockBukkit.unmock();
  }
}
