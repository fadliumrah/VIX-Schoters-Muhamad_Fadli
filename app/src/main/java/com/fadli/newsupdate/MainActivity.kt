package com.fadli.newsupdate

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.fadli.newsupdate.databinding.ActivityMainBinding
import com.fadli.newsupdate.features.aboutme.AboutMeFragment
import com.fadli.newsupdate.features.bookmarks.BookmarksFragment
import com.fadli.newsupdate.features.breakingnews.NewsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    /**
     * Bottom Navigation Code
     * */
    private lateinit var newsFragment: NewsFragment
    private lateinit var searchNewsFragment: AboutMeFragment
    private lateinit var bookmarksFragment: BookmarksFragment

    private val fragments: Array<Fragment>
        get() = arrayOf(newsFragment, searchNewsFragment, bookmarksFragment)

    private var selectedIndex = 0
    private val selectedFragment get() = fragments[selectedIndex]

    /**
     * Method to change the fragments
     * */
    private fun selectFragment(selectedFragment: Fragment) {
        var transaction = supportFragmentManager.beginTransaction()
        fragments.forEachIndexed { index, fragment ->
            if (selectedFragment == fragment) {
                transaction = transaction.attach(fragment)
                selectedIndex = index
            } else {
                transaction = transaction.detach(fragment)
            }
        }
        transaction.commit()
        // Setting title of the current fragment at the top of the Activity
        title = when (selectedFragment) {
            is NewsFragment -> getString(R.string.title_breaking_news)
            is AboutMeFragment -> getString(R.string.title_search_news)
            is BookmarksFragment -> getString(R.string.title_bookmarks)
            else -> ""
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) { // Not configuration change
            newsFragment = NewsFragment()
            searchNewsFragment = AboutMeFragment()
            bookmarksFragment = BookmarksFragment()

            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, newsFragment, TAG_BREAKING_NEWS_FRAGMENT)
                .add(R.id.fragment_container, searchNewsFragment, TAG_SEARCH_NEWS_FRAGMENT)
                .add(R.id.fragment_container, bookmarksFragment, TAG_BOOKMARKS_FRAGMENT)
                .commit()
        } else { // Configuration change happens
            newsFragment =
                supportFragmentManager.findFragmentByTag(TAG_BREAKING_NEWS_FRAGMENT) as NewsFragment
            searchNewsFragment =
                supportFragmentManager.findFragmentByTag(TAG_SEARCH_NEWS_FRAGMENT) as AboutMeFragment
            bookmarksFragment =
                supportFragmentManager.findFragmentByTag(TAG_BOOKMARKS_FRAGMENT) as BookmarksFragment

            selectedIndex = savedInstanceState.getInt(KEY_SELECTED_INDEX, 0)
        }
        selectFragment(selectedFragment)

        /**
         * Setting Listener for Bottom Navigation Change by the user.
         * */
        binding.bottomNav.setOnItemSelectedListener { item ->
            val fragment = when (item.itemId) {
                R.id.nav_breaking -> newsFragment
                R.id.nav_about_me -> searchNewsFragment
                R.id.nav_bookmark -> bookmarksFragment
                else -> throw IllegalArgumentException("Unexpected itemId")
            }
            if (selectedFragment === fragment) { // three equals means -> comparing references
                fragment.onPrimaryNavigationFragmentChanged(true)
            } else {
                selectFragment(fragment)
            }
            true
        }
    }

    interface OnBottomNavigationFragmentReselectedListener {
        fun onBottomNavigationFragmentReselected()

    }

    override fun onBackPressed() {
        if (selectedIndex != 0) {
            binding.bottomNav.selectedItemId = R.id.nav_breaking
        } else {
            super.onBackPressed()
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putInt(KEY_SELECTED_INDEX, selectedIndex)
    }
}

private const val TAG_BREAKING_NEWS_FRAGMENT = "TAG_BREAKING_NEWS_FRAGMENT"
private const val TAG_SEARCH_NEWS_FRAGMENT = "TAG_SEARCH_NEWS_FRAGMENT"
private const val TAG_BOOKMARKS_FRAGMENT = "TAG_BOOKMARKS_FRAGMENT"
private const val KEY_SELECTED_INDEX = "KEY_SELECTED_INDEX"