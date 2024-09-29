package com.mehmetbaloglu.jettipapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mehmetbaloglu.jettipapp.ui.theme.JetTipAppTheme
import kotlin.math.sin

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
    val sliderPositionState = remember { mutableStateOf(0f) }
    val focusManager = LocalFocusManager.current
    Surface(
        modifier = Modifier
            .padding(2.dp)
            .fillMaxWidth()
            .clickable { focusManager.clearFocus()},
        shape = RoundedCornerShape(corner = CornerSize(8.dp)),
        border = BorderStroke(width = 2.dp, color = Color.LightGray)
    ) {
        Column {
            EnterBill(valueState)
            SplitRow()
            TipRow()
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "${sliderPositionState.value}")
                Spacer(modifier = Modifier.height(14.dp))
                Slider(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                    steps = 5,
                    valueRange = 0f..100f,
                    value = sliderPositionState.value,
                    onValueChange = {
                        sliderPositionState.value = it // Slider pozisyonunu güncelle
                        Log.d("Slider", "MainContent: $it")},
                    onValueChangeFinished = {
                        Log.d("Slider", "MainContent: Finished")
                    }
                )

            }
        }
    }
}

@Composable
private fun TipRow() {
    Row(modifier = Modifier.padding(10.dp)) {
        Text(text = "Tip")
        Text(
            text = "$33.00",
            modifier = Modifier.padding(start = 195.dp)
        )
    }
}

@Composable
private fun EnterBill(valueState: MutableState<String>) {
    val focusManager = LocalFocusManager.current
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
            imeAction = ImeAction.Done
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.AttachMoney,
                contentDescription = "Money Icon",
                tint = MaterialTheme.colorScheme.onBackground
            )
        },
        textStyle = TextStyle(
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onBackground
        ),
        singleLine = true,
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
            }
        )


    )
}

@Composable
private fun SplitRow() {
    Row(modifier = Modifier.padding(3.dp)) {
        Text(
            text = "Split",
            modifier = Modifier
                .align(alignment = Alignment.CenterVertically)
                .padding(10.dp)
        )
        Spacer(modifier = Modifier.width(120.dp))
        Surface(
            modifier = Modifier.size(40.dp), // Butonun boyutunu ayarla
            shape = CircleShape, // Yuvarlak bir buton yapısı
            color = Color.White, // Arka plan rengi beyaz
            shadowElevation = 4.dp // Butona gölge efekti ekle
        ) {
            IconButton(
                onClick = { /* Butona tıklanınca yapılacak işlemler */ },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Icon",
                    tint = Color.Black // İkonun rengi siyah
                )
            }
        }
        Text(
            text = "2",
            modifier = Modifier
                .align(alignment = Alignment.CenterVertically)
                .padding(start = 10.dp, end = 10.dp)
        )
        Surface(
            modifier = Modifier.size(40.dp), // Butonun boyutunu ayarla
            shape = CircleShape, // Yuvarlak bir buton yapısı
            color = Color.White, // Arka plan rengi beyaz
            shadowElevation = 4.dp // Butona gölge efekti ekle
        ) {
            IconButton(
                onClick = { /* Butona tıklanınca yapılacak işlemler */ },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(
                    imageVector = Icons.Default.Remove,
                    contentDescription = "Add Icon",
                    tint = Color.Black // İkonun rengi siyah
                )
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
            .padding(10.dp),
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


