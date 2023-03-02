package com.knoldus.models

import akka.actor.ActorRef

case class ProductAndQuantity(productAndQuantity : Map[String, Int])

case class Order(orderList : List[ProductAndQuantity])

case class OrderRequest()

case object OrderStatus

case class CollectOrder(buyers : Set[ActorRef])

case class OrderStatusResponse(orders : Option[List[ProductAndQuantity]])
