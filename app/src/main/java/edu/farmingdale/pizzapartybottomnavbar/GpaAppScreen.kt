package edu.farmingdale.pizzapartybottomnavbar

/**
 * Name: Himal Shrestha
 * Class: Mobile Application Development
 * Prof: Alrajab
 * Homework 8 - Pizza Party App Nav
 */

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight

// ToDo 4 Done: Match the UI as in drawable gpa_design.png. Use the following hints:
// - The background color should be Color.Cyan
// - Fix padding, alignment, and keypad type

// ToDo 5 Done:  Add the GpaAppScreen composable button that clears the input fields when clicked

@Composable
fun GpaAppScreen() {

    // Variables for storing the grades input by the user
    var grade1 by remember { mutableStateOf("") }
    var grade2 by remember { mutableStateOf("") }
    var grade3 by remember { mutableStateOf("") }

    // Declare variables for GPA result and background color
    var gpa by remember { mutableStateOf("") }
    var backColor by remember { mutableStateOf(Color.Cyan) } // Set default background color to Cyan
    var btnLabel by remember { mutableStateOf("Calculate GPA") } // Button label that toggles between "Calculate GPA" and "Clear"

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
        OutlinedTextField(
            value = grade1, // Current value of grade1
            onValueChange = { grade1 = it }, // Update grade1 when the user types
            Modifier
                .padding(8.dp) // Padding around the TextField
                .fillMaxWidth(), // Make the TextField take the full width of the screen
            label = { Text("Course 1 Grade") }, // Label for the TextField
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number) // Set keyboard type to number
        )

        // TextField for Course 2 Grade input
        OutlinedTextField(
            value = grade2, // Current value of grade2
            onValueChange = { grade2 = it }, // Update grade2 when the user types
            Modifier
                .padding(8.dp) // Padding around the TextField
                .fillMaxWidth(), // Make the TextField take the full width of the screen
            label = { Text("Course 2 Grade") }, // Label for the TextField
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number) // Set keyboard type to number
        )

        // TextField for Course 3 Grade input
        OutlinedTextField(
            value = grade3, // Current value of grade3
            onValueChange = { grade3 = it }, // Update grade3 when the user types
            Modifier
                .padding(8.dp) // Padding around the TextField
                .fillMaxWidth(), // Make the TextField take the full width of the screen
            label = { Text("Course 3 Grade") }, // Label for the TextField
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number) // Set keyboard type to number
        )

        // Button to compute GPA or clear input fields
        Button(
            onClick = {
                if (btnLabel == "Calculate GPA") {
                    // Compute GPA when the button label is "Calculate GPA"
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
                    btnLabel = "Calculate GPA" // Reset button label to "Calculate GPA"
                }
            },
            modifier = Modifier
                .padding(top = 24.dp) // Padding at the top of the button
                .width(180.dp) // Set a fixed width to match the button in the image
                .height(50.dp), // Set a fixed height for consistency
            shape = RoundedCornerShape(24.dp), // Rounded corners to match the button style in the image
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF6A1B9A), // Purple color as shown in the image
                contentColor = Color.White // White text color for better contrast
            )
        ) {
            Text(
                text = btnLabel,
                fontSize = 16.sp, // Set a proper font size for the text
                fontWeight = FontWeight.Bold // Set the font weight to bold
            )
        }

        // Display the calculated GPA if it is not empty
        if (gpa.isNotEmpty()) {
            Text(
                text = "GPA: $gpa",
                modifier = Modifier.padding(top = 16.dp), // Show GPA result with padding at the top
                fontSize = 20.sp, // Set the font size for the GPA result text
                fontWeight = FontWeight.Bold // Set the font weight to bold
            )
        }

    }
}

// Function to calculate GPA based on the grades provided
fun calGPA(grade1: String, grade2: String, grade3: String): Double? {
    return try {
        // Convert the grade strings to doubles and calculate their average
        val grades = listOf(grade1.toDouble(), grade2.toDouble(), grade3.toDouble())
        grades.average() // Return the average value
    } catch (e: NumberFormatException) {
        null // Return null if there is an error in parsing the grades
    }
}
