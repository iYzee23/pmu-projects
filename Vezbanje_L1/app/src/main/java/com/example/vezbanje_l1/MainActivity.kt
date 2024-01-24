package com.example.vezbanje_l1

import android.app.Activity
import android.os.Bundle
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vezbanje_l1.ui.theme.Vezbanje_L1Theme
import java.util.Locale.Category
import kotlin.math.exp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Vezbanje_L1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    OpeningScreen()
                }
            }
        }
    }
}

@Composable
fun OpeningScreen(
    modifier: Modifier = Modifier
) {
    var screen by remember { mutableStateOf(0) }
    var itemList: MutableList<Item> = remember { mutableStateListOf<Item>(
        Item("Coca Cola", 5, 15.0, "Pice"),
        Item("Milka", 10, 200.0, "Slatkisi"),
        Item("Milka", 10, 200.0, "Slatkisi"),
        Item("Milka", 10, 200.0, "Slatkisi"),
        Item("Milka", 10, 200.0, "Slatkisi"),
        Item("Milka", 10, 200.0, "Slatkisi"),
        Item("Milka", 10, 200.0, "Slatkisi"),
        Item("Milka", 10, 200.0, "Slatkisi")
    ) }

    fun changeScreen(scr: Int) {
        screen = scr
    }

    fun addItemToItemList(item: Item) {
        itemList += item
    }

    fun countArticles(): Int {
        return itemList.sumOf { it.count }
    }

    fun sumArticles(): Double {
        return itemList.sumOf { it.price }
    }

    fun countArticlesCategory(category: String): Int {
        return itemList.filter { it.category == category}.sumOf { it.count }
    }

    fun sumArticlesCategory(category: String): Double {
        return itemList.filter { it.category == category }.sumOf { it.price }
    }

    when (screen) {
        0 -> MainScreen(
            onFirstButtonClick = { changeScreen(1) },
            onSecondButtonClick = { changeScreen(2) },
            numOfArticles = countArticles(),
            totalSum = sumArticles(),
            numOfArticlesCategory = { countArticlesCategory(it) },
            totalSumCategory = { sumArticlesCategory(it) }
        )
        1 -> FirstScreen(
            goBack = { changeScreen(0) },
            addItem = { addItemToItemList(it) }
        )
        2 -> SecondScreen(
            goBack = { changeScreen(0) },
            itemList = itemList
        )
    }
}

@Composable
fun MainScreen(
    onFirstButtonClick: () -> Unit,
    onSecondButtonClick: () -> Unit,
    numOfArticles: Int,
    totalSum: Double,
    numOfArticlesCategory: (String) -> Int,
    totalSumCategory: (String) -> Double,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }


    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Number of articles: $numOfArticles",
            modifier = modifier.padding(vertical = 16.dp)
        )
        Text(
            text = "Total sum to pay: $totalSum",
            modifier = modifier.padding(vertical = 16.dp)
        )
        Text(
            text = "Number of articles (for category): ${ numOfArticlesCategory(categories[selectedIndex]) }",
            modifier = modifier.padding(vertical = 16.dp)
        )
        Text(
            text = "Total sum to pay (for category): ${ totalSumCategory(categories[selectedIndex]) }",
            modifier = modifier.padding(vertical = 16.dp)
        )
        Box(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = categories.get(selectedIndex),
                modifier = Modifier
                    .clickable { expanded = true }
                    .background(Color.Gray)
                    .padding(16.dp)
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                categories.forEachIndexed { index, label ->
                    DropdownMenuItem(
                        text = { Text(text = label) },
                        onClick = { expanded = false; selectedIndex = index }
                    )
                }
            }
        }
        Button(onClick = onFirstButtonClick) {
            Text(text = "Enter the articles")
        }
        Button(onClick = onSecondButtonClick) {
            Text(text = "Show all the articles")
        }
    }
}

val categories: List<String> = listOf(
    "Pice",
    "Meso",
    "Peciva",
    "Voce",
    "Povrce",
    "Mlecni proizvodi",
    "Slatkisi",
    "Smrznuti proizvodi",
    "Kucna hemija"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirstScreen(
    goBack: () -> Unit,
    addItem: (Item) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = (LocalContext.current as Activity)

    var name by remember { mutableStateOf("") }
    var count by remember { mutableStateOf(0) }
    var price by remember { mutableStateOf(0.0) }
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(text = "Name of the article") }
        )
        OutlinedTextField(
            value = count.toString(),
            onValueChange = { count = if (it != "") it.toInt() else 0 },
            label = { Text(text = "Count of the article") }
        )
        OutlinedTextField(
            value = price.toString(),
            onValueChange = { price = if (it != "") it.toDouble() else 0.0 },
            label = { Text(text = "Price of the article") },
        )
        Box(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = categories.get(selectedIndex),
                modifier = Modifier
                    .clickable { expanded = true }
                    .background(Color.Gray)
                    .padding(16.dp)
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                categories.forEachIndexed { index, label ->
                    DropdownMenuItem(
                        text = { Text(text = label) },
                        onClick = { expanded = false; selectedIndex = index }
                    )
                }
            }
        }
        Button(
            onClick = {
                if (name.isNotEmpty() && count != 0 && price != 0.0) {
                    val item: Item = Item(name, count, price, categories[selectedIndex])
                    addItem(item)
                    goBack()
                }
                else {
                    Toast.makeText(context, "You have to enter all three parameters!", Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            Text(text = "Go back")
        }
    }
}

@Composable
fun SecondScreen(
    goBack: () -> Unit,
    itemList: MutableList<Item>,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxHeight()
    ) {
        LazyColumn(
            modifier = Modifier.weight(8f).fillMaxWidth()
        ) {
            items(itemList) {
                Surface(
                    color = Color.Gray,
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.padding(16.dp).fillMaxWidth(),
                ) {
                    Text(
                        text = "Name: ${it.name}\nCount: ${it.count}\nPrice: ${it.price}\nCategory: ${it.category}",
                        modifier = Modifier.padding(16.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
        Button(
            onClick = goBack,
            modifier = Modifier.weight(2f).fillMaxWidth().padding(16.dp)
        ) {
            Text(text = "Go back")
        }
    }
}