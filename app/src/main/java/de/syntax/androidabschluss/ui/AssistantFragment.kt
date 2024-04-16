package de.syntax.androidabschluss.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import de.syntax.androidabschluss.data.models.Message
import de.syntax.androidabschluss.databinding.FragmentAssistantBinding
import de.syntax.androidabschluss.utils.copyToClipboard
import de.syntax.androidabschluss.utils.hideKeyBoard
import de.syntax.androidabschluss.utils.longToastShow
import de.syntax.androidabschluss.viewmodel.MainViewModel


class AssistantFragment : Fragment(){

    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var binding : FragmentAssistantBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Layout inflate edilir ve root view döndürülür.
        binding = FragmentAssistantBinding.inflate(inflater,container,false)
        return binding.root
    }


//InputText, Chatview, sendbutton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.sendButton.setOnClickListener {
            view.context.hideKeyBoard(it)

            val messageText = binding.inputText.text.toString().trim()
            if (messageText.isNotEmpty()) {
                val message = Message(content = messageText, role = "user")
                viewModel.createChatCompletion(listOf(message), "gpt-3.5-turbo")
                binding.inputText.text = null


            } else {
                view.context.longToastShow("Bitte eingabe machen vorher!")
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.chatView.visibility = if (isLoading) View.GONE else View.VISIBLE
        }

        viewModel.chatResponse.observe(viewLifecycleOwner) { response ->
            response?.let {
                binding.chatView.text = it
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            error?.let {
                binding.chatView.text = it
            }
        }
        binding.chatView.setOnLongClickListener {
            context?.copyToClipboard(binding.chatView.text, "Chat Text")
            true // Long click eventini burada sonlandırıyoruz.
        }
    }
}