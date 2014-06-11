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
object Matches extends Controller with Secured {
  /**
   * Display the dashboard.
   */
  def index = IsAuthenticated {
    email => implicit request =>
      User.findByEmail(email).map { user =>
        Ok(html.matches(Match.findAll, user))
      }.getOrElse(Forbidden)
  }

  def bet(idTeam1: Int, idTeam2: Int, g1: Int, g2: Int) = IsAuthenticated {email => implicit request =>
    // User.createMatchBet(idTeam1, idTeam2, g1, g2, email)
    // Ok
    Forbidden
  }

  /**
   * Display the dashboard.
   */
  def admin = IsAdmin { email => implicit request =>
      Ok(html.administration.matchesControl(Match.findAll))
  }

  def set(idTeam1: Int, idTeam2: Int, g1: Int, g2: Int) = IsAdmin { email => implicit request =>
    Match.set(idTeam1: Int, idTeam2: Int, g1: Int, g2: Int)
    Ok
  }
}
