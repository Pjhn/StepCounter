package com.example.stepcounterapp.features.main.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.example.stepcounterapp.R
import com.example.stepcounterapp.features.common.model.StepRecord
import com.example.stepcounterapp.features.main.presentation.input.IMainViewModelInput
import com.example.stepcounterapp.features.main.presentation.output.MainState
import com.example.stepcounterapp.features.main.presentation.screen.components.MainTopAppBar
import com.example.stepcounterapp.features.main.presentation.screen.components.PrimaryButtonSection
import com.example.stepcounterapp.features.main.presentation.screen.components.RecordDetailSection
import com.example.stepcounterapp.features.main.presentation.screen.components.SensorButtonSection
import com.example.stepcounterapp.features.main.presentation.screen.components.StepCountSection
import com.example.stepcounterapp.ui.components.button.CustomIconButton
import java.time.LocalDateTime

@Composable
fun MainScreen(
    mainStateHolder: State<MainState>,
    input: IMainViewModelInput,
    stepRecord: State<StepRecord>
) {
    val currentDate = remember {
        LocalDateTime.now()
    }

    Scaffold(
        topBar = {
            MainTopAppBar(
                date = currentDate,
                button = {
                    CustomIconButton(
                        onClick = { input.openSensitivityDialog() },
                        icon = {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.ic_chart),
                                contentDescription = "정보 아이콘"
                            )
                        }
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            RecordDetailSection(
                mainStateHolder = mainStateHolder,
                stepRecord = stepRecord.value
            )
            StepCountSection(
                stepRecord = stepRecord.value,
                modifier = Modifier.weight(1f)
            )

            SensorButtonSection()

            PrimaryButtonSection(
                mainStateHolder = mainStateHolder,
                input = input
            )
        }
    }
}