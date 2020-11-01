package de.snickit.forte

import javafx.application.Application
import tornadofx.App
import tornadofx.InternalWindow

class CustomerApp : App(CustomerForm::class, InternalWindow.Styles::class)

fun main(args: Array<String>) {
    Application.launch(CustomerApp::class.java, *args)
}
