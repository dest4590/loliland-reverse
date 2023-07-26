import com.google.common.collect.Maps;
import cpw.mods.fml.relauncher.ReflectionHelper;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import loliland.core.I1O1I1LaNd;
import loliland.forge.hookslib.asm.At;
import loliland.forge.hookslib.asm.Hook;
import loliland.forge.hookslib.asm.InjectionPoint;
import loliland.forge.hookslib.asm.ReturnCondition;
import loliland.forge.hookslib.asm.Shift;
import loliland.lolimod.III01ilaNd;
import loliland.lolimod.O1l0i01lLAND;
import loliland.lolimod.O1lO0ILaND;
import loliland.lolimod.OllOO1IlanD;
import loliland.lolimod.i1IllilLaNd;
import loliland.lolimod.iIiillaND;
import loliland.lolimod.l0IOIland;
import loliland.lolimod.l1O00OlaNd;
import loliland.lolimod.lI0il11LaND;
import loliland.lolimod.lIO00i0Iland;
import loliland.lolimod.lO0011LAND;
import loliland.lolimod.lOO1LaNd;
import loliland.lolimod.liOOIILAnD;
import net.minecraft.block.BlockFarmland;
import net.minecraft.crash.CrashReport;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.server.network.NetHandlerLoginServer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import org.apache.commons.lang3.ArrayUtils;

public class HooksServer {
    private static ExecutorService async;
    private static final Map CUSTOM_LOGGED_DATA;

    @Hook(targetMethod="readPacketData", at=@At(point=InjectionPoint.RETURN))
    public static void readPacketData(C00PacketLoginStart c00PacketLoginStart, PacketBuffer packetBuffer) throws IOException {
    }

    @Hook(targetMethod="slotClick", returnCondition=ReturnCondition.ON_TRUE, returnNull=true)
    public static boolean slotClickStackSize(Container container, int n, int n2, int n3, EntityPlayer entityPlayer) {
        Slot slot;
        if (n == -999 && entityPlayer.field_71071_by.func_70445_o() != null && entityPlayer.field_71071_by.func_70445_o().field_77994_a <= 0) {
            entityPlayer.field_71071_by.func_70437_b(null);
            return true;
        }
        if (n >= 0 && n < container.field_75151_b.size() && (slot = container.func_75139_a(n)) != null && slot.func_75216_d() && slot.func_75211_c().field_77994_a <= 0) {
            slot.func_75215_d(null);
            return true;
        }
        return false;
    }

    @Hook(targetMethod="decrementAnimations")
    public static void decrementAnimationsStackSize(InventoryPlayer inventoryPlayer) {
        int n = 0;
        if (n < inventoryPlayer.field_70462_a.length) {
            if (inventoryPlayer.field_70462_a[n] != null && inventoryPlayer.field_70462_a[n].field_77994_a <= 0) {
                inventoryPlayer.field_70462_a[n] = null;
            }
            ++n;
            return;
        }
        n = 0;
        if (n < inventoryPlayer.field_70460_b.length) {
            if (inventoryPlayer.field_70460_b[n] != null && inventoryPlayer.field_70460_b[n].field_77994_a <= 0) {
                inventoryPlayer.field_70460_b[n] = null;
            }
            ++n;
            return;
        }
    }

    @Hook(targetMethod="onFallenUpon", returnCondition=ReturnCondition.ON_TRUE)
    public static boolean crashFarmland(BlockFarmland blockFarmland, World world, int n, int n2, int n3, Entity entity, float f) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer entityPlayer = (EntityPlayer)entity;
            liOOIILAnD liOOIILAnD2 = new liOOIILAnD(i1IllilLaNd.I1O1I1LaNd(entityPlayer.field_70170_p), new iIiillaND(n, n2, n3), lO0011LAND.I1O1I1LaNd(entityPlayer));
            l1O00OlaNd.I1O1I1LaNd(liOOIILAnD2);
            return liOOIILAnD2.lil0liLand();
        }
        return false;
    }

    @Hook(targetMethod="putStack", returnCondition=ReturnCondition.ON_TRUE)
    public static boolean putStackStackSize(Slot slot, ItemStack itemStack) {
        return itemStack != null && itemStack.field_77994_a <= 0;
    }

    @Hook(targetMethod="onItemRightClick", returnCondition=ReturnCondition.ALWAYS)
    public static ItemStack onItemRightClick(ItemExpBottle itemExpBottle, ItemStack itemStack, World world, EntityPlayer entityPlayer) {
        int n = 0;
        int n2 = 0;
        int n3 = entityPlayer.func_70093_af() ? itemStack.field_77994_a : 1;
        if (n2 < n3) {
            n += 3 + world.field_73012_v.nextInt(5) + world.field_73012_v.nextInt(5);
            ++n2;
            return null;
        }
        entityPlayer.func_71023_q(n);
        itemStack.field_77994_a -= n3;
        return itemStack;
    }

    @Hook(targetMethod="onImpact", returnCondition=ReturnCondition.ALWAYS)
    public static void onImpact(EntityExpBottle entityExpBottle, MovingObjectPosition movingObjectPosition) {
        if (!entityExpBottle.field_70170_p.field_72995_K) {
            entityExpBottle.field_70170_p.func_72926_e(2002, MathHelper.func_76128_c((double)entityExpBottle.field_70165_t), MathHelper.func_76128_c((double)entityExpBottle.field_70163_u), MathHelper.func_76128_c((double)entityExpBottle.field_70161_v), 0);
            entityExpBottle.func_70106_y();
        }
    }

    static {
        CUSTOM_LOGGED_DATA = Maps.newHashMap();
        async = Executors.newFixedThreadPool(10);
    }

    private static void lambda$processLoginStart$0(Object[] objectArray, NetHandlerLoginServer netHandlerLoginServer, String string, C00PacketLoginStart c00PacketLoginStart) {
        try {
            Object object;
            Class<?> clazz;
            Object object2;
            Object object3;
            lIO00i0Iland lIO00i0Iland2;
            if (ArrayUtils.isEmpty(objectArray)) {
                netHandlerLoginServer.func_147322_a("Токен авторизации устарел. Перезайдите в клиент!");
                return;
            }
            String string2 = netHandlerLoginServer.field_147333_a.func_74430_c().toString();
            string2 = string2.substring(string2.indexOf("/") + 1);
            string2 = string2.substring(0, string2.indexOf(":"));
            int n = 0;
            Object object4 = lOO1LaNd.I1O1I1LaNd().iterator();
            if (object4.hasNext()) {
                EntityPlayer entityPlayer = (EntityPlayer)object4.next();
                if (lOO1LaNd.OOOIilanD(entityPlayer).equalsIgnoreCase(string)) {
                    ++n;
                }
                return;
            }
            if (n > 0) {
                netHandlerLoginServer.func_147322_a("Вы уже на сервере!");
                return;
            }
            n = 0;
            int n2 = 0;
            object4 = III01ilaNd.I1O1I1LaNd().field_71305_c;
            int n3 = ((WorldServer[])object4).length;
            if (n2 < n3) {
                WorldServer worldServer = object4[n2];
                Iterator iterator = worldServer.field_73010_i.iterator();
                if (iterator.hasNext()) {
                    Object e = iterator.next();
                    if (e instanceof EntityPlayer && lOO1LaNd.OOOIilanD((EntityPlayer)e).equalsIgnoreCase(string)) {
                        ++n;
                    }
                    return;
                }
                ++n2;
                return;
            }
            if (n > 0) {
                netHandlerLoginServer.func_147322_a("Вы уже на сервере!");
                return;
            }
            object4 = lI0il11LaND.I1O1I1LaNd((String)objectArray[0]);
            String string3 = ((lI0il11LaND)object4).OOOIilanD("token").Oill1LAnD();
            lIO00i0Iland lIO00i0Iland3 = lIO00i0Iland2 = ((lI0il11LaND)object4).lI00OlAND("hardware_data") ? lIO00i0Iland.I1O1I1LaNd(((lI0il11LaND)object4).OOOIilanD("hardware_data").O1il1llOLANd().toString()) : null;
            if (lIO00i0Iland2 == null) {
                netHandlerLoginServer.func_147322_a("§c'System local data' is null. Попробуйте перезайти в клиент!");
                return;
            }
            if (I1O1I1LaNd.lli0OiIlAND.equals("2.0")) {
                object3 = O1lO0ILaND.I1O1I1LaNd(string, string3, string2, lIO00i0Iland2);
                if (((lI0il11LaND)object3).O1il1llOLANd()) {
                    netHandlerLoginServer.func_147322_a("§cСервер авторизации в данный момент выключен. Попробуйте зайти через пару минут!");
                    return;
                }
                if (((lI0il11LaND)object3).lI00OlAND("error_code")) {
                    int n4 = ((lI0il11LaND)object3).OOOIilanD("error_code").I1O1I1LaNd();
                    if (n4 == -4) {
                        netHandlerLoginServer.func_147322_a("Пользователь не найден. Перезайдите в клиент!");
                    } else if (n4 == 1) {
                        netHandlerLoginServer.func_147322_a("Токен авторизации устарел. Перезайдите в клиент!");
                    } else if (n4 == -6) {
                        netHandlerLoginServer.func_147322_a("§cСервер не найден. Попробуйте зайти через пару минут!");
                    } else if (n4 == -7) {
                        netHandlerLoginServer.func_147322_a("Вам нужно подтвердить 2FA в Telegram / VK!");
                    } else {
                        if (n4 == 2) {
                            String string4 = ((lI0il11LaND)object3).OOOIilanD("admin").Oill1LAnD();
                            long l = ((lI0il11LaND)object3).OOOIilanD("timeEnd").lI00OlAND();
                            String string5 = ((lI0il11LaND)object3).OOOIilanD("cause").Oill1LAnD();
                            String string6 = "§cДо разбана осталось " + O1l0i01lLAND.I1O1I1LaNd((l - System.currentTimeMillis()) / 1000L) + "\n§cЗабанил §4" + string4 + "\n§6Причина: §e" + string5 + "\n§2Цена разбана: §a300р §f| §2https://loliland.ru/bans";
                            if (l == -1L) {
                                string6 = "§cВы были забанены навсегда.\n§cЗабанил §4" + string4 + "\n§6Причина: §e" + string5 + "\n§2Цена разбана: §a300р §f| §2https://loliland.ru/bans";
                            }
                            netHandlerLoginServer.func_147322_a(string6);
                            return;
                        }
                        if (n4 == -5) {
                            netHandlerLoginServer.func_147322_a("§cВы были забанены навсегда.");
                        } else {
                            netHandlerLoginServer.func_147322_a("Обратитесь в тех. поддержку. Номер ошибки: " + n4);
                            System.out.print("Auth unhandled error: " + ((lI0il11LaND)object3).OOOIilanD("error_message").Oill1LAnD());
                        }
                    }
                    return;
                }
                object2 = lO0011LAND.OOOIilanD(string);
                clazz = ((lI0il11LaND)object3).OOOIilanD("access_token").Oill1LAnD();
                object = ((lI0il11LaND)object3).OOOIilanD("login").Oill1LAnD();
                ((lO0011LAND)object2).lIOILand((String)((Object)clazz));
                ((lO0011LAND)object2).lil0liLand((String)object);
                ((lO0011LAND)object2).I1O1I1LaNd(System.currentTimeMillis());
                ((lO0011LAND)object2).II1i1l0laND();
            } else {
                object3 = O1lO0ILaND.OOOIilanD(string, string3, string2, lIO00i0Iland2);
                if (!((lI0il11LaND)object3).lI00OlAND("type")) {
                    netHandlerLoginServer.func_147322_a("§cСервер авторизации в данный момент выключен. Попробуйте зайти через пару минут!");
                    return;
                }
                object2 = ((lI0il11LaND)object3).OOOIilanD("type").Oill1LAnD();
                if (((String)object2).equalsIgnoreCase("error")) {
                    int n5 = ((lI0il11LaND)object3).OOOIilanD("error").I1O1I1LaNd();
                    if (n5 == 0) {
                        netHandlerLoginServer.func_147322_a("Пользователь не найден. Перезайдите в клиент!");
                    } else if (n5 == 1) {
                        netHandlerLoginServer.func_147322_a("Токен авторизации устарел. Перезайдите в клиент!");
                    } else if (n5 == 2) {
                        netHandlerLoginServer.func_147322_a("§cСервер не найден. Попробуйте зайти через пару минут!");
                    } else if (n5 == 3) {
                        netHandlerLoginServer.func_147322_a("Вам нужно подтвердить 2FA в Telegram / VK!");
                    } else {
                        if (n5 == 4) {
                            String string7 = ((lI0il11LaND)object3).OOOIilanD("admin").Oill1LAnD();
                            long l = ((lI0il11LaND)object3).OOOIilanD("timeEnd").lI00OlAND();
                            String string8 = ((lI0il11LaND)object3).OOOIilanD("cause").Oill1LAnD();
                            String string9 = "§cДо разбана осталось " + O1l0i01lLAND.I1O1I1LaNd((l - System.currentTimeMillis()) / 1000L) + "\n§cЗабанил §4" + string7 + "\n§6Причина: §e" + string8 + "\n§2Цена разбана: §a300р §f| §2https://loliland.ru/bans";
                            if (l == -1L) {
                                string9 = "§cВы были забанены навсегда.\n§cЗабанил §4" + string7 + "\n§6Причина: §e" + string8 + "\n§2Цена разбана: §a300р §f| §2https://loliland.ru/bans";
                            }
                            netHandlerLoginServer.func_147322_a(string9);
                            return;
                        }
                        if (n5 == 5) {
                            netHandlerLoginServer.func_147322_a("§cВы были забанены навсегда.");
                        } else {
                            netHandlerLoginServer.func_147322_a("Обратитесь в тех. поддержку. Номер ошибки: " + n5);
                        }
                    }
                    return;
                }
                clazz = lO0011LAND.OOOIilanD(string);
                l0IOIland.I1O1I1LaNd((lO0011LAND)((Object)clazz), string2);
            }
            object3 = lO0011LAND.OOOIilanD(string);
            object2 = new OllOO1IlanD((lO0011LAND)object3, netHandlerLoginServer);
            ((l1O00OlaNd)object2).lIOILand();
            ReflectionHelper.setPrivateValue(NetHandlerLoginServer.class, (Object)netHandlerLoginServer, (Object)c00PacketLoginStart.func_149304_c(), (String[])new String[]{"field_147337_i"});
            clazz = Class.forName("net.minecraft.server.network.NetHandlerLoginServer$LoginState");
            object = clazz.getDeclaredField("READY_TO_ACCEPT");
            ((Field)object).setAccessible(true);
            Object object5 = ((Field)object).get(null);
            ReflectionHelper.setPrivateValue(NetHandlerLoginServer.class, (Object)netHandlerLoginServer, (Object)object5, (String[])new String[]{"field_147328_g"});
        }
        catch (Exception exception) {
            netHandlerLoginServer.func_147322_a("Login runtime exception");
            exception.printStackTrace();
        }
        catch (Throwable throwable) {
            netHandlerLoginServer.func_147322_a("Login runtime throwable");
            throwable.printStackTrace();
        }
    }

    @Hook(targetMethod="saveToFile", at=@At(point=InjectionPoint.RETURN))
    public static void saveCrash(CrashReport crashReport, File file) {
    }

    @Hook(targetMethod="processLoginStart", returnCondition=ReturnCondition.ON_TRUE, at=@At(point=InjectionPoint.METHOD_CALL, target="validState", shift=Shift.AFTER, ordinal=0))
    public static boolean processLoginStart(NetHandlerLoginServer netHandlerLoginServer, C00PacketLoginStart c00PacketLoginStart) {
        return true;
    }
}
