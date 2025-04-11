package edu.usna.mobileos.rss

import java.io.Serializable

class RssItem(var title: String = "", var description: String = "",
    var pubDate: String = "", var link: String = ""): Serializable {

}