package de.syntax.androidabschluss.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
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
    // ViewBinding ile UI bileşenlerine erişim sağlanır.
    private lateinit var binding : FragmentAssistantBinding

    // ViewModel, API isteklerini yönetmek için kullanılır.
    private val viewModel: OpenAiViewModel by viewModels { OpenAiViewModel.Factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Layout inflate edilir ve root view döndürülür.
        binding = FragmentAssistantBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Chat view'ın üzerine uzun basıldığında metni kopyalama işlemi.
        binding.chatView.setOnLongClickListener {
            val textToString = binding.chatView.text.toString()
            val clipboard = ContextCompat.getSystemService(it.context, ClipboardManager::class.java) as ClipboardManager
            val clip = ClipData.newPlainText("Copied Text", textToString)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(it.context, "Text copied to clipboard", Toast.LENGTH_SHORT).show()
            true // Event'in handle edildiğini belirtir.
        }

        // "Send" butonuna tıklanınca mesaj gönderme işlemi.
        binding.sendButton.setOnClickListener {
            val inputMessage: String = binding.inputText.text.toString()
            if (inputMessage.isNotEmpty()) {
                makeRequestToChatGpt(inputMessage) // API isteği yapılır.
                hideKeyboard()
            } else {
                Toast.makeText(requireContext(), "Please enter your recipe or cocktail recipe request", Toast.LENGTH_SHORT).show()
            }
        }

        // API'den gelen yanıtları gözlemlemek için Observer.
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.apiResponse.observe(viewLifecycleOwner, Observer { response ->
                response?.let {
                    binding.chatView.text = it // Gelen yanıt UI'da gösterilir.
                }
            })
        }
    }

    // API isteği yapmak için kullanılan helper fonksiyon.
    private fun makeRequestToChatGpt(message: String) {
        viewModel.getApiResponse(message)
    }

    private fun hideKeyboard() {
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }
}
