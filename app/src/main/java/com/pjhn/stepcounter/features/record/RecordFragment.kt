package com.pjhn.stepcounter.features.record

import android.content.Intent
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
import com.google.android.play.core.review.ReviewManagerFactory
import com.pjhn.stepcounter.features.record.presentation.output.RecordUiEffect
import com.pjhn.stepcounter.features.record.presentation.screen.RecordScreen
import com.pjhn.stepcounter.features.record.presentation.viewmodel.RecordViewModel
import com.pjhn.stepcounter.ui.theme.StepCounterAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import androidx.core.net.toUri

@AndroidEntryPoint
class RecordFragment : Fragment() {

    private val viewModel: RecordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
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
                        recordsProgress = viewModel.recordsProgress.collectAsState(),
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

                        is RecordUiEffect.OpenPlayStore -> {
                            openPlayStore()
                        }
                    }
                }
            }
        }
    }

    //리뷰 팝업 시 사용
    private fun openReview() {
        val manager = ReviewManagerFactory.create(requireContext())
        val request = manager.requestReviewFlow()
        request.addOnCompleteListener { task ->
            val reviewInfo = task.result
            val flow = manager.launchReviewFlow(requireActivity(), reviewInfo)
            flow.addOnCompleteListener {}
                .addOnFailureListener { openPlayStore() }
        }.addOnFailureListener {
            openPlayStore()
        }
    }

    private fun openPlayStore() {
        val packageName = requireContext().packageName
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                "https://play.google.com/apps/details?id=${packageName}".toUri()
            )
        )
    }
}

