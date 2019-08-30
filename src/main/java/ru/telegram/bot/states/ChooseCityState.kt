package ru.telegram.bot.states

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message
import ru.telegram.bot.UserContext

class ChooseCityState(userContext: UserContext, chatId: Long?, send: ((SendMessage, Boolean) -> Unit)) :
        UserState(userContext, chatId, send) {


    override fun onMessageReceived(message: Message) {
        val messageText = message.text
        when (messageText.toLowerCase()) {
            "назад" -> userContext.changeState(StartState(userContext, chatId, send))
            else -> sendMessage("Вы ввели: $messageText", true)
        }
    }

    override fun showFirst() {
        showButtons(listOf("Назад"), "Введите название города", false)
    }

}