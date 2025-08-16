package com.example.stepcounterapp.features.main.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.stepcounterapp.features.common.model.StepRecord
import com.example.stepcounterapp.features.main.presentation.input.IMainViewModelInput
import com.example.stepcounterapp.features.main.presentation.output.MainState
import com.example.stepcounterapp.features.main.presentation.output.SensorState
import com.example.stepcounterapp.features.main.presentation.screen.components.MainTopAppBar
import com.example.stepcounterapp.features.main.presentation.screen.components.PrimaryButtonSection
import com.example.stepcounterapp.features.main.presentation.screen.components.RecordDetailSection
import com.example.stepcounterapp.features.main.presentation.screen.components.SensorButtonSection
import com.example.stepcounterapp.features.main.presentation.screen.components.StepCountSection
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private const val DATE_FMT_US = "MM/dd/yyyy"

@Composable
fun MainScreen(
    mainStateHolder: State<MainState>,
    sensorStateHolder: State<SensorState>,
    input: IMainViewModelInput,
    stepRecord: State<StepRecord>
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
            Text("Drawer title", modifier = Modifier.padding(16.dp))
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
                sensorState = sensorStateHolder.value,
                input = input,
                stepRecord = stepRecord,
                paddingValues = paddingValues
            )
        }
    }
}

@Composable
fun MainContent(
    mainState: MainState,
    sensorState: SensorState,
    input: IMainViewModelInput,
    stepRecord: State<StepRecord>,
    paddingValues: PaddingValues
){
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

        SensorButtonSection(
            sensorState = sensorState,
            input = input
        )

        PrimaryButtonSection(
            mainState = mainState,
            input = input
        )
    }
}