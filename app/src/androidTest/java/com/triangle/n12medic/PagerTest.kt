package com.triangle.n12medic

import android.content.Context.MODE_PRIVATE
import androidx.activity.compose.setContent
import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.triangle.n12medic.ui.theme.N12MedicTheme
import com.triangle.n12medic.view.OnboardActivity
import org.junit.Rule
import org.junit.Test

import org.junit.Assert.*

class PagerTest {

    @get:Rule
    val pagerTestRule = createAndroidComposeRule<OnboardActivity>()

    //Изображение и текста из очереди извлекается правильно (в порядке добавления в очередь).
    @Test
    fun pagerImageTest() {
        pagerTestRule.activity.setContent {
            N12MedicTheme {
                pagerTestRule.activity.OnboradScreenContent()
            }
        }

        pagerTestRule.onNodeWithText("Анализы").assertIsDisplayed()
        pagerTestRule.onNodeWithTag("pager").performTouchInput { swipeLeft() }
        pagerTestRule.onNodeWithText("Уведомления").assertIsDisplayed()
        pagerTestRule.onNodeWithTag("pager").performTouchInput { swipeLeft() }
        pagerTestRule.onNodeWithText("Мониторинг").assertIsDisplayed()
    }

    //В случае, когда в очереди несколько картинок, устанавливается правильная надпись на кнопке.
    @Test
    fun pagerButtonTest() {
        pagerTestRule.activity.setContent {
            N12MedicTheme {
                pagerTestRule.activity.OnboradScreenContent()
            }
        }

        pagerTestRule.onNodeWithTag("pager").performTouchInput { swipeLeft() }
        pagerTestRule.onNodeWithText("Пропустить").assertIsDisplayed()

        pagerTestRule.onNodeWithTag("pager").performTouchInput { swipeLeft() }
        pagerTestRule.onNodeWithText("Завершить").assertIsDisplayed()

        pagerTestRule.onNodeWithTag("pager").performTouchInput { swipeRight() }
        pagerTestRule.onNodeWithText("Пропустить").assertIsDisplayed()
    }

    //Если очередь пустая и пользователь нажал на кнопку “Завершить”, происходит открытие экрана «Вход и регистрация/не заполнено» приложения. Если очередь не пустая – переход отсутствует.
    @Test
    fun pagerOnButtonPressedTest() {
        pagerTestRule.activity.setContent {
            N12MedicTheme {
                pagerTestRule.activity.OnboradScreenContent()
            }
        }

        pagerTestRule.onNodeWithTag("pager").performTouchInput { swipeLeft() }
        pagerTestRule.onNodeWithTag("pager").performTouchInput { swipeLeft() }

        pagerTestRule.onNodeWithText("Завершить").performClick()
        pagerTestRule.onNodeWithTag("authScreen").assertIsDisplayed()
    }

    // Наличие вызова метода сохранения флага об успешном прохождении приветствия пользователем.
    @Test
    fun pagerSaveTagTest() {
        pagerTestRule.activity.setContent {
            N12MedicTheme {
                pagerTestRule.activity.OnboradScreenContent()
            }
        }

        val isEnded = pagerTestRule.activity.getSharedPreferences("shared", MODE_PRIVATE).getBoolean("isFirstLaunch", true)
        assertEquals(isEnded, false)
    }
}