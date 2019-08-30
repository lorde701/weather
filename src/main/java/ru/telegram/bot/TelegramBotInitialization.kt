package ru.telegram.bot

import org.telegram.abilitybots.api.bot.AbilityBot
import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.bots.DefaultBotOptions
import org.telegram.telegrambots.meta.ApiContext
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException

import java.net.Authenticator
import java.net.PasswordAuthentication
import java.util.function.Function
import java.util.function.Supplier

object TelegramBotInitialization {

    // use startUpBot function
    @Deprecated("")
    fun init(botName: String, botToken: String) {
        try {
            ApiContextInitializer.init()
            val botsApi = TelegramBotsApi()
            botsApi.registerBot(MyBotAbility(botToken, botName))
        } catch (e: TelegramApiRequestException) {
            e.printStackTrace()
        }

    }

    // use startUpBot function
    @Deprecated("")
    fun init(botName: String, botToken: String, proxyHost: String, proxyPort: Int, proxyUser: String, proxyPassword: String) {
        try {
            ApiContextInitializer.init()
            val botsApi = TelegramBotsApi()

            // Создаем экземпляр настроек
            val botOptions = getDefaultBotOption(proxyHost, proxyPort, proxyUser, proxyPassword)

            botsApi.registerBot(MyBotAbility(botToken, botName, botOptions))

        } catch (e: TelegramApiException) {
            e.printStackTrace()
        }

    }

    fun startUpBot(botCreator: Supplier<AbilityBot>) {
        try {
            ApiContextInitializer.init()
            val botsApi = TelegramBotsApi()
            botsApi.registerBot(botCreator.get())
        } catch (e: TelegramApiRequestException) {
            e.printStackTrace()
        }

    }

    fun startUpBot(proxyHost: String, proxyPort: Int, proxyUser: String, proxyPassword: String,
                   botCreator: Function<DefaultBotOptions, AbilityBot>) {
        try {
            ApiContextInitializer.init()
            val botsApi = TelegramBotsApi()

            // Создаем экземпляр настроек
            val botOptions = getDefaultBotOption(proxyHost, proxyPort, proxyUser, proxyPassword)

            botsApi.registerBot(botCreator.apply(botOptions))

        } catch (e: TelegramApiException) {
            e.printStackTrace()
        }

    }

    fun getDefaultBotOption(proxyHost: String, proxyPort: Int, proxyUser: String, proxyPassword: String): DefaultBotOptions {
        // Авторизация бота в прокси, после создания будет использоваться автоматически
        Authenticator.setDefault(object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(proxyUser, proxyPassword.toCharArray())
            }
        })

        // Создаем экземпляр настроек
        val botOptions = ApiContext.getInstance(DefaultBotOptions::class.java)

        // Устанавливаем настройки прокси
        botOptions.setProxyHost(proxyHost)
        botOptions.setProxyPort(proxyPort)
        // Выбираем тип прокси: [HTTP|SOCKS4|SOCKS5] (по умолчанию: NO_PROXY)
        botOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS5)

        return botOptions
    }
}
