package edu.farmingdale.pizzapartybottomnavbar
/**
 * Name: Himal Shrestha
 * Class: Mobile Application Development
 * Prof: Alrajab
 *
 */
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ToDo 2: the slider should be able to change the text value of the screen
// Done: the slider has a value that changes according to the slider value
// ToDo 3: Make the UI look better by adding a gradient background (vertical) and padding
// done: the UI has a gradient background and padding blue dark and light.
@Composable
fun Screen3() {

    // State for slider value, initialized to 0.5
    var sliderValue by remember { mutableStateOf(0.5f) }

    // State for checkbox value, initialized to true
    var chkd by remember { mutableStateOf(true) }

    // Get the context to use for launching intents
    val context = LocalContext.current

    // Main container for the screen content
    Column(
        modifier = Modifier
            .fillMaxSize() // Fill the entire available space
            .background( // Set a gradient background
                brush = Brush.verticalGradient(
                    colors = listOf(Color.Red, Color.White, Color.Blue) // Colors for the gradient
                )
            )
            .padding(20.dp), // Padding around the entire column
        verticalArrangement = Arrangement.Center, // Center elements vertically
        horizontalAlignment = Alignment.CenterHorizontally // Center elements horizontally
    ) {
        // Slider for adjusting a value between 0 and 1
        Slider(
            value = sliderValue, // Current value of the slider
            onValueChange = { sliderValue = it }, // Update slider value when changed
            Modifier.fillMaxWidth(), // Make the slider take full width
            enabled = chkd // Enable or disable the slider based on checkbox state
        )

        // Display the current value of the slider
        Text(fontSize = 20.sp, text = "Slider Value: ${String.format("%.2f", sliderValue)}")

        // Button that starts an intent to make a phone call
        Button(onClick = {
            val newInt = Intent(Intent.ACTION_VIEW) // Create a new intent for viewing
            newInt.setData(Uri.parse("tel:6314202000")) // Set the data to a phone number
            context.startActivity(newInt) // Start the activity to make a call
        }) {
            Text(fontSize = 20.sp, text = "Call me") // Button label
        }

        // Checkbox to enable or disable the slider
        Checkbox(
            checked = chkd, // Current state of the checkbox
            onCheckedChange = { chkd = it }, // Update checkbox state when clicked
            modifier = Modifier.padding(10.dp) // Padding around the checkbox
        )
    }
}
