package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import anorm._

import models._
import views._

/**
 * Created by 71772901 on 07/05/2014.
 */
object Players extends Controller with Secured {


  /**
   * Display the dashboard.
   */
  def index = IsAuthenticated {
    _ => _ =>
      Ok(html.players(Player.findAll))
  }

//  def bet = IsAuthenticated { email => implicit request =>
//    Form("playerId" -> number).bindFromRequest.fold(
//      errors => BadRequest,
//      playerId => {
//        User.createBet(playerId, email)
//        Ok
//      }
//    )
//  }

  def bet(id: Int) = IsAuthenticated { email => implicit request =>
    User.createPlayerBet(id, email)
    Ok
  }
}