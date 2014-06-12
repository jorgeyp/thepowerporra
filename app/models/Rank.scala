package models

import anorm.SqlParser._
import anorm._
import play.api.db.DB
import anorm.~
import play.api.Play.current

/**
 * Created by 71772901 on 12/06/2014.
 */
case class Rank(username: String, matchesPoints: Double, classificationPoints: Double, playerPoints: Double, totalPoints: Double)

object Rank {

  val simple = {
    get[String]("v_puntos.descUsuario") ~
      get[Double]("v_puntos.matchesPoints") ~
      get[Double]("v_puntos.classificationPoints") ~
      get[Double]("v_puntos.playerPoints") ~
      get[Double]("v_puntos.puntos") map {
      case username~matchesPoints~classificationPoints~playerPoints~totalPoints => Rank(username, matchesPoints, classificationPoints, playerPoints, totalPoints)
    }
  }

  def findAll: Seq[Rank] = {
    DB.withConnection { implicit connection =>
      SQL("select * from v_puntos").as(Rank.simple *)
    }
  }
}
