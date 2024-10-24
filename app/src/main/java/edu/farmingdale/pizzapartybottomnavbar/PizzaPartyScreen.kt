package edu.farmingdale.pizzapartybottomnavbar

/**
 * Name: Himal Shrestha
 * Prof: Alrajab
 * Class: BCS 371 - Mobile Application Development
 */
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlin.math.ceil

@Preview(showBackground = true)
@Composable
internal fun PizzaPartyScreen(modifier: Modifier = Modifier, viewModel: PizzaPartyViewModel = viewModel()) {
    // State variables to hold the input data and calculation result
    // `remember` is used to keep track of values during the app's lifecycle
    var totalPizzas by remember { mutableIntStateOf(0) } // Total number of pizzas needed
    var numPeopleInput by remember { mutableStateOf("") } // Input for the number of people
    var hungerLevel by remember { mutableStateOf("Medium") } // Level of hunger selected
    var hasCalculated by remember { mutableStateOf(false) } // Whether the Calculate button has been pressed

    // Main container for the screen
    Column(
        modifier = modifier.padding(10.dp) // Add padding around the container
    ) {
        // Title of the screen - displays "Pizza Party" in bold and centered
        Text(
            text = "\uD83C\uDF55Pizza Party\uD83C\uDF55", // Emoji decoration for the title
            fontSize = 38.sp, // Font size of the title
            fontWeight = FontWeight.Bold, // Make the title text bold
            textAlign = TextAlign.Center, // Align the text to the center
            modifier = modifier
                .padding(bottom = 16.dp) // Add padding below the title
                .fillMaxWidth() // Make sure the text takes the full width to center properly
        )

        // Input field for the number of people attending the party
        NumberField(
            labelText = "Number of people? 1\uFE0F\u20E3 2\uFE0F\u20E3 3\uFE0F\u20E3", // Label for the input field with emoji
            textInput = numPeopleInput, // Current value of the input field
            onValueChange = {
                numPeopleInput = it // Update the input value when it changes
                hasCalculated = false // Reset the calculation if the input changes
            },
            modifier = modifier
                .padding(bottom = 16.dp) // Add padding below the input field
                .fillMaxWidth() // Make the input field take the full width
        )

        // Radio buttons for selecting hunger level
        RadioGroup(
            labelText = "HOW HUNGRY ARE YOU? \uD83D\uDC41\uFE0F\uD83D\uDC43\uD83D\uDC41\uFE0F", // Label for the hunger level selection
            radioOptions = listOf(
                "Light \uD83D\uDE34", // Option for light hunger
                "Medium \uD83D\uDE0B", // Option for medium hunger
                "Hungry \uD83E\uDD24", // Option for hungry
                "Ravenous \uD83D\uDC3A" // Option for ravenous hunger
            ),
            selectedOption = hungerLevel, // Currently selected hunger level
            onSelected = {
                hungerLevel = it // Update the hunger level when an option is selected
                hasCalculated = false // Reset the calculation if the selection changes
            },
            modifier = modifier
        )

        // Display the total number of pizzas required
        Text(
            text = "Total pizzas: $totalPizzas", // Display the calculated number of pizzas
            fontSize = 22.sp, // Font size for the text
            fontWeight = FontWeight.Bold, // Make the text bold
            modifier = modifier.padding(top = 16.dp, bottom = 16.dp) // Add padding above and below the text
        )

        // Button to calculate the number of pizzas required
        Button(
            onClick = {
                // Calculate the number of pizzas based on input and hunger level
                totalPizzas = calculateNumPizzas(numPeopleInput.toIntOrNull() ?: 0, hungerLevel)
                hasCalculated = true // Set hasCalculated to true when button is pressed
            },
            modifier = modifier.fillMaxWidth() // Make the button take the full width
        ) {
            Text(
                text = "Calculate", // Button label
                fontWeight = FontWeight.Bold // Make the button text bold
            )
        }

        // Display an image based on the hunger level selected and whether calculation is done
        val image: Painter = if (hasCalculated) {
            // Select an image based on the selected hunger level
            when (hungerLevel) {
                "Light \uD83D\uDE34" -> painterResource(id = R.drawable.light_hunger_image)
                "Medium \uD83D\uDE0B" -> painterResource(id = R.drawable.medium_hunger_image)
                "Hungry \uD83E\uDD24" -> painterResource(id = R.drawable.hungry_hunger_image)
                "Ravenous \uD83D\uDC3A" -> painterResource(id = R.drawable.ravenous_hunger_image)
                else -> painterResource(id = R.drawable.pizza_image)
            }
        } else {
            painterResource(id = R.drawable.pizza_image) // Default image when not calculated
        }

        // Add the Image to the screen
        Image(
            painter = image, // Painter resource for the image
            contentDescription = "Pizza Image", // Description for accessibility
            modifier = modifier
                .fillMaxWidth() // Make the image take the full width
                .padding(top = 16.dp) // Add padding above the image
        )
    }
}

@Composable
fun NumberField(
    labelText: String,
    textInput: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    // Text field for inputting the number of people attending the party
    TextField(
        value = textInput, // The current value of the text field
        onValueChange = onValueChange, // Function to handle value change
        label = { Text(labelText, fontWeight = FontWeight.Bold) }, // Label for the text field, made bold
        singleLine = true, // Restrict input to a single line
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number // Only allow numerical input
        ),
        modifier = modifier
    )
}

@Composable
fun RadioGroup(
    labelText: String,
    radioOptions: List<String>,
    selectedOption: String,
    onSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    // Display a label for the radio group
    Text(labelText, fontWeight = FontWeight.Bold)
    // Iterate over each radio option to create a selectable row
    radioOptions.forEach { option ->
        Row(
            modifier = modifier
                .selectable(
                    selected = (selectedOption == option), // Determine if this option is selected
                    onClick = { onSelected(option) }, // Set the selected option when clicked
                    role = Role.RadioButton // Set the role for accessibility purposes
                )
                .padding(8.dp) // Add padding around each row
        ) {
            // Radio button component
            RadioButton(
                selected = (selectedOption == option), // Indicate if the button is selected
                onClick = null, // Click is handled by the row
                modifier = modifier.padding(end = 8.dp) // Add padding to the right of the radio button
            )
            // Display the text for each radio option, make it bold
            Text(
                text = option,
                modifier = modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// Function to calculate the number of pizzas needed based on the number of people and hunger level
fun calculateNumPizzas(
    numPeople: Int,
    hungerLevel: String,
): Int {
    val slicesPerPizza = 8 // Each pizza has 8 slices
    // Determine how many slices each person needs based on their hunger level
    val slicesPerPerson = when (hungerLevel) {
        "Light" -> 2 // Light hunger: 2 slices per person
        "Medium" -> 3 // Medium hunger: 3 slices per person
        "Hungry" -> 4 // Hungry: 4 slices per person
        else -> 5 // Very hungry (Ravenous): 5 slices per person
    }

    // Calculate the total number of pizzas required, rounding up to the next whole number
    return ceil(numPeople * slicesPerPerson / slicesPerPizza.toDouble()).toInt()
}

// ViewModel class for managing the state of the Pizza Party screen
class PizzaPartyViewModel : ViewModel() {
    var totalPizzas by mutableStateOf(0) // Total number of pizzas required
    var numPeopleInput by mutableStateOf("") // Input for the number of people
    var hungerLevel by mutableStateOf("Medium") // Selected hunger level
}
