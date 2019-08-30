package ru.telegram.bot.states

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import ru.telegram.bot.UserContext

import java.util.ArrayList
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup





abstract class UserState protected constructor(
        protected val userContext: UserContext,
        protected val chatId: Long?,
        protected var send: ((SendMessage, Boolean) -> Unit)) {


    abstract fun onMessageReceived(message: Message)

    abstract fun showFirst()


    protected fun sendMessage(text: String, withReply: Boolean) {
        val sm = SendMessage()
        sm.setChatId(chatId!!)
        sm.text = text
        send(sm, withReply)
    }

    protected fun showButtons(buttonNames: List<String>, comment: String, withReply: Boolean) {
        val replyKeyboardMarkup = ReplyKeyboardMarkup()

        replyKeyboardMarkup.selective = true
        replyKeyboardMarkup.resizeKeyboard = true
        replyKeyboardMarkup.oneTimeKeyboard = false

        val keyboardRow = ArrayList<KeyboardRow>()
        for (button in buttonNames) {
            val row = KeyboardRow()
            row.add(KeyboardButton(button))
            keyboardRow.add(row)
        }
        replyKeyboardMarkup.keyboard = keyboardRow

        val sm = SendMessage()
        sm.setChatId(chatId)
        sm.replyMarkup = replyKeyboardMarkup
        sm.text = comment

        send(sm, withReply)
    }

    protected fun showInlineKeyboard(buttonNames: List<String>, comment: String, withReply: Boolean) {
        val replyKeyboardMarkup = ReplyKeyboardMarkup()

//        val inlineKeyboardButton = InlineKeyboardButton()
//        inlineKeyboardButton.text = "Тык"
//        inlineKeyboardButton.callbackData = "Button \"Тык\" has been pressed"
//
//        val inlineKeyboardMarkup = InlineKeyboardMarkup()
//
//
//        val inlineKeyboardButton1 = InlineKeyboardButton()
//        val inlineKeyboardButton2 = InlineKeyboardButton()
//        inlineKeyboardButton1.text = "Тык"
//        inlineKeyboardButton1.callbackData = "Button \"Тык\" has been pressed"
//        inlineKeyboardButton2.text = "Тык2"
//        inlineKeyboardButton2.callbackData = "Button \"Тык2\" has been pressed"
//        val keyboardButtonsRow1 = ArrayList<InlineKeyboardButton>()
//        val keyboardButtonsRow2 = ArrayList<InlineKeyboardButton>()
//        keyboardButtonsRow1.add(inlineKeyboardButton1)
//        keyboardButtonsRow1.add(InlineKeyboardButton().setText("Fi4a").setCallbackData("CallFi4a"))
//        keyboardButtonsRow2.add(inlineKeyboardButton2)
//
//
//        val rowList = ArrayList<List<InlineKeyboardButton>>()
//        rowList.add(keyboardButtonsRow1)
//        rowList.add(keyboardButtonsRow2)


//        inlineKeyboardMarkup.keyboard = rowList

//        val myKeyboardButtonsRow1 = ArrayList<InlineKeyboardButton>()
//        myKeyboardButtonsRow1.add(InlineKeyboardButton().setText("Fi4a").setCallbackData("CallFi4a"))
//        val myRowList = ArrayList<List<InlineKeyboardButton>>()
//        val myInlineKeyboardMarkup = InlineKeyboardMarkup()
//        myInlineKeyboardMarkup.keyboard = myRowList






        replyKeyboardMarkup.selective = true
        replyKeyboardMarkup.resizeKeyboard = true
        replyKeyboardMarkup.oneTimeKeyboard = false

        val myInlineKeyboardMarkup = InlineKeyboardMarkup()
        val myRowList = ArrayList<List<InlineKeyboardButton>>()


//        val keyboardRow = ArrayList<KeyboardRow>()
        for (button in buttonNames) {
            val myKeyboardButtonsRow = ArrayList<InlineKeyboardButton>()
            myKeyboardButtonsRow.add(InlineKeyboardButton().setText(button).setCallbackData(button))
            myRowList.add(myKeyboardButtonsRow)
        }
        myInlineKeyboardMarkup.keyboard = myRowList
//        replyKeyboardMarkup.keyboard = keyboardRow

        val sm = SendMessage()
        sm.setChatId(chatId!!)
        sm.replyMarkup = myInlineKeyboardMarkup
        sm.text = comment

        send(sm, withReply)
    }
}
