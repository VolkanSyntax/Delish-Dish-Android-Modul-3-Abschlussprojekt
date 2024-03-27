package de.syntax.androidabschluss.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import de.syntax.androidabschluss.databinding.FragmentAssistantBinding
import de.syntax.androidabschluss.viewmodel.OpenAiViewModel
import kotlinx.coroutines.launch


class AssistantFragment : Fragment() {

    //Binding
    private lateinit var binding : FragmentAssistantBinding
    //viewmodel initialisieren
    private val viewModel: OpenAiViewModel by viewModels { OpenAiViewModel.Factory }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentAssistantBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Um den Text zu Kopieren
        binding.chatView.setOnLongClickListener {
            val textToString = binding.chatView.text.toString()
            val clipboard = ContextCompat.getSystemService(it.context, ClipboardManager::class.java) as ClipboardManager
            val clip = ClipData.newPlainText("Copied Text", textToString)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(it.context, "Text copied to clipboard", Toast.LENGTH_SHORT).show()
            true
        }

        binding.sendButton.setOnClickListener {
            val inputMessage: String = binding.inputText.text.toString()
            if (inputMessage.isNotEmpty()) {
                makeRequestToChatGpt(inputMessage)
            } else {
                Toast.makeText(requireContext(), "Please enter a message.", Toast.LENGTH_SHORT).show()
            }
        }

        // Observer Lifecycle
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.apiResponse.observe(viewLifecycleOwner, Observer { response ->
                // API'den gelen cevap null değilse, cevabı UI'da göster
                response?.let {
                    binding.chatView.text = it
                }
            })
        }
    }

    private fun makeRequestToChatGpt(message: String) {
        // API'den yanıtı almak için ViewModel'deki fonksiyonu çağır
        viewModel.getApiResponse(message)
    }

}