package me.x1xx.bees;

import me.x1xx.bees.database.TokenDAO;
import me.x1xx.bees.database.model.TokenRow;
import me.x1xx.bees.utility.TableBuilder;
import net.dv8tion.jda.api.entities.Activity;

import java.util.List;
import java.util.Scanner;

public class CommandScanner {
    public static void start(Main main) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String in = scanner.nextLine();
            String[] s = in.split(" ");
            try {
                if (s[0].equalsIgnoreCase("token")) {
                    if (s[1].equalsIgnoreCase("add")) {
                        String token = s[2];
                        String alias = in.substring(s[2].length() + s[1].length() + s[0].length() + 3);

                        int id = main.getDatabase().withExtension(TokenDAO.class, dao -> dao.insert(token, alias));
                        main.getLogger().info("Added token " + alias + ":" + id);
                        main.getInitializer().loginToken(main.getDatabase().withExtension(TokenDAO.class, dao -> dao.retrieveById(id)));
                    } else if (s[1].equalsIgnoreCase("remove")) {
                        int id = Integer.parseInt(s[2]);
                        TokenRow result = main.getDatabase().withExtension(TokenDAO.class, dao -> dao.retrieveById(id));
                        main.getDatabase().useExtension(TokenDAO.class, dao -> dao.removeById(id));
                        if (result != null) {
                            main.getLogger().info("Removed token " + result.getAlias() + ":" + result.getId());
                            main.getInitializer().logOutJDA(id);
                        } else
                            main.getLogger().info("No token could be found with that id!");
                    } else if (s[1].equalsIgnoreCase("list")) {
                        List<TokenRow> tokens = main.getDatabase().withExtension(TokenDAO.class, TokenDAO::retrieveAll);
                        String[][] values = new String[tokens.size()][6];
                        for (int i = 0; i < tokens.size(); i++) {
                            TokenRow token = tokens.get(i);
                            values[i][0] = token.getId() + "";
                            values[i][1] = token.getAlias();
                            values[i][2] = token.getToken().substring(token.getToken().length() - 7);
                            values[i][3] = token.getActivity() != null ? token.getActivity().toString() : "N/A";
                            values[i][4] = token.getUrl() != null ? token.getUrl().substring(0, Integer.min(token.getUrl().length(), 10)) : "N/A";
                            values[i][5] = token.getTopic() != null ? token.getTopic().substring(0, Integer.min(token.getTopic().length(), 10)) : "N/A";
                        }
                        main.getLogger().info("\n" + new TableBuilder()
                                .addHeaders("Id", "Alias", "Token", "Activity", "url", "topic")
                                .setValues(values)
                                .setBorders(TableBuilder.Borders.FRAME)
                                .frame(true).build());
                    } else
                        main.getLogger().info("Options for token is add | remove | list");
                } else if (s[0].equalsIgnoreCase("activity")) {
                    if (s[1].equalsIgnoreCase("add")) {
                        if (s[2].equalsIgnoreCase("url")) {
                            int id = Integer.parseInt(s[3]);
                            String url = in.substring(s[3].length() + s[2].length() + s[1].length() + s[0].length() + 4);
                            main.getDatabase().useExtension(TokenDAO.class, dao -> dao.updateURL(id, url));
                            main.getLogger().info("Updated url!");
                        } else if (s[2].equalsIgnoreCase("topic")) {
                            int id = Integer.parseInt(s[3]);
                            String topic = in.substring(s[3].length() + s[2].length() + s[1].length() + s[0].length() + 4);
                            main.getDatabase().useExtension(TokenDAO.class, dao -> dao.updateTopic(id, topic));
                            main.getLogger().info("Updated topic!");
                        } else if (s[2].equalsIgnoreCase("activity")) {
                            int id = Integer.parseInt(s[3]);
                            int activity = Integer.parseInt(s[4]);
                            main.getDatabase().useExtension(TokenDAO.class, dao -> dao.updateActivity(id, activity));
                            main.getLogger().info("Updated activity!");
                        }
                    } else if (s[1].equalsIgnoreCase("remove")) {
                        if (s[2].equalsIgnoreCase("url")) {
                            int id = Integer.parseInt(s[3]);
                            main.getDatabase().useExtension(TokenDAO.class, dao -> dao.updateURL(id, null));
                            main.getLogger().info("Removed url!");
                        } else if (s[2].equalsIgnoreCase("topic")) {
                            int id = Integer.parseInt(s[3]);
                            main.getDatabase().useExtension(TokenDAO.class, dao -> dao.updateTopic(id, null));
                            main.getLogger().info("Removed topic!");
                        } else if (s[2].equalsIgnoreCase("activity")) {
                            int id = Integer.parseInt(s[3]);
                            main.getDatabase().useExtension(TokenDAO.class, dao -> dao.updateActivity(id, 2));
                            main.getLogger().info("Removed activity!");
                        }
                    } else if (s[1].equalsIgnoreCase("types")) {
                        String[][] values = new String[Activity.ActivityType.values().length][2];
                        for (int i = 0; i < Activity.ActivityType.values().length; i++) {
                            Activity.ActivityType type = Activity.ActivityType.values()[i];
                            values[i][0] = type.ordinal() + "";
                            values[i][1] = type.toString();
                        }

                        main.getLogger().info("\n" + new TableBuilder()
                                .addHeaders("Id", "Name")
                                .setValues(values)
                                .setBorders(TableBuilder.Borders.FRAME)
                                .frame(true).build());
                    } else if (s[1].equalsIgnoreCase("update")) {
                        int id = Integer.parseInt(s[2]);
                        main.getInitializer().updateStatus(id);
                        main.getLogger().info("Updated status!");
                    } else
                        main.getLogger().info("Options for activity is add | remove");
                } else if (s[0].equalsIgnoreCase("shutdown")) {
                    main.shutDown();
                } else {
                    main.getLogger().info("Unknown command! Please use the command token or activity");
                }
            } catch (Exception e) {
                main.getLogger().error("Unknown command or error!", e);
            }


        }
    }
}
