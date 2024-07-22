package com.example.appmobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.appmobile.ui.theme.AppMobileTheme

class MovieDetailActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppMobileTheme {
                val movie = intent.getParcelableExtra<Movie>("movie")
                movie?.let {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Box(modifier = Modifier.fillMaxWidth()) {
                                        Text(
                                            text = "Details",
                                            color = Color.White,
                                            modifier = Modifier.align(Alignment.Center)
                                        )
                                    }
                                },
                                colors = TopAppBarDefaults.topAppBarColors(
                                    containerColor = colorResource(id = R.color.purple_500)
                                )
                            )
                        }
                    ) { innerPadding ->
                        MovieDetailScreen(it, Modifier.padding(innerPadding)) {
                            onBackPressed()  // Acción para volver atrás
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MovieDetailScreen(movie: Movie, modifier: Modifier = Modifier, onBack: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start // Alinear elementos a la izquierda
    ) {
        Image(
            painter = rememberImagePainter("https://image.tmdb.org/t/p/w500${movie.poster_path}"),
            contentDescription = movie.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp), // Reducir la altura para hacer la imagen más pequeña
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = movie.title,
            style = MaterialTheme.typography.headlineSmall.copy(
                color = colorResource(id = R.color.purple_500),
                fontWeight = FontWeight.Bold
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = movie.overview,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Justify
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Release Date: ${movie.release_date}",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = colorResource(id = R.color.purple_500)
            ),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Rating: ${movie.vote_average}",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = colorResource(id = R.color.purple_500)
            ),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Duration: ${movie.runtime} minutes",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = colorResource(id = R.color.purple_500)
            ),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onBack,
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.purple_500)),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Back", color = Color.White)
        }
    }
}
