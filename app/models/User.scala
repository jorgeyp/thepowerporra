package models

import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._

/**
 * Created by 71772901 on 07/05/2014.
 */
case class User(email: String, username: String, sha1Hash: String)

object User {
  // -- Parsers

  /**
   * Parse a User from a ResultSet
   */
  val simple = {
    get[String]("usuario.email") ~
    get[String]("usuario.descUsuario") ~
    get[String]("usuario.sha1Hash") map {
      case email~username~sha1Hash => User(email, username, sha1Hash)
    }
  }

  // -- Queries

  /**
   * Retrieve a User from email.
   */
  def findByEmail(email: String): Option[User] = {
    DB.withConnection { implicit connection =>
      SQL("select * from usuario where email = {email}").on(
        'email -> email
      ).as(User.simple.singleOpt)
    }
  }

  /**
   * Retrieve all users.
   */
  def findAll: Seq[User] = {
    DB.withConnection { implicit connection =>
      SQL("select * from usuario").as(User.simple *)
    }
  }

  /**
   * Authenticate a User.
   */
  def authenticate(email: String, sha1Hash: String): Option[User] = {
    DB.withConnection { implicit connection =>
      SQL(
        """
         select * from usuario where
         email = {email} and sha1Hash = {sha1Hash}
        """
      ).on(
          'email -> email,
          'sha1Hash -> sha1Hash
        ).as(User.simple.singleOpt)
    }
  }

  /**
   * Create a User.
   */
  def create(user: User): User = {
    DB.withConnection { implicit connection =>
      SQL(
        """
          insert into user values (
            {email}, {descUsuario}, {sha1Hash}
          )
        """
      ).on(
          'email -> user.email,
          'descUsuario -> user.username,
          'sha1Hash -> user.sha1Hash
        ).executeUpdate()

      user

    }
  }

  def createBet(playerId: Int, userEmail: String) = {
    DB.withConnection { implicit connection =>

      SQL(
        """
          delete from jugador_apuesta
        """
      ).executeUpdate()

      // Get the user id
      val userId: Int = SQL(
        """select idUsuario from usuario where email={email}"""
      ).on(
        'email -> userEmail
        ).as(scalar[Int].single)


      SQL(
        """insert into jugador_apuesta values({idUsuario}, {idJugador})"""
      ).on(
          'idUsuario -> userId,
          'idJugador -> playerId
        ).executeUpdate()
    }
  }

}
