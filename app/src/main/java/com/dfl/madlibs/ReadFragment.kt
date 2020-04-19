package com.dfl.madlibs

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_read.*


class ReadFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_read, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        storyTextView.text = arguments?.getString("story")
        storyTextView.movementMethod = ScrollingMovementMethod()
        makeAnotherButton.setOnClickListener {
            val action = ReadFragmentDirections.actionReadFragmentToStoryFragment()
            view.findNavController().navigate(action)
        }
    }
}
