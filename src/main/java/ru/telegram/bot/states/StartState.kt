package ru.telegram.bot.states

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message
import ru.telegram.bot.UserContext
import java.util.function.BiConsumer

class StartState(userContext: UserContext, chatId: Long?, send: ((SendMessage, Boolean) -> Unit)) :
        UserState(userContext, chatId, send) {

    val buttons = listOf("Выбрать/изменить город", "Вторая кнопка", "Еще кнопка", "Круто?")

    override fun onMessageReceived(message: Message) {
        val messageText = message.text
        when (messageText) {
            "Выбрать/изменить город" -> userContext.changeState(ChooseCityState(userContext, chatId, send))
            else -> sendMessage("Выберите другой вариант", true)
        }

    }



    override fun showFirst() {
        showInlineKeyboard(buttons, "", false)
    }
}
