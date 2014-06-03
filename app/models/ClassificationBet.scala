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

/*  TODO: add new Field
 case class ClassificationBet(idUsuario: Int, idRound: Int, idTeam: Int, descTeam: String)
*/
case class ClassificationBet(idUsuario: Int, idRound: Int, idTeam: Int)

object ClassificationBet {
  // -- Parsers

  /**
   * Parse a ClassificationBet from a ResultSet.
   * 
   * TODO: added new field
  val simple = {
    get[Int]("clasificado_apuesta.idusuario") ~
      get[Int]("clasificado_apuesta.idRonda") ~
      get[Int]("clasificado_apuesta.idEquipo") ~
      get[String]("equipo.descEquipo") map {
      case idUser~idRound~idTeam~descTeam=> ClassificationBet(idUser, idRound, idTeam, descTeam)
    }
  }
   */
  val simple = {
    get[Int]("clasificado_apuesta.idusuario") ~
      get[Int]("clasificado_apuesta.idRonda") ~
      get[Int]("clasificado_apuesta.idEquipo") map {
      case idUser~idRound~idTeam=> ClassificationBet(idUser, idRound, idTeam)
    }
  }

  /**
   * Retrieve all ClassificationBet.
   */
  def findAll(email: String): Seq[ClassificationBet] = {

    val user = User.userId(email)

    /*
    TODO: Retrieve country name:
      SELECT clasificado_apuesta.* , equipo.descEquipo
      FROM clasificado_apuesta, equipo
      WHERE clasificado_apuesta.idEquipo = equipo.idEquipo and clasificado_apuesta.idUsuario={user} 
    */
    DB.withConnection { implicit connection =>
      SQL(
        "select * from clasificado_apuesta where idUsuario={user}"
      ).on(
          'user -> user
        ).as(ClassificationBet.simple *)
    }
  }

}
