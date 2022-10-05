package com.tama.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.tama.githubuser.databinding.UserDetailBinding


class DetailActivity : AppCompatActivity() {

    private lateinit var binding: UserDetailBinding
    private lateinit var viewModel: DetailViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = UserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.detail_name)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)
        viewModel.setdetail(username.toString())
        viewModel.getDetail().observe(this) {
            if (it != null) {
                binding.apply {
                    detailName.text = it.name
                    detailUsername.text = it.login
                    detailCompany.text = if (it.company != null) it.company else " null "
                    detailLocation.text = if (it.location != null) it.location else " null "
                    detailRepository.text = resources.getString(R.string.repository, it.publicRepo)

                    Glide.with(this@DetailActivity)
                        .load(it.avatarUrl)
                        .circleCrop()
                        .into(detailProfile)
                }
            }
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this, bundle)
        binding.apply {
            viewPager.adapter = sectionsPagerAdapter
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }
        supportActionBar?.elevation = 0f
    }
    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_1,
            R.string.tab_2
        )
        const val EXTRA_USERNAME = "extra_username"
    }
}