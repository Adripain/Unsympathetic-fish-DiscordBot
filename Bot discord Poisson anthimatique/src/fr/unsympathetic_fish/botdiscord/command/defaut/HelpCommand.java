package fr.unsympathetic_fish.botdiscord.command.defaut;

import java.awt.Color;
import java.time.LocalTime;

import fr.unsympathetic_fish.botdiscord.command.Command;
import fr.unsympathetic_fish.botdiscord.command.Command.ExecutorType;
import fr.unsympathetic_fish.botdiscord.command.CommandMap;
//import fr.furryfr.botdiscord.command.SimpleCommand;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.impl.UserImpl;

public class HelpCommand {

//	private final CommandMap commandMap;
	
	private static final MessageChannel PrivateChannel = null;

	public HelpCommand(CommandMap commandMap) {
//		this.commandMap = commandMap;
	}
	
	@Command(name="help",type=ExecutorType.USER)
	private void help(User user, MessageChannel channel, JDA jda) {
		
		EmbedBuilder builder = new EmbedBuilder();
		builder.setTitle("Voici la liste des commandes : ");
		builder.addField("!info","Obtenir des infos sur vous", false);
		
		builder.setColor(Color.CYAN);
		
//		for(SimpleCommand command : commandMap.getCommands()) {
//			if(command.getExecutorType() == ExecutorType.CONSOLE) continue;
//			
//    		builder.addField(command.getName(), command.getDescription(), false); //False c'est 1 sur les autes ou l'un à coté des autres 
			
//		}
		if(channel!=PrivateChannel) {
		channel.sendMessage(user.getAsMention() + ", Veuillez checker vos messages privés").queue();
		}

		if(!channel.getId().equals("459819246315110430")) {
			if(!user.hasPrivateChannel()) user.openPrivateChannel().complete();
			((UserImpl)user).getPrivateChannel().sendMessage(builder.build()).queue();
			//jda.getGuildById("437613161840115734").getTextChannelById("437622014598184960").sendMessage( LocalTime.now().toString() + " - "  + user.getAsMention() + " à réaliser la commande help <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<").queue();
			

		}
	}
}
