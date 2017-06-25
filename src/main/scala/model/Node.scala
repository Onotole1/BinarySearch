package model

import scala.beans.BeanProperty


/**
  * Date: 24.06.2017
  * Time: 20:45
  *
  * @author Anatoliy
  */
class Node[T](@BeanProperty val value: T, @BeanProperty val parent: Node[T]) {

  @BeanProperty
  var leftNode: Node[T] = _

  @BeanProperty
  var rightNode: Node[T] = _

}