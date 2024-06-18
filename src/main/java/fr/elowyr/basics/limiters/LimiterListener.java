package fr.elowyr.basics.limiters;

import fr.elowyr.basics.utils.Utils;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class LimiterListener implements Listener {

	private LimiterManager limiterManager = LimiterManager.getInstance();
	
	@EventHandler
	public void onLimitedBlockWasPlaced(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		Block block = event.getBlockPlaced();
		World world = block.getWorld();
		Chunk chunk = block.getChunk();
		Limiter blockLimiter = this.limiterManager.getBlockLimiter(block);
		if(blockLimiter != null) {
			if (!LimiterManager.getInstance().isActive()) {
				return;
			}
			int blocksCount = 0, chunckSize = 16, chunkPosX = chunk.getX() << 4, chunkPosZ = chunk.getZ() << 4;
			for(int posX = chunkPosX; posX < (chunkPosX + chunckSize); posX++) {
				for(int posZ = chunkPosZ; posZ < (chunkPosZ + chunckSize); posZ++) {
					for(int posY = 0; posY < 256; posY++) {
						Block current = world.getBlockAt(posX, posY, posZ);
						if(current.getType() == blockLimiter.getMaterial()) {
							blocksCount++;
						}
					}
				}
			}
			int limitPerChunk = blockLimiter.getLimitPerChunk();
			if (blocksCount > limitPerChunk) {
				event.setCancelled(true);
				player.sendMessage(Utils.color("§4§l✘ &7&l• &cVous avez atteint la limite de ce bloc par chunk §7(" + limitPerChunk + ")"));
			}
		}
	}
	
	
	@EventHandler
	public void onBlockPistonExtend(BlockPistonExtendEvent event) {
		Block block = event.getBlock();
		Block targetBlock = block.getRelative(event.getDirection(), event.getLength() + 1);
		World world = targetBlock.getWorld();
		Chunk chunk = targetBlock.getChunk();
		Limiter blockLimiter = this.limiterManager.getBlockLimiter(block);
		if(blockLimiter != null) {
			int blocksCount = 0, chunckSize = 16, chunkPosX = chunk.getX() << 4, chunkPosZ = chunk.getZ() << 4;
			for(int posX = chunkPosX; posX < (chunkPosX + chunckSize); posX++) {
				for(int posZ = chunkPosZ; posZ < (chunkPosZ + chunckSize); posZ++) {
					for(int posY = 0; posY < 256; posY++) {
						Block current = world.getBlockAt(posX, posY, posZ);
						if(current.getType() == blockLimiter.getMaterial()) {
							blocksCount++;
						}
					}
				}
			}
			int limitPerChunk = blockLimiter.getLimitPerChunk();
			if (blocksCount > limitPerChunk) {
				event.setCancelled(true);
			}
		}
	}
}
