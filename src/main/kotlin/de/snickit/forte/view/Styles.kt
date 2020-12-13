package de.snickit.forte.view

import de.jensd.fx.glyphs.materialicons.MaterialIcon
import de.jensd.fx.glyphs.materialicons.MaterialIconView
import javafx.geometry.Pos
import javafx.scene.paint.Color
import tornadofx.*

class Styles : Stylesheet() {
    companion object {

        val startStopButton by cssclass()
        val addButton by cssclass()
        val editButton by cssclass()
        val removeButton by cssclass()
        val taskViewElement by cssclass()
        val strikethrough by cssclass()
        val itemRoot by cssclass()
        val closeIcon by cssclass()
        val contentLabel by cssid()
        val title by cssid()
        val addItemRoot by cssclass()
        val mainCheckBox by cssclass()
        val header by cssclass()
        val footer by cssclass()
        val largeRectPicker by cssclass()

        fun removeIcon() = MaterialIconView(MaterialIcon.REMOVE_CIRCLE_OUTLINE).apply {
            glyphSize = 12
            addClass(removeButton)
        }

        fun addIcon() = MaterialIconView(MaterialIcon.ADD_CIRCLE_OUTLINE).apply {
            glyphSize = 40
        }

        fun editIcon() = MaterialIconView(MaterialIcon.EDIT).apply {
            glyphSize = 12
            addClass(editButton)
        }

        fun getStartButton() = MaterialIconView(MaterialIcon.PLAY_CIRCLE_OUTLINE).apply {
            glyphSize = 20
        }

        fun getStopButton() = MaterialIconView(MaterialIcon.STOP).apply {
            glyphSize = 20
        }

    }

    init {
        strikethrough {
            Stylesheet.text {
                strikethrough = true
            }
        }

        itemRoot {
            padding = box(8.px)
            Stylesheet.button {
                backgroundColor += c("transparent")
                padding = box(-2.px)
            }
            alignment = Pos.CENTER_LEFT
        }

        contentLabel {
            fontSize = 1.2.em
        }

        title {
            fontSize = 3.em
            textFill = c(175, 47, 47, 0.5)
        }

        addItemRoot {
            padding = box(1.em)
            Stylesheet.textField {
                prefWidth = 200.px
            }
        }

        mainCheckBox {
            padding = box(0.1.em, 1.em, 0.1.em, 0.1.em)
        }

        header {
            alignment = Pos.CENTER
            Stylesheet.star {
                alignment = Pos.CENTER_LEFT
            }
        }

        footer {
            padding = box(10.px)
            alignment = Pos.CENTER
            spacing = 20.px
            Stylesheet.star {
                spacing = 10.px
            }
        }

        taskViewElement {
            borderRadius = multi(box(20.px))
            prefHeight = 90.px
            prefWidth = 130.px
        }

        largeRectPicker {
            colorLabelVisible = false
            maxWidth = Dimension(30.0, Dimension.LinearUnits.px)
            maxHeight = Dimension(20.0, Dimension.LinearUnits.px)
            fontSize = Dimension(0.0, Dimension.LinearUnits.px)
            colorRect {
                maxWidth = Dimension(5.0, Dimension.LinearUnits.px)
                maxHeight = Dimension(5.0, Dimension.LinearUnits.px)
            }
            backgroundColor = multi(Color.TRANSPARENT)
        }

        removeButton {
            backgroundColor = multi(Color.TRANSPARENT)
            fill = Color.RED
            and(hover) {
                fill = Color.DARKRED
            }
        }

        startStopButton {
            alignment = Pos.CENTER
            backgroundColor = multi(Color.TRANSPARENT)
        }

        editButton {
            backgroundColor = multi(Color.TRANSPARENT)
            fill = Color.GREEN
            and(hover) {
                fill = Color.DARKGREEN
            }
        }

        addButton {
            backgroundColor = multi(Color.TRANSPARENT)
        }
    }
}