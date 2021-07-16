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
- Avatar command
- Help command
- Ping command
- Blacklist command
- Prefix command.

## 🍅 Credits
- [Velen](https://github.com/ShindouMihou/Velen)
- [Javacord](https://github.com/Javacord/Javacord)
- [Redis](https://redislabs.com)
- [Jedis](https://github.com/redis/jedis)
