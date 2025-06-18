package com.example.xplorer.ui.pages

import android.os.Build
import android.util.Log
import androidx.activity.compose.LocalActivity
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.xplorer.R
import com.example.xplorer.navigator.XplorerScreens
import com.example.xplorer.ui.theme.LargePadding
import com.example.xplorer.viewModels.LoginViewModel

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun LoginScreen (navController: NavController) {
    val viewModel = hiltViewModel<LoginViewModel>()
    //agregar el usuario dentro de room.
    val userData = viewModel.userData.collectAsStateWithLifecycle()
    val isAuthenticated = viewModel.isAuthenticated.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(LargePadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (userData.value == null) {
            if (!isAuthenticated.value) {
                val activity = LocalActivity.current as FragmentActivity
                activity?.let { viewModel.authenticate(it) }
            }
            GoogleLoginButton(
                modifier = Modifier,
                onClick = { viewModel.launchCredentialManager() }
            )
        }
        else {
            userData.value!!.displayName
            navController.navigate(XplorerScreens.Home)
        }
    }
}

@Composable
fun GoogleLoginButton(
    onClick: () -> Unit,
    modifier: Modifier
) {
    GoogleButtonUI(
        modifier = modifier,
        onClick = onClick,
    )
}

@Composable
private fun GoogleButtonUI(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = stringResource(R.string.sign_in),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(navController = NavController(context = LocalContext.current))
}