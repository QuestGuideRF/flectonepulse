# FlectonePulse

### 🎥 Видеообзор FlectonePulse

[![FlectonePulse](https://img.youtube.com/vi/UjIlfjXzdxE/maxresdefault.jpg)](https://youtu.be/UjIlfjXzdxE "Посмотреть")
## 🏆 Что делает FlectonePulse особенным?

FlectonePulse — плагин и мод для Minecraft-серверов, который берёт под контроль чат, сообщения и уведомления. Новичкам он особенно понравится, потому что настройка простая, а результат — красивый чат, интеграции и полезные команды без лишней мороки.

- Все операции выполняются асинхронно, основной поток сервера не затрагивается
- Используется Google Guice для инъекции зависимостей, что упрощает расширение функционала
- Поддерживает все популярные платформы Bukkit, Spigot, Paper, Purpur, Folia, Fabric, BungeeCord, Waterfall и Velocity на версиях 1.8.8 до самой последней

## 🎨 Гибкое форматирование текста

Поддерживаются все форматы цветов, от устаревших (`&` или `§` для цветов) до современных тегов MiniMessage

| **Ввод кода**                                        | **Преобразование**                                             |
|-------------------------------------------------------|-------------------------------------------------------------|
| `&0`-`&9`, `&a`-`&f`                                  | `<black>`, `<dark_blue>`, ..., `<white>`                    |
| `&l`/`&m`/`&n`/`&o`/`&k`/`&r`                         | `<b>` / `<st>` / `<u>` / `<i>` / `<obf>` / `<reset>`        |
| `&#rrggbb`, `#rrggbb`, `&x&r&r&g&g&b&b`, `<##rrggbb>` | `<#rrggbb>`                                                 |
| Теги MiniMessage                                      | `<color:#rrggbb>`, `<rainbow>`, `<click:...>`, `<font>`, и т.д. |

```yaml
# ПРИМЕР
join:
  format: "<gradient:#FF0000:#00FF00>&lПривет</gradient> <rainbow><player></rainbow>!"
```

![color](https://flectone.net/pulse/welcomemessage.png)

## 🧱 Головы игроков в чате

Добавьте аватарки в сообщения с помощью плейсхолдера `<player_head>` или можно указывать ник `<player_head:имя>`. Для этого не нужен ресурспак, все игроки это увидят

![object](https://flectone.net/pulse/object.png)

## 🌈 Настройка чата с /chatsetting

![chatsetting](https://flectone.net/pulse/commandchatsetting.gif)

Команда /chatsetting открывает меню для быстрой кастомизации сообщений каждому игроку. Выберите цвета, стили и отключите ненужные сообщения

## 🌍 Умная локализация

### Как это работает
[![locale](https://flectone.net/pulse/locale.gif)](https://flectone.net/pulse/docs/config/language/)

При включённом `by_player: true` FlectonePulse определяет язык клиента и показывает сообщения на нём. Если перевода нет, будет использоваться дефолтный из конфига. Подробнее в [документации](https://flectone.net/pulse/docs/config#language-player) 🔗

## ✨ Настраиваемые элементы

| **Визуал** | **Описание** |
|---------------|-----------------|
| ![status](https://flectone.net/pulse/version.png) | **MOTD сервера** <br> Добавьте кастомные тексты для приветствия в списке серверов |
| ![join](https://flectone.net/pulse/join.png) | **Сообщения о входе** <br> Приветствуйте игроков |
| ![tab](https://flectone.net/pulse/tab.png) | **TAB-меню** <br> Покажите пинг, онлайн, ранги — всё в одном месте |
| ![death](https://flectone.net/pulse/deathserver.png) | **Сообщения о смерти** <br> Сделайте их забавными с текстом или звуками |
| ![brand](https://flectone.net/pulse/brand.png) | **Брендинг** <br> Добавьте название сервера в меню F3 |
| ![advancement](https://flectone.net/pulse/task.png) | **Достижения** <br> Кастомные сообщения о достижениях |
| Полный список в [документации](https://flectone.net/pulse/docs/message/) 🔗                                                | ...                                                               |

## 🤝 Интеграции

### Внешние платформы

| **Визуал** | **Описание** |
|---------|----------|
| [![discord](https://flectone.net/pulse/discordmessage.png)](https://flectone.net/pulse/docs/integration/discord/) | **Discord** <br> Синхронизируйте чат сервера с каналами Discord |
| [![telegram](https://flectone.net/pulse/telegrammessage2.png)](https://flectone.net/pulse/docs/integration/telegram/) | **Telegram** <br> Отправляйте сообщения через бота в Telegram и синхронизируйте чаты |
| [![twitch](https://flectone.net/pulse/twitchmessage.png)](https://flectone.net/pulse/docs/integration/twitch/) | **Twitch** <br> Уведомления о стримах в чате Minecraft и синхронизация чата сервера |

### Плагины

| **Плагин**                                                                                | **Описание**                                                 |
|-----------------------------------------------------------------------------------------------|--------------------------------------------------------------------|
| 💬 **[InteractiveChat](https://flectone.net/pulse/docs/integration/interactivechat/)**        | Интерактивные элементы в чате              |
| 🛡️ **[LuckPerms](https://flectone.net/pulse/docs/integration/luckperms/)**                   | Управление правами и группами         |
| 🧩 **[PlaceholderAPI](https://flectone.net/pulse/docs/integration/placeholderapi/)**          | Плейсхолдеры из других плагинов, например %player_level%           |
| 🎙️ **[PlasmoVoice & SimpleVoice](https://flectone.net/pulse/docs/integration/plasmovoice/)** | Синхронизация игноров и мутов в голосовом чате           |
| 🖼️ **[SkinsRestorer](https://flectone.net/pulse/docs/integration/skinsrestorer/)**           | Отображение скинов в чате и TAB                 |
| 👻 **[SuperVanish](https://flectone.net/pulse/docs/integration/supervanish/)**                | Скрытие игроков в ванише              |
| Полный список в [документации](https://flectone.net/pulse/docs/integration/) 🔗                                                                                          | ...                                                               |

## 🎮 Более 30 команд

| **Визуал** | **Описание** |
|--------------|------------------------|
| [![ball](https://flectone.net/pulse/commandball.png)](https://flectone.net/pulse/docs/command/) | **/ball** <br> Волшебный шар с множеством ответов |
| [![tictactoe](https://flectone.net/pulse/commandtictactoe.png)](https://flectone.net/pulse/docs/command/) | **/tictactoe** <br> Крестики-нолики |
| [![stream](https://flectone.net/pulse/commandstream.png)](https://flectone.net/pulse/docs/command/) | **/stream** <br> Уведомления о стримах в чате |
| [![try](https://flectone.net/pulse/commandtry.png)](https://flectone.net/pulse/docs/command/) | **/try** <br> Испытай удачу от 0% до 100% |
| Полный список в [документации](https://flectone.net/pulse/docs/command/) 🔗                                                                                           | ...                                                              |

## 🙏 Благодарности

FlectonePulse основан на этих проектах:

- 🏗️ **[Google Guice](https://github.com/google/guice)** — для модульного кода
- 📚 **[JDBI](https://jdbi.org/)** с **[HikariCP](https://github.com/brettwooldridge/HikariCP)** — эффективная работа с БД
- 📦 **[Jackson](https://github.com/FasterXML/jackson)** — сериализация данных
- 🧙 **[PacketEvents](https://github.com/retrooper/packetevents)** — обработка пакетов
- 🎨 **[Adventure](https://github.com/KyoriPowered/adventure)** — форматирование текста
- ⌨️ **[Cloud](https://github.com/Incendo/cloud)** — команды с автодополнением
- ⏱️ **[Universal Scheduler](https://github.com/Anon8281/UniversalScheduler)** — планирование задач
- 🔣 **[Symbol Chat](https://github.com/replaceitem/symbol-chat)** — символы в чате
- 🖥️ **[PacketUxUi](https://github.com/OceJlot/PacketUxUi)** — GUI-элементы
- 💬 **[LightChatBubbles](https://github.com/atesin/LightChatBubbles)** — сообщения над головой
- 🌐 **[MiniTranslator](https://github.com/imDaniX/MiniTranslator)** — конвертация устаревших цветов
- 🌱 **[FlectoneChat](https://github.com/Flectone/FlectoneChat)** — предок FlectonePulse

И спасибо сообществу! Каждая звезда на GitHub и отзыв на платформах показывают, что FlectonePulse действительно нужен ⭐

FlectonePulse полностью бесплатный. Скачивайте, модифицируйте, ставьте на сервер

Подпись относится к текущей версии проекта.

---

Автор: legenda  
Telegram: [@LEGENDA_SD](https://t.me/LEGENDA_SD)
