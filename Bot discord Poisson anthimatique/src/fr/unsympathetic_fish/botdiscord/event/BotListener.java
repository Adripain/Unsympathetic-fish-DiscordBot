package fr.unsympathetic_fish.botdiscord.event;


import java.awt.Color;
import java.time.LocalTime;

import fr.unsympathetic_fish.botdiscord.command.CommandMap;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.impl.UserImpl;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.user.UserOnlineStatusUpdateEvent;
import net.dv8tion.jda.core.hooks.EventListener;



public class BotListener implements EventListener {

	private final CommandMap commandMap;
	
	public BotListener(CommandMap commandMap) {
		this.commandMap = commandMap;
	}
	
	@Override
	public void onEvent(Event event) {
		//System.out.println("Un événement! Type : " + event.getClass());
		System.out.println(event.getClass().getSimpleName());
		if(event instanceof MessageReceivedEvent) OnMessage((MessageReceivedEvent)event); // Normallement onMessage *
		else if(event instanceof GuildMemberJoinEvent) onGuildMemberJoin((GuildMemberJoinEvent) event);
		else if(event instanceof GuildMemberLeaveEvent) onGuildMemberLeave((GuildMemberLeaveEvent) event);
		else if(event instanceof UserOnlineStatusUpdateEvent) onGuildMemberConect((UserOnlineStatusUpdateEvent) event);
	}

	private void OnMessage(MessageReceivedEvent event) {
		if(event.getAuthor().equals(event.getJDA().getSelfUser())) return;
		 
		String message = event.getMessage().getContentDisplay();
		if(message.startsWith(commandMap.getTag())){
			message = message.replaceFirst(commandMap.getTag(), "");
			if(commandMap.commandUser(event.getAuthor(), message, event.getMessage())){
				if(event.getTextChannel() != null && event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_MANAGE)) {
					//event.getMessage().delete().queue(); Enlever les slatch pour supprimer les messages
				}
			}
		}
	}
	

	
	private void onGuildMemberJoin(GuildMemberJoinEvent event) {
	event.getGuild().getDefaultChannel().sendMessage("Bienvenue à toi, " + event.getUser().getAsMention()+" ! Moi, c'est Poisson antipathique et je vais te casser les couolles !").queue();
	
	EmbedBuilder builder = new EmbedBuilder();
	builder.setTitle("Voici quelque règles concernant le discord.");
	builder.addField("Regle numero 1","Se soumettre à adrien. ", false);
	builder.setColor(Color.RED);
	
	if(!event.getUser().hasPrivateChannel()) event.getUser().openPrivateChannel().complete();
    ((UserImpl)event.getUser()).getPrivateChannel().sendMessage(builder.build()).queue();
	
	
	}
	
	private void onGuildMemberConect(UserOnlineStatusUpdateEvent event) {
		event.getJDA().getGuildById("894496944917999626").getTextChannelById("904695303159021619").sendMessage(" - " + event.getUser() + " : " + event.getCurrentOnlineStatus()  ).queue();
	}
	
	private void onGuildMemberLeave(GuildMemberLeaveEvent event) {
		
		event.getJDA().getGuildById("894496944917999626").getTextChannelById("894496944917999631").sendMessage("@everyone \n- " + event.getUser().getName() +" a leave.").queue();
	}

} 

