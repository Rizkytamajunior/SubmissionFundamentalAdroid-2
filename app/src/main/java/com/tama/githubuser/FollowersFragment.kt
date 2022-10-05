package com.tama.githubuser

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tama.githubuser.databinding.FragmentFollowersBinding

class FollowersFragment : Fragment(R.layout.fragment_followers) {
    private var _binding : FragmentFollowersBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: UserViewModel
    private lateinit var adapter: UserAdapter
    private lateinit var username : String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        username = args?.getString(DetailActivity.EXTRA_USERNAME).toString()
        _binding = FragmentFollowersBinding.bind(view)

        adapter = UserAdapter()

        binding.apply {
            recycleviewFollowers.layoutManager = LinearLayoutManager(activity)
            recycleviewFollowers.setHasFixedSize(true)
            recycleviewFollowers.adapter = adapter
        }

        showLoading(true)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UserViewModel::class.java)

        viewModel.setFollowers(username)
        viewModel.get().observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.setList(it)
                showLoading(false)
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showLoading(state : Boolean){
            binding.progressBarFollowers.visibility = if (state) View.VISIBLE else View.GONE
    }
}