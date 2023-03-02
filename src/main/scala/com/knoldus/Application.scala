package com.knoldus

import akka.actor.{ActorSystem, Props}
import com.knoldus.models.{CollectOrder, Order, ProductAndQuantity}
import com.knoldus.services.{Buyer, OrderCollector}

object Application extends App {

  val actorSystem = ActorSystem("my-actor")
  val buyer1 = actorSystem.actorOf(Props[Buyer])
  val buyer2 = actorSystem.actorOf(Props[Buyer])
  buyer1 ! Order(List(ProductAndQuantity(Map("Chocolates" -> 2)), ProductAndQuantity(Map("Toffees" -> 5))))
  buyer2 ! Order(List(ProductAndQuantity(Map("Chocolates" -> 5)), ProductAndQuantity(Map("Muffins" -> 3))))

  val orderCollector = actorSystem.actorOf(Props[OrderCollector])
  orderCollector ! CollectOrder(Set(buyer1, buyer2))
}
