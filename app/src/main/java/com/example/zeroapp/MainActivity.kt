package com.example.zeroapp

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    val City : String = "Johannesburg,ZA"
    val API : String = "1414d1497d6d04bf0bee13c507d872f5"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        weatherTask().execute()
    }
    inner class weatherTask() : AsyncTask<String, Void, String>()
    {
        override fun onPreExecute() {
            super.onPreExecute()
            findViewById<ProgressBar>(R.id.loader).visibility = View.VISIBLE
            findViewById<RelativeLayout>(R.id.mainContainer).visibility = View.GONE
            findViewById<TextView>(R.id.errortext).visibility = View.GONE
        }

        override fun doInBackground(vararg params: String?): String {

            return try {
                val response = URL("https://api.openweathermap.org/data/2.5/weather?q=$City&units=metric&appid=$API")
                    .readText(Charsets.UTF_8)
                Log.d("WeatherAPIResponse", response)
                response
            } catch (e: Exception) {
                Log.e("WeatherError", "Network error: ${e.localizedMessage}")
                null.toString()
            }

//            return try {
//                URL("https://api.openweathermap.org/data/2.5/weather?q=$City&units=metric&appid=$API")
//                    .readText(Charsets.UTF_8)
//            } catch (e: Exception){
//                Log.e("WeatherError", "Network error: ${e.localizedMessage}")
//                null
//            }.toString()

//            var response : String
//            try {
//                response = URL("https://api.openweathermap.org/data/2.5/weather?q=$City&units=metric&appid=$API")
//                    .readText(Charsets.UTF_8)
//            }catch (e:Exception)
//            {
//                response = null.toString()
//            }
//            return response

         }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)


            if (result == null) {
                findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
                findViewById<TextView>(R.id.errortext).visibility = View.VISIBLE
                return
            }

            try {
                val jsonObj = JSONObject(result)

                val main = jsonObj.getJSONObject("main")
                val sys = jsonObj.getJSONObject("sys")
                val wind = jsonObj.getJSONObject("wind")
//                val weather = jsonObj.getJSONArray("weather").getJSONObject(0)
                val weatherArray = jsonObj.getJSONArray("weather")
                val weather = weatherArray.getJSONObject(0)
                val updatedAt = jsonObj.getLong("dt")
                val updatedAtText = "Updated at : "+SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(Date(updatedAt*1000))
                val temp = main.getString("temp")+"°C"
                val tempMin = "Min Temp: "+main.getString("temp_min")+"°C"
                val tempMax = "Max Temp: "+main.getString("temp_max")+"°C"
                val pressure = main.getString("pressure")
                val humidity = main.getString("humidity")
                val sunrise:Long = sys.getLong("sunrise")
                val sunset:Long = sys.getLong("sunset")
                val windSpeed = wind.getString("speed")
                val weatherDescription = weather.getString("description")
                val address = jsonObj.getString("name")+","+sys.getString("country")

                findViewById<TextView>(R.id.address).text = address
                findViewById<TextView>(R.id.updated_at).text = updatedAtText
                findViewById<TextView>(R.id.Status).text = weatherDescription.replaceFirstChar { it.uppercase() }
                findViewById<TextView>(R.id.temp).text = temp
                findViewById<TextView>(R.id.temp_min).text = tempMin
                findViewById<TextView>(R.id.temp_max).text = tempMax
                findViewById<TextView>(R.id.sunrise).text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunrise * 1000))
                findViewById<TextView>(R.id.sunset).text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunset * 1000))
                findViewById<TextView>(R.id.wind).text = windSpeed
                findViewById<TextView>(R.id.pressure).text = pressure
                findViewById<TextView>(R.id.humidity).text = humidity

                findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
                findViewById<RelativeLayout>(R.id.mainContainer).visibility = View.VISIBLE

            } catch (e: Exception) {
                Log.e("WeatherError", "Exception: ${e.message}")
                findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
                findViewById<TextView>(R.id.errortext).visibility = View.VISIBLE
            }

//                findViewById<TextView>(R.id.address).text = address
//                findViewById<TextView>(R.id.updated_at).text = updatedAtText
//                findViewById<TextView>(R.id.Status).text = weatherDescription.capitalize()
//                findViewById<TextView>(R.id.temp).text = temp
//                findViewById<TextView>(R.id.temp_min).text = tempMin
//                findViewById<TextView>(R.id.temp_max).text = tempMax
//                findViewById<TextView>(R.id.sunrise).text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunrise*1000))
//                findViewById<TextView>(R.id.sunset).text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunset*1000))
//                findViewById<TextView>(R.id.wind).text = windSpeed
//                findViewById<TextView>(R.id.pressure).text = pressure
//                findViewById<TextView>(R.id.humidity).text = humidity
//
//                findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
//                findViewById<ProgressBar>(R.id.mainContainer).visibility = View.VISIBLE
//
//
//            }
//            catch (e: Exception)
//            {
//                findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
//                findViewById<ProgressBar>(R.id.errortext).visibility = View.VISIBLE
//            }
        }
    }
}


















//
//setContentView(R.layout.activity_main)
//ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//    val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//    v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//    insets
//}
//    override fun onStart() {
//        super.onStart()
//        println("onStart()")
//    }
//
//    override fun onResume() {
//        super.onResume()
//        println("onResume()")
//    }
//
//    override fun onPause() {
//        super.onPause()
//        println("onPause")
//    }
//
//    override fun onStop() {
//        super.onStop()
//        println("onStop()")
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        println("onDestroy()")
//    }
//
//    override fun onRestart() {
//        super.onRestart()
//        println("onRestart()")
//    }
//}



