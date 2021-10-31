package fr.unsympathetic_fish.botdiscord.command.defaut;

import java.awt.Color;
import java.io.*;
import java.time.LocalTime;
import java.util.List;

//import javax.naming.Name;

import fr.unsympathetic_fish.botdiscord.BotDiscord;
import fr.unsympathetic_fish.botdiscord.command.Command;
import fr.unsympathetic_fish.botdiscord.command.Command.ExecutorType;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.impl.UserImpl;
import net.dv8tion.jda.core.managers.GuildController;


public class CommandDefault {

	public CommandDefault(BotDiscord botDiscord) {
	}

	// sHIFT + Ctrl + *
	// --------------------------ADMIN command  --------------------- //

	@Command(name="info",type=ExecutorType.USER)

	private void info(User user, MessageChannel channel) {
		if(channel instanceof TextChannel) {
			TextChannel textChannel = (TextChannel)channel;
			if(!textChannel.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_EMBED_LINKS)) return;
		}
		
		EmbedBuilder builder = new EmbedBuilder();
		
		builder.setAuthor(user.getName(), null, user.getAvatarUrl()+"?size=256");
		builder.setTitle("Informations");
		builder.setDescription("[>](1) "+user.getIdLong());
		builder.setColor(Color.CYAN);

		channel.sendMessage(builder.build()).complete();
	}


	
	@Command(name="game", description="Amusez vous à changer le jeu du bot")
	private void game(User user, JDA jda, String[] args, MessageChannel channel) {
		StringBuilder builder = new StringBuilder();
		for(String str : args) {
			if(builder.length() > 0) builder.append(" ");
			builder.append(str);
		}
		channel.sendMessage("Bon eh bien maintenant je joue à " + builder.toString() + " !").queue();
		jda.getPresence().setGame(Game.playing(builder.toString()));
	}
	
	@Command(name="wakeup", description="Amusez vous à changer le jeu du bot")
	private void wakeup(User user, MessageChannel channel, JDA jda, Guild guild,Message message) {
		
		User target1;
		try {
		  target1 = message.getMentionedMembers().get(0).getUser();
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
			return;
		}

		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < 20; i++){
			channel.sendMessage(target1.getAsMention() + " réveille toi connard !").queue();
		}

	}
	
	@Command(name="42", description="Amusez vous à changer le jeu du bot")
	private void my42(User user, MessageChannel channel, JDA jda, Guild guild,Message message) {
		channel.sendMessage("https://media.discordapp.net/attachments/894496944917999631/895401160545042512/fuck5.gif").queue();
	}
}
