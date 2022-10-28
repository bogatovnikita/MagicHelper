package ar.cleaner.first.pf.ui.boost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ar.cleaner.first.pf.R
import ar.cleaner.first.pf.databinding.FragmentBoostBinding
import ar.cleaner.first.pf.extensions.checkUsageStatsAllowed
import ar.cleaner.first.pf.extensions.fragmentLifecycleScope
import ar.cleaner.first.pf.extensions.observeWhenResumed
import ar.cleaner.first.pf.ui.dialogs.DialogPermission
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BoostFragment : Fragment() {

    private var _binding: FragmentBoostBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BoostViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBoostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
        initClick()
    }

    private fun initObserver() {
        viewModel.state.observeWhenResumed(lifecycleScope = fragmentLifecycleScope, ::renderState)
    }

    private fun renderState(state: BoostState) {
        state.ramDetails ?: return
        binding.percentTv.text = requireContext().getString(
            R.string.percent_D, state.ramDetails.usagePercents
        )
        binding.progressBar.progress = state.ramDetails.usagePercents
        binding.occupiedTotalTv.text = requireContext().getString(
            R.string._F_gb_F_gb,
            state.ramDetails.usedRam,
            state.ramDetails.totalRam
        )
        if (state.ramDetails.isOptimized) {
            binding.groupOptimizeIsDone.visibility = View.VISIBLE
            binding.groupIsNotOptimize.visibility = View.GONE

            binding.dangerButton.apply {
                setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.green
                    )
                )
                setBackgroundResource(R.drawable.background_button_not_danger)
            }
        }
    }

    private fun initClick() {
        binding.arrowBackIv.setOnClickListener {
            findNavController().navigate(BoostFragmentDirections.actionBoostFragmentToMenuFragment())
        }
        binding.boostButton.setOnClickListener {
            if(!viewModel.state.value.isUsageStatsAllowed){
                checkPermissionOrSubscribeOnData()
            }else{
                findNavController().navigate(BoostFragmentDirections.actionBoostFragmentToBoostProgressFragment(viewModel.mapListForOptimization()))
            }
        }
    }

    private fun checkPermissionOrSubscribeOnData() {
        with(viewModel.state.value) {
            when {
                checkUsageStatsAllowed() && isDataLoaded -> {
                    return@with
                }
                else -> {
                    showDialogUsageStats()
                }
            }
        }
    }

    private fun showDialogUsageStats(){
        val dialog = DialogPermission()
        dialog.show(parentFragmentManager, "BoostFragment")
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}