package models

import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._

import org.mindrot.jbcrypt.BCrypt
import controllers.Classifications

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
  def authenticate(email: String, passwd: String): Option[User] = {

    DB.withConnection { implicit connection =>

      val sha1Hash = SQL(
        """select sha1Hash from usuario where email={email}"""
      ).on(
        'email -> email
        ).as(str("sha1Hash").singleOpt)

      if (BCrypt.checkpw(passwd, sha1Hash.get)) {
        SQL(
          """
           select * from usuario where
           email = {email} and sha1Hash = {sha1Hash}
          """
        ).on(
            'email -> email,
            'sha1Hash -> sha1Hash
          ).as(User.simple.singleOpt)
      } else {
        Option.empty
      }
    }
  }

  def isAdmin(user: Int): Boolean = {
    DB.withConnection { implicit connection =>
      val idPerm = SQL(
        """select idPermiso from permiso_usuario where idUsuario={user}"""
      ).on(
          'user -> user
        ).as(scalar[Int].singleOpt)

      val perm = SQL(
        """select descPermiso from permiso where idPermiso={idPerm}"""
      ).on(
          'idPerm -> idPerm
        ).as(str("descPermiso").singleOpt)

      if(perm.getOrElse("null") == "Actualizacion")
        true
      else
        false
    }
  }

  def isVerified(email: String): Boolean = {
    DB.withConnection { implicit connection =>

      val user = userId(email)

      SQL(
        """select validado from usuario where idUsuario={user}"""
      ).on(
          'user -> user
        ).as(scalar[Boolean].single)
    }
  }

  /**
   * Create a User.
   */
  def create(user: User): User = {

    val passwd = BCrypt.hashpw(user.sha1Hash, BCrypt.gensalt(12))

    DB.withConnection { implicit connection =>
      SQL(
        """
          insert into usuario (descUsuario, email, sha1Hash) values (
            {descUsuario}, {email}, {sha1Hash}
          )
        """
      ).on(
          'descUsuario -> user.username,
          'email -> user.email,
          'sha1Hash -> passwd
        ).executeUpdate()

      user

    }
  }

  def createPlayerBet(playerId: Int, userEmail: String) = {
    DB.withConnection { implicit connection =>

      SQL(
        """
          delete from jugador_apuesta where idUsuario={user}
        """
      ).on(
        'user -> userId(userEmail)
        ).executeUpdate()

      SQL(
        """insert into jugador_apuesta values({idUsuario}, {idJugador})"""
      ).on(
          'idUsuario -> userId(userEmail),
          'idJugador -> playerId
        ).executeUpdate()
    }
  }

  def createMatchBet(idTeam1: Int, idTeam2: Int, goalsTeam1: Int, goalsTeam2: Int, userEmail: String) = {
    DB.withConnection { implicit connection =>

      SQL(
        """
          delete from partido_apuesta where idUsuario={idUsuario} and idEquipo1={idEquipo1} and idEquipo2={idEquipo2}
        """
      ).on(
          'idUsuario -> userId(userEmail),
          'idEquipo1 -> idTeam1,
          'idEquipo2 -> idTeam2
        ).executeUpdate()

      SQL(
        """
          |insert into partido_apuesta
          |values({idUsuario}, {idEquipo1}, {idEquipo2},  {golesEquipo1}, {golesEquipo2})
        """.stripMargin
      ).on(
          'idUsuario -> userId(userEmail),
          'idEquipo1 -> idTeam1,
          'idEquipo2 -> idTeam2,
          'golesEquipo1 -> goalsTeam1,
          'golesEquipo2 -> goalsTeam2
        ).executeUpdate()
    }
  }

  def deleteClassificationBets(userEmail: String, idRound: Int) = {
    DB.withConnection { implicit connection =>
        SQL(
          """
            delete from clasificado_apuesta where idUsuario={userId} and idRonda={idRound}
        """
      ).on(
         'userId -> userId(userEmail),
         'idRound -> idRound
        ).executeUpdate()
    }
  }

  def userId(userEmail: String): Int = {
    DB.withConnection { implicit connection =>
      SQL(
        """select idUsuario from usuario where email={email}"""
      ).on(
          'email -> userEmail
        ).as(scalar[Int].single)
    }
  }

  def createClassificationBet(idRound: Int, idTeam: Int, userEmail: String) = {
    DB.withConnection { implicit connection =>
      SQL(
        """insert into clasificado_apuesta values({idUsuario}, {idRonda}, {idEquipo})"""
      ).on(
          'idUsuario -> userId(userEmail),
          'idRonda -> idRound,
          'idEquipo -> idTeam
        ).executeUpdate()
    }
  }

  def getMatchBet(userEmail: String, idTeam1: Int, idTeam2: Int): Option[MatchBet] = {
    DB.withConnection { implicit connection =>

      val user = userId(userEmail)

      SQL(
        """select * from partido_apuesta where idUsuario={user} and idEquipo1={idTeam1} and idEquipo2={idTeam2}"""
      ).on(
          'user -> user,
          'idTeam1 -> idTeam1,
          'idTeam2 -> idTeam2
        ).as(MatchBet.simple.singleOpt)
    }
  }

  def getMatchBetTeam1(userEmail: String, idTeam1: Int, idTeam2: Int): Int = {
    DB.withConnection { implicit connection =>

      val user = userId(userEmail)

      val bet = SQL(
        """select * from partido_apuesta where idUsuario={user} and idEquipo1={idTeam1} and idEquipo2={idTeam2}"""
      ).on(
          'user -> user,
          'idTeam1 -> idTeam1,
          'idTeam2 -> idTeam2
        ).as(MatchBet.simple.singleOpt)

      if (bet.nonEmpty)
        bet.get.goalsTeam1
      else
        -1
    }
  }

  def getMatchBetTeam2(userEmail: String, idTeam1: Int, idTeam2: Int): Int = {
    DB.withConnection { implicit connection =>

      val user = userId(userEmail)

      val bet = SQL(
        """select * from partido_apuesta where idUsuario={user} and idEquipo1={idTeam1} and idEquipo2={idTeam2}"""
      ).on(
          'user -> user,
          'idTeam1 -> idTeam1,
          'idTeam2 -> idTeam2
        ).as(MatchBet.simple.singleOpt)

      if (bet.nonEmpty)
        bet.get.goalsTeam2
      else
        -1
    }
  }

  def getPlayerBet(userEmail: String): Option[Player] = {
    DB.withConnection { implicit connection =>

      val user = userId(userEmail)

      val playerId = SQL(
        """select idJugador from jugador_apuesta where idUsuario={user}"""
      ).on(
          'user -> user
        ).as(scalar[Int].singleOpt)
        
        playerId match {
            case Some(id) => {
                SQL(
                """select * from jugador where idJugador={playerId}"""
                ).on(
                    'playerId -> id
                ).as(Player.simple.singleOpt)
            }
            case None => {
                None
            }
        }
    }
  }

  def getReward: Long = {
    DB.withConnection { implicit connection =>
      SQL(
        """select count(idUsuario) from usuario where validado=1"""
      ).as(scalar[Long].single) * 5
    }
  }

}
