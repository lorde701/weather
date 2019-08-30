package ru.telegram.bot

object AppTelegramBot {
    private val BOT_NAME_PROP_KEY = "BOT_NAME"
    private val BOT_TOKEN_PROP_KEY = "BOT_TOKEN"

    private val USE_PROXY_PROP_KEY = "USE_PROXY" // true/false
    private val PROXY_HOST_PROP_KEY = "PROXY_HOST"
    private val PROXY_PORT_PROP_KEY = "PROXY_PORT"
    private val PROXY_USER_PROP_KEY = "PROXY_USER"
    private val PROXY_PASSWORD_PROP_KEY = "PROXY_PASSWORD"

    //    private static final Logger logger = LogManager.getLogger(AppTelegramBot.class.getName());

    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {

        //        logger.debug("Debug Message Logged !!!");
        //        logger.info("Info Message Logged !!!");
        //        logger.warn("Warn Message Logger !!!");
        //        logger.error("Error Message Logged !!!", new NullPointerException("NullError"));

//        ConfigInit.init()
        setup()
    }

    @Throws(Exception::class)
    fun setup() {
        setupBot()
    }

    @Throws(Exception::class)
    private fun setupBot() {
        val botName = System.getProperty(BOT_NAME_PROP_KEY)
        val botToken = System.getProperty(BOT_TOKEN_PROP_KEY)

        if (botName == null || botToken == null) {
            throw Exception("Not input bot name or token")
        }

        var isUseProxy = false

        try {
            isUseProxy = java.lang.Boolean.valueOf(System.getProperty(USE_PROXY_PROP_KEY))
        } catch (ignore: Exception) {
        }

        if (isUseProxy) {
            val proxyHost = System.getProperty(PROXY_HOST_PROP_KEY)
            val proxyPort = System.getProperty(PROXY_PORT_PROP_KEY)
            val proxyUser = System.getProperty(PROXY_USER_PROP_KEY)
            val proxyPassword = System.getProperty(PROXY_PASSWORD_PROP_KEY)

            if (proxyHost == null || proxyPort == null || proxyUser == null || proxyPassword == null) {
                throw Exception("Not input proxyHost, proxyPort, proxyUser or proxyPassword")
            }

            val proxyPortNumber: Int
            try {
                proxyPortNumber = Integer.parseInt(proxyPort)
            } catch (e: NumberFormatException) {
                throw Exception("Proxy port should be number")
            }

            TelegramBotInitialization.init(botName, botToken, proxyHost, proxyPortNumber, proxyUser, proxyPassword)

        } else {
            TelegramBotInitialization.init(botName, botToken)
        }
    }

}
