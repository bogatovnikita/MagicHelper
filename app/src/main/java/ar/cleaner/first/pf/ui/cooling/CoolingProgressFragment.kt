package ar.cleaner.first.pf.ui.cooling

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ar.cleaner.first.pf.R
import ar.cleaner.first.pf.databinding.FragmentProgressBinding
import ar.cleaner.first.pf.domain.usecases.cooling.CpuOptimizerUseCase
import ar.cleaner.first.pf.ui.progress.ActionsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class CoolingProgressFragment : Fragment() {

    private var _binding: FragmentProgressBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var cpuOptimizerUseCase: CpuOptimizerUseCase

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
        updateCoolingUseCase()
        initRecyclerView()
    }

    private fun updateCoolingUseCase() {
        lifecycleScope.launch {
            cpuOptimizerUseCase.invoke().collect {}
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
            delay(500)
            withContext(Dispatchers.Main) {
                binding.recyclerView.visibility = View.GONE
                binding.isDoneTv.visibility = View.VISIBLE
            }
            delay(1000)
            withContext(Dispatchers.Main) { goScreenResult() }
        }
    }

    private fun goScreenResult() {
        findNavController().navigate(
            CoolingProgressFragmentDirections.actionCoolingProgressFragmentToResultFragment(
                3
            )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}