package fr.unsympathetic_fish.botdiscord;

import java.util.Scanner;

import javax.security.auth.login.LoginException;

import fr.unsympathetic_fish.botdiscord.command.CommandMap;
import fr.unsympathetic_fish.botdiscord.event.BotListener;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;



public class BotDiscord implements Runnable{

	private final JDA jda;
	private final CommandMap commandMap = new CommandMap(this);
	private final Scanner scanner = new Scanner(System.in);
	
	private boolean running;

	public BotDiscord() throws LoginException, IllegalArgumentException, RateLimitedException {
		jda = new JDABuilder(AccountType.BOT).setToken("OTA0NDE5NTI0MTQzNjExOTY1.YX7QUQ.Nm-SgcuI6Tg1eQLPlInx-exJL7A").buildAsync();
		jda.addEventListener(new BotListener(commandMap)); 
		System.out.println("Bot Poisson antipathique is now connected");
		jda.getPresence().setGame(Game.playing("sucer Léandre"));
	}
	
	public JDA getJda() {
		return jda;
	}
	
	public void setRunning(boolean running) {
		this.running =running;
	}
	
	@Override
	public void run() {
		running = true;

		while (running) {
			if(scanner.hasNextLine()) commandMap.commandConsole(scanner.nextLine());
		}
		
		scanner.close();
		System.out.println("Stop stopped.");
		jda.shutdown(); // (false)
		System.exit(0);
	}

	public static void main(String[] args) {
		try {
			BotDiscord botDiscord = new BotDiscord();
			new Thread(botDiscord, "bot").start();
		} catch (LoginException | IllegalArgumentException | RateLimitedException e) {
			e.printStackTrace();
		}
	}
}