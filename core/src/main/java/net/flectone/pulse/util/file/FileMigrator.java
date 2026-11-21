package net.flectone.pulse.util.file;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.flectone.pulse.config.*;
import net.flectone.pulse.model.FColor;
import net.flectone.pulse.model.file.FilePack;
import net.flectone.pulse.util.constant.CacheName;
import net.flectone.pulse.util.constant.DefaultLocalization;
import net.flectone.pulse.util.constant.MessageType;
import net.flectone.pulse.util.constant.SettingText;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class FileMigrator {
    private final Provider<FileLoader> fileLoaderProvider;
    public FilePack migration_1_4_3(FilePack files) {
        Permission.Message.Update update = files.permission().message().update();
        if (update.name().equals("flectonepulse.module.message.op")) {
            files = files.withPermission(files.permission().withMessage(files.permission().message().withUpdate(files.permission().message().update()
                    .withName("flectonepulse.module.message.update.sound")
                    .withSound(new Permission.PermissionEntry("flectonepulse.module.message.update.sound", Permission.Type.TRUE))))
            );
        }
        return files;
    }
    public FilePack migration_1_5_0(FilePack files) {
        String oldChatKey = "CHAT";
        String newChatKey = "CHAT_GLOBAL";
        Integration.Discord discord = files.integration().discord();
        if (discord.messageChannel().containsKey(oldChatKey)) {
            discord.messageChannel().put(newChatKey, discord.messageChannel().remove(oldChatKey));
        }
        Integration.Telegram telegram = files.integration().telegram();
        if (telegram.messageChannel().containsKey(oldChatKey)) {
            telegram.messageChannel().put(newChatKey, telegram.messageChannel().remove(oldChatKey));
        }
        Integration.Twitch twitch = files.integration().twitch();
        if (twitch.messageChannel().containsKey(oldChatKey)) {
            twitch.messageChannel().put(newChatKey, twitch.messageChannel().remove(oldChatKey));
        }
        for (Localization localization : files.localizations().values()) {
            Localization.Integration.Discord localizationDiscord = localization.integration().discord();
            if (localizationDiscord.messageChannel().containsKey(oldChatKey)) {
                localizationDiscord.messageChannel().put(newChatKey, localizationDiscord.messageChannel().remove(oldChatKey));
            }
            Localization.Integration.Twitch localizationTwitch = localization.integration().twitch();
            if (localizationTwitch.messageChannel().containsKey(oldChatKey)) {
                localizationTwitch.messageChannel().put(newChatKey, localizationTwitch.messageChannel().remove(oldChatKey));
            }
            Localization.Integration.Telegram localizationTelegram = localization.integration().telegram();
            if (localizationTelegram.messageChannel().containsKey(oldChatKey)) {
                localizationTelegram.messageChannel().put(newChatKey, localizationTelegram.messageChannel().remove(oldChatKey));
            }
        }
        return files;
    }
    public FilePack migration_1_6_0(FilePack files) {
        List<Command.Chatsetting.Menu.Color.Type> colorTypes = files.command().chatsetting().menu().see().types();
        for (Command.Chatsetting.Menu.Color.Type colorType : colorTypes) {
            if (colorType.name().equals("default")) {
                colorType.colors().put(1, "");
                colorType.colors().put(2, "");
            }
        }
        Map<String, Integer> types = files.command().chatsetting().checkbox().types();
        Map<String, Integer> oldTypes = new HashMap<>(types);
        types.clear();
        if (oldTypes.containsKey("AFK")) {
            types.put(MessageType.AFK.name(), 9);
        }
        if (oldTypes.containsKey("ADVANCEMENT")) {
            types.put(MessageType.ADVANCEMENT.name(), 10);
        }
        types.put(MessageType.CHAT.name(), 11);
        if (oldTypes.containsKey("ANON")) {
            types.put(MessageType.COMMAND_ANON.name(), 12);
        }
        if (oldTypes.containsKey("BALL")) {
            types.put(MessageType.COMMAND_BALL.name(), 13);
        }
        if (oldTypes.containsKey("BROADCAST")) {
            types.put(MessageType.COMMAND_BROADCAST.name(), 14);
        }
        if (oldTypes.containsKey("COIN")) {
            types.put(MessageType.COMMAND_COIN.name(), 15);
        }
        if (oldTypes.containsKey("DICE")) {
            types.put(MessageType.COMMAND_DICE.name(), 16);
        }
        if (oldTypes.containsKey("DO")) {
            types.put(MessageType.COMMAND_DO.name(), 17);
        }
        if (oldTypes.containsKey("MAIL")) {
            types.put(MessageType.COMMAND_MAIL.name(), 18);
        }
        if (oldTypes.containsKey("ME")) {
            types.put(MessageType.COMMAND_ME.name(), 19);
        }
        if (oldTypes.containsKey("POLL")) {
            types.put(MessageType.COMMAND_POLL.name(), 20);
        }
        if (oldTypes.containsKey("ROCKPAPERSCISSORS")) {
            types.put(MessageType.COMMAND_ROCKPAPERSCISSORS.name(), 21);
        }
        types.put(MessageType.COMMAND_STREAM.name(), 22);
        if (oldTypes.containsKey("TELL")) {
            types.put(MessageType.COMMAND_TELL.name(), 23);
        }
        if (oldTypes.containsKey("TICTACTOE")) {
            types.put(MessageType.COMMAND_TICTACTOE.name(), 24);
        }
        if (oldTypes.containsKey("TRY")) {
            types.put(MessageType.COMMAND_TRY.name(), 25);
        }
        if (oldTypes.containsKey("DEATH")) {
            types.put(MessageType.DEATH.name(), 26);
        }
        if (oldTypes.containsKey("DISCORD")) {
            types.put(MessageType.FROM_DISCORD_TO_MINECRAFT.name(), 27);
        }
        if (oldTypes.containsKey("TELEGRAM")) {
            types.put(MessageType.FROM_TELEGRAM_TO_MINECRAFT.name(), 28);
        }
        if (oldTypes.containsKey("TWITCH")) {
            types.put(MessageType.FROM_TWITCH_TO_MINECRAFT.name(), 29);
        }
        if (oldTypes.containsKey("JOIN")) {
            types.put(MessageType.JOIN.name(), 30);
        }
        if (oldTypes.containsKey("QUIT")) {
            types.put(MessageType.QUIT.name(), 31);
        }
        types.put(MessageType.SLEEP.name(), 32);
        Map<String, Permission.Command.Chatsetting.SettingItem> settings = files.permission().command().chatsetting().settings();
        settings.clear();
        settings.put(SettingText.CHAT_NAME.name(), new Permission.Command.Chatsetting.SettingItem("flectonepulse.module.command.chatsetting.chat_name", Permission.Type.TRUE));
        settings.put("FCOLOR_" + FColor.Type.SEE.name(), new Permission.Command.Chatsetting.SettingItem("flectonepulse.module.command.chatsetting.fcolor_see", Permission.Type.TRUE));
        settings.put("FCOLOR_" + FColor.Type.OUT.name(), new Permission.Command.Chatsetting.SettingItem("flectonepulse.module.command.chatsetting.fcolor_out", Permission.Type.OP));
        settings.put(MessageType.AFK.name(), new Permission.Command.Chatsetting.SettingItem("flectonepulse.module.command.chatsetting.afk", Permission.Type.TRUE));
        settings.put(MessageType.ADVANCEMENT.name(), new Permission.Command.Chatsetting.SettingItem("flectonepulse.module.command.chatsetting.advancement", Permission.Type.TRUE));
        settings.put(MessageType.CHAT.name(), new Permission.Command.Chatsetting.SettingItem("flectonepulse.module.command.chatsetting.chat", Permission.Type.TRUE));
        settings.put(MessageType.COMMAND_ANON.name(), new Permission.Command.Chatsetting.SettingItem("flectonepulse.module.command.chatsetting.command_anon", Permission.Type.TRUE));
        settings.put(MessageType.COMMAND_BALL.name(), new Permission.Command.Chatsetting.SettingItem("flectonepulse.module.command.chatsetting.command_ball", Permission.Type.TRUE));
        settings.put(MessageType.COMMAND_BROADCAST.name(), new Permission.Command.Chatsetting.SettingItem("flectonepulse.module.command.chatsetting.command_broadcast", Permission.Type.TRUE));
        settings.put(MessageType.COMMAND_COIN.name(), new Permission.Command.Chatsetting.SettingItem("flectonepulse.module.command.chatsetting.command_coin", Permission.Type.TRUE));
        settings.put(MessageType.COMMAND_DICE.name(), new Permission.Command.Chatsetting.SettingItem("flectonepulse.module.command.chatsetting.command_dice", Permission.Type.TRUE));
        settings.put(MessageType.COMMAND_DO.name(), new Permission.Command.Chatsetting.SettingItem("flectonepulse.module.command.chatsetting.command_do", Permission.Type.TRUE));
        settings.put(MessageType.COMMAND_MAIL.name(), new Permission.Command.Chatsetting.SettingItem("flectonepulse.module.command.chatsetting.command_mail", Permission.Type.TRUE));
        settings.put(MessageType.COMMAND_ME.name(), new Permission.Command.Chatsetting.SettingItem("flectonepulse.module.command.chatsetting.command_me", Permission.Type.TRUE));
        settings.put(MessageType.COMMAND_POLL.name(), new Permission.Command.Chatsetting.SettingItem("flectonepulse.module.command.chatsetting.command_poll", Permission.Type.TRUE));
        settings.put(MessageType.COMMAND_ROCKPAPERSCISSORS.name(), new Permission.Command.Chatsetting.SettingItem("flectonepulse.module.command.chatsetting.command_rockpaperscissors", Permission.Type.TRUE));
        settings.put(MessageType.COMMAND_STREAM.name(), new Permission.Command.Chatsetting.SettingItem("flectonepulse.module.command.chatsetting.command_stream", Permission.Type.TRUE));
        settings.put(MessageType.COMMAND_TELL.name(), new Permission.Command.Chatsetting.SettingItem("flectonepulse.module.command.chatsetting.command_tell", Permission.Type.TRUE));
        settings.put(MessageType.COMMAND_TICTACTOE.name(), new Permission.Command.Chatsetting.SettingItem("flectonepulse.module.command.chatsetting.command_tictactoe", Permission.Type.TRUE));
        settings.put(MessageType.COMMAND_TRY.name(), new Permission.Command.Chatsetting.SettingItem("flectonepulse.module.command.chatsetting.command_try", Permission.Type.TRUE));
        settings.put(MessageType.DEATH.name(), new Permission.Command.Chatsetting.SettingItem("flectonepulse.module.command.chatsetting.death", Permission.Type.TRUE));
        settings.put(MessageType.FROM_DISCORD_TO_MINECRAFT.name(), new Permission.Command.Chatsetting.SettingItem("flectonepulse.module.command.chatsetting.from_discord_to_minecraft", Permission.Type.TRUE));
        settings.put(MessageType.FROM_TELEGRAM_TO_MINECRAFT.name(), new Permission.Command.Chatsetting.SettingItem("flectonepulse.module.command.chatsetting.from_telegram_to_minecraft", Permission.Type.TRUE));
        settings.put(MessageType.FROM_TWITCH_TO_MINECRAFT.name(), new Permission.Command.Chatsetting.SettingItem("flectonepulse.module.command.chatsetting.from_twitch_to_minecraft", Permission.Type.TRUE));
        settings.put(MessageType.JOIN.name(), new Permission.Command.Chatsetting.SettingItem("flectonepulse.module.command.chatsetting.join", Permission.Type.TRUE));
        settings.put(MessageType.QUIT.name(), new Permission.Command.Chatsetting.SettingItem("flectonepulse.module.command.chatsetting.quit", Permission.Type.TRUE));
        settings.put(MessageType.SLEEP.name(), new Permission.Command.Chatsetting.SettingItem("flectonepulse.module.command.chatsetting.sleep", Permission.Type.TRUE));
        Map<String, Localization> newLocalizations = new HashMap<>();
        for (Localization localization : files.localizations().values()) {
            Map<String, String> localizationTypes = localization.command().chatsetting().checkbox().types();
            localizationTypes.clear();
            boolean isRussian = localization.language().toLowerCase().contains("ru");
            if (isRussian) {
                localizationTypes.put(MessageType.AFK.name(), "<status_color>Афк");
                localizationTypes.put(MessageType.ADVANCEMENT.name(), "<status_color>Достижения");
                localizationTypes.put(MessageType.CHAT.name(), "<status_color>Сообщения чата");
                localizationTypes.put(MessageType.COMMAND_ANON.name(), "<status_color>Команда /anon");
                localizationTypes.put(MessageType.COMMAND_BALL.name(), "<status_color>Команда /ball");
                localizationTypes.put(MessageType.COMMAND_BROADCAST.name(), "<status_color>Команда /broadcast");
                localizationTypes.put(MessageType.COMMAND_COIN.name(), "<status_color>Команда /coin");
                localizationTypes.put(MessageType.COMMAND_DICE.name(), "<status_color>Команда /dice");
                localizationTypes.put(MessageType.COMMAND_DO.name(), "<status_color>Команда /do");
                localizationTypes.put(MessageType.COMMAND_MAIL.name(), "<status_color>Команда /mail");
                localizationTypes.put(MessageType.COMMAND_ME.name(), "<status_color>Команда /me");
                localizationTypes.put(MessageType.COMMAND_POLL.name(), "<status_color>Команда /poll");
                localizationTypes.put(MessageType.COMMAND_ROCKPAPERSCISSORS.name(), "<status_color>Команда /rockpaperscissors");
                localizationTypes.put(MessageType.COMMAND_STREAM.name(), "<status_color>Команда /stream");
                localizationTypes.put(MessageType.COMMAND_TELL.name(), "<status_color>Команда /tell");
                localizationTypes.put(MessageType.COMMAND_TICTACTOE.name(), "<status_color>Команда /tictactoe");
                localizationTypes.put(MessageType.COMMAND_TRY.name(), "<status_color>Команда /try");
                localizationTypes.put(MessageType.DEATH.name(), "<status_color>Смерти");
                localizationTypes.put(MessageType.FROM_DISCORD_TO_MINECRAFT.name(), "<status_color>Сообщения из Discord");
                localizationTypes.put(MessageType.FROM_TELEGRAM_TO_MINECRAFT.name(), "<status_color>Сообщения из Telegram");
                localizationTypes.put(MessageType.FROM_TWITCH_TO_MINECRAFT.name(), "<status_color>Сообщения из Twitch");
                localizationTypes.put(MessageType.JOIN.name(), "<status_color>Вход на сервер");
                localizationTypes.put(MessageType.QUIT.name(), "<status_color>Выход с сервера");
                localizationTypes.put(MessageType.SLEEP.name(), "<status_color>Сон");
                Localization defaultRussianLocalization = fileLoaderProvider.get().getDefaultFiles().localizations().get(DefaultLocalization.RUSSIAN.getName());
                newLocalizations.put(localization.language(), localization.withCommand(localization.command()
                                .withClearmail(defaultRussianLocalization.command().clearmail())
                                .withMail(defaultRussianLocalization.command().mail())
                                .withTell(defaultRussianLocalization.command().tell())
                                .withTictactoe(defaultRussianLocalization.command().tictactoe())
                                .withToponline(defaultRussianLocalization.command().toponline())
                        )
                );
            } else {
                localizationTypes.put(MessageType.AFK.name(), "<status_color>Афк");
                localizationTypes.put(MessageType.ADVANCEMENT.name(), "<status_color>Продвижение");
                localizationTypes.put(MessageType.CHAT.name(), "<status_color>Сообщения чата");
                localizationTypes.put(MessageType.COMMAND_ANON.name(), "<status_color>Команда /anon");
                localizationTypes.put(MessageType.COMMAND_BALL.name(), "<status_color>Команда /ball");
                localizationTypes.put(MessageType.COMMAND_BROADCAST.name(), "<status_color>Команда /broadcast");
                localizationTypes.put(MessageType.COMMAND_COIN.name(), "<status_color>Команда /coin");
                localizationTypes.put(MessageType.COMMAND_DICE.name(), "<status_color>Команда /dice");
                localizationTypes.put(MessageType.COMMAND_DO.name(), "<status_color>Команда /do");
                localizationTypes.put(MessageType.COMMAND_MAIL.name(), "<status_color>Команда /mail");
                localizationTypes.put(MessageType.COMMAND_ME.name(), "<status_color>Команда /me");
                localizationTypes.put(MessageType.COMMAND_POLL.name(), "<status_color>Команда /poll");
                localizationTypes.put(MessageType.COMMAND_ROCKPAPERSCISSORS.name(), "<status_color>Команда /rockpaperscissors");
                localizationTypes.put(MessageType.COMMAND_STREAM.name(), "<status_color>Команда /stream");
                localizationTypes.put(MessageType.COMMAND_TELL.name(), "<status_color>Команда /tell");
                localizationTypes.put(MessageType.COMMAND_TICTACTOE.name(), "<status_color>Команда /tictactoe");
                localizationTypes.put(MessageType.COMMAND_TRY.name(), "<status_color>Команда /try");
                localizationTypes.put(MessageType.DEATH.name(), "<status_color>Смерть");
                localizationTypes.put(MessageType.FROM_DISCORD_TO_MINECRAFT.name(), "<status_color>Сообщения от Discord");
                localizationTypes.put(MessageType.FROM_TELEGRAM_TO_MINECRAFT.name(), "<status_color>Сообщения из Telegram");
                localizationTypes.put(MessageType.FROM_TWITCH_TO_MINECRAFT.name(), "<status_color>Сообщения от Twitch");
                localizationTypes.put(MessageType.JOIN.name(), "<status_color>Присоединиться");
                localizationTypes.put(MessageType.QUIT.name(), "<status_color>Выйти");
                localizationTypes.put(MessageType.SLEEP.name(), "<status_color>Сон");
                Localization defaultEnglishLocalization = fileLoaderProvider.get().getDefaultFiles().localizations().get(DefaultLocalization.ENGLISH.getName());
                newLocalizations.put(localization.language(), localization.withCommand(localization.command()
                                .withClearmail(defaultEnglishLocalization.command().clearmail())
                                .withMail(defaultEnglishLocalization.command().mail())
                                .withTell(defaultEnglishLocalization.command().tell())
                                .withTictactoe(defaultEnglishLocalization.command().tictactoe())
                                .withToponline(defaultEnglishLocalization.command().toponline())
                        )
                );
            }
        }
        return files.withLocalizations(newLocalizations);
    }
    public FilePack migration_1_7_0(FilePack files) {
        Map<String, List<String>> messageChannel = files.integration().discord().messageChannel();
        messageChannel.put(MessageType.FROM_DISCORD_TO_MINECRAFT.name(), List.of("123456"));
        messageChannel.put("CHAT_GLOBAL", List.of("123456"));
        files = files.withIntegration(files.integration().withDiscord(files.integration().discord().withMessageChannel(messageChannel)));
        Map<String, String> triggers = files.message().format().replacement().triggers();
        Map<String, String> updates = new HashMap<>();
        updates.put("smile", ":-?\\)");
        updates.put("big_smile", ":-?D");
        updates.put("sad", ":-?\\(");
        updates.put("ok_hand", "(?i):ок:");
        updates.put("thumbs_up", ":\\+1:");
        updates.put("thumbs_down", ":-1:");
        updates.put("cool_smile", "(?i):круто:");
        updates.put("cool_glasses", "B-\\)");
        updates.put("clown", "(?i):клоун:");
        updates.put("heart", "<3");
        updates.put("laughing", "(?i)хд");
        updates.put("confused", "%-\\)");
        updates.put("happy", "=D");
        updates.put("angry", ">:-?\\(");
        updates.put("ascii_idk", "(?i):я не знаю:");
        updates.put("ascii_angry", "(?i):злой:");
        updates.put("ascii_happy", "(?i):счастливый:");
        updates.put("ping", "%pобработка%");
        updates.put("tps", "%tпс%");
        updates.put("online", "%online%");
        updates.put("coords", "%cоордов%");
        updates.put("stats", "%sтат%");
        updates.put("skin", "%sкин%");
        updates.put("item", "%iимеет%");
        updates.put("spoiler", "\\|\\|");
        updates.put("bold", "\\*\\*");
        updates.put("italic", "\\*");
        updates.put("underline", "__");
        updates.put("obfuscated", "\\?\\?");
        updates.put("strikethrough", "~~");
        String boundaryPattern = "(?<!\\\\)(?<!\\S)%s(?!\\S)";
        String formatTemplate = "(?<!\\S)%1$s([^%1$s\\n]+)%1$s(?!\\S)";
        updates.forEach((key, value) -> {
            if (triggers.containsKey(key)) {
                String pattern = key.equals("spoiler") || key.equals("bold") || key.equals("italic") || key.equals("underline") || key.equals("obfuscated") || key.equals("strikethrough")
                        ? formatTemplate : boundaryPattern;
                triggers.put(key, String.format(pattern, value));
            }
        });
        if (files.message().bubble().elevation() == 1) {
            files = files.withMessage(files.message().withBubble(files.message().bubble().withElevation(0.4f)));
        }
        Map<String, Localization> newLocalizations = new HashMap<>();
        for (Localization localization : files.localizations().values()) {
            Localization.Integration localizationIntegration = localization.integration();
            localizationIntegration.discord().messageChannel().put(MessageType.FROM_DISCORD_TO_MINECRAFT.name(), Localization.Integration.Discord.ChannelEmbed.builder().content("<fcolor:2><global_name> <fcolor:1>» <fcolor:4><message>").build());
            localizationIntegration.telegram().messageChannel().put(MessageType.FROM_TELEGRAM_TO_MINECRAFT.name(), "<fcolor:2><user_name> <fcolor:1>» <fcolor:4><message>");
            localizationIntegration.twitch().messageChannel().put(MessageType.FROM_TWITCH_TO_MINECRAFT.name(), "<fcolor:2><name> <fcolor:1>» <fcolor:4><message>");
            localizationIntegration.discord().infoChannel().forEach((key, value) ->
                    localizationIntegration.discord().infoChannel().put(key, Strings.CS.replace(value, "<tps>", "<замена: тпс>")));
            localizationIntegration.telegram().infoChannel().forEach((key, value) ->
                    localizationIntegration.telegram().infoChannel().put(key, Strings.CS.replace(value, "<tps>", "<замена: тпс>")));
            Localization.Message.Vanilla localizationVanilla = localization.message().vanilla();
            for (Map.Entry<String, String> entry : localizationVanilla.types().entrySet()) {
                localizationVanilla.types().put(entry.getKey(), Strings.CS.replace(entry.getValue(), "<аргумент_", "<аргумент:"));
            }
            boolean isRussian = localization.language().equalsIgnoreCase("ru_ru");
            if (isRussian) {
                localizationVanilla.types().put("commands.list.players", "<fcolor:1>\uD83D\uDC65 На сервере <fcolor:2><argument:0><fcolor:1> из <fcolor:2><argument:1><fcolor:1> игроков: <argument:2>");
                localizationVanilla.types().put("death.attack.spear", "<fcolor:1>☠ <argument:0> был проткнут <argument:1>");
                localizationVanilla.types().put("death.attack.spear.item", "<fcolor:1>☠ <argument:0> был проткнут <argument:1> с помощью <argument:2>");
            } else {
                localizationVanilla.types().put("commands.list.players", "<fcolor:1>\uD83D\uDC65 There are <fcolor:2><argument:0><fcolor:1> of a max of <fcolor:2><argument:1><fcolor:1> players online: <argument:2>");
                localizationVanilla.types().put("death.attack.spear", "<fcolor:1>☠ <argument:0> was speared by <argument:1>");
                localizationVanilla.types().put("death.attack.spear.item", "<fcolor:1>☠ <argument:0> was speared by <argument:1> using <argument:2>");
            }
            Localization.Command.Ping localizationPing = localization.command().ping();
            String oldPingFormat = localizationPing.format();
            Localization.Message.Update localizationUpdate = localization.message().update();
            String oldUpdateFormat = localizationUpdate.formatPlayer();
            localization = localization
                    .withCommand(localization.command()
                            .withPing(localization.command().ping().withFormat(Strings.CS.replace(oldPingFormat, "<пинг>", "<замена: пинг>")))
                    )
                    .withMessage(localization.message()
                            .withUpdate(localization.message().update().withFormatPlayer(Strings.CS.replace(oldUpdateFormat, "<URL:", "<замена: URL:")))
                    );
            String[] oldTags = new String[]{"ping", "tps", "online"};
            Localization.Message.Format.Replacement localizationReplacement = localization.message().format().replacement();
            for (String key : oldTags) {
                String value = localizationReplacement.values().get(key);
                if (value != null) {
                    localizationReplacement.values().put(key, Strings.CS.replace(value, "<" + key + ">", "<значение>"));
                }
            }
            if (isRussian && localizationReplacement.values().containsKey("url")) {
                localizationReplacement.values().put("url", "<click:open_url:\"<message_1>\"><hover:show_text:\"<fcolor:2>Открыть ссылку <br><u><message_1>\"><fcolor:2><u>🗗 Ссылка</u></hover></click>");
            }
            Consumer<List<String>> stringsWithOldTagsConsumer = strings -> {
                for (int i = 0; i < strings.size(); i++) {
                    String string = strings.get(i);
                    for (String oldTag : oldTags) {
                        if (string.contains(oldTag)) {
                            string = Strings.CS.replace(string, "<" + oldTag + ">", "<замена:" + oldTag + ">");
                            strings.set(i, string);
                        }
                    }
                }
            };
            localization.message().sidebar().values().forEach(stringsWithOldTagsConsumer);
            localization.message().tab().footer().lists().forEach(stringsWithOldTagsConsumer);
            localization.message().tab().header().lists().forEach(stringsWithOldTagsConsumer);
            newLocalizations.put(localization.language(), localization);
        }
        return files.withLocalizations(newLocalizations);
    }
    public FilePack migration_1_7_1(FilePack files) {
        List<Message.Vanilla.VanillaMessage> vanillaMessages = new ArrayList<>();
        for (Message.Vanilla.VanillaMessage vanillaMessage : files.message().vanilla().types()) {
            if (!vanillaMessage.name().equals("DEATH")) {
                vanillaMessages.add(vanillaMessage);
                continue;
            }
            List<String> translationKeys = new ArrayList<>(vanillaMessage.translationKeys());
            if (!translationKeys.contains("death.attack.spear")) {
                translationKeys.add("death.attack.spear");
            }
            if (!translationKeys.contains("death.attack.spear.item")) {
                translationKeys.add("death.attack.spear.item");
            }
            vanillaMessages.add(vanillaMessage.withTranslationKeys(translationKeys));
        }
        files.config().cache().types().put(CacheName.COOLDOWN, new Config.Cache.CacheSetting(5, TimeUnit.HOURS, 5000));
        return files.withMessage(files.message().withVanilla(files.message().vanilla().withTypes(vanillaMessages)));
    }
    public FilePack migration_1_7_2(FilePack files) {
        Map<String, Localization> newLocalizations = new HashMap<>();
        for (Localization localization : files.localizations().values()) {
            String newDisplay = Strings.CS.replace(localization.message().format().names().display(), "<голова_игрока>", "<white><player_head></white>");
            String newPlayerlistname = Strings.CS.replace(localization.message().tab().playerlistname().format(), "<голова_игрока>", "<white><player_head></white>");
            newLocalizations.put(localization.language(),
                    localization.withMessage(localization.message()
                            .withFormat(localization.message().format().withNames(localization.message().format().names().withDisplay(newDisplay)))
                            .withTab(localization.message().tab().withPlayerlistname(localization.message().tab().playerlistname().withFormat(newPlayerlistname)))
                    )
            );
        }
        return files.withLocalizations(newLocalizations);
    }
    public FilePack migration_1_7_4(FilePack files) {
        Command.Chatsetting.Menu menu = files.command().chatsetting().menu();
        UnaryOperator<List<Command.Chatsetting.Menu.Color.Type>> migrateTypeOperator = types -> {
            List<Command.Chatsetting.Menu.Color.Type> newTypes = new LinkedList<>();
            for (Command.Chatsetting.Menu.Color.Type type : types) {
                if ("default".equals(type.name())) {
                    newTypes.add(type);
                    continue;
                }
                newTypes.add(Command.Chatsetting.Menu.Color.Type.builder()
                        .name(type.name())
                        .material(type.material())
                        .colors(type.colors().entrySet().stream()
                                .collect(Collectors.toMap(
                                                Map.Entry::getKey,
                                                entry -> StringUtils.isEmpty(entry.getValue()) ? "null" : entry.getValue(),
                                                (object, object2) -> object,
                                                LinkedHashMap::new
                                        )
                                ))
                        .build()
                );
            }
            return newTypes;
        };
        return files.withCommand(
                files.command().withChatsetting(
                        files.command().chatsetting().withMenu(menu
                                .withSee(menu.see().withTypes(migrateTypeOperator.apply(menu.see().types())))
                                .withOut(menu.out().withTypes(migrateTypeOperator.apply(menu.out().types())))
                        )
                )
        );
    }
}