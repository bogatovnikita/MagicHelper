package ar.cleaner.first.pf.ui.junk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import ar.cleaner.first.pf.R
import ar.cleaner.first.pf.databinding.FragmentProgressBinding
import ar.cleaner.first.pf.ui.ads.showAds
import ar.cleaner.first.pf.ui.progress.ActionsAdapter
import ar.cleaner.first.pf.utils.MenuItems
import ar.cleaner.first.pf.utils.NativeProvider
import ar.cleaner.first.pf.utils.OptimizationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class JunkProgressFragment(
    private val onComplete: Fragment.(
        menuItem: MenuItems,
        data: Any?,
        simpleData: String?
    ) -> Unit
) : Fragment(R.layout.fragment_progress) {

    private var _binding: FragmentProgressBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProgressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        binding.tvOptimization.text = getString(MenuItems.cleaning.title)
        cleaning()
    }

    private fun cleaning() {
        val items = NativeProvider.getFolders().toList().shuffled().subList(0, 20)
        stringActions(items)
    }

    private fun stringActions(items: List<String>) {
        val adapter = ActionsAdapter(items.toList())
//        binding.rvActions.apply {
//            layoutManager = LinearLayoutManager(requireContext())
//            this.adapter = adapter
//        }

        val repeat = items.size
        startPercents()
        lifecycleScope.launch(Dispatchers.Default) {
            repeat(items.size) {
                delay(TimeUnit.SECONDS.toMillis(8) / repeat)
                withContext(Dispatchers.Main) {
                    adapter.removeFirst()
                }
            }
            withContext(Dispatchers.Main) {
                binding.apply {
                    showAds(){
                        goNext()
                    }
                }
            }
        }
    }

    private fun startPercents() {
        lifecycleScope.launch(Dispatchers.Default) {
            val step = 7000 / 100
            for (i in 0 .. 100) {
                withContext(Dispatchers.Main) {
//                    binding.tvPercents.text = getString(R.string.d_percents, i)
                }
                delay(step.toLong())
            }
        }
    }

    private fun goNext(){
//        onComplete(MenuItems.cleaning, null, garbage())
    }

//    private fun garbage(): String {
//        val simpleData1 = OptimizationProvider.getGarbageSize().toString()
//        NativeProvider.junk(requireContext())
//        return simpleData1
//    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}