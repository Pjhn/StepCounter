package com.example.stepcounterapp.features.main.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.stepcounterapp.BuildConfig
import com.example.stepcounterapp.R
import com.example.stepcounterapp.features.common.model.StepRecord
import com.example.stepcounterapp.features.main.presentation.input.IMainViewModelInput
import com.example.stepcounterapp.features.main.presentation.output.MainState
import com.example.stepcounterapp.features.main.presentation.output.SensorState
import com.example.stepcounterapp.features.main.presentation.screen.components.MainTopAppBar
import com.example.stepcounterapp.features.main.presentation.screen.components.PrimaryButtonSection
import com.example.stepcounterapp.features.main.presentation.screen.components.RecordDetailSection
import com.example.stepcounterapp.features.main.presentation.screen.components.StepCountSection
import com.example.stepcounterapp.features.main.presentation.screen.components.StepGoalSection
import com.example.stepcounterapp.ui.dialog.NumberInputDialog
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private const val DATE_FMT_US = "MM/dd/yyyy"

@Composable
fun MainScreen(
    mainStateHolder: State<MainState>,
    sensorStateHolder: State<SensorState>,
    input: IMainViewModelInput,
    stepRecord: State<StepRecord>,
    stepGoal: State<Int>
) {
    var openAlertDialog by remember { mutableStateOf(false) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()



    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(300.dp),
                drawerContainerColor = MaterialTheme.colorScheme.background
            ) {
                ModalContent(
                    sensorState = sensorStateHolder.value,
                    input = input
                )
            }
        },
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = {
                MainTopAppBar(
                    title = LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern(DATE_FMT_US)),
                    input = input,
                    scope = scope,
                    drawerState = drawerState
                )
            }
        ) { paddingValues ->
            MainContent(
                mainState = mainStateHolder.value,
                input = input,
                stepRecord = stepRecord,
                stepGoal = stepGoal,
                onEditGoal = { openAlertDialog = true },
                paddingValues = paddingValues
            )
        }

        if (openAlertDialog) {
            NumberInputDialog(
                initialNumber = stepGoal.value,
                onConfirm = { new ->
                    input.updateStepGoal(new)
                    openAlertDialog = false
                },
                onDismiss = {
                    openAlertDialog = false
                }
            )
        }
    }
}

@Composable
fun MainContent(
    mainState: MainState,
    input: IMainViewModelInput,
    stepRecord: State<StepRecord>,
    stepGoal: State<Int>,
    onEditGoal: ()->Unit,
    paddingValues: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        RecordDetailSection(
            mainState = mainState,
            stepRecord = stepRecord.value
        )

        StepCountSection(
            stepRecord = stepRecord.value,
            modifier = Modifier.weight(1f)
        )

        StepGoalSection(
            stepRecord = stepRecord.value,
            stepGoal = stepGoal.value,
            buttonOnClick = onEditGoal
        )

        PrimaryButtonSection(
            mainState = mainState,
            input = input
        )
    }
}

@Composable
fun ModalContent(
    sensorState: SensorState,
    input: IMainViewModelInput
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(Modifier.height(12.dp))

        Text("Settings", modifier = Modifier.padding(16.dp))

        NavigationDrawerItem(
            label = { Text("Add Widget") },
            selected = false,
            shape = RoundedCornerShape(12.dp),
            badge = {
                Icon(
                painter = painterResource(R.drawable.ic_add_small),
                contentDescription = "Add Widget Icon", tint = Color.Unspecified,
            ) },
            onClick = { input.requestWidget() }
        )

        Spacer(Modifier.height(8.dp))

        NavigationDrawerItem(
            label = { Text("Sensor Delay") },
            selected = false,
            shape = RoundedCornerShape(12.dp),
            onClick = { input.updateSensorDelay() },
            badge = {
                Text(
                    text = stringResource(
                        id = when (sensorState) {
                            SensorState.DelayHigh -> R.string.high
                            SensorState.DelayLow -> R.string.low
                        }
                    ),
                    fontWeight = FontWeight.Bold
                )
            }
        )

        Spacer(Modifier.height(8.dp))

        NavigationDrawerItem(
            label = { Text("Version") },
            selected = false,
            shape = RoundedCornerShape(12.dp),
            onClick = {},
            badge = {
                Text(
                    text = BuildConfig.VERSION_NAME
                )
            }
        )
    }
}