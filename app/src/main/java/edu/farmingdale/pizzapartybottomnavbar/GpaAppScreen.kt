package edu.farmingdale.pizzapartybottomnavbar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

// ToDo 4: Match the UI as in drawable gpa_design.png. Use the following hints:
// - The background color should be Color.Cyan
// - Fix padding, alignment, and keypad type

// ToDo 5:  Add the GpaAppScreen composable button that clears the input fields when clicked

@Composable
fun GpaAppScreen() {

    // Variables for storing the grades input by the user
    var grade1 by remember { mutableStateOf("") }
    var grade2 by remember { mutableStateOf("") }
    var grade3 by remember { mutableStateOf("") }

    // Declare variables for GPA result and background color
    var gpa by remember { mutableStateOf("") }
    var backColor by remember { mutableStateOf(Color.Cyan) } // Set default background color to Cyan
    var btnLabel by remember { mutableStateOf("Compute GPA") } // Button label that toggles between "Compute GPA" and "Clear"

    // Main container for the GPA calculator UI
    Column(
        modifier = Modifier
            .fillMaxSize() // Fill the entire available screen size
            .background(backColor) // Set the background color
            .padding(16.dp), // Add padding around the column
        verticalArrangement = Arrangement.Center, // Center the content vertically
        horizontalAlignment = Alignment.CenterHorizontally // Center the content horizontally
    ) {

        // TextField for Course 1 Grade input
        TextField(
            value = grade1, // Current value of grade1
            onValueChange = { grade1 = it }, // Update grade1 when the user types
            Modifier
                .padding(8.dp) // Padding around the TextField
                .fillMaxWidth(), // Make the TextField take the full width of the screen
            label = { Text("Course 1 Grade") }, // Label for the TextField
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number) // Set keyboard type to number
        )

        // TextField for Course 2 Grade input
        TextField(
            value = grade2, // Current value of grade2
            onValueChange = { grade2 = it }, // Update grade2 when the user types
            Modifier
                .padding(8.dp) // Padding around the TextField
                .fillMaxWidth(), // Make the TextField take the full width of the screen
            label = { Text("Course 2 Grade") }, // Label for the TextField
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number) // Set keyboard type to number
        )

        // TextField for Course 3 Grade input
        TextField(
            value = grade3, // Current value of grade3
            onValueChange = { grade3 = it }, // Update grade3 when the user types
            Modifier
                .padding(8.dp) // Padding around the TextField
                .fillMaxWidth(), // Make the TextField take the full width of the screen
            label = { Text("Course 3 Grade") }, // Label for the TextField
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number) // Set keyboard type to number
        )

        // Button to compute GPA or clear input fields
        Button(onClick = {
            if (btnLabel == "Compute GPA") {
                // Compute GPA when the button label is "Compute GPA"
                val gpaVal = calGPA(grade1, grade2, grade3)
                if (gpaVal != null) {
                    gpa = gpaVal.toString() // Set the GPA result

                    // Change background color based on GPA value
                    backColor = when {
                        gpaVal < 60 -> Color.Red // Red for GPA less than 60
                        gpaVal in 60.0..79.0 -> Color.Yellow // Yellow for GPA between 60 and 79
                        else -> Color.Green // Green for GPA 80 and above
                    }
                    btnLabel = "Clear" // Change button label to "Clear"
                } else {
                    gpa = "Invalid input" // Show error message if input is invalid
                }
            } else {
                // Clear all fields when the button label is "Clear"
                grade1 = ""
                grade2 = ""
                grade3 = ""
                gpa = ""
                backColor = Color.Cyan // Reset background color to Cyan
                btnLabel = "Compute GPA" // Reset button label to "Compute GPA"
            }
        }, modifier = Modifier
            .padding(top = 24.dp) // Padding at the top of the button
            .fillMaxWidth()) { // Make the button take the full width of the screen
            Text(btnLabel) // Button label text
        }

        // Display the calculated GPA if it is not empty
        if (gpa.isNotEmpty()) {
            Text(text = "GPA: $gpa", modifier = Modifier.padding(top = 16.dp)) // Show GPA result with padding at the top
        }

    }
}

// Function to calculate GPA based on the grades provided
fun calGPA(grade1: String, grade2: String, grade3: String): Double {
    return try {
        // Convert the grade strings to doubles and calculate their average
        val grades = listOf(grade1.toDouble(), grade2.toDouble(), grade3.toDouble())
        grades.average() // Return the average value
    } catch (e: NumberFormatException) {
        Double.NaN // Return NaN if there is an error in parsing the grades
    }
}
