import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import scala.collection.JavaConversions._
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import scala.io.Source

object JsoupParsing extends App{

  println("Start of parsing")
  //val document =  Jsoup.connect("https://www.flipkart.com/desaster-new-black-sport-fashion-shoes-running-men/p/itm7de3539269e68?pid=SHOFP6Z4KZJRJCYG&lid=LSTSHOFP6Z4KZJRJCYGZOCWKR&marketplace=FLIPKART&srno=s_1_1&otracker=AS_QueryStore_OrganicAutoSuggest_1_11_na_na_na&otracker1=AS_QueryStore_OrganicAutoSuggest_1_11_na_na_na&fm=SEARCH&iid=en_6mH0KdiLkzODZ08Wytr52nhsLbrzBRGWRKpYAr%2BRfYpWyaxePEpgtrJ5%2FE2UFVfNXYkosFyA7BBrT6FIG1pO7Q%3D%3D&ppt=sp&ppn=sp&ssid=wynjkqc14g0000001591873078138&qH=c3a154eb663ec0ec").get

  // Read the urls from the file.
  val bufferedSource = Source.fromFile("E:\\Technical\\interviews\\Ugam\\urls.txt")
  for (line <- bufferedSource.getLines) {
      println(line)

    // Dowload the html page and parse data and convert to product properties.
    val productProperties = downloadParse(line)

    println("Map content")
      var jsonString = "{"
      var i =0;
    productProperties.foreach {

      case(keyPro, value1) => jsonString = jsonString + "\"" + keyPro + "\"" + ":" + "\"" +value1 + "\""
      i += 1;
        if (i < 8)
          {
            jsonString = jsonString + ","
          }
    }
      jsonString += "}"
      println(jsonString)

      val r = requests.post("http://localhost:9200/fliptest/_doc/?pretty", headers = Map("Content-Type" -> "application/json"), data = jsonString)

      println(r)
  }
  bufferedSource.close

  // Download parse content and create map from the product details field
  def downloadParse(urlString: String) : Map[String, String]=
  {
    var returnVals:Map[String,String]=Map()
    val document =  Jsoup.connect(urlString).get

    val title = document.title();
    returnVals += ("Title" -> title)

    val CurrentPrice = document.select("div._1vC4OE._3qQ9m1").text();
    returnVals += ("CurrentPrince" -> CurrentPrice)

    val OriginalPrice = document.select("div._3auQ3N._1POkHg").text();
    returnVals += ("OriginalPrince" -> OriginalPrice)

    val discountPercentage = document.select("div.VGWI6T._1iCvwn._9Z7kX3").text();
    returnVals += ("Discountpercentage" -> discountPercentage)

    //Parser and reterive the data for row.
    val rows = document.select("div._2GNeiG div.row ")

    val color = rows.select("div.col-9-12._1BMpvA").get(0).text()
    returnVals += ("Color" -> color)
    val OuterMaterial = rows.select("div.col-9-12._1BMpvA").get(2).text()
    returnVals += ("OuterMaterial" -> OuterMaterial)
    val ModelName = rows.select("div.col-9-12._1BMpvA").get(3).text()
    returnVals += ("ModelName" -> ModelName)
    val idealFor = rows.select("div.col-9-12._1BMpvA").get(4).text()
    returnVals += ("idealFor" -> idealFor)

    return returnVals
  }

}

