package ch.epfl.doomwatcher

import org.scalatra._
import better.files._

class MyScalatraServlet extends Rl4jDoomWebAppStack {

  get("/") {
    <html>
      <body>
        <h1>Hello, world!</h1>
        Say <a href="hello-scalate">hello to Scalate</a>.
      </body>
    </html>
  }

  get("/video/:id") {
    val dir = File(Configuration.dir+"doomreplay/")
    val search = dir.list.find(x => x.name == "DoomReplay-"+params("id")+".mp4")
    search match {
      case Some(file) =>
        contentType="video/mp4"
        file.toJava
      case None =>
        NotFound("Sorry file not found")
    }
//    Ok(first)
  }

  get("/chart"){
    val chart = File(Configuration.dir+"score")
    Ok(chart.lines.mkString("\n"))
  }

  get("/videos") {
    contentType="text/html"
    val dir = File(Configuration.dir+"doomreplay/")
    val files = dir.list.filter(_.name contains(".mp4")).map(_.name.dropRight(4).drop(11)).toList.sortBy(_.toInt)
    scaml("videos.scaml", "files" -> files, "title" -> "Videos", "video_url" -> Configuration.video_url)
  }


}
