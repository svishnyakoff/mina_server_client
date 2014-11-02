package aratog.task;

import aratog.task.command.Command;
import aratog.task.command.DecCommand;
import aratog.task.command.IncCommand;
import aratog.task.request.CommandArgs;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import java.util.HashMap;
import java.util.Map;


public class ServerHandler extends IoHandlerAdapter {

    private Map<Byte, Command> commandMap = new HashMap<Byte, Command>(){
        {
            put((byte)0x01, new IncCommand());
            put((byte)0x02, new DecCommand());
        }
    };

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        CommandArgs commandArgs = (CommandArgs) message;
        Command command = commandMap.get(commandArgs.getCommandCode());
        command.execute(commandArgs);
    }
}
