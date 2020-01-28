package com.qartf.fallingwords.ui

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.core.animation.doOnEnd
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.qartf.fallingwords.R
import com.qartf.fallingwords.data.Word
import com.qartf.fallingwords.databinding.FragmentGameBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class GameFragment : Fragment() {

    private val gameViewModel: GameViewModel by viewModel()
    private val wordsPerGame = 5
    private val animationDuration = 5000L
    private var timer = Timer()

    private lateinit var wordList: List<Word>
    private lateinit var wordIterator: Iterator<Word>
    private lateinit var currentWord: Word

    private var scoreCounter: Int = 0
    private var wordCounter: Int = 0
    private var timeCounter: Int = 0
    private var animation: ObjectAnimator? = null

    internal lateinit var binding: FragmentGameBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game, container, false)

        onYesButtonClick()
        onNoButtonClick()

        observerWordList()
        gameViewModel.getWordList()

        return binding.root
    }

    private fun setTimer() {
        timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                this@GameFragment.activity?.runOnUiThread {
                    timeCounter--
                    binding.gameTimerTextView.text =
                        (if (timeCounter > 0) timeCounter else 0).toString()
                }
            }
        }, 0, 1000)
    }

    private fun observerWordList() {
        gameViewModel.wordList.observe(viewLifecycleOwner, Observer {
            wordList = it
            newGame()
        })
    }

    private fun getNewWordIterator(wordList: List<Word>): Iterator<Word> {
        return wordList.shuffled().iterator()
    }

    private fun newGame() {
        scoreCounter = 0
        wordCounter = 0

        wordIterator = getNewWordIterator(wordList)
        newWord()
    }

    private fun newWord() {
        animation?.removeAllListeners()
        animation?.cancel()
        timer.cancel()
        if (context == null) return
        if (wordIterator.hasNext().not()) wordIterator = getNewWordIterator(wordList)


        currentWord = wordIterator.next()
        binding.fallingWordTextView.translationY = 0f
        binding.fallingWordTextView.text = currentWord.text_spa

        if (wordCounter >= wordsPerGame) {
            MaterialAlertDialogBuilder(context)
                .setCancelable(false)
                .setTitle("Score")
                .setMessage("Good job, you've got ${scoreCounter}/${wordsPerGame} points")
                .setPositiveButton("Ok") { _, _ -> newGameDialog() }
                .create().show()
        } else {
            setTimer()
            timeCounter = (animationDuration / 1000).toInt()
            binding.gameTimerTextView.text = timeCounter.toString()
            binding.gameQuestionTextView.text = currentWord.text_eng
            startAnimation()
            setButtons(true)
            wordCounter++
        }
    }

    private fun newGameDialog() {
        MaterialAlertDialogBuilder(context)
            .setCancelable(false)
            .setTitle("New Game")
            .setMessage("Do you want to play again?")
            .setPositiveButton("Yes") { _, _ -> newGame() }
            .setNegativeButton("No") { _, _ -> activity?.onBackPressed() }
            .create().show()
    }

    private fun startAnimation() {
        val animation = ObjectAnimator.ofFloat(
            binding.fallingWordTextView
            , "translationY"
            , binding.root.height.toFloat()
        )
        this.animation = animation
        animation.interpolator = LinearInterpolator()
        animation.duration = animationDuration
        animation.start()
        animation.doOnEnd { newWord() }
    }

    private fun onNoButtonClick() {
        binding.noButton.setOnClickListener {
            setButtons(false)
            newWord()
        }
    }

    private fun onYesButtonClick() {
        binding.yesButton.setOnClickListener {
            setButtons(false)
            scoreCounter++
            binding.gameScoreTextView.text = "Score: $scoreCounter"
            newWord()
        }
    }

    private fun setButtons(isEnabled: Boolean) {
        binding.yesButton.isEnabled = isEnabled
        binding.noButton.isEnabled = isEnabled
    }

}