package ru.telegram.bot

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message
import ru.telegram.bot.states.StartState
import ru.telegram.bot.states.UserState
import java.util.function.BiConsumer

class UserContext(private val chatId: Long?,
                  _sendMessage: (SendMessage) -> Unit) {
    private var lastMessageId: Int? = null

    private var sendMessage: ((SendMessage, Boolean) -> Unit)

    private lateinit var userState: UserState

    var searchId: Long? = null

    init {
        sendMessage = {sm, reply ->
            if (reply) {
                sm.replyToMessageId = lastMessageId
            }
            _sendMessage(sm)
        }
        changeState(StartState(this, chatId, sendMessage))
    }


    fun changeState(newUserState: UserState) {
        this.userState = newUserState
        userState.showFirst()
         System.out.println("Класс: $newUserState")
    }

    fun onMessageReceived(message: Message) {
        //  Тоже не очень решение, но лучше чем, если случится
        //  коллизия и будет делаться реплай на сообщение другого пользователя
        lastMessageId = message.messageId
        userState.onMessageReceived(message)
    }
}
