package models

import anorm.SqlParser._
import anorm._
import play.api.db.DB
import anorm.~
import play.api.Play.current


/**
 * Created by 71772901 on 12/05/2014.
 */
case class Player(id: Int, description: String, idTeam: Int, confirmed: Boolean)

object Player {
  // -- Parsers

  /**
   * Parse a Player from a ResultSet
   */
  val simple = {
    get[Int]("jugador.idJugador") ~
      get[String]("jugador.descJugador") ~
      get[Int]("jugador.idEquipo") ~
      get[Boolean]("jugador.confirmado") map {
      case id~description~idTeam~confirmed=> Player(id, description, idTeam, confirmed)
    }
  }

  /**
   * Retrieve all players.
   */
  def findAll: Seq[Player] = {
    DB.withConnection { implicit connection =>
      SQL("select * from jugador").as(Player.simple *)
    }
  }

  def playerId(name: String): Int = {
    DB.withConnection { implicit connection =>
      SQL(
        """select idJugador from jugador where descJugador={name} order by idJugador limit 1"""
      ).on(
          'name -> name
        ).as(scalar[Int].single)
    }
  }
  /**
   * Create a Player.
   */
  def create(name: String, team: Int) = {
    DB.withConnection { implicit connection =>
      SQL(
        """
          insert into jugador (descJugador, idEquipo) values (
            {name}, {team}
          )
        """
      ).on(
          'name -> name,
          'team -> team
        ).executeUpdate()
    }
  }


}

