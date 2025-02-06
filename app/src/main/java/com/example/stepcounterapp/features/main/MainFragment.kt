package com.example.stepcounterapp.features.main

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
import com.example.stepcounterapp.features.main.presentation.output.MainUiEffect
import com.example.stepcounterapp.features.main.presentation.screen.MainScreen
import com.example.stepcounterapp.features.main.presentation.viewmodel.MainViewModel
import com.example.stepcounterapp.ui.theme.StepCounterAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        observeUiEffects()

        return ComposeView(requireContext()).apply {
            setContent {
                StepCounterAppTheme {
                    MainScreen(
                        mainStateHolder = viewModel.mainState.collectAsState(),
                        input = viewModel.input,
                        userRecord = viewModel.userRecord.collectAsState()
                    )
                }
            }
        }
    }

    private fun observeUiEffects() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.output.mainUiEffect.collectLatest {
                    when (it) {
                        is MainUiEffect.StartMeasurement -> {
                            /**
                             * 백그라운드 측정 서비스 로직 동작
                             * */
                        }

                        is MainUiEffect.PauseMeasurement -> {
                            /**
                             * 백그라운드 측정 서비스 로직 중단
                             * */
                        }

                        is MainUiEffect.OpenSensitivityDialog -> {
                            /**
                             * 민감도 측정 다이얼 로그 호출
                             * */
                        }
                    }
                }
            }
        }
    }
}