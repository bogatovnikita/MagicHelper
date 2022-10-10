package ar.cleaner.first.pf.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ar.cleaner.first.pf.R
import ar.cleaner.first.pf.data.RamUsageModel
import ar.cleaner.first.pf.databinding.FragmentResultBinding
import ar.cleaner.first.pf.utils.*
import ar.cleaner.first.pf.utils.NativeProvider.boost
import com.google.gson.Gson

class ResultFragment(
    val onBack: Fragment.() -> Unit,
    val onBattery: Fragment.() -> Unit,
    val onBoost: Fragment.() -> Unit,
    val onCpu: Fragment.() -> Unit,
    val onJunk: Fragment.() -> Unit,
) : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    private lateinit var menuItems: MenuItems

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        menuItems = MenuItems.valueOf(arguments?.getString(MENU_ITEM_NAME) ?: MenuItems.boost.name)
//        binding.ivMenu.setImageResource(menuItems.headerIcon)
        binding.ivBack.setOnClickListener { onBack() }
        binding.tvTitle.text = getString(menuItems.title)
        binding.rvOptimization.apply {
//            adapter = HorizontalMenuAdapter(
//                MenuItems.values().toMutableList().apply {
//                    this.remove(menuItems)
//                },
//                requireContext()
//            )
            {
//                when (it) {
//                    MenuItems.boost -> onBoost()
//                    MenuItems.power -> onBattery()
//                    MenuItems.cooling -> onCpu()
//                    MenuItems.cleaning -> onJunk()
//                }
            }
            layoutManager = LinearLayoutManager(requireContext())
        }

//        when (menuItems) {
//            MenuItems.boost -> boostResult()
//            MenuItems.power -> powerResult()
//            MenuItems.cooling -> cpuResult()
//            MenuItems.cleaning -> junkResult()
//        }
    }

    private fun powerResult() {
        val minutesOld = requireArguments().getString(DATA_SIMPLE)!!.toInt()
        val minutesNow = NativeProvider.calculateWorkingMinutes(
            requireContext(),
            BatteryChargeReceiver.getInfo().value!!
        )
        val optimizedMinutes = minutesNow - minutesOld
        val hoursOptimized = optimizedMinutes / 60
        val minutesOptimized = optimizedMinutes % 60

        val hours = minutesNow / 60
        val minutes = minutesNow % 60
//        binding.apply {
//            tvFirst.text = getString(R.string.power_result, hoursOptimized, minutesOptimized)
//            tvSecond.text = getString(
//                R.string.time_to_work,
//                ((minutesOld.toFloat() / minutesNow.toFloat()) * 100).toInt()
//            )
//            tvThird.text = getString(R.string.working_time, hours, minutes)
//        }
    }

    private fun boostResult() {
        val data = gson.fromJson(arguments?.getString(DATA_OBJECT), RamUsageModel::class.java)
//        val currentInfo = OptimizationProvider.getRamUsageInfo()

//        val nowFreeGb = currentInfo.availableGb - data.availableGb
//        val percentsFree = data.percent - currentInfo.percent
//        binding.tvFirst.text = getString(R.string.boost_result_first, nowFreeGb)
//        binding.tvSecond.text = getString(R.string.boost_result_second, data.percentsToFree)
//        binding.tvThird.text =
//            getString(R.string.available_ram_d_d, currentInfo.availableGb, currentInfo.totalGb)
    }

    private fun cpuResult() {
        val oldTemperature = arguments?.getString(DATA_SIMPLE)!!.toInt()
        val currentTemp = BatInfoReceiver.getBatteryInfo().value ?: 0
        val cooled = oldTemperature - currentTemp
//        binding.tvFirst.text = getString(R.string.cooled_by, cooled)
//        binding.tvSecond.text = getString(R.string.temperature_now_d, currentTemp)
//        binding.tvThird.text = getString(R.string.normal_cpu)
    }

    private fun junkResult() {
        val junk = arguments?.getString(DATA_SIMPLE)!!.toInt()
//        binding.tvFirst.text = getString(R.string.cleaned_mb, junk)
        binding.tvSecond.visibility = View.GONE
        binding.tvThird.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {

        private const val MENU_ITEM_NAME = "menu_item_name"
        private const val DATA_OBJECT = "result_data_object"
        private const val DATA_SIMPLE = "result_data_simple"

        private val gson = Gson()

        fun getBundle(
            menuItems: MenuItems,
            dataObject: Any? = null,
            simpleData: String? = null
        ): Bundle {
            val bundle = Bundle()
            bundle.putString(MENU_ITEM_NAME, menuItems.name)
            bundle.putString(DATA_OBJECT, gson.toJson(dataObject))
            bundle.putString(DATA_SIMPLE, simpleData)
            return bundle
        }

    }
}