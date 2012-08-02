package controllers

import play.api._
import play.api.mvc._
import play.api.libs.concurrent.Promise

object Timeout extends Controller {
  
  def index() = Action {
    Ok("You asked for: ")
  }
  
  /**
   * All this does is sleep, and then returns the actual amount of time
   * the request slept for
   */
  def timeout(sleep: Long) = Action {
    val start = System.currentTimeMillis
    Async {
      Promise.timeout[Result]({
        val diff = System.currentTimeMillis - start
        Ok(diff toString)
      }, sleep)
    }
  }
}
