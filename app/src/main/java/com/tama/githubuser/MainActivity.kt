package com.tama.githubuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tama.githubuser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

        private lateinit var binding : ActivityMainBinding
        private lateinit var viewModel: UserViewModel
        private lateinit var adapter: UserAdapter


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            adapter = UserAdapter()
            adapter.notifyDataSetChanged()

            adapter.setOnClickCallback(
                object : UserAdapter.OnItemClickCallback {
                override fun onItemClicked(data: User) {
                    intent = Intent(this@MainActivity, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_USERNAME, data.login)
                    startActivity(intent)
                    }

                }
            )

            binding.apply {
                rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
                rvUser.setHasFixedSize(true)
                rvUser.adapter = adapter

                btnSearch.setOnQueryTextListener(object :  SearchView.OnQueryTextListener {

                    override fun onQueryTextChange(newText: String): Boolean {
                        return false
                    }

                    override fun onQueryTextSubmit(query: String): Boolean {
                        search()
                        return false
                    }

                })

                btnSearch.setOnKeyListener { v, keyCode, event ->
                    if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                        search()
                        return@setOnKeyListener true
                    }
                    return@setOnKeyListener false
                }
            }

            viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UserViewModel::class.java)
            viewModel.get().observe(this, {
                if (it!=null){
                    adapter.setList(it)
                    showLoading(false)
                }
            })
        }

        private fun search(){
            binding.apply {
                val input = btnSearch.query.toString()
                if (input.isEmpty()) return
                showLoading(true)
                viewModel.setSearch(input)
            }
        }
        private fun showLoading(state : Boolean){
            if(state){
                binding.bars.visibility = View.VISIBLE
            } else {
                binding.bars.visibility = View.GONE
            }
        }
    }