package de.syntax.androidabschluss.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import de.syntax.androidabschluss.data.models.Message
import de.syntax.androidabschluss.databinding.FragmentAssistantBinding
import de.syntax.androidabschluss.utils.hideKeyBoard
import de.syntax.androidabschluss.utils.longToastShow
import de.syntax.androidabschluss.viewmodel.ChatViewModel


class AssistantFragment : Fragment(){
    private lateinit var binding : FragmentAssistantBinding

    // ViewModel
    private val viewModel: ChatViewModel by lazy {
        ViewModelProvider(this).get(ChatViewModel::class.java)
    }


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
            view.context.hideKeyBoard(it) // Versteckt die Tastatur

            val messageText = binding.inputText.text.toString().trim()
            if (messageText.isNotEmpty()) {
                val message = Message(content = messageText, role = "user")
                viewModel.createChatCompletion(listOf(message), "gpt-3.5-turbo") // Modell entsprechend anpassen 3.5 ist billiger
                binding.inputText.text = null


            } else {
                view.context.longToastShow("Bitte eingabe machen vorher!") // auch im utils für cleaneren code
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
    }






}
/**class AssistantFragment : Fragment() {
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
**/