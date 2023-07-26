import alfheim.common.core.handler.CardinalSystem;
import alfheim.common.item.equipment.bauble.faith.FaithHandlerHeimdall;
import alfheim.common.item.equipment.bauble.faith.FaithHandlerLoki;
import alfheim.common.item.equipment.bauble.faith.FaithHandlerNjord;
import alfheim.common.item.equipment.bauble.faith.FaithHandlerOdin;
import alfheim.common.item.equipment.bauble.faith.FaithHandlerSif;
import alfheim.common.item.equipment.bauble.faith.FaithHandlerThor;
import alfmod.common.entity.EntityFireSpirit;
import com.rwtema.extrautils.tileentity.endercollector.CollectorHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.EventBus;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import crazypants.enderio.machine.wireless.WirelessChargerController;
import forestry.apiculture.render.ParticleRenderer;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import loliland.lolimod.IOiO0111lanD;
import loliland.lolimod.OOO1010lLaNd;
import loliland.lolimod.illlIlIllAnd;
import loliland.lolimod.l1II1laND;
import loliland.lolimod.llIIi1lanD;
import mekanism.client.render.block.TextureSubmap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.profiler.Profiler;
import net.minecraft.util.MovementInputFromOptions;
import net.minecraft.util.Timer;
import net.minecraftforge.common.MinecraftForge;

@IOiO0111lanD
public class li0OI1llAND
extends llIIi1lanD {
    private static int lI00OlAND;
    public static final Set I1O1I1LaNd;
    private boolean lli0OiIlAND;
    public static ArrayList OOOIilanD;

    @SideOnly(value=Side.CLIENT)
    @SubscribeEvent
    public void I1O1I1LaNd(TickEvent.ClientTickEvent clientTickEvent) {
        if (clientTickEvent.phase != TickEvent.Phase.END) {
            return;
        }
        Minecraft minecraft = Minecraft.func_71410_x();
        EntityClientPlayerMP entityClientPlayerMP = minecraft.field_71439_g;
        if (minecraft.field_71441_e == null || entityClientPlayerMP == null) {
            return;
        }
        if (!this.lli0OiIlAND && entityClientPlayerMP.field_70173_aa > 300 && entityClientPlayerMP.field_70173_aa % 100 == 0) {
            ArrayList<String> arrayList = new ArrayList<String>();
            try {
                File file;
                File file2;
                Object object;
                int n = li0OI1llAND.OOOIilanD();
                if (lI00OlAND > 0 && n > lI00OlAND) {
                    arrayList.add("Неверное количество ивентов (" + n + " из " + lI00OlAND + ")");
                }
                if (entityClientPlayerMP.field_70138_W > 1.25f) {
                    arrayList.add("Слишком большой player.stepHeight (" + entityClientPlayerMP.field_70138_W + ")");
                }
                if (minecraft.field_71424_I.getClass() != Profiler.class || minecraft.field_71424_I.getClass().getSuperclass() != Object.class) {
                    arrayList.add("Подмена Profiler (" + minecraft.field_71424_I.getClass().getName() + ")");
                }
                if ((object = ReflectionHelper.getPrivateValue(Minecraft.class, (Object)minecraft, (String[])new String[]{"timer", "field_71428_T", "Q"})).getClass() != Timer.class || object.getClass().getSuperclass() != Object.class) {
                    arrayList.add("Подмена Timer (" + object.getClass().getName() + ")");
                }
                if (entityClientPlayerMP.field_71158_b.getClass() != MovementInputFromOptions.class) {
                    arrayList.add("Подмена player.movementInput (" + entityClientPlayerMP.field_71158_b.getClass().getName() + ")");
                }
                if (minecraft.func_147114_u().getClass() != NetHandlerPlayClient.class) {
                    arrayList.add("Подмена player.sendQueue (" + minecraft.func_147114_u().getClass().getName() + ")");
                }
                if (Loader.isModLoaded((String)"xenobyte")) {
                    arrayList.add("Найден 'XenoByte'");
                }
                if (Loader.isModLoaded((String)"EHacks")) {
                    arrayList.add("Найден 'EHacks'");
                }
                if ((file2 = new File("./config/ehacks")).exists()) {
                    arrayList.add("Обнаружен конфиг от 'EHacks' на LoliLand");
                    try {
                        file2.delete();
                    }
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                if ((file = new File(System.getProperty("user.home") + "/xenobyte/config.cfg")).exists()) {
                    arrayList.add("Обнаружен конфиг от 'XenoByte' на LoliLand");
                    try {
                        file.delete();
                    }
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                OOOIilanD.forEach(arg_0 -> li0OI1llAND.I1O1I1LaNd(arrayList, arg_0));
            }
            catch (Throwable throwable) {
                arrayList.add("Произошла ошибка при проверке значений: " + throwable.getMessage());
            }
            if (!arrayList.isEmpty()) {
                illlIlIllAnd.I1O1I1LaNd(new OOO1010lLaNd(arrayList));
                this.lli0OiIlAND = true;
            }
        }
    }

    public static void I1O1I1LaNd(int n) {
        lI00OlAND = n;
    }

    static {
        I1O1I1LaNd = new HashSet();
        OOOIilanD = new ArrayList();
        OOOIilanD.add("PgndbAkn17WQieF.PgndbAkn17WQieF.PgndbAkn17WQieF.Npa9GOLD3HJ8tJoj");
        OOOIilanD.add("QU1zzq64VdQhBsFU.QU1zzq64VdQhBsFU.QU1zzq64VdQhBsFU.U6LIrs0jmqlZEmU");
        OOOIilanD.add("QU1zzq64VdQhBsFU.QU1zzq64VdQhBsFU.QU1zzq64VdQhBsFU.U6LIrs0jmqlZEmU");
        OOOIilanD.add("qeL8ldjimmt8nyzP.qeL8ldjimmt8nyzP.qeL8ldjimmt8nyzP.V1eMMoFF77yycw6F");
        OOOIilanD.add("DCVynoTvQwmdpl.DCVynoTvQwmdpl.DCVynoTvQwmdpl.OUidljcU8ow3cMnx");
        OOOIilanD.add("ehacks.mod.main.Main");
    }

    public static int OOOIilanD() {
        return li0OI1llAND.I1O1I1LaNd(MinecraftForge.EVENT_BUS) + li0OI1llAND.I1O1I1LaNd(FMLCommonHandler.instance().bus());
    }

    private static int I1O1I1LaNd(EventBus eventBus) {
        ConcurrentHashMap concurrentHashMap = (ConcurrentHashMap)ReflectionHelper.getPrivateValue(EventBus.class, (Object)eventBus, (int)1);
        return concurrentHashMap.values().stream().mapToInt(ArrayList::size).sum();
    }

    private static void I1O1I1LaNd(ArrayList arrayList, String string) {
        try {
            Class<?> clazz = Class.forName(string);
            arrayList.add("Найден чит-класс '" + string + "'");
        }
        catch (ClassNotFoundException classNotFoundException) {
            // empty catch block
        }
    }

    public static int lI00OlAND() {
        return lI00OlAND;
    }

    public static void I1O1I1LaNd() {
    }

    @Override
    public void init() {
        MinecraftForge.EVENT_BUS.register((Object)this);
        FMLCommonHandler.instance().bus().register((Object)this);
        li0OI1llAND.I1O1I1LaNd();
        File file = new File(System.getProperty("user.home") + "/xenobyte/config.cfg");
        if (file.exists()) {
            try {
                file.delete();
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        new l1II1laND().start();
        if (Loader.isModLoaded((String)"Mekanism")) {
            TextureSubmap.class.getName();
        }
        if (Loader.isModLoaded((String)"Forestry")) {
            ParticleRenderer.getInstance();
        }
        if (Loader.isModLoaded((String)"EnderIO")) {
            WirelessChargerController.instance.getChangeCount();
        }
        if (Loader.isModLoaded((String)"ExtraUtilities")) {
            CollectorHandler.INSTANCE.getClass();
        }
        if (Loader.isModLoaded((String)"alfheim")) {
            FaithHandlerHeimdall.INSTANCE.getClass();
            FaithHandlerLoki.INSTANCE.getClass();
            FaithHandlerNjord.INSTANCE.getClass();
            FaithHandlerOdin.INSTANCE.getClass();
            FaithHandlerSif.INSTANCE.getClass();
            FaithHandlerThor.INSTANCE.getClass();
        }
        if (Loader.isModLoaded((String)"alfmod")) {
            EntityFireSpirit.Companion.getClass();
            CardinalSystem.INSTANCE.getClass();
            CardinalSystem.SpellCastingSystem.INSTANCE.getClass();
            CardinalSystem.PartySystem.INSTANCE.getClass();
            CardinalSystem.ManaSystem.INSTANCE.getClass();
        }
    }
}
