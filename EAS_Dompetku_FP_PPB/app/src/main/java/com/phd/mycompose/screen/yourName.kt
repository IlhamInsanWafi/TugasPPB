
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.phd.mycompose.viewmodel.NameViewModel

@Composable
fun DisplayName(navController: NavController, nameViewModel: NameViewModel) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Header(navController)
            Spacer(modifier = Modifier.height(50.dp))
            NameOutput(nameViewModel)
            Spacer(modifier = Modifier.weight(1f)) // Menambahkan spacer agar tombol berada di bagian bawah
            SetButton(navController)
        }
    }
}

@Composable
fun NameOutput(nameViewModel: NameViewModel) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp)
    ) {
        // Menampilkan nama pengguna jika sudah diinput
        val name = nameViewModel.name.value
        if (name.isNotEmpty()) {
            Text(
                text = "Hello, $name!",
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
    }
}

@Composable
fun SetButton(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(Color(0xFF3B82F6), Color(0xFF9333EA))
                ),
                shape = MaterialTheme.shapes.medium
            )
            .clickable {
                // Lakukan sesuatu dengan nama yang dimasukkan
                navController.navigate("setCurrency")
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "NEXT",
            fontSize = 20.sp,
            style = TextStyle(color = Color.White),
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NameOutPreview() {
    // Membuat NameViewModel untuk Preview
    val nameViewModel = NameViewModel().apply {
        setName("John Doe") // Set sample name for preview
    }

    // Membuat NavController dummy untuk Preview
    val navController = rememberNavController()

    // Menampilkan DisplayName dengan nameViewModel dan navController dummy
    DisplayName(navController = navController, nameViewModel = nameViewModel)
}

