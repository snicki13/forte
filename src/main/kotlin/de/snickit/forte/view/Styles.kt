package de.snickit.forte.view

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.geometry.Insets
import javafx.geometry.Pos
import tornadofx.*

class Styles : Stylesheet() {
    companion object {

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

        fun closeIcon() = FontAwesomeIconView(FontAwesomeIcon.CLOSE).apply {
            glyphSize = 22
            addClass(closeIcon)
        }

    }

    init {
        strikethrough {
            Stylesheet.text {
                strikethrough = true
            }
        }

        closeIcon {
            fill = c("#cc9a9a")

            and(hover) {
                fill = c("#af5b5e")
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
            alignment = Pos.CENTER_RIGHT
            colorLabelVisible = false
            maxWidth = Dimension(30.0, Dimension.LinearUnits.px)
            maxHeight = Dimension(20.0, Dimension.LinearUnits.px)
            fontSize = Dimension(0.0, Dimension.LinearUnits.px)
            colorRect {
                maxWidth = Dimension(5.0, Dimension.LinearUnits.px)
                maxHeight = Dimension(5.0, Dimension.LinearUnits.px)

            }
        }
    }
}