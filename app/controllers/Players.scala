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

//  val playerForm = Form(
//    mapping(
//      "name" -> text,
//      "team" -> text
//    )
//    {(name, team, _) => Player(name, Team.findIdByName(name))}
//  )

//  def index = IsAuthenticated { username => _ =>
//    User.findByEmail(username).map { user =>
//      Ok(
//        html.perfil(user)
//      )
//    }.getOrElse(Forbidden)
//  }

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

  def submit(name: String, team: Int) = IsAuthenticated {email => implicit request =>
    Player.create(name, team, 0)
    User.createPlayerBet(Player.playerId(name), email)
    Ok
  }

  def admin = IsAdmin { email => implicit request =>
    Ok(html.administration.playersControl(Player.findAll))
  }
}