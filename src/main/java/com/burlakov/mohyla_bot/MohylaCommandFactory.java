package com.burlakov.mohyla_bot;

import io.github.nixtabyte.telegram.jtelebot.client.RequestHandler;
import io.github.nixtabyte.telegram.jtelebot.response.json.Message;
import io.github.nixtabyte.telegram.jtelebot.server.Command;
import io.github.nixtabyte.telegram.jtelebot.server.CommandFactory;
import org.apache.log4j.Logger;


/**
 * Created by denysburlakov on 04.09.15.
 */
public class MohylaCommandFactory implements CommandFactory{
    private static final Logger logger = Logger.getLogger(MohylaCommandFactory.class);
    @Override
    public Command createCommand(Message message, RequestHandler requestHandler) {
        logger.info("INPUT MESSAGE: "+message.getText());
        return new MohylaCommand(message, requestHandler);
    }
}
