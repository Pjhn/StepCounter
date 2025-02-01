package com.example.stepcounterapp.features.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.stepcounterapp.features.main.presentation.screen.MainScreen
import com.example.stepcounterapp.features.main.presentation.viewmodel.MainViewModel
import com.example.stepcounterapp.ui.theme.StepCounterAppTheme

class MainFragment: Fragment() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                StepCounterAppTheme {
                    MainScreen(
                        viewModel.userRecord.collectAsState()
                    )
                }
            }
        }
    }
}