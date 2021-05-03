package com.hellu.netflixcollection

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressBack
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.google.android.material.tabs.TabLayout
import com.hellu.netflixcollection.ui.main.MainActivity
import com.hellu.netflixcollection.utils.DataDummy
import com.hellu.netflixcollection.utils.EspressoIdlingResource
import org.hamcrest.CoreMatchers
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Test

class MainActivityTest {

    private val dummyMovies = DataDummy.generateDummyMovies()
    private val dummyTv = DataDummy.generateDummyTVShows()

    @Before
    fun setUp() {
        ActivityScenario.launch(MainActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun loadFavoriteMovies() {
        onView(withId(R.id.rvMovies)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.favoriteButton)).perform(click())
        onView(isRoot()).perform(pressBack())
        onView(withId(R.id.navFavorites)).perform(click())
        onView(withId(R.id.rvFavoriteMovie)).check(matches(isDisplayed()))
        onView(withId(R.id.rvFavoriteMovie)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.titleMovieDesc)).check(matches(isDisplayed()))
        onView(withId(R.id.detailImage)).check(matches(isDisplayed()))
        onView(withId(R.id.tvValuePopular)).check(matches(isDisplayed()))
        onView(withId(R.id.tvValueRating)).check(matches(isDisplayed()))
        onView(withId(R.id.textOverview)).check(matches(isDisplayed()))
        onView(withId(R.id.releaseDateDesc)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_back)).perform(pressBack())
    }

    @Test
    fun loadFavoriteTVShows() {
        onView(withId(R.id.navTVShows)).perform(click())
        onView(withId(R.id.rvTVShow)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.favoriteButton)).perform(click())
        onView(isRoot()).perform(pressBack())
        onView(withId(R.id.navFavorites)).perform(click())
        onView(withId(R.id.tab)).perform(selectTabPosition(1))
        onView(withId(R.id.rvFavoriteTVShow)).check(matches(isDisplayed()))
        onView(withId(R.id.rvFavoriteTVShow)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                clickChildViewWithId(R.id.itemMovie)
            )
        )
        onView(withId(R.id.titleMovieDesc)).check(matches(isDisplayed()))
        onView(withId(R.id.detailImage)).check(matches(isDisplayed()))
        onView(withId(R.id.tvValuePopular)).check(matches(isDisplayed()))
        onView(withId(R.id.tvValueRating)).check(matches(isDisplayed()))
        onView(withId(R.id.textOverview)).check(matches(isDisplayed()))
        onView(withId(R.id.releaseDateDesc)).check(matches(isDisplayed()))
        onView(withId(R.id.favoriteButton)).perform(click())
        onView(isRoot()).perform(pressBack())
    }

    @Test
    fun loadMovies() {
        onView(withId(R.id.navMovies)).perform(click())
        onView(withId(R.id.rvMovies)).check(matches(isDisplayed()))
        onView(withId(R.id.rvMovies)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyMovies.size)
        )
    }

    @Test
    fun loadMoviesDetail() {
        onView(withId(R.id.navMovies)).perform(click())
        onView(withId(R.id.rvMovies)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.titleMovieDesc)).check(matches(isDisplayed()))
        onView(withId(R.id.detailImage)).check(matches(isDisplayed()))
        onView(withId(R.id.tvValuePopular)).check(matches(isDisplayed()))
        onView(withId(R.id.tvValueRating)).check(matches(isDisplayed()))
        onView(withId(R.id.textOverview)).check(matches(isDisplayed()))
        onView(withId(R.id.releaseDateDesc)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_back)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_back)).perform(pressBack())
    }

    @Test
    fun loadTVShow() {
        onView(withId(R.id.navTVShows)).perform(click())
        onView(withId(R.id.rvTVShow)).check(matches(isDisplayed()))
        onView(withId(R.id.rvTVShow)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyTv.size)
        )
    }

    @Test
    fun loadTVShowsDetail() {
        onView(withId(R.id.navTVShows)).perform(click())
        onView(withId(R.id.rvTVShow)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.titleMovieDesc)).check(matches(isDisplayed()))
        onView(withId(R.id.detailImage)).check(matches(isDisplayed()))
        onView(withId(R.id.tvValuePopular)).check(matches(isDisplayed()))
        onView(withId(R.id.tvValueRating)).check(matches(isDisplayed()))
        onView(withId(R.id.textOverview)).check(matches(isDisplayed()))
        onView(withId(R.id.releaseDateDesc)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_back)).perform(pressBack())
    }



    private fun selectTabPosition(tabIndex: Int): ViewAction {
        return object : ViewAction {
            override fun getDescription() = "with tab at index $tabIndex"

            override fun getConstraints() =
                CoreMatchers.allOf(isDisplayed(), isAssignableFrom(TabLayout::class.java))

            override fun perform(uiController: UiController, view: View) {
                val tabLayout = view as TabLayout
                val tabAtIndex: TabLayout.Tab = tabLayout.getTabAt(tabIndex)
                    ?: throw PerformException.Builder()
                        .withCause(Throwable("No tab at index $tabIndex"))
                        .build()

                tabAtIndex.select()
            }
        }
    }

    private fun clickChildViewWithId(id: Int): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View>? {
                return null
            }

            override fun getDescription(): String {
                return "Click on a child view with specified id."
            }

            override fun perform(uiController: UiController, view: View) {
                val v = view.findViewById<View>(id)
                v.performClick()
            }
        }
    }


}