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
case class Match(idTeam1: Int, idTeam2: Int, goalsTeam1: Int, goalsTeam2: Int, date: LocalDate)

object Match {
  // -- Parsers

  /**
   * Parse a Match from a ResultSet. Goals will be -1 until the match is played.
   */
  val simple = {
    get[Int]("partido.idEquipo1") ~
      get[Int]("partido.idEquipo2") ~
      get[Option[Int]]("partido.golesEquipo1") ~
      get[Option[Int]]("partido.golesEquipo2") ~
      get[LocalDate]("partido.fechaJornada") map {
      case idTeam1~idTeam2~goalsTeam1~goalsTeam2~date=> Match(idTeam1, idTeam2, goalsTeam1.getOrElse(-1), goalsTeam2.getOrElse(-1), date)
    }
  }

  /**
   * Retrieve all matches.
   */
  def findAll: Seq[Match] = {
    DB.withConnection { implicit connection =>
      SQL("select * from partido").as(Match.simple *)
    }
  }

  /**
   * Update a Match.
   */
  def set(idTeam1: Int, idTeam2: Int, g1: Int, g2: Int) = {
    DB.withConnection { implicit connection =>
      SQL(
        """
          update partido set golesEquipo1={g1}, golesEquipo2={g2} where idEquipo1={idTeam1} and idEquipo2={idTeam2}
        """
      ).on(
          'g1 -> g1,
          'g2 -> g2,
          'idTeam1 -> idTeam1,
          'idTeam2 -> idTeam2
        ).executeUpdate()
    }
  }

}
