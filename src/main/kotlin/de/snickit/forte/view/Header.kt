package de.snickit.forte.view

import de.snickit.forte.controller.ForteController
import javafx.beans.property.StringProperty
import tornadofx.*

class Header : View() {
    private val controller: ForteController by inject()
    private val activeTask = stringProperty(controller.getActiveTask()?.name ?: "Kein aktiver Task")

    override val root = vbox {
        addClass(Styles.header)
        label("todos").setId(Styles.title)
        hbox {
            addClass(Styles.addItemRoot)
            label(activeTask)
            /*checkbox {
                addClass(Styles.mainCheckBox)
                visibleWhen { booleanBinding(store.todos) { isNotEmpty() } }
                action { store.toggleCompleted(isSelected) }
                activeTask.onChange { isSelected = it }
            }*/
            textfield {
                promptText = "What needs to be done?"
                action {
                    controller.addTask(text)
                    clear()
                }
            }
        }
    }
}