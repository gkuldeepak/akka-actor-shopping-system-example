package com.knoldus.services

import akka.actor.{Actor, ActorRef}
import com.knoldus.models.{CollectOrder, OrderStatus, OrderStatusResponse, ProductAndQuantity}

class OrderCollector extends Actor{

  var waitingForOrder : Set[ActorRef] = Set()
  var currentOrders : Map[String, Int] = Map()

  override def receive: Receive = {
    case CollectOrder(customers) =>
      waitingForOrder = customers
      customers.foreach(customerRef => customerRef ! OrderStatus)
    case OrderStatusResponse(None) =>
      sender() ! OrderStatusResponse
    case OrderStatusResponse(Some(customer)) =>
      val stillWaitingForOrders = waitingForOrder - sender()
      customer.map{
        orders: ProductAndQuantity =>
          orders.productAndQuantity.foreach {
            order =>
              val currentProductName = order._1
              val existingProductQuantity = currentOrders.getOrElse(currentProductName, 0)
              val currentProductQuantity = order._2
              currentOrders = currentOrders + (currentProductName -> (existingProductQuantity + currentProductQuantity))
          }
      }
      if(stillWaitingForOrders.isEmpty){
        println(s"[Info] Current Orders : $currentOrders ")
      }
      else {
        waitingForOrder = stillWaitingForOrders
      }
  }

}
