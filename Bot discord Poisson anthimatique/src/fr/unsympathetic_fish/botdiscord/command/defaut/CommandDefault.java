package fr.unsympathetic_fish.botdiscord.command.defaut;

import java.awt.Color;

import javax.swing.text.Document;

//import javax.naming.Name;

import fr.unsympathetic_fish.botdiscord.BotDiscord;
import fr.unsympathetic_fish.botdiscord.command.Command;
import fr.unsympathetic_fish.botdiscord.command.Command.ExecutorType;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageHistory;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.impl.UserImpl;
import net.dv8tion.jda.core.entities.impl.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import java.time.LocalTime;


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
	
	@Command(name="spam", description="Amusez vous à changer le jeu du bot")
	private void spam(User user, MessageChannel channel, JDA jda, Guild guild,Message message) {
		
		User target1;
		try {
		  target1 = message.getMentionedMembers().get(0).getUser();
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
			return;
		}
		
		long nb;
		String nbTxt = null;
		try{
			nbTxt = message.getContentDisplay().substring(message.getContentDisplay().lastIndexOf(" "), message.getContentDisplay().length());
			nbTxt = nbTxt.trim();
			nb = Long.parseLong(nbTxt);
		}
		catch(NumberFormatException nfe){
			System.out.println(nbTxt);
			channel.sendMessage("C'est pas un nombre : (" + nbTxt + ")").queue();
//			channel.sendMessage("Ou alors le chiffre est trop grand").queue();
			return;
		}
		
		for(int i = 0; i < nb; i++){
			channel.sendMessage(target1.getAsMention() + " réveille toi connard !").queue();
		}

	}
	
	private static final MessageChannel PrivateChannel = null;
	@Command(name="wakeup", description="Amusez vous à changer le jeu du bot")
	private void wakeup(User user, MessageChannel channel, JDA jda, Guild guild,Message message) {
		
		User target1;
		try {
		  target1 = message.getMentionedMembers().get(0).getUser();
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
			return;
		}
		
		if(channel!=PrivateChannel) {
			if(((Member) target1).getOnlineStatus().toString)) {
				channel.sendMessage(target1.getAsMention() + " réveille toi connard !").queue();
			}
			else {
				channel.sendMessage(target1.getAsMention() + " on te parle je vois que tes connecté !!!").queue();
			}
		}
		
		

	}

	@Command(name="42", description="Amusez vous à changer le jeu du bot")
	private void my42(User user, MessageChannel channel, JDA jda, Guild guild,Message message) {
		channel.sendMessage("https://media.discordapp.net/attachments/894496944917999631/895401160545042512/fuck5.gif").queue();
	}
	
	@Command(name="stats", description="Amusez vous à changer le jeu du bot")
	private void stat(User user, MessageChannel channel, JDA jda, Guild guild,Message message) {
		User target1;
		if ( message.getMentionedMembers().isEmpty() ) {
			target1 = user;
		}else {
			try {
				target1 = message.getMentionedMembers().get(0).getUser();
			} catch (ArrayIndexOutOfBoundsException e) {
				e.printStackTrace();
				return;
			}
		}
		if(!target1.hasPrivateChannel()) target1.openPrivateChannel().complete();
		((UserImpl)target1).getPrivateChannel().getHistory().retrievePast(1).queue(messages -> {
			if (messages.size() > 0) {
				String key = messages.get(0).getContentDisplay(); 
				String kfpart = key.split("-")[0];
				if (!kfpart.equals("auth")) {
					channel.sendMessage("[101] Blob blop no key").queue();
				}
				String tempurl = "https://intra.epitech.eu/key/user/?format=json";
				URL url = null;
				try {
					url = new URL(tempurl.replace("key", key));
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				Scanner sc = null;
				try {
					sc = new Scanner(url.openStream());
				} catch (IOException e) {
					e.printStackTrace();
				}
				StringBuffer sb = new StringBuffer();
				while(sc.hasNext()) {
					sb.append(sc.next());
				}
				String result = sb.toString();
				System.out.println("Contents of the web page: "+result);
				
				String[] credit = result.split("credits");
				credit = credit[1].split("\":\"");
				credit = credit[0].split(",\"");
				credit = credit[0].split("\":");
				
				String[] gpa = result.split("gpa");
				gpa = gpa[2].split("\":\"");
				gpa = gpa[1].split("\",\"cycle");

				String[] first_name = result.split("firstname");
				first_name = first_name[1].split("\":\"");
				first_name = first_name[1].split("\",\"");

				String[] last_name = result.split("lastname");
				last_name = last_name[1].split("\":\"");
				last_name = last_name[1].split("\",\"");
				
				String[] mail = result.split("internal_email");
				mail = mail[1].split("\":\"");
				mail = mail[1].split("\",\"");
				
				String[] promo = result.split("promo");
				promo = promo[1].split("\":\"");
				String promostring = promo[0];
				promostring = promostring.replace("\"", "");
				promostring = promostring.replace(":", "");
				promostring = promostring.replace(",", " / ");
				promostring = promostring.replace("semester", "Semester ");
				promostring = promostring.replace("location", "Location ");
				
				String[] loc = result.split("location");
				loc = loc[1].split("\":\"");
				loc = loc[1].split("\",\"");
				loc = loc[0].split("\\/");
				
				String[] log_time = result.split("nsstat");
				log_time = log_time[1].split("\":\"");
				log_time = log_time[0].split(",");
				log_time = log_time[0].split(":");
				
				String[] log_time_wanted = result.split("nsstat");
				log_time_wanted = log_time_wanted[1].split("\":\"");
				log_time_wanted = log_time_wanted[0].split(",");
				log_time_wanted = log_time_wanted[4].split(":");
				log_time_wanted = log_time_wanted[1].split("}");
				EmbedBuilder builder = new EmbedBuilder();
				builder.setTitle("Analytics for " + first_name[0] + " " + last_name[0] + " :");
				builder.addField("Email",mail[0], false);
				builder.addField("Promo",promostring + " " + loc[1], false);
				builder.addField("Credits",credit[1], false);
				builder.addField("GPA",gpa[0], false);
				builder.addField("Log Time",log_time[2] + " / " + log_time_wanted[0], false);
				builder.setColor(Color.magenta);
				channel.sendMessage(builder.build()).queue();
			}
			else {
				channel.sendMessage("[102] Blob blop no key").queue();
			}
		});
	}
	
	
	@Command(name="wap", description="Amusez vous à changer le jeu du bot")
	private void wap(User user, MessageChannel channel, JDA jda, Guild guild,Message message) {
		channel.sendMessage("https://cdn.discordapp.com/attachments/378648464990666752/906262658540519424/v09044g40000c3ee205gt1bjj47f4hkg.mov").queue();
	}

	@Command(name="credit", description="get_credits")
	public void get_credit(User user, MessageChannel channel, JDA jda, Guild guild,Message message) throws IOException {
		User target1;
		if ( message.getMentionedMembers().isEmpty() ) {
			target1 = user;
		}else {
			try {
				target1 = message.getMentionedMembers().get(0).getUser();
			} catch (ArrayIndexOutOfBoundsException e) {
				e.printStackTrace();
				return;
			}
		}
		if(!target1.hasPrivateChannel()) target1.openPrivateChannel().complete();
		((UserImpl)target1).getPrivateChannel().getHistory().retrievePast(1).queue(messages -> {
			if (messages.size() > 0) {
				String key = messages.get(0).getContentDisplay(); 
				String kfpart = key.split("-")[0];
				if (!kfpart.equals("auth")) {
					channel.sendMessage("[101] Blob blop no key").queue();
				}
				String tempurl = "https://intra.epitech.eu/key/user/?format=json";
				URL url = null;
				try {
					url = new URL(tempurl.replace("key", key));
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				Scanner sc = null;
				try {
					sc = new Scanner(url.openStream());
				} catch (IOException e) {
					e.printStackTrace();
				}
				StringBuffer sb = new StringBuffer();
				while(sc.hasNext()) {
					sb.append(sc.next());
				}
				String result = sb.toString();
				String[] arr = result.split("credits");
				arr = arr[1].split("\":\"");
				arr = arr[0].split(",\"");
				arr = arr[0].split("\":");
				System.out.println("Contents of the web page: "+result);
				channel.sendMessage(arr[1]).queue();
			}
			else {
				channel.sendMessage("[102] Blob blop no key").queue();
			}
		});
	}

	@Command(name="gpa", description="get_gpa")
	public void get_gpa(User user, MessageChannel channel, JDA jda, Guild guild,Message message) throws IOException {
		User target1;
		if ( message.getMentionedMembers().isEmpty() ) {
			target1 = user;
		}else {
			try {
				target1 = message.getMentionedMembers().get(0).getUser();
			} catch (ArrayIndexOutOfBoundsException e) {
				e.printStackTrace();
				return;
			}
		}
		if(!target1.hasPrivateChannel()) target1.openPrivateChannel().complete();
		((UserImpl)target1).getPrivateChannel().getHistory().retrievePast(1).queue(messages -> {
			if (messages.size() > 0) {
				String key = messages.get(0).getContentDisplay(); 
				String kfpart = key.split("-")[0];
				if (!kfpart.equals("auth")) {
					channel.sendMessage("[101] Blob blop no key").queue();
				}
				String tempurl = "https://intra.epitech.eu/key/user/?format=json";
				URL url = null;
				try {
					url = new URL(tempurl.replace("key", key));
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				Scanner sc = null;
				try {
					sc = new Scanner(url.openStream());
				} catch (IOException e) {
					e.printStackTrace();
				}
				StringBuffer sb = new StringBuffer();
				while(sc.hasNext()) {
					sb.append(sc.next());
				}
				String result = sb.toString();
				String[] arr = result.split("gpa");
				arr = arr[2].split("\":\"");
				arr = arr[1].split("\",\"cycle");
				System.out.println("Contents of the web page: "+result);
				channel.sendMessage(arr[0]).queue();
			}
			else {
				channel.sendMessage("[102] Blob blop no key").queue();
			}
		});
	}
}
