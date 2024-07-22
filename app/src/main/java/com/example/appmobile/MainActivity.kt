package com.example.appmobile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import coil.compose.rememberImagePainter
import com.example.appmobile.ui.theme.AppMobileTheme
import kotlinx.coroutines.Dispatchers

class MainActivity : ComponentActivity() {

    private val movieViewModel: MovieViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppMobileTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {
                                Box(modifier = Modifier.fillMaxWidth()) {
                                    Text(
                                        text = "Welcome",
                                        style = MaterialTheme.typography.headlineSmall,
                                        color = Color.White,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = colorResource(id = R.color.purple_500)
                            ),
                            actions = {
                                IconButton(onClick = {
                                    // Acción de cierre de sesión
                                    val intent = Intent(this@MainActivity, LoginActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    startActivity(intent)
                                    finish()
                                }) {
                                    Icon(imageVector = Icons.Default.Logout, contentDescription = "Cerrar sesión", tint = Color.White)
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    MovieListScreen(movieViewModel, modifier = Modifier.padding(innerPadding))
                }
            }
        }
        Log.d("MainActivity", "MainActivity started")
    }
}

@Composable
fun MovieListScreen(viewModel: MovieViewModel, modifier: Modifier = Modifier) {
    val movies by viewModel.movies.observeAsState(initial = emptyList())
    Log.d("MovieListScreen", "Movies: ${movies.size}")

    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(movies) { movie ->
            MovieItem(movie)
        }
    }
}

@Composable
fun MovieItem(movie: Movie) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                val intent = Intent(context, MovieDetailActivity::class.java)
                intent.putExtra("movie", movie)
                context.startActivity(intent)
            }
    ) {
        Image(
            painter = rememberImagePainter("https://image.tmdb.org/t/p/w500${movie.poster_path}"),
            contentDescription = movie.title,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.66f) // Proporción aproximada de un póster de película (2:3)
                .height(250.dp),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .background(Color(0xAA000000)) // Fondo semitransparente para mejorar la legibilidad
                .padding(8.dp)
        ) {
            Column {
                Text(
                    text = movie.title,
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Rating: ${movie.vote_average}",
                    color = Color.White,
                    fontSize = 12.sp
                )
            }
        }
    }
}

class MovieViewModel : ViewModel() {

    val movies = liveData(Dispatchers.IO) {
        try {
            val response = RetrofitInstance.api.getNowPlayingMovies(apiKey = "c0823934438075d63f1dbda4023e76fc")
            emit(response.results)
            Log.d("MovieViewModel", "Movies fetched: ${response.results.size}")
        } catch (e: Exception) {
            Log.e("MovieViewModel", "Error fetching movies", e)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AppMobileTheme {
        Text("Hello Android!")
    }
}
