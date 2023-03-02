package com.knoldus.services

import akka.actor.Actor
import com.knoldus.models.{Order, OrderStatus, OrderStatusResponse, ProductAndQuantity}

class Buyer extends Actor{

  var buyer : Option[List[ProductAndQuantity]] = None

  override def receive: Receive = {
    case Order(name) => buyer = Some(name)
    case OrderStatus => sender() ! OrderStatusResponse(buyer)
  }

}
