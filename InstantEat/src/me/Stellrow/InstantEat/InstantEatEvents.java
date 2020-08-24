package me.Stellrow.InstantEat;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class InstantEatEvents implements Listener {
    private final InstantEat pl;

    public InstantEatEvents(InstantEat pl) {
        this.pl = pl;
    }

    @EventHandler
    public void onEat(PlayerInteractEvent e){
        if(e.getAction()== Action.RIGHT_CLICK_AIR||e.getAction()==Action.RIGHT_CLICK_BLOCK){
            if(e.getClickedBlock() instanceof Container){
                return;
            }
            if(e.getItem()==null){
                return;
            }
            if(e.getPlayer().getFoodLevel()>=20){
                return;
            }
            if(pl.foods.containsKey(e.getItem().getType())){
                e.setCancelled(true);

                eat(e.getPlayer(),e.getItem(),pl.foods.get(e.getItem().getType()));
            }
        }
    }
    private void eat(Player p, ItemStack item, Integer time){
            new BukkitRunnable(){

                @Override
                public void run() {
                    if(p.getInventory().getItemInMainHand()==null){
                        return;
                    }
                    if(p.getInventory().getItemInMainHand().equals(item)) {
                        item.setAmount(item.getAmount() - 1);
                        Integer food[] = Foods.valueOf(item.getType().toString()).getValue();
                        setItem(p, item);
                        p.setFoodLevel(p.getFoodLevel() + food[0]);
                        p.setSaturation(p.getSaturation() + food[1]);

                    }
                }
            }.runTaskLater(pl,time);

    }
    private void setItem(Player p,ItemStack item){
        p.getInventory().setItemInMainHand(null);
        new BukkitRunnable(){

            @Override
            public void run() {
                p.getInventory().setItemInMainHand(item);
                p.updateInventory();
            }
        }.runTaskLater(pl,1);
    }
    public enum Foods{
        APPLE(4,2),
        BAKED_POTATO(5,6),
        BEETROOT(1,1),
        BEETROOT_SOUP(6,7),
        BREAD(5,6),
        CARROT(3,3),
        COOKED_CHICKEN(6,7),
        COOKED_COD(5,6),
        COOKED_MUTTON(6,9),
        COOKED_PORKCHOP(8,12),
        COOKED_RABBIT(5,6),
        COOKED_SALMON(5,9),
        COOKIE(2,0),
        DRIED_KELP(1,0),
        MELON_SLICE(2,1),
        POTATO(1,0),
        COOKED_BEEF(8,12),
        SWEET_BERRIES(2,1),
        PUMPKIN_PIE(8,5),
        RABBIT_STEW(10,12),
        ROTTEN_FLESH(4,0),
        MUSHROOM_STEW(6,7),

        ;


        ;
        private Integer[] value;
        private Foods(Integer value,Integer saturation){
            this.value= new Integer[]{value,saturation};
        }
        public Integer[] getValue(){
            return value;
        }
        public Foods returnByName(String name){
            return Foods.valueOf(name);
        }

    }
}
