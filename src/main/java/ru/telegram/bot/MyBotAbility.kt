package ru.telegram.bot

import org.telegram.abilitybots.api.bot.AbilityBot
import org.telegram.telegrambots.bots.DefaultBotOptions
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException

import java.util.HashMap

class MyBotAbility : AbilityBot {


    private val userContextMap = HashMap<Long, UserContext>()

    private val sendNewMessage: (SendMessage) -> Unit = { sm ->
        try {
            execute<Message, SendMessage>(sm)
        } catch (e: TelegramApiRequestException) {
            if (e.apiResponse == "Forbidden: bot was blocked by the user") {
                //                UserRepository.getInstance().deleteByChatId(Long.valueOf(sm.getChatId()));
//                ADSRepository.getInstance().deleteRecodsByCharId(java.lang.Long.valueOf(sm.getChatId()))
//                SearchRepository.getInstance().deleteRecodsByCharId(java.lang.Long.valueOf(sm.getChatId()))
            } else {
            }
        } catch (e: TelegramApiException) {
            e.printStackTrace()
        }
    }

    internal constructor(botToken: String, botUsername: String, options: DefaultBotOptions) : super(botToken, botUsername, options) {
        init()
    }

    internal constructor(botToken: String, botUsername: String) : super(botToken, botUsername) {
        init()
    }

    private fun init() {
//        TaskExecutor.getInstance()
//                .addTask(SearchTask(sendNewMessage))
//                .addTask(DeleteOldAdsTask())
//                .start()
    }

    override fun onUpdateReceived(update: Update) {
        super.onUpdateReceived(update)

        val message = update.message

        if (message == null || message.text == null) {
            return
        }

        val chatId = message.chatId

        (userContextMap as java.util.Map<Long, UserContext>).computeIfAbsent(chatId) { k -> UserContext(chatId, sendNewMessage) }
                .onMessageReceived(message)
    }

    override fun creatorId(): Int {
        return 0
    }
}

