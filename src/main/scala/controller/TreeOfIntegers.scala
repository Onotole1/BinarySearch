package controller

import java.util

import controller.TreeOfIntegers._
import model.Node

object TreeOfIntegers {

  private val INIT_ROOT: String = "Init root node with value: %d\n"
  private val INIT_LEFT_NODE: String = "Init left node with value: %d Parent value: %d\n"
  private val INIT_RIGHT_NODE: String = "Init right node with value: %d Parent value: %d\n"

  private val RECURSIVE_SORT_START: String = "Start sorting...\n"
  private val RECURSIVE_SORT: String = "Current node has value: %d\n"
  private val RECURSIVE_SORT_ADD: String = "Add value: %d\n"
  private val RECURSIVE_SORT_ITERATIONS: String = "Iterations count: "
}

/**
  * Date: 24.06.2017
  * Time: 20:42
  *
  * @author Anatoliy
  */
class TreeOfIntegers {

  private val logs: util.ArrayList[String] = new util.ArrayList[String]()

  private var root: Node[Integer] = _

  private var size: Int = 0

  private var iterations: Int = 0

  def insert(values: Array[Integer]): Unit = {
    for (value <- values) {
      if (null == root) {
        root = new Node[Integer](value, null)
        logs.add(String.format(INIT_ROOT, value))
        size += 1
      } else {
        var break = false
        while (!break) {
          val rootValue: java.lang.Integer = root.getValue
          if (rootValue.compareTo(value) > 0) {
            if (null == root.getLeftNode) {
              root.setLeftNode(new Node[Integer](value, root))
              logs.add(String.format(INIT_LEFT_NODE, value, rootValue))
              size += 1
              break = true
            } else {
              root = root.getLeftNode
            }
          } else if (rootValue.compareTo(value) < 0) {
            if (null == root.getRightNode) {
              root.setRightNode(new Node[Integer](value, root))
              logs.add(String.format(INIT_RIGHT_NODE, value, rootValue))
              size += 1
              break = true
            } else {
              root = root.getRightNode
            }
          }
        }
      }
      returnRoot()
    }
  }

  def getSortedArray: Array[Integer] = {
    val result: util.ArrayList[Integer] = new util.ArrayList[Integer](size)
    if (size == 0) {
      result.toArray(Array.ofDim[Integer](size))
    }

    logs.add(RECURSIVE_SORT_START)
    traverse(root, result)
    logs.add(RECURSIVE_SORT_ITERATIONS + iterations + "\n")
    result.toArray(Array.ofDim[Integer](size))
  }

  def getLogs: Array[String] = logs.toArray(Array.ofDim[String](logs.size))

  private def traverse(node: Node[Integer], result: util.ArrayList[Integer]): Unit = {
    iterations += 1
    logs.add(String.format(RECURSIVE_SORT, node.getValue))

    val leftNode: Node[Integer] = node.getLeftNode
    if (leftNode != null) {
      traverse(leftNode, result)
    }

    logs.add(String.format(RECURSIVE_SORT_ADD, node.getValue))
    result.add(node.getValue)

    val rightNode: Node[Integer] = node.getRightNode
    if (rightNode != null) traverse(rightNode, result)
  }

  private def returnRoot(): Unit = {
    if (null != root) {
      while (null != root.getParent) root = root.getParent
    }
  }
}