package controllers

import play.api._
import play.api.mvc._
import play.api.libs.concurrent.Promise

object Timeout extends Controller {
  
  def index() = Action {
    Ok(views.html.index())
  }

  def test() = Action { 
    Ok(views.html.test())
  }
  
  /**
   * All this does is sleep, and then returns the actual amount of time
   * the request slept for
   */
  def timeout(sleep: Long) = Action {
    SleepThenDo(sleep, x => {
      Ok(x + "ms")
    })
  }

  def timeoutJS(sleep: Long, fn: String) = Action {
    SleepThenDo(sleep, time => {
      Ok(fn + "(" + time +  ");").as("text/javascript")
    })
  }

  private def SleepThenDo(sleep: Long, fn: (Long) => Result): Result = {
    
    if (sleep == 0) return fn(0) // not idiomatic scala, but easier to understand

    val start = System.currentTimeMillis
    Async {
      Promise.timeout[Result]({
        val diff = System.currentTimeMillis - start
        fn(diff)
      }, sleep, java.util.concurrent.TimeUnit.MILLISECONDS)
    } 
  }
}
