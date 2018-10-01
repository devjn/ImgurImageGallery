package com.github.devjn.imgurimagegallery.view

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.view.menu.MenuPopupHelper
import androidx.appcompat.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.*
import com.github.devjn.imgurimagegallery.BR
import com.github.devjn.imgurimagegallery.R
import com.github.devjn.imgurimagegallery.Section
import com.github.devjn.imgurimagegallery.SubSection
import com.github.devjn.imgurimagegallery.data.DataItem
import com.github.devjn.imgurimagegallery.databinding.FragmentMainBinding
import com.github.devjn.imgurimagegallery.utils.AndroidUtils
import com.github.devjn.imgurimagegallery.viewmodel.MainViewModel
import com.github.devjn.imgurimagegallery.widgets.GridSpacingItemDecoration


@SuppressLint("RestrictedApi")
class MainFragment : Fragment() {

    companion object {
        private const val ARG_SECTION = "SECTION"

        private const val LAYOUT_TYPE_LIST = 0
        private const val LAYOUT_TYPE_GRID = 1
        private const val LAYOUT_TYPE_STAGGERED = 2

        fun newInstance(section: String) = MainFragment().apply {
            arguments = Bundle().apply { putString(ARG_SECTION, section) }
        }
    }

    private val viewModel by lazy { ViewModelProviders.of(this).get(MainViewModel::class.java) }
    private lateinit var binding: FragmentMainBinding
    private lateinit var adapter: Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        viewModel.section = arguments!!.getString(ARG_SECTION, Section.HOT)
        viewModel.doRequest()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel
        binding.list.apply {
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(GridSpacingItemDecoration(AndroidUtils.dp(16), true))
        }
        adapter = Adapter(object : ClickListener {
            override fun onClick(data: DataItem) {
                startActivity(Intent(activity, DetailsActivity::class.java).apply {
                    putExtra("data", data)
                })
            }
        })
        binding.list.adapter = adapter
        viewModel.layoutType.observe(this, Observer {
            binding.list.apply {
                val spans = if (resources.configuration.orientation == ORIENTATION_PORTRAIT) 2 else 3
                layoutManager = when (it) {
                    LAYOUT_TYPE_LIST -> LinearLayoutManager(requireContext())
                    LAYOUT_TYPE_GRID -> GridLayoutManager(requireContext(), spans)
                    else -> StaggeredGridLayoutManager(spans, ORIENTATION_PORTRAIT)
                }
            }
        })
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.data.observe(this, Observer { data ->
            data?.data?.let {
                adapter.data = it
            }
        })
    }

    private val popupMenu by lazy {
        val view: View = activity!!.findViewById(R.id.action_select_layout)
        val popup = PopupMenu(requireContext(), view).apply {
            inflate(R.menu.layout_selection_popup)
            setOnMenuItemClickListener { viewModel.layoutType.value = it.order;true }
        }
        MenuPopupHelper(context!!, popup.menu as MenuBuilder, view).apply {
            setForceShowIcon(true)
        }
    }


    private
    val subSection: SubSection? by lazy {
        when (viewModel.section) {
            Section.TOP -> SubSection(R.menu.top_menu, R.array.top_array, object : SubSection.SubAction {
                override fun apply(data: String) {
                    viewModel.window = data
                }
            })
            Section.USER -> SubSection(R.menu.user_menu, R.array.sort_array, object : SubSection.SubAction {
                override fun apply(data: String) {
                    viewModel.sort = data
                }
            })
            else -> null
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.findItem(R.id.action_viral)?.isChecked = viewModel.isShowViral;
        subSection?.let {
            inflater.inflate(it.menu, menu)

            val item = menu.findItem(R.id.spinner)
            val spinner = item.actionView as Spinner

            val adapter = ArrayAdapter.createFromResource(context!!, it.arrayResId, android.R.layout.simple_spinner_item)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            spinner.adapter = adapter
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    it.subAction.apply(adapter.getItem(position)!!.toString())
                    viewModel.doRequest()
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_select_layout -> {
            popupMenu.show()
            true
        }
        R.id.action_viral -> {
            item.isChecked = viewModel.toggleViral()
            viewModel.doRequest()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }


    interface ClickListener {
        fun onClick(data: DataItem)
    }

    private inner class Adapter(var clickListener: ClickListener?) : RecyclerView.Adapter<Adapter.SimpleViewHolder>() {
        init {
            setHasStableIds(true)
        }

        var data: List<DataItem> = emptyList()
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SimpleViewHolder(
                DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                        if (viewType == LAYOUT_TYPE_GRID) R.layout.list_item_grid else R.layout.list_item_staggered,
                        parent, false))

        override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
            holder.bind(data[position])
        }

        override fun getItemViewType(position: Int) = viewModel.layoutType.value!!
        override fun getItemId(position: Int) = data[position].datetime
        override fun getItemCount() = data.size

        inner class SimpleViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

            init {
                itemView.setOnClickListener {
                    clickListener?.onClick(data[adapterPosition])
                }
            }

            fun bind(data: DataItem) {
                binding.setVariable(BR.data, data)
                binding.setLifecycleOwner(this@MainFragment)
            }
        }
    }

}
