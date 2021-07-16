## ❤️ Javacord and Velen Example Bot
This is a full-fledged example of a Javacord Discord bot that utilizes Velen as a framework paired with Redis
for a per-server prefix and blacklist. This is written for Javacord `v3.3.2` and Velen `v2.0.0`, you may use your own
database paradigm like `MongoDB` or `MySQL` if you like.

## 🥰 Requirements
- Gradle
- Redis (Data Persistence should be enabled).
- Java 11

## 😊 Build
You can build this application through the application plugin with Gradle:
```gradle
./gradlew build
```

## 🍔 Environmental Variables
- **redis_host**: The host name of the Redis server.
- **redis_port**: The port of the Redis server.
- **redis_pass**: The password of the Redis database.
- **token**: The Discord bot token.

## 🎆 Demonstrations
The bot has several basic functions and commands, but the main point of this example
bot is to demonstrate the following:
- **Creating a Help Command with Velen.**
- **Utilizing Fuzzy Command Suggestion with Velen.**
- **Creating Commands with Velen.**
- **Utilizing Gradle with Application plugin instead of Fatjars.**
- **Logging with Logback and SLF4J.**
- **Utilizing Redis with Velen.**
- **Per-server Prefixes (Persistent) with Velen.**
- **Persistent Blacklist with Velen.**

## 💡 Commands
Here are the basic commands implemented:
- **Avatar command**: To get the avatar of a user or yourself.
- **Help command**: The starting point of the bot, shows all the commands categories.
- **Ping command**: To get the latency of the bot from the server to Discord.
- **Blacklist command**: An owner-only command that is used to blacklist users from using the bot.
- **Prefix command**: To change or get the prefix used by the server.

## 🍅 Credits
- [Velen](https://github.com/ShindouMihou/Velen): The framework used to quickly create this bot.
- [Javacord](https://github.com/Javacord/Javacord): The library used by Velen and the bot to communicate with Discord, etc.
- [Redis](https://redislabs.com): The database that is being used by this bot.
- [Jedis](https://github.com/redis/jedis): The database driver that is being used by this bot.
- [Logback](https://logback.qos.ch/): The logger used by this bot.
- [SLF4J](http://www.slf4j.org/): The logging API used by the bot.
