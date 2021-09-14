package com.fastaccess.compose.easyforms.example.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fastaccess.compose.easyforms.*
import com.fastaccess.compose.easyforms.example.MainViewModel
import com.fastaccess.compose.easyforms.example.ui.components.*
import com.fastaccess.compose.easyforms.example.ui.theme.ComposeFormsValidationTheme

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeFormsValidationTheme {
                EasyForm(viewModel.easyForms) {
                    viewModel.onButtonClicked()
                }
            }
        }
    }
}

@Composable
private fun EasyForm(
    easyForm: EasyForms,
    onButtonClicked: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("EasyForms") }
            )
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            EmailTextField(easyForm)
            Space()
            PasswordTextField(easyForm)
            Space()
            Salutation(easyForm)
            Space()
            NameTextField(easyForm)
            Space()
            UrlTextField(easyForm)
            Space()
            PhoneTextField(easyForm)
            Space()
            CardTextField(easyForm)
            Space()
            SmallText("Checkbox")
            CheckboxLayout(easyForm)
            Space()
            SmallText("TriCheckbox")
            TriCheckboxLayout(easyForm)
            Space()
            SmallText("RadioButton")
            RadioButtonLayout(easyForm)
            Space()
            SmallText("Switch")
            SwitchLayout(easyForm)
            Space()
            SmallText("Slider")
            SliderLayout(easyForm)
            Space()
            SmallText("RangeSlider")
            RangeSliderLayout(easyForm)
            Space(34.dp)
            LoginButton(easyForm, onClick = onButtonClicked)
        }
    }
}

@Composable
private fun LoginButton(
    easyForm: EasyForms,
    onClick: () -> Unit,
) {
    Column {
        val errorStates = easyForm.listenToErrorStates()
        val formDataState = rememberSaveable() { mutableStateOf<String?>(null) }
        Button(
            onClick = {
                formDataState.value = "${easyForm.formData()}"
                onClick.invoke()
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = errorStates.value.all { it.value == EasyFormsErrorState.VALID }
        ) {
            Text("Login")
        }
        Space(34.dp)
        if (!formDataState.value.isNullOrEmpty()) {
            Text(formDataState.value!!, style = MaterialTheme.typography.caption)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReviewForm() {
    EasyForm(easyForm = EasyForms()) {}
}