package teams.xianlin.com.teamshit.NetWork;

import java.io.Serializable;

import com.google.gson.annotations.Expose;

@SuppressWarnings("serial")
public abstract class Packet implements Serializable
{
	@Expose
	protected int	Command;//命令字

	public int getCommand() {
		return Command;
	}

	public void setCommand(int command) {
		Command = command;
	}

}
