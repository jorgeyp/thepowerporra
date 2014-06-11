package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import anorm._

import models._
import views._
import play.api.libs.json._
import play.api.libs.functional.syntax._

/**
 * Created by 71772901 on 07/05/2014.
 */
object Classifications extends Controller with Secured {

  implicit val classificationBetWrites: Writes[ClassificationBet] = (
    (JsPath \ "user").write[Int] and
    (JsPath \ "round").write[Int] and
    (JsPath \ "team").write[Int] and
      (JsPath \ "teamName").write[String]
    )(unlift(ClassificationBet.unapply))

  /**
   * Display the dashboard.
   */
  def index = IsAuthenticated {
    email => implicit request =>
      Ok(html.classifications())
  }

  def bet(idRound: Int, idTeam: Int) = IsAuthenticated { email => implicit request =>
    User.createClassificationBet(idRound, idTeam, email)
    // Ok
    Forbidden
  }

  def delete(idRound: Int) = IsAuthenticated { email => implicit request =>
    User.deleteClassificationBets(email, idRound)
    // Ok
    Forbidden
  }

  def admin = IsAdmin { email => implicit request =>
    Ok(html.administration.classificationControl())
  }

  def json = IsAuthenticated { email => implicit request =>
    val json = Json.toJson(ClassificationBet.findAll(email))
    Ok(json)
  }
}
