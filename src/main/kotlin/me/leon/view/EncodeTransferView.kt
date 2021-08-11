package me.leon.view

import javafx.beans.property.SimpleBooleanProperty
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.control.RadioButton
import javafx.scene.control.TextArea
import javafx.scene.input.DragEvent
import me.leon.controller.EncodeController
import me.leon.ext.EncodeType
import me.leon.ext.copy
import me.leon.ext.encodeType
import tornadofx.*

class EncodeTransferView : View("编码转换") {
    private val controller: EncodeController by inject()
    override val closeable = SimpleBooleanProperty(false)
    private lateinit var input: TextArea
    private lateinit var output: TextArea
    private lateinit var infoLabel: Label
    private val info: String
        get() =
            " $srcEncodeType --> $dstEncodeType  输入长度: ${inputText.length}  输出长度: ${outputText.length}"
    private val inputText: String
        get() = input.text.takeIf { isEncode } ?: input.text.replace("\\s".toRegex(), "")
    private val outputText: String
        get() = output.text

    private var dstEncodeType = EncodeType.Base64
    private var srcEncodeType = EncodeType.Base64
    private var isEncode = true

    private val eventHandler =
        EventHandler<DragEvent> {
            println("${it.dragboard.hasFiles()}______" + it.eventType)
            if (it.eventType.name == "DRAG_ENTERED") {
                if (it.dragboard.hasFiles()) {
                    println(it.dragboard.files)
                    input.text = it.dragboard.files.first().readText()
                }
            }
        }
    override val root = vbox {
        paddingAll = 8
        label("待处理:") { paddingAll = 8 }
        hbox {
            paddingAll = 8
            alignment = Pos.BASELINE_CENTER
            togglegroup {
                spacing = 8.0
                radiobutton("base64") {
                    isSelected = true
                }
                radiobutton("urlEncode")
                radiobutton("base32")
                radiobutton("base16")
                radiobutton("unicode")
                radiobutton("hex")
                radiobutton("binary")
                radiobutton("base64 safe")
                selectedToggleProperty().addListener { _, _, new ->
                    srcEncodeType = (new as RadioButton).text.encodeType()
                }
            }
        }
        input =
            textarea {
                promptText = "请输入内容或者拖动文件到此区域"
                isWrapText = true
                onDragEntered = eventHandler
            }
        hbox {
            paddingAll = 8
            alignment = Pos.BASELINE_CENTER
            togglegroup {
                spacing = 8.0
                radiobutton("base64") {
                    isSelected = true
                }
                radiobutton("urlEncode")
                radiobutton("base32")
                radiobutton("base16")
                radiobutton("unicode")
                radiobutton("hex")
                radiobutton("binary")
                radiobutton("base64 safe")
                selectedToggleProperty().addListener { _, _, new ->
                    dstEncodeType = (new as RadioButton).text.encodeType()
                    run()
                }
            }
        }
        hbox {
            alignment = Pos.BASELINE_CENTER
            paddingAll = 8.0f
            button("转换") {
                action { run() } }
            button("上移") {
                action {
                    input.text = outputText
                    output.text = ""
                }
            }
            button("复制结果") {
                action { outputText.copy() } }
        }
        label("输出内容:") { paddingBottom = 8 }
        output =
            textarea {
                promptText = "结果"
                isWrapText = true
            }

        infoLabel = label { paddingTop = 8 }
    }

    private fun run() {
        output.text = controller.encode(controller.decode(inputText, srcEncodeType),dstEncodeType)
        infoLabel.text = info
    }
}