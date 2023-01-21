package by.rodkin.testtaskshift.presenter

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import by.rodkin.testtaskshift.data.model.BinInfo
import by.rodkin.testtaskshift.ui.theme.TestTaskShiftTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.currentCoroutineContext
import kotlin.coroutines.coroutineContext

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestTaskShiftTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val message = remember { mutableStateOf("") }
                    val activityViewModel = hiltViewModel<MainActivityViewModel>()

                    val exception = activityViewModel.exception.collectAsState()
                    if (exception.value != null) Toast.makeText(
                        LocalContext.current,
                        "Повторите попытку через минуту + ${exception.value}",
                        Toast.LENGTH_SHORT
                    ).show().also { activityViewModel.cleanException() }
                    val history = activityViewModel.bikInfoHist.collectAsState(initial = null).value

                    Column {
                        OutlinedTextField(
                            value = message.value,
                            onValueChange = { text ->
                                if (text.length < 9 && !text.contains("[^0-9]".toRegex()))
                                    message.value = text
                                if (text.length == 8) activityViewModel.getInfoFromBinNetwork(message.value)
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            placeholder = { Text(text = "Введите первые 8 цифр карты") },
                            maxLines = 1,
                        )
                        if (history != null) {
                            LazyColumn() {
                                items(history) {
                                    BankCard(it)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BankCard(bank: BinInfo?) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(8.dp)
            .border(
                2.dp, color = Color.Black,
                RoundedCornerShape(16.dp)
            )
            .padding(8.dp)
    ) {
        Text(
            modifier = Modifier.clickable {
                val intent = Intent(Intent.ACTION_VIEW, "https://${bank?.bank?.url}".toUri())
                startActivity(context, intent, null)
            }, text = "Bank URL : ${bank?.bank?.url}", fontSize = 22.sp, color = Color.Blue
        )
        Text(
            modifier = Modifier.clickable {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = "tel:${bank?.bank?.phone}".toUri()
                startActivity(context, intent, null)
            }, text = "Bank phone : ${bank?.bank?.phone}", fontSize = 22.sp, color = Color.Blue
        )
        Text(
            modifier = Modifier.clickable {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    "geo:${bank?.country?.latitude},${bank?.country?.longitude}?q=${bank?.country?.name}, ${bank?.bank?.city},${bank?.bank?.name}".toUri()
                )
                intent.setPackage("com.google.android.apps.maps")
                startActivity(context, intent, null)
            },
            text = "Bank coordinats : ${bank?.country?.latitude} and ${bank?.country?.longitude}",
            fontSize = 22.sp, color = Color.Blue
        )
        Text(text = "Other info : $bank")
    }

}