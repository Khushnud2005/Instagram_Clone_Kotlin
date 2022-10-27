package uz.example.instaclone.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import uz.example.instaclone.R
import uz.example.instaclone.adapter.ViewPagerAdapter
import uz.example.instaclone.fragment.*

/**
 * Contains view pager with 5 fragments in MainActivity,
 * and pages can be controlled by BottomNavigationView
 */
class MainActivity : BaseActivity() {
    val TAG = MainActivity::class.java.simpleName

    lateinit var homeFragment: HomeFragment
    lateinit var uploadFragment: UploadFragment
    lateinit var viewPager: ViewPager
    lateinit var bottomNavigationView: BottomNavigationView
    var index = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    private fun initViews() {
        viewPager = findViewById(R.id.viewPager)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        setupViewPager(viewPager)

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    viewPager.setCurrentItem(0)
                }
                R.id.navigation_search -> {
                    viewPager.setCurrentItem(1)
                }
                R.id.navigation_upload -> {
                    viewPager.setCurrentItem(2)
                }
                R.id.navigation_favorite -> {
                    viewPager.setCurrentItem(3)
                }
                R.id.navigation_profile -> {
                    viewPager.setCurrentItem(4)
                }
            }
            true
        }
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int,positionOffset: Float,positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                index = position
                bottomNavigationView.getMenu().getItem(index).setChecked(true);
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(HomeFragment())
        adapter.addFragment(SearchFragment())
        adapter.addFragment(UploadFragment())
        adapter.addFragment(FavoriteFragment())
        adapter.addFragment(ProfileFragment())
        viewPager.adapter = adapter
    }
}