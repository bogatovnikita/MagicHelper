package ar.cleaner.first.pf.ui.boost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import ar.cleaner.first.pf.R
import ar.cleaner.first.pf.databinding.FragmentProgressBinding
import ar.cleaner.first.pf.ui.progress.ActionsAdapter
import ar.cleaner.first.pf.utils.MenuItems
import ar.cleaner.first.pf.utils.NativeProvider
import ar.cleaner.first.pf.utils.OptimizationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class BoostProgressFragment(
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
        super.onViewCreated(view, savedInstanceState)
        boosting()
    }

    private fun boosting() {
        val items = resources.getStringArray(R.array.boost_and_cooling_items)
        stringAction(items.toList())
    }

    private fun stringAction(list: List<String>) {
        val adapter = ActionsAdapter(list)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }

        val repeat = list.size
        lifecycleScope.launch(Dispatchers.Default) {
            repeat(list.size) {
                delay(TimeUnit.SECONDS.toMillis(8) / repeat)
                withContext(Dispatchers.Main) {
                    adapter.removeFirst()
                }
            }
            withContext(Dispatchers.Main) {
                delay(500)
                binding.recyclerView.visibility = View.GONE
                binding.isDoneTv.visibility = View.VISIBLE
                delay(1000)
                binding.apply {
//                    showAds() {
                    goNext()
//                    }
                }
            }
        }
    }

    private fun goNext() {
        val data = boostingData()
        onComplete(MenuItems.Boost, data, null)
    }

    private fun boostingData(): Any {
        val result = OptimizationProvider.getRamUsageInfo()
        result.percentsToFree =
            OptimizationProvider.getVarArgs(MenuItems.Boost)[0].toString().toInt()
        NativeProvider.boost(requireContext())
        return result
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}