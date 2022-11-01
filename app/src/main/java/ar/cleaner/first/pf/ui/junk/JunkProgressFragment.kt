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
import ar.cleaner.first.pf.ui.progress.ActionsAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class JunkProgressFragment() : Fragment(R.layout.fragment_progress) {

    private var _binding: FragmentProgressBinding? = null
    private val binding get() = _binding!!

    private var scanIsDone = false

    override fun onResume() {
        super.onResume()
//        if (scanIsDone) scanIsDone()
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
    }

//    private fun initRecyclerView(actions: List<String>) {
//        val adapter = ActionsAdapter(actions)
//        binding.recyclerView.apply {
//            layoutManager = LinearLayoutManager(requireContext())
//            this.adapter = adapter
//        }
//        val repeat = actions.size
//        lifecycleScope.launch(Dispatchers.Default) {
//            repeat(repeat) {
//                delay(TimeUnit.SECONDS.toMillis(8) / repeat)
//                withContext(Dispatchers.Main) {
//                    adapter.removeFirst()
//                }
//            }
//            scanIsDone = true
//            if (scanIsDone) scanIsDone()
//        }
//    }

//    private fun scanIsDone() {
//        lifecycleScope.launch(Dispatchers.Default) {
//            withContext(Dispatchers.Main) {
//                delay(500)
//                withContext(Dispatchers.Main) {
//                    binding.recyclerView.visibility = View.GONE
//                    binding.isDoneTv.visibility = View.VISIBLE
//                }
//                delay(1000)
//                withContext(Dispatchers.Main) { goScreenResult() }
//            }
//        }
//    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}