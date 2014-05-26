package models

import anorm.SqlParser._
import anorm._
import play.api.db.DB
import anorm.~
import play.api.Play.current


/**
 * Created by 71772901 on 12/05/2014.
 */
case class Player(id: Int, description: String, idTeam: Int, goals: Int)

object Player {
  // -- Parsers

  /**
   * Parse a Player from a ResultSet
   */
  val simple = {
    get[Int]("jugador.idJugador") ~
      get[String]("jugador.descJugador") ~
      get[Int]("jugador.idEquipo") ~
      get[Int]("jugador.golesJugador") map {
      case id~description~idTeam~goals=> Player(id, description, idTeam, goals)
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
  def create(name: String, team: Int, goals: Int) = {
    DB.withConnection { implicit connection =>
      SQL(
        """
          insert into jugador (descJugador, idEquipo, golesJugador) values (
            {name}, {team}, {goals}
          )
        """
      ).on(
          'name -> name,
          'team -> team,
          'goals -> goals
        ).executeUpdate()
    }
  }


}

