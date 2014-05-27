package models

import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._

/**
 * Created by 71772901 on 23/05/2014.
 */
case class Team(id: Int, name: String)

object Team {

  val simple = {
    get[Int]("equipo.idEquipo") ~
    get[String]("equipo.descEquipo") map {
      case id~name => Team(id, name)
    }
  }

  /**
   * Retrieve a Team from id.
   */
  def findById(id: Int): Option[Team] = {
    DB.withConnection { implicit connection =>
      SQL("select * from equipo where idEquipo = {idEquipo}").on(
        'idEquipo -> id
      ).as(Team.simple.singleOpt)
    }
  }

  /**
   * Retrieve a Team from name.
   */
  def findByName(name: String): Option[Team] = {
    DB.withConnection { implicit connection =>
      SQL("select * from equipo where descEquipo = {descEquipo}").on(
        'descEquipo -> name
      ).as(Team.simple.singleOpt)
    }
  }

  /**
   * Retrieve a Team from name.
   */
  def findIdByName(name: String): Int = {
    DB.withConnection { implicit connection =>
      SQL("select idEquipo from equipo where descEquipo = {descEquipo}").on(
        'descEquipo -> name
      ).as(scalar[Int].single)
    }
  }

  /**
   * Retrieve all teams.
   */
  def findAll: Seq[Team] = {
    DB.withConnection { implicit connection =>
      SQL("select * from equipo").as(Team.simple *)
    }
  }
}
