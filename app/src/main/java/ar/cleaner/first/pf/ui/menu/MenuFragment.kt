package ar.cleaner.first.pf.ui.menu

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import ar.cleaner.first.pf.R
import ar.cleaner.first.pf.databinding.FragmentMenuBinding
import ar.cleaner.first.pf.domain.models.CleanerDetails
import ar.cleaner.first.pf.domain.models.details.RamDetails
import ar.cleaner.first.pf.extensions.fragmentLifecycleScope
import ar.cleaner.first.pf.extensions.observeWhenResumed
import ar.cleaner.first.pf.models.MenuItems
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MenuViewModel by viewModels()

    private lateinit var adapter: MenuAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setColorStatusBar()
        initObserver()
        initRecyclerView()
    }

    private fun initObserver() {
        viewModel.state.observeWhenResumed(
            lifecycleScope = fragmentLifecycleScope,
            ::renderState
        )
        viewModel.state.observeWhenResumed(lifecycleScope = fragmentLifecycleScope) { state ->
            renderRecyclerView(state)
        }
    }

    private fun renderState(screenState: MenuState) {
        with(screenState) {
            ramDetails.render()
            cleanerDetails.render()
        }
    }

    private fun RamDetails?.render() {
        this ?: return
        with(binding) {
            ramProgressBar.progressPercent = usagePercents.toFloat()
            ramPercentTv.text = getString(R.string.percent_D, usagePercents)
            descriptionRamTv.text = getString(R.string._F_gb_F_gb, usedRam, totalRam)
        }
    }

    private fun CleanerDetails?.render() {
        this ?: return
        with(binding) {
            storageProgressBar.progressPercent = usedPercents.toFloat()
            storagePercentTv.text = getString(R.string.percent_D, usedPercents)
            descriptionStorageTv.text = getString(R.string._F_gb_F_gb, usedMemorySize, totalSize)
        }
    }

    private fun initRecyclerView() {
        adapter = MenuAdapter(object : MenuAdapter.Listener {
            override fun onChooseMenu(item: MenuItems) {
                Toast.makeText(requireContext(), item.title, Toast.LENGTH_SHORT).show()
            }
        })
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerView.adapter = adapter
    }

    private fun renderRecyclerView(state: MenuState) {
        val tempList: MutableList<MenuItems> = mutableListOf()
        state.menuItemList.forEach {
            tempList.add(it.value)
        }
        adapter.submitList(tempList)
    }

    private fun setColorStatusBar() {
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireActivity(), R.color.dark_blue)
        requireActivity().window.decorView.systemUiVisibility = 0
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}