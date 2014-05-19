package controllers

import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import models._
import views._
import play.api.Routes

/**
 * Created by 71772901 on 15/05/2014.
 */
object SignUp extends Controller {

  val signupForm: Form[User] = Form(

    // Define a mapping that will handle User values
    mapping(
      "username" -> text(minLength = 4),
      "email" -> email,

      // Create a tuple mapping for the password/confirm
      "password" -> tuple(
        "main" -> text(minLength = 6),
        "confirm" -> text
      ).verifying(
          // Add an additional constraint: both passwords must match
          "Las contraseñas no coinciden", passwords => passwords._1 == passwords._2
        ),

      // Create a mapping that will handle UserProfile values
//      "profile" -> mapping(
//        "country" -> nonEmptyText,
//        "address" -> optional(text),
//        "age" -> optional(number(min = 18, max = 100))
//      )
        // The mapping signature matches the UserProfile case class signature,
        // so we can use default apply/unapply functions here
//        (UserProfile.apply)(UserProfile.unapply),

      "accept" -> checked("Debes aceptar las condiciones del servicio")

    )
      // The mapping signature doesn't match the User case class signature,
      // so we have to define custom binding/unbinding functions
    {
      // Binding: Create a User from the mapping result (ignore the second password and the accept field)
      (email, username, passwords, _) => User(email, username, passwords._1)
    }
    {
      // Unbinding: Create the mapping values from an existing User value
      user => Some(user.email, user.username, (user.sha1Hash, ""), false)
    }.verifying(
        // Add an additional constraint: The username must not be taken (you could do an SQL request here)
        "Este usuario ya está cogido",
//        user => !Seq("admin", "guest").contains(user.username)
          user => !User.findAll.contains(user.username)
      )
  )

  /**
   * Display an empty form.
   */
  def form = Action {
    Ok(html.signup.form(signupForm));
  }

  /**
   * Handle form submission.
   */
  def submit = Action { implicit request =>
    signupForm.bindFromRequest.fold(
      // Form has errors, redisplay it
      errors => BadRequest(html.signup.form(errors)),

      // We got a valid User value, display the summary
      user => Redirect(routes.Porra.index).withSession("email" -> user.email)
    )
  }
}
