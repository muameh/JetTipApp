package com.mehmetbaloglu.jettipapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mehmetbaloglu.jettipapp.ui.theme.JetTipAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetTipAppTheme {
                MyApp {

                }
            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    TopHeader()
}


@Composable
fun TopHeader() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(shape = CircleShape.copy(all = CornerSize(12.dp))),
        color = Color(0xFFE9D7F7)
    ) {
        Column (
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Text(
                text = "Total Per Person",
                modifier = Modifier.padding(5.dp),
                style = MaterialTheme.typography.headlineMedium)
            Text(
                text = "$200",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.ExtraBold
                )
        }

    }
}

@Preview
@Composable
fun DefaultPreview() {
    JetTipAppTheme {
        MyApp {}
    }
}


