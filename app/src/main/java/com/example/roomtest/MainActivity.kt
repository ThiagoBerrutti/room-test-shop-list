package com.example.roomtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.roomtest.ui.JetShoppingNavigation
import com.example.roomtest.ui.home.HomeScreen
import com.example.roomtest.ui.theme.JetShoppingTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val db = Room.databaseBuilder(
//            applicationContext,
//            AppDatabase::class.java, "database-name"
//        ).build()


        setContent {
//            val userDao = db.userDao()
//            val users: List<User> = userDao.getAll()
            JetShoppingTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    JetShoppingApp()
//                    HomeScreen(onNavigate = {})
                }
            }
        }
    }

    @Composable
    fun JetShoppingApp() {
        JetShoppingNavigation()
    }
}