package com.example.stepcounterapp.features.record

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.example.stepcounterapp.features.record.presentation.screen.RecordScreen
import com.example.stepcounterapp.ui.theme.StepCounterAppTheme

class RecordFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                StepCounterAppTheme {
                    RecordScreen()
                }
            }
        }
    }
}