package ar.cleaner.first.pf.ui.boost

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ar.cleaner.first.pf.R
import ar.cleaner.first.pf.ads.preloadAd
import ar.cleaner.first.pf.ads.showAds
import ar.cleaner.first.pf.databinding.FragmentProgressBinding
import ar.cleaner.first.pf.domain.usecases.boosting.ExtendedOptimizerUseCase
import ar.cleaner.first.pf.domain.usecases.boosting.GetBackgroundAppsUseCase
import ar.cleaner.first.pf.domain.wrapper.CaseResult
import ar.cleaner.first.pf.ui.progress.ActionsAdapter
import ar.cleaner.first.pf.ui.result.ResultFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class BoostProgressFragment : Fragment() {

    private var _binding: FragmentProgressBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var extendedOptimizerUseCase: ExtendedOptimizerUseCase

    @Inject
    lateinit var getBackgroundAppsUseCase: GetBackgroundAppsUseCase

    private var scanIsDone = false

    override fun onResume() {
        super.onResume()
        if (scanIsDone) scanIsDone()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProgressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preloadAd()
        updateExtendedOptimizerUseCase()
        initRecyclerView()
    }

    private fun updateExtendedOptimizerUseCase() {
        lifecycleScope.launch(Dispatchers.Default) {
            getBackgroundAppsUseCase.invoke().collect {
                when (it) {
                    is CaseResult.Success -> {
                        extendedOptimizerUseCase.invoke(it.response).collect {}
                    }
                    is CaseResult.Failure -> {
                        Log.e(
                            "pie",
                            "updateExtendedOptimizerUseCase: BoostProgressFragment Failure",
                        )
                    }
                }
            }
        }
    }

    private fun initRecyclerView() {
        val strings = resources.getStringArray(R.array.progress_cooling).toList()
        val adapter = ActionsAdapter(strings)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }
        val repeat = strings.size
        lifecycleScope.launch(Dispatchers.Default) {
            repeat(repeat) {
                delay(TimeUnit.SECONDS.toMillis(8) / repeat)
                withContext(Dispatchers.Main) {
                    adapter.removeFirst()
                }
            }
            scanIsDone = true
            if (scanIsDone) scanIsDone()
        }
    }

    private fun scanIsDone() {
        lifecycleScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.Main) {
                delay(500)
                withContext(Dispatchers.Main) {
                    binding.recyclerView.visibility = View.GONE
                    binding.isDoneTv.visibility = View.VISIBLE
                }
                delay(1000)
                withContext(Dispatchers.Main) { goScreenResult() }
            }
        }
    }

    private fun goScreenResult() {
        showAds {
        findNavController().navigate(
            BoostProgressFragmentDirections.actionBoostProgressFragmentToResultFragment(
                ResultFragment.BOOST_KEY
            )
        )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}