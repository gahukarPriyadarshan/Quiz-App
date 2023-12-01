package com.example.edunotesbnv.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.example.edunotesbnv.dataApi.APIInterface
import com.example.edunotesbnv.dataApi.QuizQuestionsList
import com.example.edunotesbnv.R
import com.example.edunotesbnv.dataApi.Quiz
import com.example.edunotesbnv.databinding.FragmentQuizBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class QuizFragment : Fragment() {

    companion object {
        var resultCount = 0
        var temp = 0
        var questionCount = 0
    }
    private var answerSelected = false
    private lateinit var quizQuestionsList: QuizQuestionsList
    private lateinit var binding: FragmentQuizBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuizBinding.inflate(inflater, container, false)

        val arguments = arguments

        val activity = activity
        if (activity is AppCompatActivity) {
            activity.supportActionBar?.title = "Quiz"
        }

        if (arguments != null && arguments.containsKey("topic_name")) {
            val topicName = arguments.getString("topic_name")

            val retrofitBuilder = Retrofit.Builder()
                .baseUrl("https://quizapi.io/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(APIInterface::class.java)

            val retrofitData = topicName?.let { retrofitBuilder.getQuiz(it) }!!

            retrofitData.enqueue(object : Callback<QuizQuestionsList> {

                override fun onResponse(
                    call: Call<QuizQuestionsList>,
                    response: Response<QuizQuestionsList>
                ) {
                    val responseBody = response.body()
                    quizQuestionsList = responseBody!!

                    updateLayoutWithQuizData(quizQuestionsList[questionCount])
                    checkAnswer()
                    binding.btNext.setOnClickListener {
                        if (!answerSelected) {
                            // Ensure that the user has selected an answer before proceeding
                            return@setOnClickListener
                        }

                        if (questionCount < quizQuestionsList.size - 2) {
                            questionCount++
                            answerSelected = false
                            updateLayoutWithQuizData(quizQuestionsList[questionCount])
                            checkAnswer()

                        } else {
                            binding.btNext.text = "Submit"
                            questionCount++
                            answerSelected = false
                            updateLayoutWithQuizData(quizQuestionsList[questionCount])
                            checkAnswer()
                            binding.btNext.setOnClickListener {
                                temp = resultCount
                                resultCount = 0
                                openQuizResultFragment(quizQuestionsList.size)

                            }
                        }
                    }
                }

                override fun onFailure(call: Call<QuizQuestionsList>, t: Throwable) {
                    binding.question.text = "NO Quiz Question to Display please press home "
                }
            })
        }

        return binding.root
    }

    private fun updateLayoutWithQuizData(quiz: Quiz) {
        binding.apply {
            var tempQuestionCount = questionCount + 1

            question.text = quiz.question
            tvAns1.text = quiz.answers.answer_a
            tvAns2.text = quiz.answers.answer_b
            tvAns3.text = quiz.answers.answer_c
            tvAns4.text = quiz.answers.answer_d

            tvQCount.text = "$tempQuestionCount / ${quizQuestionsList.size}"

//            check.text = quiz.correct_answers.answer_a_correct +
//                    quiz.correct_answers.answer_b_correct +
//                    quiz.correct_answers.answer_c_correct +
//                    quiz.correct_answers.answer_d_correct
            // Reset the card backgrounds
            resetCardViewColors()
        }
    }

    private fun checkAnswer() {
        binding.tvAns1.setOnClickListener {
            if (!answerSelected) {
                answerSelected = true
                checkAnswerAndSetColor("answer_a", binding.cardView1)
            }
        }
        binding.tvAns2.setOnClickListener {
            if (!answerSelected) {
                answerSelected = true
                checkAnswerAndSetColor("answer_b", binding.cardView2)
            }
        }
        binding.tvAns3.setOnClickListener {
            if (!answerSelected) {
                answerSelected = true
                checkAnswerAndSetColor("answer_c", binding.cardView3)
            }
        }
        binding.tvAns4.setOnClickListener {
            if (!answerSelected) {
                answerSelected = true
                checkAnswerAndSetColor("answer_d", binding.cardView4)
            }
        }
    }

    private fun checkAnswerAndSetColor(selectedAnswer: String, cardView: CardView) {
        val isCorrect = isCorrectAnswer(quizQuestionsList[questionCount], selectedAnswer)
        if (isCorrect) {
            cardView.setCardBackgroundColor(Color.parseColor("#3376dc"))
            resultCount++
        } else {
            cardView.setCardBackgroundColor(Color.RED)
        }

        disableClickListeners()
    }

    private fun disableClickListeners() {
        binding.tvAns1.setOnClickListener(null)
        binding.tvAns2.setOnClickListener(null)
        binding.tvAns3.setOnClickListener(null)
        binding.tvAns4.setOnClickListener(null)
    }


    private fun resetCardViewColors() {
        binding.cardView1.setCardBackgroundColor(
            ContextCompat.getColor(requireContext(), R.color.white)
        )
        binding.cardView2.setCardBackgroundColor(
            ContextCompat.getColor(requireContext(), R.color.white)
        )
        binding.cardView3.setCardBackgroundColor(
            ContextCompat.getColor(requireContext(), R.color.white)
        )
        binding.cardView4.setCardBackgroundColor(
            ContextCompat.getColor(requireContext(), R.color.white)
        )
    }

    private fun isCorrectAnswer(quiz: Quiz, selectedAnswer: String): Boolean {
        return when (selectedAnswer) {
            "answer_a" -> quiz.correct_answers.answer_a_correct.toBoolean()
            "answer_b" -> quiz.correct_answers.answer_b_correct.toBoolean()
            "answer_c" -> quiz.correct_answers.answer_c_correct.toBoolean()
            "answer_d" -> quiz.correct_answers.answer_d_correct.toBoolean()
            else -> false
        }
    }

    private fun openQuizResultFragment(size: Int) {
        val fragmentManager = requireActivity().supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        val quizResultFragment = QuizResultFragment()

        val bundle = Bundle()
        if (arguments != null) {
            bundle.putString("topic_name_to_display", requireArguments().getString("topic_name"))
            bundle.putInt("result_count", temp)
            bundle.putInt("length_quiz", size)
            quizResultFragment.arguments = bundle
        }

        transaction.replace(R.id.frameLayout, quizResultFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}

