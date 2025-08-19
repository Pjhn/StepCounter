package com.example.stepcounterapp.features.record

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.stepcounterapp.features.record.presentation.output.RecordUiEffect
import com.example.stepcounterapp.features.record.presentation.screen.RecordScreen
import com.example.stepcounterapp.features.record.presentation.viewmodel.RecordViewModel
import com.example.stepcounterapp.ui.theme.StepCounterAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecordFragment : Fragment() {

    private val viewModel: RecordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        observeUiEffects()

        return ComposeView(requireContext()).apply {
            setContent {
                StepCounterAppTheme {
                    RecordScreen(
                        recordStateHolder = viewModel.output.recordState.collectAsState(),
                        selectedCategory = viewModel.selectedCategory.collectAsState(),
                        selectedDuration = viewModel.selectedDuration.collectAsState(),
                        chartRecords = viewModel.chartRecords.collectAsState(),
                        stepRecord = viewModel.stepRecord.collectAsState(),
                        stepGoal = viewModel.stepGoal.collectAsState(),
                        input = viewModel.input,
                    )
                }
            }
        }
    }

    private fun observeUiEffects() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.output.recordUiEffect.collectLatest {
                    when (it) {
                        is RecordUiEffect.Back -> {
                            findNavController().navigateUp()
                        }
                    }
                }
            }
        }
    }
}