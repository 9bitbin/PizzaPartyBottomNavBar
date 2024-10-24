package edu.farmingdale.pizzapartybottomnavbar
/**
 * Name: Himal Shrestha
 * Class: Mobile Application Development
 * Prof: Alrajab
 */
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay

// ToDo 1: The splash screen should have the image displayed in the center of the screen.

@Composable
fun SplashScreen(navController: NavHostController) {

    // Remember the animation state for scaling the image
    val scale = remember {
        Animatable(0f) // Initial scale set to 0 (completely shrunk)
    }

    // Launch an effect to handle the animation and navigation after delay
    LaunchedEffect(key1 = true) {
        // Animate the scale of the image to its full size with an overshoot effect
        scale.animateTo(
            targetValue = 1f, // Scale to its original size
            animationSpec = tween(
                durationMillis = 1000, // Animation duration of 1 second
                easing = {
                    OvershootInterpolator(2f).getInterpolation(it) // Overshoot effect for a bounce-like animation
                }
            )
        )
        // Delay for 3 seconds before navigating to the next screen
        delay(3000)
        navController.navigate(BottomNavigationItems.PizzaScreen.route) // Navigate to PizzaScreen
    }

    // Use Box with fillMaxSize to ensure the image is centered on the screen
    Box(
        contentAlignment = Alignment.Center, // Center the content inside the box
        modifier = Modifier.fillMaxSize() // Fill the entire available size
    ) {
        // Display the logo image centered inside the box
        Image(
            painter = painterResource(id = R.drawable.fsclogo), // Load the drawable resource for the image
            contentDescription = "", // Content description for accessibility
            modifier = Modifier.wrapContentSize(Alignment.Center) // Ensure the image wraps content and stays centered
        )
    }
}// SplashScreen function ends here


