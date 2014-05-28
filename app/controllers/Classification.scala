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
object Classification extends Controller with Secured {
  /**
   * Display the dashboard.
   */
  def index = IsAuthenticated {
    _ => _ =>
      Ok(html.classification())
  }

  def bet(idRound: Int, idTeam: Int) = IsAuthenticated { email => implicit request =>
    User.createClassificationBet(idRound, idTeam, email)
    Ok
  }

  def set = IsAdmin { email => implicit request =>
    Ok(html.administration.classificationControl())
  }

}
