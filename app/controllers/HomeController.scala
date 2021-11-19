package controllers

import javax.inject._
import play.api._
import play.api.mvc._

/** This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class HomeController @Inject() (val controllerComponents: ControllerComponents)
    extends BaseController
    with Logging {

  /** Create an Action to render an HTML page.
    *
    * The configuration in the `routes` file means that this method will be
    * called when the application receives a `GET` request with a path of `/`.
    */
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index("Welcome"))
  }

  def showIPAddress = Action { implicit request: Request[AnyContent] =>
    val xip =
      request.headers.get("X-Forwarded-For").map(_.split(',').toList.last)
    Ok(views.html.index(xip.getOrElse(request.remoteAddress)))
  }

  def paypayTransactionEventWebhook = Action {
    implicit request: Request[AnyContent] =>
      val t = request.body.asJson
      t.map(jsValue =>
        logger.info(s"paypayTransactionEventWebhook receive: ${jsValue}")
      )
      Ok("OK")
  }
}
