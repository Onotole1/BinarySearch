package view

import javax.swing.{JFrame, JOptionPane}

import controller.TreeOfIntegers

import scala.swing.GridBagPanel.Fill
import scala.swing.{Action, Alignment, BorderPanel, Button, Dimension, GridBagPanel, Insets, Label, ListView, MainFrame, ScrollPane, TextArea, TextField}

/**
  * Date: 25.06.2017
  * Time: 1:48
  *
  * @author Anatoliy
  */
class MainForm {
  private val TITLE_NAME = "Лабораторная работа №1"
  private val INPUT_TEXT_FIELD_LABEL_TEXT = "Введите значения через запятую"

  private val TITLE_ERROR_DIALOG = "Ошибка"
  private val TEXT_ERROR_DIALOG = "Вы ввели пустую строку"

  private val TEXT_ACTION_BUTTON = "Выполнить сортировку"

  private val EMPTY_STRING = ""
  private val SPACE = " "
  private val COMMA_SEP = ","

  private val RESULT_PANEL_TITLE = "Результаты"

  private val LOG_PANEL_TITLE = "Ход выполнения"

  private var treeOfIntegers = new TreeOfIntegers()

  val mainFrame = new MainFrame {
    title = TITLE_NAME

    val outputListView = new ListView[String]()
    val resultTextArea = new TextArea() {
      editable = false
    }
    val inputField = new TextField(20)
    val inputPanel = new GridBagPanel {
      val c = new Constraints

      preferredSize = new Dimension(300, 90)
      minimumSize = preferredSize
      maximumSize = preferredSize

      val inputTextFieldLabel = new Label(INPUT_TEXT_FIELD_LABEL_TEXT)
      inputTextFieldLabel.horizontalTextPosition = Alignment.Center
      c.fill = Fill.Horizontal
      c.gridx = 0
      c.gridy = 0
      layout(inputTextFieldLabel) = c


      c.fill = Fill.Horizontal
      c.insets = new Insets(5, 0, 0, 0)
      c.gridx = 0
      c.gridy = 1
      c.ipady = 10
      layout(inputField) = c

      val button = new Button(Action(TEXT_ACTION_BUTTON) {
        outputListView.listData = List()
        resultTextArea.text = EMPTY_STRING
        if (inputField.text.replace(SPACE, EMPTY_STRING) == EMPTY_STRING) {
          JOptionPane.showMessageDialog(new JFrame(), TEXT_ERROR_DIALOG, TITLE_ERROR_DIALOG, JOptionPane.ERROR_MESSAGE)
        }
        else {
          val inputTextString = inputField.text.replaceAll(SPACE, EMPTY_STRING)
          val inputTextArray = inputTextString.split(COMMA_SEP).array

          val numbersFromInput: Array[Integer] = Array.ofDim[Integer](inputTextArray.length)
          for (i <- inputTextArray.indices) {
            numbersFromInput(i) = java.lang.Integer.parseInt(inputTextArray(i))
          }

          treeOfIntegers.insert(numbersFromInput)
          val integersResult = treeOfIntegers.getSortedArray

          val strings: Array[String] = Array.ofDim[String](integersResult.length)
          for (i <- strings.indices) {
            resultTextArea.text += String.valueOf(integersResult(i))
            if (i != strings.length - 1) {
              resultTextArea.text += "\n"
            }
          }

          val logs = treeOfIntegers.getLogs
          outputListView.listData_=(logs)

          treeOfIntegers = new TreeOfIntegers()
        }
      })

      c.fill = Fill.Horizontal
      c.insets = new Insets(5, 0, 0, 0)
      c.gridx = 0
      c.gridy = 2
      c.ipady = 0
      layout(button) = c
    }

    val resultPanel = new BorderPanel {
      preferredSize = new Dimension(300, 95)
      minimumSize = preferredSize

      val textLabel = new Label(RESULT_PANEL_TITLE)
      textLabel.horizontalTextPosition = Alignment.Center
      layout(textLabel) = BorderPanel.Position.North

      val scrollPane = new ScrollPane(resultTextArea)
      layout(scrollPane) = BorderPanel.Position.Center
    }

    val outputPanel = new BorderPanel {
      preferredSize = new Dimension(270, 180)
      minimumSize = preferredSize
      val textLabelLog = new Label(LOG_PANEL_TITLE)
      textLabelLog.horizontalTextPosition = Alignment.Center
      layout(textLabelLog) = BorderPanel.Position.North

      val scroll = new ScrollPane(outputListView)
      layout(scroll)= BorderPanel.Position.Center
    }
    contents = new GridBagPanel {
      val c = new Constraints

      c.fill = Fill.Horizontal
      c.gridx = 0
      c.gridy = 0
      layout(inputPanel) = c

      c.fill = Fill.Horizontal
      c.insets = new Insets(5, 0, 0, 0)
      c.gridx = 0
      c.gridy = 2
      c.gridwidth = 1
      layout(resultPanel) = c

      c.fill = Fill.Horizontal
      c.insets = new Insets(5, 0, 0, 0)
      c.gridx = 0
      c.gridy = 1
      c.gridwidth = 1
      layout(outputPanel) = c
    }
    size = new Dimension(350, 450)
    centerOnScreen
  }

  def showMainFrame(): Unit = {
    mainFrame.visible = true
  }
}
