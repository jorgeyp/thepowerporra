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
    _ => _ =>
      Ok(html.matches())
  }


}
