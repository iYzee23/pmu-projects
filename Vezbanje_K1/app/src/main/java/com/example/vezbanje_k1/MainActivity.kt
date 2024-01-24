package com.example.vezbanje_k1

import android.app.Activity
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vezbanje_k1.ui.theme.Vezbanje_K1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Vezbanje_K1Theme {
                val configuration = LocalConfiguration.current
                when (configuration.orientation) {
                    Configuration.ORIENTATION_PORTRAIT -> Portrait()
                    Configuration.ORIENTATION_LANDSCAPE -> Landscape()
                }
            }
        }
    }
}

@Composable
fun Portrait(
    modifier: Modifier = Modifier
) {
    var selectedItem by remember { mutableStateOf<Data?>(null) }

    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier,
        color = MaterialTheme.colorScheme.background
    ) {
        Column {
            TopSegment(
                modifier = Modifier.padding(bottom = 8.dp),
                selectedItem = selectedItem,
                onIconButtonClick = {
                    selectedItem!!.isFavourite = !selectedItem!!.isFavourite
                }
            )
            if (selectedItem == null) {
                LeftSegment(
                    modifier = Modifier.padding(top = 8.dp),
                    onItemClicked = { selectedItem = it }
                )
            }
            else {
                RightSegment(
                    image = selectedItem!!.image,
                    title = selectedItem!!.title,
                    text = selectedItem!!.text,
                    isFavourite = selectedItem!!.isFavourite,
                    onClick = { selectedItem = null }
                )
            }
        }
    }
}

@Composable
fun Landscape(
    modifier: Modifier = Modifier
) {
    var selectedItem by remember { mutableStateOf<Data?>(null) }

    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier,
        color = MaterialTheme.colorScheme.background
    ) {
        Column {
            TopSegment(
                modifier = Modifier.padding(bottom = 8.dp),
                selectedItem = selectedItem,
                onIconButtonClick = {
                    selectedItem!!.isFavourite = !selectedItem!!.isFavourite

                }
            )
            Row {
                LeftSegment(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .weight(3f),
                    onItemClicked = { selectedItem = it }
                )
                if (selectedItem == null) {
                    Text(
                        text = "Odaberite neku od vesti",
                        modifier = Modifier.weight(7f)
                    )
                }
                else {
                    RightSegment(
                        image = selectedItem!!.image,
                        title = selectedItem!!.title,
                        text = selectedItem!!.text,
                        isFavourite = selectedItem!!.isFavourite,
                        onClick = { selectedItem = null },
                        modifier = Modifier.weight(7f)
                    )
                }
            }
        }
    }
}

@Composable
fun LeftSegment(
    onItemClicked: (Data) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth()
    ) {
        items(itemsData) {
            Item(it.image, it.title, it.text, it.isFavourite, onClick = { onItemClicked(it) })
        }
    }
}

@Composable
fun Item(
    @DrawableRes image: Int,
    title: String,
    text: String,
    isFavourite: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row (
        modifier = modifier
            .height(200.dp)
            .padding(vertical = 16.dp)
            .background(Color.LightGray)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(image),
            contentDescription = "Image",
            modifier = Modifier.weight(4f),
            contentScale = ContentScale.Crop
        )
        Column (
            modifier = Modifier.weight(5f)
        ) {
            Text(
                text = title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = text,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
        if (isFavourite) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Favorite",
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun TopSegment(
    selectedItem: Data?,
    onIconButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current as Activity

    Row(
        modifier = modifier
            .background(Color.Green)
            .height(50.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Vesti",
            modifier = Modifier.padding(start = 16.dp),
            style = MaterialTheme.typography.titleMedium,
            color = Color.White
        )
        IconButton(onClick = {
            if (selectedItem != null) {
                //selectedItem.isFavourite = !selectedItem.isFavourite
                //selectedItem = selectedItem.copy(isFavourite = !selectedItem.isFavourite)
                onIconButtonClick()
            }
            else Toast.makeText(context, "Morate uci u odgovarajuci item", Toast.LENGTH_SHORT).show()
        }) {
            Icon(
                imageVector = Icons.Default.FavoriteBorder,
                contentDescription = "Favourite"
            )
        }
    }
}

@Composable
fun RightSegment(
    @DrawableRes image: Int,
    title: String,
    text: String,
    isFavourite: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        Image(
            painter = painterResource(image),
            contentDescription = "Image",
            modifier = Modifier
                .height(200.dp)
                .width(200.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onClick) {
            Text(text = "Nazad na glavni screen")
        }
    }
}

val itemsData: MutableList<Data> = mutableListOf(
    Data(
        R.drawable.luka_jovic,
        "Ovacije Bernabea - Jović dao prvi gol za Real!",
        "Jović je u osmom nastupu u \"kraljevskom\" dresu konačno razbio mrežu protivnika!"
    ),
    Data(
        R.drawable.joker,
        "Rezultati ankete: Najbolji Džoker je Hit Ledžer.",
        "Iako mnogi smatraju da je Hoakin Finiks glavni kandidat za Oskara, nije braćo."
    ),
    Data(
        R.drawable.trg_republike,
        "Novi rok za Trg republike 23, januar!",
        "Kako piše \"Blic\", treba napomenuti da se to odnosi na zahtev vladajuće koalicije."
    ),
    Data(
        R.drawable.alfa_romeo,
        "Važni meseci ispred Alfra Romea.",
        "Narednih nekoliko meseci biće izuzetno važni za Alfa Romeo, jer se kvari kao somina."
    ),
    Data(
        R.drawable.pica_brokoli,
        "Zašto biramo nezdrave grickalice umesto zdrave hrane?",
        "Slabije funkcionisanje mozga zbog umora i iscrpljenosti nije toliko nepovezano sa ishranom?"
    )
)
