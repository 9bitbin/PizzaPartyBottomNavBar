package edu.farmingdale.pizzapartybottomnavbar

/**
 * Name: Himal Shrestha
 * Class: Mobile Application Development
 * Prof: Alrajab
 *
 */

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlin.math.ceil

// ToDo 6: Add another level of hunger that is Hungry that is in between Medium and Very hungry

// ToDo 7: Using the ViewModel class, create a new ViewModel class called PizzaPartyViewModel as
// a subclass of ViewModel. Add the following properties to the PizzaPartyViewModel - see Brightspace

@Composable
fun PizzaPartyScreen(modifier: Modifier = Modifier, viewModel: PizzaPartyViewModel = viewModel()) {
    // State to hold the total number of pizzas required
    var totalPizzas by remember { mutableIntStateOf(0) }
    // State to hold the input for the number of people
    var numPeopleInput by remember { mutableStateOf("") }
    // State to hold the selected hunger level
    var hungerLevel by remember { mutableStateOf("Medium") }

    // Main container for the screen
    Column(
        modifier = modifier.padding(10.dp) // Padding for the column
    ) {
        // Title of the screen
        Text(
            text = "Pizza Party",
            fontSize = 38.sp,
            modifier = modifier.padding(bottom = 16.dp) // Padding below the title
        )
        // Input field for the number of people
        NumberField(
            labelText = "Number of people?",
            textInput = numPeopleInput,
            onValueChange = { numPeopleInput = it },
            modifier = modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth() // Padding below the input field
        )
        // Radio buttons for selecting hunger level
        RadioGroup(
            labelText = "How hungry?",
            radioOptions = listOf("Light", "Medium", "Hungry", "Very hungry"),
            selectedOption = hungerLevel,
            onSelected = { hungerLevel = it },
            modifier = modifier
        )
        // Display the total number of pizzas required
        Text(
            text = "Total pizzas: $totalPizzas", fontSize = 22.sp, modifier = modifier.padding(
                top = 16.dp, bottom = 16.dp
            ) // Padding above and below the text
        )
        // Button to calculate the number of pizzas required
        Button(
            onClick = {
                totalPizzas = calculateNumPizzas(numPeopleInput.toInt(), hungerLevel)
            }, modifier = modifier.fillMaxWidth() // Button takes the full width
        ) {
            Text("Calculate") // Button label
        }
    }
}

@Composable
fun NumberField(
    labelText: String,
    textInput: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    // Text field for inputting the number of people
    TextField(
        value = textInput, // Current input value
        onValueChange = onValueChange, // Update the input value when changed
        label = { Text(labelText) }, // Label for the text field
        singleLine = true, // Restrict to a single line
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number // Only allow numerical input
        ), modifier = modifier
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
    // Lambda to determine if a radio button is selected
    val isSelectedOption: (String) -> Boolean = { selectedOption == it }

    // Main container for the radio group
    Column {
        // Label for the radio group
        Text(labelText)
        // Iterate through each radio option
        radioOptions.forEach { option ->
            Row(
                modifier = modifier
                    .selectable(
                        selected = isSelectedOption(option), // Determine if this option is selected
                        onClick = { onSelected(option) }, // Set this option as selected when clicked
                        role = Role.RadioButton // Set the role for accessibility purposes
                    )
                    .padding(8.dp) // Padding around each row
            ) {
                // Radio button component
                RadioButton(
                    selected = isSelectedOption(option), // Determine if the button is selected
                    onClick = null, // The click is handled by the row
                    modifier = modifier.padding(end = 8.dp) // Padding to the right of the radio button
                )
                // Display the text for each radio option
                Text(
                    text = option, modifier = modifier.fillMaxWidth() // Fill the width of the row
                )
            }
        }
    }
}

// Function to calculate the number of pizzas needed based on the number of people and hunger level
fun calculateNumPizzas(
    numPeople: Int,
    hungerLevel: String,
): Int {
    val slicesPerPizza = 8 // Number of slices per pizza
    val slicesPerPerson = when (hungerLevel) {
        "Light" -> 2 // Light hunger: 2 slices per person
        "Medium" -> 3 // Medium hunger: 3 slices per person
        "Hungry" -> 4 // Hungry: 4 slices per person
        else -> 5 // Very hungry: 5 slices per person
    }

    // Calculate the total number of pizzas required, rounded up to the next whole number
    return ceil(numPeople * slicesPerPerson / slicesPerPizza.toDouble()).toInt()
}

// ViewModel class for Pizza Party screen
class PizzaPartyViewModel : ViewModel() {
    // State for total number of pizzas required
    var totalPizzas by mutableStateOf(0)

    // State for input number of people
    var numPeopleInput by mutableStateOf("")

    // State for selected hunger level
    var hungerLevel by mutableStateOf("Medium")
}
