package controllers

import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import models._
import views._
import play.api.Routes

object Application extends Controller {

  // -- Authentication

  val loginForm = Form(
    tuple(
      "email" -> text,
      "password" -> text
    ) verifying ("El usuario no existe o la contraseña es incorrecta", result => result match {
      case (email, password) => User.authenticate(email, password).isDefined
    })
  )

  /**
   * Login page.
   */
  def login = Action { implicit request =>
    Ok(html.login(loginForm))
  }

//  def index = Action {
//    Ok(views.html.index("Your new application is ready."))
//  }

  /**
   * Handle login form submission.
   */
  def authenticate = Action { implicit request =>
    loginForm.bindFromRequest.fold(
      formWithErrors => BadRequest(html.login(formWithErrors)),
      user => Redirect(routes.Porra.index).withSession("email" -> user._1)
    )
  }

  /**
   * Logout and clean the session.
   */
  def logout = Action {
    Redirect(routes.Application.login).withNewSession.flashing(
      "info" -> "Hasta pronto"
    )
  }

  // -- Javascript routing

  def javascriptRoutes = Action { implicit request =>
    import routes.javascript._
    Ok(
      Routes.javascriptRouter("jsRoutes")(
        Players.bet
      )
    ).as("text/javascript")
  }
}

/**
 * Provide security features
 */
trait Secured {
  /**
   * Retrieve the connected user email.
   */
  private def email(request: RequestHeader) = request.session.get("email")

  /**
   * Redirect to login if the user in not authorized.
   */
  private def onUnauthorized(request: RequestHeader) = Results.Redirect(routes.Application.login)

  // --

  /**
   * Action for authenticated users.
   */
  def IsAuthenticated(f: => String => Request[AnyContent] => Result) = Security.Authenticated(email, onUnauthorized) { user =>
    Action(request => f(user)(request))
  }
}


