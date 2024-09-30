package com.mehmetbaloglu.jettipapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetTipAppTheme {
                MyApp { }
            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    val perPerson = remember { mutableStateOf(0.0) }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopHeader(perPerson)
        MainContent(perPerson)
    }
}

@Composable
fun TopHeader(perPerson: MutableState<Double>) {
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
            val total = "%.2f".format(perPerson.value)
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

@Composable
fun MainContent(perPerson: MutableState<Double>) {
    val valueState = remember { mutableStateOf("") }
    val sliderPositionState = remember { mutableStateOf(0) }
    val focusManager = LocalFocusManager.current
    val splitValue = remember { mutableStateOf(1) }
    val tipAmountState = remember { mutableStateOf("") }

    // Calculate the tip amount and update tipAmountState
    val tipAmount = CalculateTipAmount(
        valueState.value.toDoubleOrNull() ?: 0.0, sliderPositionState.value.toDouble()
    )
    tipAmountState.value = "%.2f".format(tipAmount)

    // Calculate the result and update perPerson
    val result = CalculationPerPerson(
        splitValue.value,
        valueState.value.toDoubleOrNull() ?: 0.0,
        sliderPositionState.value.toDouble()
    )
    perPerson.value = result // perPerson'ı result'a eşitle

    Surface(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .clickable { focusManager.clearFocus() },
        shape = RoundedCornerShape(corner = CornerSize(8.dp)),
        border = BorderStroke(width = 2.dp, color = Color.LightGray)
    ) {
        Column(modifier = Modifier.padding(5.dp)) {
            EnterBill(valueState)
            SplitRow(splitValue)
            TipRow(tipAmountState)
            SliderColumn(sliderPositionState)
        }
    }
}

@Composable
private fun SliderColumn(sliderPositionState: MutableState<Int>) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "${sliderPositionState.value.toInt()}%")
        Spacer(modifier = Modifier.height(14.dp))
        Slider(modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            valueRange = 0f..100f,
            value = sliderPositionState.value.toFloat(),
            onValueChange = {
                sliderPositionState.value = it.toInt()
            },
            onValueChangeFinished = {
                Log.d("Slider", "MainContent: Finished")
            })
    }
}

@Composable
private fun TipRow(tipAmount: MutableState<String>) {
    Row(modifier = Modifier.padding(10.dp)) {
        Text(text = "Tip")
        Text(
            text = "$${tipAmount.value}", modifier = Modifier.padding(start = 195.dp)
        )
    }
}

private fun CalculateTipAmount(totalBill: Double, tipPercentage: Double): Double {
    return totalBill * tipPercentage / 100
}

private fun CalculationPerPerson(
    splitAmount: Int, totalBill: Double, tipPercentage: Double
): Double {
    val tipAmount = totalBill * tipPercentage / 100
    return (totalBill + tipAmount) / splitAmount
}


@Composable
private fun EnterBill(valueState: MutableState<String>) {
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        value = valueState.value,
        onValueChange = { valueState.value = it },
        label = { Text("Enter Bill") },
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.AttachMoney,
                contentDescription = "Money Icon",
                tint = MaterialTheme.colorScheme.onBackground
            )
        },
        textStyle = TextStyle(
            fontSize = 18.sp, color = MaterialTheme.colorScheme.onBackground
        ),
        singleLine = true,
        keyboardActions = KeyboardActions(onDone = {
            focusManager.clearFocus()
        })
    )
}

@Composable
private fun SplitRow(sliderPositionState: MutableState<Int>) {
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
                onClick = { sliderPositionState.value++ },
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
            text = sliderPositionState.value.toString(),
            modifier = Modifier
                .align(alignment = Alignment.CenterVertically)
                .padding(start = 10.dp, end = 10.dp)
        )
        Surface(
            modifier = Modifier.size(40.dp),
            shape = CircleShape,
            color = Color.White,
            shadowElevation = 4.dp
        ) {
            IconButton(
                onClick = {
                    if (sliderPositionState.value > 1) {
                        sliderPositionState.value--
                    } else {
                        sliderPositionState.value = 1
                    }
                }, modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(
                    imageVector = Icons.Default.Remove,
                    contentDescription = "Add Icon",
                    tint = Color.Black
                )
            }
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


