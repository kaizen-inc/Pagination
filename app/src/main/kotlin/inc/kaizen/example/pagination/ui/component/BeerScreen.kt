package inc.kaizen.example.pagination.ui.component

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import inc.kaizen.example.pagination.client.Beer
import inc.kaizen.example.pagination.ui.theme.PaginationTheme

@Composable
fun BeerScreen(
    beers: LazyPagingItems<Beer>
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = beers.loadState) {
        if(beers.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: ${(beers.loadState.refresh as LoadState.Error).error.message}",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if(beers.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(
                color = Color.Red,
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(
                    count = beers.itemCount
                ) { index ->
                    val beer = beers[index]
                    if(beer != null) {
                        BeerItem(
                            beer = beer,
                            modifier = Modifier.fillMaxWidth())
                    }
                }
                item {
                    if(beers.loadState.append is LoadState.Loading) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

@Composable
fun BeerItem(
    beer: Beer,
    modifier: Modifier
) {
    Card(
        modifier = modifier.background(Color.Yellow),
        elevation =  4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .padding(16.dp)
        ) {
            AsyncImage(model = beer.image_url,
                contentDescription = beer.name,
                modifier = Modifier
                    .weight(1f)
                    .height(150.dp)
            )

            Spacer(modifier = Modifier
                .width(16.dp))

            Column(
                modifier = Modifier
                    .weight(3f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = beer.name,
                    style = MaterialTheme.typography.h6,
                    color = Color.Blue,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier
                    .width(8.dp))

                Text(
                    text = beer.tagline,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                    color = Color.DarkGray,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier
                    .width(8.dp))

                Text(
                    text = beer.description,
                    color = Color.LightGray,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier
                    .width(8.dp))

                Text(
                    text = "First brewed in ${beer.first_brewed}",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End,
                    color = Color.LightGray,
                    fontSize = 8.sp
                )
            }
        }
    }
}

@Preview
@Composable
fun BeerItemPreview() {
    PaginationTheme {
        BeerItem (
            beer = Beer(
                1,
                "Beer",
                "This is cool beer",
                "This is the description for the beer\n this is next line",
                "08/2023",
                null
            ),
            Modifier.fillMaxWidth()
        )
    }
}