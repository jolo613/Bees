package me.x1xx.bees.modules;

import me.x1xx.bees.Main;
import me.x1xx.bees.database.TokenDAO;
import me.x1xx.bees.database.model.TokenRow;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import org.slf4j.Logger;

import javax.security.auth.login.LoginException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class ModuleInitializer {
    private final Main main;
    private final Logger logger;

    private final JDABuilder builder;

    private final Set<Module> moduleSet;

    private final Map<Integer, JDA> jdaMap = new HashMap<>();


    public ModuleInitializer(Main main) {
        this.main = main;
        logger = main.getLogger();
        JDABuilder builder = JDABuilder.createDefault(null).setAutoReconnect(true)
                .setEnableShutdownHook(true);


        moduleSet = new HashSet<>();

        for (Class<? extends Module> aClass : ModuleRegistry.moduleSet) {
            try {
                Module module = aClass.getDeclaredConstructor().newInstance();
                module.preInitialization(builder, main);
                moduleSet.add(module);


            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        this.builder = builder;
        List<TokenRow> tokens = main.getDatabase().withExtension(TokenDAO.class, TokenDAO::retrieveAll);

        for (TokenRow token : tokens) {
            loginToken(token);
        }


    }

    public void loginToken(TokenRow token) {
        if(token == null) {
            return;
        }
        logger.info("Logging into token - " + token.getId() + ":" + token.getAlias());
        String topic = token.getTopic() != null ? token.getTopic() : "Bees!";
        String url = token.getUrl();
        Activity.ActivityType type = token.getActivity() != null ? token.getActivity() : Activity.ActivityType.LISTENING;

        try {
            JDA jda = builder.setToken(token.getToken()).build();
            jda.getPresence().setPresence(OnlineStatus.ONLINE, Activity.of(type, topic, url));
            jdaMap.put(token.getId(), jda);
            for (Module module : moduleSet) {
                module.initialization(jda);
            }
        } catch (LoginException e) {
            logger.error("Could not login to JDA instance " + token.getId() + " : " + token.getAlias(), e);
        }
    }

    public void logOutJDA(int id) {
        JDA jda = jdaMap.remove(id);
        jda.shutdown();
    }

    public void updateStatus(int id) {

       TokenRow token = main.getDatabase().withExtension(TokenDAO.class, dao -> dao.retrieveById(id));

        if(token == null) {
            return;
        }


        String topic = token.getTopic() != null ? token.getTopic() : "Bees!";
        String url = token.getUrl();
        Activity.ActivityType type = token.getActivity() != null ? token.getActivity() : Activity.ActivityType.LISTENING;


        jdaMap.get(id).getPresence().setPresence(OnlineStatus.ONLINE, Activity.of(type, topic, url));

    }

    public void shutdown() {
        for (JDA jda : jdaMap.values()) {
            jda.shutdown();
        }
    }
}
