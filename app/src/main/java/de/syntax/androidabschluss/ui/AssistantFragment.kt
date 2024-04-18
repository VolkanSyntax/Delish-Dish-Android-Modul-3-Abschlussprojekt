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

class AssistantFragment : Fragment() {
    // Activity kapsamında tanımlı bir ViewModel'e erişim. Bu ViewModel, asistan işlevlerini yönetir.
    // Zugriff auf ein im Activity-Kontext definiertes ViewModel, das die Assistentenfunktionen verwaltet.
    private val viewModel: MainViewModel by activityViewModels()

    // ViewBinding ile layout'taki view'lara erişim sağlanır.
    // ViewBinding wird verwendet, um auf Views im Layout zuzugreifen.
    private lateinit var binding: FragmentAssistantBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Fragment layout'unun inflate işlemi ve binding nesnesinin başlatılması.
        // Inflation des Fragment-Layouts und Initialisierung des Binding-Objekts.
        binding = FragmentAssistantBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // "Send" butonuna tıklandığında, klavye gizlenir ve mesaj gönderilir.
        // Beim Klicken auf die "Senden"-Schaltfläche wird die Tastatur verborgen und die Nachricht gesendet.
        binding.sendButton.setOnClickListener {
            view.context.hideKeyBoard(it)  // Klavye gizlenir.
            // Die Tastatur wird verborgen.

            val messageText = binding.inputText.text.toString().trim()
            if (messageText.isNotEmpty()) {
                // Boş olmayan mesaj, ViewModel aracılığıyla işlenir.
                // Nicht-leere Nachricht wird über das ViewModel verarbeitet.
                val message = Message(content = messageText, role = "user")
                viewModel.createChatCompletion(listOf(message), "gpt-3.5-turbo")
                binding.inputText.text = null
            } else {
                // Boş mesaj durumunda uyarı gösterilir.
                // Bei leerer Nachricht wird eine Warnung angezeigt.
                view.context.longToastShow("Bitte eingabe machen vorher!")
            }
        }

        // Yükleniyor durumu gözlemlenir ve UI elemanları buna göre ayarlanır.
        // Ladezustand wird beobachtet und UI-Elemente entsprechend angepasst.
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.chatView.visibility = if (isLoading) View.GONE else View.VISIBLE
        }

        // Sohbet yanıtı gözlemlenir ve gösterilir.
        // Chat-Antwort wird beobachtet und angezeigt.
        viewModel.chatResponse.observe(viewLifecycleOwner) { response ->
            response?.let {
                binding.chatView.text = it
            }
        }

        // Hata mesajları gözlemlenir ve gösterilir.
        // Fehlermeldungen werden beobachtet und angezeigt.
        viewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            error?.let {
                binding.chatView.text = it
            }
        }

        // Chat görünümü üzerinde uzun basma ile metin panoya kopyalanır.
        // Durch langes Drücken auf die Chat-Ansicht wird der Text in die Zwischenablage kopiert.
        binding.chatView.setOnLongClickListener {
            context?.copyToClipboard(binding.chatView.text, "Chat Text")
            true // Long click eventini burada sonlandırıyoruz.
            // Das Long-Click-Ereignis wird hier beendet.
        }
    }
}
