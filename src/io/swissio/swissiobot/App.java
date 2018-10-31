package io.swissio.swissiobot;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.requests.restaction.MessageAction;

public class App extends ListenerAdapter
{
    public static void main( String[] args ) throws Exception
    {
    	JDA jda = new JDABuilder(AccountType.BOT).setToken(Ref.TOKEN).build();
    	jda.addEventListener(new App ()); 
    }
    
    @Override
    public void onMessageReceived (MessageReceivedEvent event)
    {
    	User user = event.getAuthor(); 
    	MessageChannel msgChannel = event.getChannel();
    	Message message = event.getMessage(); 
    	if (message.getContentRaw().equalsIgnoreCase(Ref.prefix+"ping"))
    	{
    		MessageAction ma = msgChannel.sendMessage(user.getAsMention() + " Pong!!");
    		ma.queue();
    	}
    	String chartsSwissIO = "500757220011802645";
    	if (message.getContentRaw().contains("tradingview.com"))
    	{
    		Guild guild = event.getGuild();  
    		TextChannel chartChannel = guild.getTextChannelById(chartsSwissIO);
    		if (!msgChannel.equals(chartChannel))
    		{
    			chartChannel.sendMessage(event.getMember().getAsMention() + " posted: " + message.getContentRaw()).queue();
    		}
    	}
    }
    
    @Override 
    public void onGuildMemberJoin(GuildMemberJoinEvent event) 
    {
    	if (event.getMember().getUser().isBot()) return;
    	
    	String joinChannelSwissIO = "500401587743621133";
    	String welcomeChannelCryptoCH = "418249034176921600";
    	Guild guild = event.getGuild();
    	TextChannel welcomeChannel = guild.getTextChannelById(joinChannelSwissIO);
    	if (welcomeChannel == null)
    	{
    		welcomeChannel = guild.getTextChannelById(welcomeChannelCryptoCH);
    	}
    	if (welcomeChannel == null) return;
    	
    	
    	//PrivateChannel pc = event.getMember().getUser().openPrivateChannel().complete();

    	welcomeChannel.sendMessage(
    			"**Hey,** " + event.getMember().getAsMention() + " and welcome on " + event.getGuild().getName() + " :wave:\n\n" +
    					"Now, have a nice day and a lot of fun on the server! Please read the pinned message!"
    			).queue();
    }
}
