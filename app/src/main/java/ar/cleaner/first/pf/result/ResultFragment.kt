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
        menuItems = MenuItems.valueOf(arguments?.getString(MENU_ITEM_NAME) ?: MenuItems.Boost.name)
        with(binding) {
            arrowBackIv.setOnClickListener { onBack() }
            titleTv.text = getString(menuItems.title)
            recyclerView.apply {
                adapter = HorizontalMenuAdapter(MenuItems.values().toMutableList().apply {
                    this.remove(menuItems)
                }) {
                    when (it) {
                        MenuItems.Boost -> onBoost()
                        MenuItems.BatteryPower -> onBattery()
                        MenuItems.CoolingCpu -> onCpu()
                        MenuItems.CleaningJunk -> onJunk()
                    }
                }
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
        when (menuItems) {
            MenuItems.Boost -> boostResult()
            MenuItems.BatteryPower -> batteryPowerResult()
            MenuItems.CoolingCpu -> coolingCpuResult()
            MenuItems.CleaningJunk -> cleaningJunkResult()
        }
    }

    private fun boostResult() {
        val data = gson.fromJson(arguments?.getString(DATA_OBJECT), RamUsageModel::class.java)
        val currentInfo = OptimizationProvider.getRamUsageInfo()
        val nowFreeGb: Double = currentInfo.availableGb - data.availableGb
        val percentsFree = data.percent - currentInfo.percent

        with(binding) {
            firstDescriptionTv.text = getString(R.string.released_2f_gb, nowFreeGb)

            secondDescriptionTv.text =
                getString(R.string.now_the_device_is_accelerated_by_d, percentsFree)

            thirdDescriptionTv.text =
                getString(
                    R.string.available_memory_2f_gb_2f_gb,
                    currentInfo.availableGb,
                    currentInfo.totalGb
                )
        }
    }

    private fun batteryPowerResult() {
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

        with(binding) {
            firstDescriptionTv.text =
                getString(R.string.battery_power_optimized, hoursOptimized, minutesOptimized)

            secondDescriptionTv.text = getString(
                R.string.working_time_increased_by_d,
                ((minutesOld.toFloat() / minutesNow.toFloat()) * 100).toInt()
            )

            thirdDescriptionTv.text =
                getString(R.string.remaining_charging_time_d_h_d_min, hours, minutes)
        }
    }

    private fun cleaningJunkResult() {
        val junk = arguments?.getString(DATA_SIMPLE)!!.toInt()
        val infoStorage = OptimizationProvider.getMemoryStorage()

        with(binding) {
            firstDescriptionTv.text = getString(R.string.released_d_gb, junk)

            secondDescriptionTv.text =
                getString(R.string.now_the_devices_memory_is_d_free, infoStorage.percent)

            thirdDescriptionTv.text = getString(
                R.string.available_memory_2f_gb_2f_gb, infoStorage.occupiedStorageMemory,
                infoStorage.totalStorageMemory
            )
        }
    }

    private fun coolingCpuResult() {
        val oldTemperature = arguments?.getString(DATA_SIMPLE)!!.toInt()
        val currentTemp = BatInfoReceiver.getBatteryInfo().value ?: 0
        val cooled = oldTemperature - currentTemp
        with(binding) {
            firstDescriptionTv.text = getString(R.string.cooled_d, cooled)
            secondDescriptionTv.text =
                getString(R.string.the_normal_temperature_of_the_processor_is_25_30)
            thirdDescriptionTv.text = getString(R.string.processor_temperature_d, currentTemp)
        }
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