package net.smudgecraft.smudgeessentials.levelup;

import java.lang.reflect.Method;

import net.smudgecraft.smudgeessentials.SmudgeEssentials;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Builder;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.meta.FireworkMeta;

import com.herocraftonline.heroes.api.events.HeroChangeLevelEvent;
import com.herocraftonline.heroes.characters.Hero;

public class LevelUp implements Listener
{
	
	public static void init()
	{
		Bukkit.getPluginManager().registerEvents(new LevelUp(), SmudgeEssentials.plugin);
	}
	
	@EventHandler
	public void onHeroExperienceGainEvent(HeroChangeLevelEvent event)
	{
		if(event.getTo()>event.getFrom())
		{
			Hero hero = event.getHero();
			if(event.getHeroClass().isPrimary())
			{
				Player player = hero.getPlayer();
				
				Builder effect = FireworkEffect.builder();
				effect.with(Type.BALL_LARGE);
				effect.withColor(Color.BLUE);
				effect.withColor(Color.SILVER);
				
				Location loc = player.getLocation();
				loc.setY(loc.getY()+0.4);
				
				try
				{
				playFirework(loc.getWorld(), loc, effect.build());
				}
				catch(Exception e)
				{
					
				}
			}
		}
	}
	
    private Method world_getHandle = null;
    private Method nms_world_broadcastEntityEffect = null;
    private Method firework_getHandle = null;
	
	public void playFirework(World world, Location loc, FireworkEffect fe) throws Exception {
        // Bukkity load (CraftFirework)
        Firework fw = (Firework) world.spawn(loc, Firework.class);
        // the net.minecraft.server.World
        Object nms_world = null;
        Object nms_firework = null;
        /*
         * The reflection part, this gives us access to funky ways of messing around with things
         */
        if(world_getHandle == null) {
            // get the methods of the craftbukkit objects
            world_getHandle = getMethod(world.getClass(), "getHandle");
            firework_getHandle = getMethod(fw.getClass(), "getHandle");
        }
        // invoke with no arguments
        nms_world = world_getHandle.invoke(world, (Object[]) null);
        nms_firework = firework_getHandle.invoke(fw, (Object[]) null);
        // null checks are fast, so having this seperate is ok
        if(nms_world_broadcastEntityEffect == null) {
            // get the method of the nms_world
            nms_world_broadcastEntityEffect = getMethod(nms_world.getClass(), "broadcastEntityEffect");
        }
        /*
         * Now we mess with the metadata, allowing nice clean spawning of a pretty firework (look, pretty lights!)
         */
        // metadata load
        FireworkMeta data = (FireworkMeta) fw.getFireworkMeta();
        // clear existing
        data.clearEffects();
        // power of one
        data.setPower(1);
        // add the effect
        data.addEffect(fe);
        // set the meta
        fw.setFireworkMeta(data);
        /*
         * Finally, we broadcast the entity effect then kill our fireworks object
         */
        // invoke with arguments
        nms_world_broadcastEntityEffect.invoke(nms_world, new Object[] {nms_firework, (byte) 17});
        // remove from the game
        fw.remove();
    }
	
	private static Method getMethod(Class<?> cl, String method) {
        for(Method m : cl.getMethods()) {
            if(m.getName().equals(method)) {
                return m;
            }
        }
        return null;
    }
}
