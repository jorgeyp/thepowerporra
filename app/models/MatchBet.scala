package models

import org.joda.time.LocalDate
import anorm.SqlParser._
import anorm._
import utils.AnormExtension._
import play.api.db.DB
import anorm.~
import play.api.Play.current

/**
 * Created by 71772901 on 12/05/2014.
 */
case class MatchBet(idUsuario: Int, idTeam1: Int, idTeam2: Int, goalsTeam1: Int, goalsTeam2: Int)

object MatchBet {
  // -- Parsers

  /**
   * Parse a Match from a ResultSet. Goals will be -1 until the match is played.
   */
  val simple = {
    get[Int]("partido_apuesta.idusuario") ~
      get[Int]("partido_apuesta.idEquipo1") ~
      get[Int]("partido_apuesta.idEquipo2") ~
      get[Option[Int]]("partido_apuesta.golesEquipo1") ~
      get[Option[Int]]("partido_apuesta.golesEquipo2") map {
      case idUser~idTeam1~idTeam2~goalsTeam1~goalsTeam2=> MatchBet(idUser, idTeam1, idTeam2, goalsTeam1.getOrElse(-1), goalsTeam2.getOrElse(-1))
    }
  }

  /**
   * Retrieve all matches.
   */
  def findAll(email: String): Seq[MatchBet] = {

    val user = User.userId(email)

    DB.withConnection { implicit connection =>
      SQL(
        "select * from partido_apuesta where idUsuario={user}"
      ).on(
        'user -> user
      ).as(MatchBet.simple *)
    }
  }

}
