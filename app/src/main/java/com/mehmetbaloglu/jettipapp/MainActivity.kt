package com.mehmetbaloglu.jettipapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        TopHeader()
        MainContent()
    }
}

@Composable
fun MainContent() {
    val valueState = remember { mutableStateOf("") }
    Surface(
        modifier = Modifier
            .padding(2.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(8.dp)),
        border = BorderStroke(width = 2.dp, color = Color.LightGray)
    ) {
        Column {
            OutlinedTextField(
                value = valueState.value,
                onValueChange = {
                    if (it.all { it.isDigit() }) {
                        valueState.value = it
                    }
                },
                label = { Text("Enter Bill") },
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = androidx.compose.ui.text.input.ImeAction.Done
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.AttachMoney,
                        contentDescription = "Money Icon"
                    )
                },
                textStyle = TextStyle(
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onBackground
                ),
            )
            Row (
                modifier = Modifier.padding(3.dp),
                horizontalArrangement = Arrangement.Start){
                Text(
                    text = "Split",
                    modifier = Modifier.align(alignment = Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.width(120.dp))

            }
        }
    }
}


@Composable
fun TopHeader(totalPerPerson: Double = 0.0) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(shape = CircleShape.copy(all = CornerSize(30.dp)))
            .padding(5.dp),
        color = Color(0xFFE9D7F7)
    ) {
        Column(
            modifier = Modifier.padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val total = "%.2f".format(totalPerPerson)
            Text(
                text = "Total Per Person",
                modifier = Modifier.padding(5.dp),
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = "$$total",
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


