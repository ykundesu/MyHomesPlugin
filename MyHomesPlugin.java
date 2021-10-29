package jp.yoppykun.myhomesplugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;


public final class MyHomesPlugin extends JavaPlugin {


    @Override
    public void onEnable() {
        CustomConfig configs = new CustomConfig(this);
        CustomConfig homedata = new CustomConfig(this, "Homes.yml");
        configs.saveDefaultConfig();
        configs.getConfig();
        homedata.saveDefaultConfig();
        homedata.getConfig();
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        CustomConfig configs = new CustomConfig(this, "Homes.yml");
        FileConfiguration config =configs.getConfig();
        CustomConfig settings = new CustomConfig(this);
        FileConfiguration setting =settings.getConfig();
        String homesetmsg=setting.getString("homesetmsg","§6Home point has been set.");
        String nohomemsg=setting.getString("nohomemsg","§6Home point is not set.");
        String hometpmsg=setting.getString("hometpmsg","§6I teleported to the home point.");
        String delhomenoname=setting.getString("delhomenoname","§6Specify the name of the home");
        String delhomenothome=setting.getString("delhomenothome","§6Home not found.");
        String delhomeok=setting.getString("delhomeok","§6Home erased.");
        Player player = (Player) sender;
        if (command.getName().equals("sethome")) {
            if (args.length == 0) {
                config.set("Homes." + player.getUniqueId().toString() + ".X", player.getLocation().getX());
                config.set("Homes." + player.getUniqueId().toString() + ".Y", player.getLocation().getY());
                config.set("Homes." + player.getUniqueId().toString() + ".Z", player.getLocation().getZ());
                config.set("Homes." + player.getUniqueId().toString() + ".World", player.getLocation().getWorld().getName());
                configs.saveConfig();
                configs.reloadConfig();
                player.sendMessage(homesetmsg);
            } else {
                //if(!config.contains("Homes." + player.getUniqueId().toString() + ".homesets")){
                //    config.set("Homes." + player.getUniqueId().toString() + ".homesets",0);
                //};
                //if (config.getInt("Homes." + player.getUniqueId().toString() + ".homesets") == 4) {
                //    player.sendMessage(homezyougen);
                //} else {
                if(config.contains("Homes." + player.getUniqueId().toString() + "."+args[0])){
                    player.sendMessage("名前が重複しています");
                }else{
                    config.set("Homes." + player.getUniqueId().toString() +"."+args[0] + ".X", player.getLocation().getX());
                    config.set("Homes." + player.getUniqueId().toString() +"."+args[0] + ".Y", player.getLocation().getY());
                    config.set("Homes." + player.getUniqueId().toString() +"."+args[0] + ".Z", player.getLocation().getZ());
                    //int homen=config.getInt("Homes." + player.getUniqueId().toString() + ".homesets") + 1;
                    //if(config.contains("Homes."+player.getUniqueId()+".homedate."+homen)){homen=homen+100;}
                    config.set("Homes." + player.getUniqueId().toString() +"."+args[0] + ".World", player.getLocation().getWorld().getName());
                    //config.set("Homes." + player.getUniqueId().toString() + ".homesets",homen);
                    //config.set("Homes." + player.getUniqueId().toString() + ".homedate." +homen, args[0]);
                    //config.set("Homes." + player.getUniqueId().toString() + "."+args[0] + ".homen",config.getInt("Homes." + player.getUniqueId().toString() + ".homesets") + 1);
                    configs.saveConfig();
                    configs.reloadConfig();
                    player.sendMessage(homesetmsg);
                    //}
                }
            }
        } else if (command.getName().equals("homehelp")) {
            if (setting.getString("helplang", "en").equals("ja")) {
                player.sendMessage("§6------ホームヘルプ------§r\n/sethome [ホーム名] : 名前ありホームをセットします\n/sethome : 名前がないホームをセットします\n/home : 名前がついていないホームにテレポートします\n/home [ホーム名] : 名前付きホームにテレポートします\n/delhome [ホーム名] : ホームを削除できます\n/homelist : ホームリストを表示します\n§6-------------------");
            } else {
                player.sendMessage("§6------HomeHelp------§r\n/sethome [Homename] : Set the home as named.\n/sethome : Set the home as unnamed.\n/home : Teleport to nameless home.\n/home [Homename] : Teleport to home with name\n/delhome [Homename] : Delete a specific home\n/homelist : Display the home list\n§6-------------------");
            }
        } else if (command.getName().equals("home")) {
            if (args.length == 0) {
                if (!config.contains("Homes." + player.getUniqueId().toString() + ".X")) {
                    player.sendMessage(nohomemsg);
                } else {
                    Location loc = new Location(
                            Bukkit.getWorld(config.getString("Homes." + player.getUniqueId().toString() + ".World", player.getLocation().getWorld().getName())),
                            config.getDouble("Homes." + player.getUniqueId().toString() + ".X"),
                            config.getDouble("Homes." + player.getUniqueId().toString() + ".Y"),
                            config.getDouble("Homes." + player.getUniqueId().toString() + ".Z"),
                            player.getLocation().getYaw(),
                            player.getLocation().getPitch()
                    );
                    player.teleport(loc);
                    if (false) {
                        player.playSound(loc, Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                    }
                    player.sendMessage(hometpmsg);
                }
            } else {
                if (!config.contains("Homes." + player.getUniqueId().toString() +"."+args[0] +".X")) {
                    player.sendMessage(nohomemsg);
                } else {
                    Location loc = new Location(
                            Bukkit.getWorld(config.getString("Homes." + player.getUniqueId().toString() +"."+ args[0] + ".World", player.getLocation().getWorld().getName())),
                            config.getDouble("Homes." + player.getUniqueId().toString() +"."+args[0] +".X"),
                            config.getDouble("Homes." + player.getUniqueId().toString() +"."+args[0] + ".Y"),
                            config.getDouble("Homes." + player.getUniqueId().toString() +"."+args[0] +".Z"),
                            player.getLocation().getYaw(),
                            player.getLocation().getPitch()
                    );
                    player.teleport(loc);
                    if (false) {
                        player.playSound(loc, Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                    }
                    player.sendMessage(hometpmsg);
                }

            }
        } else if(command.getName().equals("delhome")){
            if(args.length==0){
                player.sendMessage(delhomenoname);
            }else{
                if (!config.contains("Homes." + player.getUniqueId().toString() + "." + args[0] +".X")) {
                    player.sendMessage(delhomenothome);
                }else{
                    config.set("Homes." + player.getUniqueId().toString() + "."+args[0],null);
                    //config.set("Homes." + player.getUniqueId().toString() + ".homesets",config.getInt("Homes." + player.getUniqueId().toString() + ".homesets")-1);
                    //int homen=config.getInt("Homes." + player.getUniqueId().toString() + ".homesets") + 1;
                    //config.set("Homes." + player.getUniqueId().toString() + ".homedate." +homen,null);
                    configs.saveConfig();
                    configs.reloadConfig();
                    player.sendMessage(delhomeok);
                }
            }
        }else if(command.getName().equals("homelist")){
            if(!config.contains("Homes."+player.getUniqueId().toString())){sender.sendMessage("ホームが設定されていません。");}else{
                sender.sendMessage("登録しているホーム:");
                for (String key : config.getConfigurationSection("Homes."+player.getUniqueId().toString()).getKeys(false)) {
                    //sender.sendMessage("key:"+key);
                    if(config.contains("Homes."+player.getUniqueId().toString()+"."+key+".X")){
                        sender.sendMessage(key);
                    }
                }}
            /*player.sendMessage("あなたが登録しているホーム:");
            String num="1";
            if(config.contains("Homes." + player.getUniqueId().toString() + ".homedate."+num)) {
                player.sendMessage(config.getString("Homes." + player.getUniqueId().toString() + ".homedate."+num));
            }
            num="2";
            if(config.contains("Homes." + player.getUniqueId().toString() + ".homedate."+num)) {
                player.sendMessage(config.getString("Homes." + player.getUniqueId().toString() + ".homedate."+num));
            }
            num="3";
            if(config.contains("Homes." + player.getUniqueId().toString() + ".homedate."+num)) {
                player.sendMessage(config.getString("Homes." + player.getUniqueId().toString() + ".homedate."+num));
            }
            num="4";
            if(config.contains("Homes." + player.getUniqueId().toString() + ".homedate."+num)) {
                player.sendMessage(config.getString("Homes." + player.getUniqueId().toString() + ".homedate."+num));
            }
        */}
        return true;
    }
}
