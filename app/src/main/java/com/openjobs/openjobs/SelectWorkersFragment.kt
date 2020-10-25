package com.openjobs.openjobs

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.select_workers_fragment.*

class SelectWorkersFragment : Fragment() , View.OnClickListener {

    private lateinit var viewModel: CreateWorkerRequestViewModel
    private lateinit var selectWorkerListAdapter: SelectWorkerListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.select_workers_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(requireActivity()).get(CreateWorkerRequestViewModel::class.java)
        selectWorkerListAdapter = SelectWorkerListAdapter()
        workerCountList.adapter = selectWorkerListAdapter
        viewModel.getWorkerOptions()
        viewModel.workerOptionsList.observe(viewLifecycleOwner, Observer { it ->
            selectWorkerListAdapter.setList(it)
            selectWorkerListAdapter.notifyDataSetChanged()
        })
        submitButton.setOnClickListener(this)
    }



    override fun onClick(v: View?) {
        if(v?.id == submitButton.id ){
            var isDataValid = true

            if(isDataValid){
                viewModel.selections  = selectWorkerListAdapter.selectionCounts
                val action = SelectWorkersFragmentDirections.actionSelectWorkersFragmentToAddDetailsFragment()
                findNavController().navigate(action)
            }else{
                Toast.makeText(requireContext(), R.string.request_workers_form_invalid_data, Toast.LENGTH_LONG).show()
            }
        }
    }

}